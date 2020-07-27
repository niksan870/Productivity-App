package com.example.polls.service;

import com.example.polls.dto.DashboardLoaderDTO;
import com.example.polls.dto.TimeRequest;
import com.example.polls.dto.goal.GoalRequest;
import com.example.polls.dto.goal.GoalResponse;
import com.example.polls.dto.user.UserProfileDTO;
import com.example.polls.exception.BadRequestException;
import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.Goal;
import com.example.polls.model.Pomodoro;
import com.example.polls.model.PomodoroMusic;
import com.example.polls.model.User;
import com.example.polls.payload.Assemblers.GoalResourceAssembler;
import com.example.polls.repository.GoalsRepository;
import com.example.polls.repository.PomodoroMusicRepository;
import com.example.polls.repository.PomodoroRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.util.ModelMapper;
import com.example.polls.util.ObjectMapperUtils;
import com.example.polls.util.TimeHandler;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GoalsService {
    @Autowired
    private GoalsRepository goalsRepository;

    @Autowired
    private PomodoroRepository pomodoroRepository;

    @Autowired
    private PomodoroMusicRepository pomodoroMusicRepository;

    @Autowired
    private UserPrincipal userPrincipal;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepository userProfileRepository;
    
    @Autowired
    private GoalResourceAssembler goalResourceAssembler;

    public GoalRequest logTime(UUID id, TimeRequest time) {
        Goal updateGoal = goalsRepository.findOneById(id);

        String addToTimeDone = updateGoal.getTimeDone();
        String formattedTime = new TimeHandler().formattedTime(time);
        if (addToTimeDone.isEmpty() || addToTimeDone == null) {
            updateGoal.setTimeDone(formattedTime);
            Goal goalToBeMapped = goalsRepository.save(updateGoal);
            GoalRequest goalRequest = new GoalRequest();

            String[] hoursAndMinutes = goalToBeMapped.getDailyTimePerDay().split(":");

            goalRequest.setDescription(goalToBeMapped.getDescription());
            goalRequest.setHours(hoursAndMinutes[0]);
            goalRequest.setMinutes(hoursAndMinutes[1]);
            goalRequest.setTitle(goalToBeMapped.getTitle());
            goalRequest.setDeadlineSetter(goalToBeMapped.getDeadlineSetter());
            goalRequest.setStringifiedJsonData(goalToBeMapped.getJsonData().toString());

            return goalRequest;
        } else {
            try {
                String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                TimeHandler time1 = TimeHandler.parse(formattedTime);
                TimeHandler time2 = TimeHandler.parse(addToTimeDone);
                String summedTime = time1.add(time2).toString();

                JSONArray jsonArray = updateGoal.getJsonData().getJSONArray("dataGraph");
                JSONArray newJSONArray = new JSONArray();
                JSONObject json = new JSONObject();

                for (int i = 0, size = jsonArray.length(); i < size; i++) {
                    JSONObject objectInArray = jsonArray.getJSONObject(i);

                    String[] elementNames = JSONObject.getNames(objectInArray);

                    JSONObject item = new JSONObject();
                    String x = "";
                    String y = "";
                    boolean foundDate = false;
                    for (String elementName : elementNames) {
                        String value = objectInArray.getString(elementName);
                        if (elementName.equals("x")) {
                            if (currentDate.equals(value)) {
                                foundDate = true;
                            }
                            x = value;
                        }
                        if (elementName.equals("y")) {
                            if (foundDate == true) {
                                y = String.valueOf(Long.valueOf(time.getTime()).longValue() + Long.valueOf(value).longValue());
                                foundDate = false;
                            } else {
                                y = value;
                            }
                        }
                    }
                    item.put("x", x);
                    item.put("y", y);
                    newJSONArray.put(item);
                }

                json.put("dataGraph", newJSONArray);
                updateGoal.setJsonData(json);
                updateGoal.setTimeDone(summedTime);
//                updateGoal.setUpdatedAt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Goal goalToBeMapped = goalsRepository.save(updateGoal);
        GoalRequest goalRequest = new GoalRequest();
        String[] hoursAndMinutes = goalToBeMapped.getDailyTimePerDay().split(":");

        goalRequest.setDescription(goalToBeMapped.getDescription());
        goalRequest.setHours(hoursAndMinutes[0]);
        goalRequest.setMinutes(hoursAndMinutes[1]);
        goalRequest.setTitle(goalToBeMapped.getTitle());
        goalRequest.setDeadlineSetter(goalToBeMapped.getDeadlineSetter());
        goalRequest.setStringifiedJsonData(goalToBeMapped.getJsonData().toString());

        return goalRequest;
    }


    public DashboardLoaderDTO getCurrentUserGoalList(long userId) {
        User user = userProfileRepository.getOne(userId);
        List<Goal> goals = goalsRepository.findAllWhereUserID(userPrincipal.getCurrentUserPrincipal().getId());
        List<Pomodoro> pomodoros = pomodoroRepository.findAllWhereUserID(userPrincipal.getCurrentUserPrincipal().getId());
        List<PomodoroMusic> pomodoroMusics = pomodoroMusicRepository.findAllWhereUserID(userPrincipal.getCurrentUserPrincipal().getId());


        UserProfileDTO userProfileDTO = ObjectMapperUtils.map(user, UserProfileDTO.class);
        List<GoalResponse> goalResponses = ObjectMapperUtils.mapAll(goals, GoalResponse.class);
        DashboardLoaderDTO dashboardLoaderDTO = new DashboardLoaderDTO(userProfileDTO, goalResponses, pomodoros, pomodoroMusics);
        return dashboardLoaderDTO;
    }

    public Page<GoalResponse> getPage(int pageNo, int pageSize, String filterParams) {
        validatePageNumberAndSize(pageNo, pageSize);

        JSONObject json = new JSONObject(filterParams);
        String q = json.has("q") ? json.getString("q") : "";
        boolean all = json.has("all") ? json.getBoolean("all") : false;

        Set<Goal> goals = null;

        if (!all) {
            goals = goalsRepository.getUserAttendeesByUserId(userPrincipal.getCurrentUserPrincipal().getId());
        } else {
//            goals = goalsRepository.findWithFilter(userPrincipal.getCurrentUserPrincipal().getId(), q, pageable);
        }
        Map<Long, User> creatorMap = getGoalCreatorMap(goals);

        List<GoalResponse> goalResponses = goals.stream().map(goal -> {
            try {
                return ModelMapper.mapGoalToGoalResponse(goal,
                        creatorMap.get(goal.getCreatedBy()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        return new PageImpl<>(goalResponses);
    }


    public Page<GoalResponse> getGoalsFromProfile(Long id) {
        Set<Goal> goals;
        if (userPrincipal.getCurrentUserPrincipal().getId() == id) {
            goals = goalsRepository.getGoalsFromMyProfile(id);
        } else {
            goals = goalsRepository.getGoalsFromProfile(id);
        }
        List<GoalResponse> goalResponses = ObjectMapperUtils.mapAll(goals, GoalResponse.class);

        return new PageImpl<>(goalResponses);
    }

    public void sendRequest(UUID id) {
        Goal goal = goalsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Goal", "id", id));
        User userProfile = userPrincipal.getCurrentUserPrincipal();

        Set<User> updateList = goal.getAttendees();

        if (!updateList.contains(userProfile)) {
            updateList.add(userProfile);
            goal.setAttendees(updateList);
        }

        goalsRepository.save(goal);
    }

    public GoalRequest getOne(UUID id) {
        Goal goal = goalsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Goal", "id", id));


        String[] hoursAndMinutes = goal.getDailyTimePerDay().split(":");

        GoalRequest goalRequest = new GoalRequest();
        goalRequest.setDeadlineSetter(goal.getDeadlineSetter());
        goalRequest.setDescription(goal.getDescription());
        goalRequest.setTitle(goal.getTitle());
        goalRequest.setHours(hoursAndMinutes[0]);
        goalRequest.setMinutes(hoursAndMinutes[1]);
        goalRequest.setStringifiedJsonData(goal.getJsonData().toString());

        return goalRequest;
    }

    public Goal create(GoalRequest goalRequest) throws NullPointerException {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        DateTime start = DateTime.parse(currentDate);
        DateTime end = DateTime.parse(goalRequest.getDeadlineSetter());
        List<DateTime> between = TimeHandler.getDateRange(start, end);

        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();

        for (DateTime d : between) {
            JSONObject item = new JSONObject();
            item.put("x", d.toString("yyyy-MM-dd"));
            item.put("y", "0");
            array.put(item);
        }

        json.put("dataGraph", array);

        Set<User> list = new HashSet<>();
        list.add(userPrincipal.getCurrentUserPrincipal());

        Goal goal = new Goal();

        goal.setAttendees(list);
        goal.setDailyTimePerDay(goalRequest.getHours() + ":" + goalRequest.getMinutes());
        goal.setJsonData(json);
        goal.setTimeDone("0");
        goal.setTimeDoneForTheDay("0");
        goal.setPrivate(goalRequest.isPrivate());
        goal.setDeadlineSetter(goalRequest.getDeadlineSetter());
        goal.setTitle(goalRequest.getTitle());
        goal.setDescription(goalRequest.getDescription());


        return goalsRepository.save(goal);
    }

    public Goal update(GoalRequest goalRequest,
                       UUID id) {
        Goal goal = goalsRepository.findOneById(id);

        goal.setTitle(goalRequest.getTitle());
        goal.setDescription(goalRequest.getDescription());
        goal.setPrivate(goalRequest.isPrivate());
        goal.setDailyTimePerDay(goalRequest.getHours() + ":" + goalRequest.getMinutes());
        goal.setDeadlineSetter(goalRequest.getDeadlineSetter());

        return goalsRepository.save(goal);
    }

    public HttpEntity delete(UUID id) {
        Goal goal = goalsRepository.findOneById(id);
        goalsRepository.delete(goal);
        return new ResponseEntity("Ops, you are not authenticated", HttpStatus.UNAUTHORIZED);
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }
    }

    Map<Long, User> getGoalCreatorMap(Set<Goal> goals) {
        List<Long> creatorIds = goals.stream()
                .map(Goal::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        List<User> creators = userRepository.findByIdIn(creatorIds);

        Map<Long, User> creatorMap = creators.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return creatorMap;
    }
}

package com.example.polls.service;

import com.example.polls.dto.DashboardLoaderDTO;
import com.example.polls.dto.TimeRequest;
import com.example.polls.dto.goal.GoalRequest;
import com.example.polls.dto.goal.GoalResponse;
import com.example.polls.dto.user.UserProfileDTO;
import com.example.polls.exception.BadRequestException;
import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.*;
import com.example.polls.payload.Assemblers.GoalResourceAssembler;
import com.example.polls.repository.*;
import com.example.polls.util.ModelMapper;
import com.example.polls.util.ObjectMapperUtils;
import com.example.polls.util.TimeHandler;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    private GoalChartRepository goalChartRepository;

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

    public GoalResponse logTime(UUID id, TimeRequest time) {
        GoalChart updateGoal = goalChartRepository.findByGoalChartByGoalId(id);

        String addToTimeDone = updateGoal.getTimeDone();
        String formattedTime = new TimeHandler().formattedTime(time);

        if (addToTimeDone.isEmpty() || addToTimeDone == null) {
            updateGoal.setTimeDone(formattedTime);
            GoalChart goalToBeMapped = goalChartRepository.save(updateGoal);

            return ObjectMapperUtils.map(goalToBeMapped, GoalResponse.class);
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
                    String date = "";
                    String expectedV = "";
                    boolean foundDate = false;
                    for (String elementName : elementNames) {
                        String value = objectInArray.getString(elementName);
                        if (elementName.equals("x")) {
                            if (currentDate.equals(value)) {
                                foundDate = true;
                            }
                            date = value;
                        }
                        if (elementName.equals("y")) {
                            if (foundDate == true) {
                                expectedV = String.valueOf(Long.valueOf(time.getTime()).longValue() + Long.valueOf(value).longValue());
                                foundDate = false;
                            } else {
                                expectedV = value;
                            }
                        }
                    }
                    item.put("date", date);
                    item.put("y", expectedV);
                    newJSONArray.put(item);
                }

                json.put("dataGraph", newJSONArray);
                updateGoal.setJsonData(json);
                updateGoal.setTimeDone(summedTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        GoalChart goalToBeMapped = goalChartRepository.save(updateGoal);

        return ObjectMapperUtils.map(goalToBeMapped, GoalResponse.class);
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

        Set<Goal> goals;

        if (!all) {
            goals = goalsRepository.getCurrentUserSubGoalsByUserId(userPrincipal.getCurrentUserPrincipal().getId(), q);
        } else {
            goals = goalsRepository.getUserSubGoalsByUserId(q);
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

    public GoalResponse getOne(UUID id) {
        Goal goal = goalsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Goal", "id", id));

        return ObjectMapperUtils.map(goal, GoalResponse.class);
    }

    public GoalResponse create(GoalRequest goalRequest) throws NullPointerException {
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

        Goal updatedGoal = goalsRepository.save(goal);

        GoalChart goalChart = new GoalChart();
        goalChart.setTimeDone(updatedGoal.getTimeDone());
        goalChart.setJsonData(updatedGoal.getJsonData());
        goalChart.setTimeDoneForTheDay(updatedGoal.getTimeDoneForTheDay());
        goalChart.setGoal(updatedGoal);

        GoalChart goalChart1 = goalChartRepository.save(goalChart);

        return ObjectMapperUtils.map(updatedGoal, GoalResponse.class);
    }

    public GoalResponse update(GoalRequest goalRequest,
                       UUID id) {
        Goal goal = goalsRepository.findOneById(id);

        goal.setTitle(goalRequest.getTitle());
        goal.setDescription(goalRequest.getDescription());
        goal.setPrivate(goalRequest.isPrivate());
        goal.setDailyTimePerDay(goalRequest.getHours() + ":" + goalRequest.getMinutes());
        goal.setDeadlineSetter(goalRequest.getDeadlineSetter());

        Goal updatedGoal = goalsRepository.save(goal);

        return ObjectMapperUtils.map(updatedGoal, GoalResponse.class);
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

    private Map<Long, User> getGoalCreatorMap(Set<Goal> goals) {
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

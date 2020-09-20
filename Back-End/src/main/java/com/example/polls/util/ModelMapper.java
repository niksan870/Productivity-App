package com.example.polls.util;

import com.example.polls.dto.goal.GoalResponse;
import com.example.polls.model.Goal;
import com.example.polls.model.Poll;
import com.example.polls.model.User;
import com.example.polls.payload.ChoiceResponse;
import com.example.polls.payload.PollResponse;
import com.example.polls.payload.UserSummary;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModelMapper {

    public static PollResponse mapPollToPollResponse(Poll poll, Map<Long, Long> choiceVotesMap, User creator,
                                                     Long userVote) throws UnsupportedEncodingException {
        PollResponse pollResponse = new PollResponse();
        pollResponse.setId(poll.getId());
        pollResponse.setQuestion(poll.getQuestion());
        pollResponse.setCreationDateTime(poll.getCreatedAt());
        pollResponse.setExpirationDateTime(poll.getExpirationDateTime());
        Instant now = Instant.now();
        pollResponse.setExpired(poll.getExpirationDateTime().isBefore(now));

        List<ChoiceResponse> choiceResponses = poll.getChoices().stream().map(choice -> {
            ChoiceResponse choiceResponse = new ChoiceResponse();
            choiceResponse.setId(choice.getId());
            choiceResponse.setText(choice.getText());

            if (choiceVotesMap.containsKey(choice.getId())) {
                choiceResponse.setVoteCount(choiceVotesMap.get(choice.getId()));
            } else {
                choiceResponse.setVoteCount(0);
            }
            return choiceResponse;
        }).collect(Collectors.toList());

        pollResponse.setChoices(choiceResponses);
        UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getName(),
                creator.getPicture());
        pollResponse.setCreatedBy(creatorSummary);

        if (userVote != null) {
            pollResponse.setSelectedChoice(userVote);
        }

        long totalVotes = pollResponse.getChoices().stream().mapToLong(ChoiceResponse::getVoteCount).sum();
        pollResponse.setTotalVotes(totalVotes);

        return pollResponse;
    }

    public static GoalResponse mapGoalToGoalResponse(Goal goal, User creator) throws UnsupportedEncodingException {
        GoalResponse goalResponse = new GoalResponse();

        String[] hoursAndMinutes = goal.getDailyTimePerDay().split(":");
        goalResponse.setDeadlineSetter(goal.getDeadlineSetter());
        goalResponse.setDescription(goal.getDescription());
        goalResponse.setTitle(goal.getTitle());
        goalResponse.setHours(hoursAndMinutes[0]);
        goalResponse.setMinutes(hoursAndMinutes[1]);
        goalResponse.setStringifiedJsonData("");
        goalResponse.setId(goal.getId());

        UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getName(),
                creator.getPicture());
        goalResponse.setCreatedBy(creatorSummary);

        return goalResponse;
    }


    public static GoalResponse mapGoalToGoalResponse(Goal goal) {
        GoalResponse goalResponse = new GoalResponse();

        String[] hoursAndMinutes = goal.getDailyTimePerDay().split(":");
        goalResponse.setDeadlineSetter(goal.getDeadlineSetter());
        goalResponse.setDescription(goal.getDescription());
        goalResponse.setTitle(goal.getTitle());
        goalResponse.setHours(hoursAndMinutes[0]);
        goalResponse.setMinutes(hoursAndMinutes[1]);
        goalResponse.setStringifiedJsonData("");
        goalResponse.setId(goal.getId());


        return goalResponse;
    }

}

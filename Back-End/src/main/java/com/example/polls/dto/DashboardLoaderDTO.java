package com.example.polls.dto;

import com.example.polls.dto.goal.GoalResponse;
import com.example.polls.dto.user.UserProfileDTO;
import com.example.polls.model.Pomodoro;
import com.example.polls.model.PomodoroMusic;

import java.util.List;

public class DashboardLoaderDTO {
    private UserProfileDTO userProfile;
    private List<GoalResponse> goals;
    private List<Pomodoro> pomodoros;
    private List<PomodoroMusic> pomodoroMusic;

    public DashboardLoaderDTO(UserProfileDTO userProfileDTO, List<GoalResponse> goalResponseList,
                              List<Pomodoro> pomodoros, List<PomodoroMusic> pomodoroMusics) {
        this.userProfile = userProfileDTO;
        this.goals = goalResponseList;
        this.pomodoros = pomodoros;
        this.pomodoroMusic = pomodoroMusics;
    }

    public UserProfileDTO getUserProfile() {
        System.out.println();
        return userProfile;
    }


    public void setUserProfile(UserProfileDTO userProfile) {
        this.userProfile = userProfile;
    }

    public List<GoalResponse> getGoals() {
        return goals;
    }

    public void setGoals(List<GoalResponse> goals) {
        this.goals = goals;
    }

    public List<Pomodoro> getPomodoros() {
        return pomodoros;
    }

    public void setPomodoros(List<Pomodoro> pomodoros) {
        this.pomodoros = pomodoros;
    }

    public List<PomodoroMusic> getPomodoroMusic() {
        return pomodoroMusic;
    }

    public void setPomodoroMusic(List<PomodoroMusic> pomodoroMusic) {
        this.pomodoroMusic = pomodoroMusic;
    }
}

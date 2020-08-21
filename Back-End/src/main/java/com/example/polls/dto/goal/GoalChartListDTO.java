package com.example.polls.dto.goal;

import java.util.List;

public class GoalChartListDTO {
    List<GoalChartDTO> goalChartDTOList;

    public GoalChartListDTO(List<GoalChartDTO> goalChartDTOList) {
        this.goalChartDTOList = goalChartDTOList;
    }

    public List<GoalChartDTO> getGoalChartDTOList() {
        return goalChartDTOList;
    }

    public void setGoalChartDTOList(List<GoalChartDTO> goalChartDTOList) {
        this.goalChartDTOList = goalChartDTOList;
    }
}

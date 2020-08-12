package com.example.polls.service;

import com.example.polls.dto.goal.GoalResponse;
import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.GoalChart;
import com.example.polls.repository.GoalChartRepository;
import com.example.polls.util.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class GoalChartService {

    @Autowired
    private GoalChartRepository goalChartRepository;

    public GoalResponse getOne(UUID id) {
        GoalChart goal = goalChartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Goal Chart", "id", id));

        return ObjectMapperUtils.map(goal, GoalResponse.class);
    }
}

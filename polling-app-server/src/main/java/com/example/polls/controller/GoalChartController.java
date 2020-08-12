package com.example.polls.controller;

import com.example.polls.dto.goal.GoalResponse;
import com.example.polls.service.GoalChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/goalschart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GoalChartController {

    @Autowired
    private GoalChartService goalChartService;

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public GoalResponse getOne(@PathVariable UUID id) {
        return goalChartService.getOne(id);
    }

}

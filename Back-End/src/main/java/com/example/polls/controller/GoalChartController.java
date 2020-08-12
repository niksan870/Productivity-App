package com.example.polls.controller;

import com.example.polls.dto.TimeRequest;
import com.example.polls.dto.goal.GoalChartDTO;
import com.example.polls.dto.goal.GoalResponse;
import com.example.polls.service.GoalChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/goalsChart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GoalChartController {

    @Autowired
    private GoalChartService goalChartService;

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public GoalChartDTO getOne(@PathVariable UUID id) {
        return goalChartService.getOne(id);
    }


    @PutMapping("/logTime/{id}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public GoalChartDTO logTime(@PathVariable UUID id,
                                @RequestBody TimeRequest time) {
        return goalChartService.logTime(id, time);
    }

}

package com.example.polls.controller;

import com.example.polls.dto.DashboardLoaderDTO;
import com.example.polls.dto.TimeRequest;
import com.example.polls.dto.goal.GoalRequest;
import com.example.polls.dto.goal.GoalResponse;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.GoalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/goals")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GoalsController {
    @Autowired
    private GoalsService goalsService;

    @GetMapping("getGoalsFromProfile/{id}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public Page<GoalResponse> getGoalsFromProfile(@PathVariable Long id){
        System.out.println();
        return goalsService.getGoalsFromProfile(id);
    }

    @GetMapping("/mine")
    @PreAuthorize("hasRole('USER')")
    public DashboardLoaderDTO getCurrentUser(@CurrentUser UserPrincipal currentUser){
        return goalsService.getCurrentUserGoalList(currentUser.getId());
    }

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public Page<GoalResponse> getPage(@RequestParam int page, @RequestParam int pageSize, @RequestParam String filterParams) {
        return goalsService.getPage(page, pageSize, filterParams);
    }


    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public GoalResponse getOne(@PathVariable UUID id) {
        return goalsService.getOne(id);
    }

    @PostMapping("")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public GoalResponse create(@RequestBody GoalRequest goalRequest) throws NullPointerException{
      return goalsService.create(goalRequest);
    }

    @PostMapping("/sendRequest/{secretKey}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public void sendRequest(@PathVariable UUID secretKey) throws NullPointerException{
        goalsService.sendRequest(secretKey);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public GoalResponse update(@RequestBody GoalRequest goalRequest,
                       @PathVariable UUID id) {
        return goalsService.update(goalRequest, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity delete(@PathVariable("id") UUID id){
        return goalsService.delete(id);
    }
}

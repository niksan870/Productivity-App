package com.example.polls.dto.goal;


import com.example.polls.model.Goal;
import com.example.polls.model.User;
import com.example.polls.util.JSONObjectConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.json.JSONObject;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.UUID;

public class GoalChartDTO {
    private UUID id;

    private String jsonData;

    private float timeDone;

    private float timeDoneForTheDay;

    private float timeExpectedToBeDone;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Goal goal;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    public GoalChartDTO() {
    }

    public GoalChartDTO(User user, String jsonData, float timeDone, float timeDoneForTheDay,
                        float timeExpectedToBeDone, Goal goal) {
        this.jsonData = jsonData;
        this.timeDone = timeDone;
        this.timeDoneForTheDay = timeDoneForTheDay;
        this.timeExpectedToBeDone = timeExpectedToBeDone;
        this.goal = goal;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public float getTimeDone() {
        return timeDone;
    }

    public void setTimeDone(float timeDone) {
        this.timeDone = timeDone;
    }

    public float getTimeDoneForTheDay() {
        return timeDoneForTheDay;
    }

    public void setTimeDoneForTheDay(float timeDoneForTheDay) {
        this.timeDoneForTheDay = timeDoneForTheDay;
    }

    public float getTimeExpectedToBeDone() {
        return timeExpectedToBeDone;
    }

    public void setTimeExpectedToBeDone(float timeExpectedToBeDone) {
        this.timeExpectedToBeDone = timeExpectedToBeDone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

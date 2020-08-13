package com.example.polls.model;

import com.example.polls.model.audit.UserDateAudit;
import com.example.polls.util.JSONObjectConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.json.JSONObject;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user_goals")
public class Goal extends UserDateAudit {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String title;

    private String description;

    private String dailyTimePerDay;

    private String deadlineSetter;

    private boolean isPrivate;

    private GoalChart goalChart;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "sub_goals",
            joinColumns = { @JoinColumn(name = "goal_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> attendees = new HashSet<User>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDailyTimePerDay() {
        return dailyTimePerDay;
    }

    public void setDailyTimePerDay(String dailyTimePerDay) {
        this.dailyTimePerDay = dailyTimePerDay;
    }

    public String getDeadlineSetter() {
        return deadlineSetter;
    }

    public void setDeadlineSetter(String deadlineSetter) {
        this.deadlineSetter = deadlineSetter;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Set<User> getAttendees() {
        return attendees;
    }

    public void setAttendees(Set<User> attendees) {
        this.attendees = attendees;
    }

    public GoalChart getGoalChart() {
        return goalChart;
    }

    public void setGoalChart(GoalChart goalChart) {
        this.goalChart = goalChart;
    }
}
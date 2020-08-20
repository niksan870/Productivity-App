package com.example.polls.model;

import com.example.polls.model.audit.UserDateAudit;
import com.example.polls.util.JSONObjectConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.json.JSONObject;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class GoalChart extends UserDateAudit {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @NonNull
    @Column(columnDefinition = "TEXT")
    @Convert(converter= JSONObjectConverter.class)
    private JSONObject jsonData;

    private float timeDone;

    private float timeDoneForTheDay;

    private float timeExpectedToBeDone;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Goal goal;

    public GoalChart() {
    }

    public GoalChart(@NonNull JSONObject jsonData, float timeDone, float timeDoneForTheDay, float timeExpectedToBeDone, Goal goal) {
        this.jsonData = jsonData;
        this.timeDone = timeDone;
        this.timeDoneForTheDay = timeDoneForTheDay;
        this.timeExpectedToBeDone = timeExpectedToBeDone;
        this.goal = goal;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @NonNull
    public JSONObject getJsonData() {
        return jsonData;
    }

    public void setJsonData(@NonNull JSONObject jsonData) {
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
}

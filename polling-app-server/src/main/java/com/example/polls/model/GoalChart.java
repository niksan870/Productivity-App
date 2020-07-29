package com.example.polls.model;

import com.example.polls.model.audit.UserDateAudit;
import com.example.polls.util.JSONObjectConverter;
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


    private String timeDone;

    private String timeDoneForTheDay;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Goal goal;

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

    public String getTimeDone() {
        return timeDone;
    }

    public void setTimeDone(String timeDone) {
        this.timeDone = timeDone;
    }

    public String getTimeDoneForTheDay() {
        return timeDoneForTheDay;
    }

    public void setTimeDoneForTheDay(String timeDoneForTheDay) {
        this.timeDoneForTheDay = timeDoneForTheDay;
    }
}

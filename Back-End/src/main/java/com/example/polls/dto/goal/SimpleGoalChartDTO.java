package com.example.polls.dto.goal;

import java.util.UUID;

public class SimpleGoalChartDTO {
    private UUID id;

    private String jsonData;

    private float timeDone;

    private float timeDoneForTheDay;

    private float timeExpectedToBeDone;

    public SimpleGoalChartDTO(UUID id, String jsonData, float timeDone, float timeDoneForTheDay, float timeExpectedToBeDone) {
        this.id = id;
        this.jsonData = jsonData;
        this.timeDone = timeDone;
        this.timeDoneForTheDay = timeDoneForTheDay;
        this.timeExpectedToBeDone = timeExpectedToBeDone;
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

package com.example.polls.dto.goal;



import java.util.UUID;

public class GoalChartDTO {
    private UUID id;
    private String jsonData;

    private String timeDone;

    private String timeDoneForTheDay;

    public GoalChartDTO(UUID id, String jsonData, String timeDone, String timeDoneForTheDay) {
        this.id = id;
        this.jsonData = jsonData;
        this.timeDone = timeDone;
        this.timeDoneForTheDay = timeDoneForTheDay;
    }

    public GoalChartDTO(){

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

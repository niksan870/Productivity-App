package com.example.polls.dto.goal;

import com.example.polls.model.User;

import java.util.UUID;

public class GoalRequest {
  public static class Builder {
    private String title;
    private String description;
    private String hours;
    private String minutes;
    private String deadlineSetter;
    private String stringifiedJsonData;
    private boolean isPrivate;
    private User user;

    public Builder(
        String title, String description, String deadlineSetter, String stringifiedJsonData) {
      this.title = title;
      this.description = description;
      this.deadlineSetter = deadlineSetter;
      this.stringifiedJsonData = stringifiedJsonData;
    }

    public Builder(String title) {
      this.title = title;
    }

    public Builder withDescription(String description) {
      this.title = description;
      return this;
    }

    public Builder withHours(String hours) {
      this.title = hours;
      return this;
    }

    public Builder withMinutes(String minutes) {
      this.title = minutes;
      return this;
    }

    public Builder withDeadlineSetter(String deadlineSetter) {
      this.title = deadlineSetter;
      return this;
    }

    public Builder withStringifiedJsonData(String stringifiedJsonData) {
      this.title = stringifiedJsonData;
      return this;
    }

    public Builder withIsPrivate(String isPrivate) {
      this.title = isPrivate;
      return this;
    }

    public GoalRequest build() {
      GoalRequest goalRequest = new GoalRequest();
      goalRequest.title = this.title;
      goalRequest.description = this.description;
      goalRequest.hours = this.hours;
      goalRequest.minutes = this.minutes;
      goalRequest.deadlineSetter = this.deadlineSetter;
      goalRequest.stringifiedJsonData = this.stringifiedJsonData;
      goalRequest.isPrivate = this.isPrivate;

      return goalRequest;
    }
  }

  private String title;
  private String description;
  private String hours;
  private String minutes;
  private String deadlineSetter;
  private String stringifiedJsonData;
  private boolean isPrivate;
  private UUID user;

  public UUID getUser() {
    return user;
  }

  public void setUser(UUID user) {
    this.user = user;
  }

  public GoalRequest() {}

  public boolean isPrivate() {
    return isPrivate;
  }

  public void setPrivate(boolean aPrivate) {
    isPrivate = aPrivate;
  }

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

  public String getHours() {
    return hours;
  }

  public void setHours(String hours) {
    this.hours = hours;
  }

  public String getMinutes() {
    return minutes;
  }

  public void setMinutes(String minutes) {
    this.minutes = minutes;
  }

  public String getDeadlineSetter() {
    return deadlineSetter;
  }

  public void setDeadlineSetter(String deadlineSetter) {
    this.deadlineSetter = deadlineSetter;
  }

  public String getStringifiedJsonData() {
    return stringifiedJsonData;
  }

  public void setStringifiedJsonData(String stringifiedJsonData) {
    this.stringifiedJsonData = stringifiedJsonData;
  }
}

package com.example.polls.dto.goal;

import com.example.polls.payload.UserSummary;

import java.util.UUID;

public class GoalResponse {
  private UUID id;
  private String title;
  private String description;
  private String hours;
  private String minutes;
  private String deadlineSetter;
  private String stringifiedJsonData;
  private boolean isPrivate;
  private long userProfile;
  private UserSummary createdBy;
  private boolean editable;

  public long getUserProfile() {
    return userProfile;
  }

  public void setUserProfile(long userProfile) {
    this.userProfile = userProfile;
  }

  public GoalResponse() {}

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UserSummary getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(UserSummary createdBy) {
    this.createdBy = createdBy;
  }

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

  public boolean isEditable() {
    return editable;
  }

  public void setEditable(boolean editable) {
    this.editable = editable;
  }
}

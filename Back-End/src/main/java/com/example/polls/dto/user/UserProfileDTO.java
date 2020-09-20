package com.example.polls.dto.user;

import com.example.polls.model.Goal;
import com.example.polls.model.Pomodoro;
import com.example.polls.model.PomodoroMusic;
import com.example.polls.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileDTO {
  private Long id;
  private String name;
  private int active;
  private String phoneNumber;
  private String gender;
  private Date dateOfBirth;
  private String city;
  private String country;
  private User user;
  private List<Pomodoro> pomodoros;
  private byte[] picture = new byte[0];
  private List<Goal> goals;
  private List<Goal> sub_goals;
  private List<PomodoroMusic> pomodoroMusic;
  private boolean editProfile;
  private Date createdAt = new Date();
  private Date updatedAt = new Date();

  public boolean isEditProfile() {
    return editProfile;
  }

  public void setEditProfile(boolean editProfile) {
    this.editProfile = editProfile;
  }

  public int getActive() {
    return active;
  }

  public void setActive(int active) {
    this.active = active;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt() {
    this.updatedAt = new Date();
  }

  public List<Goal> getGoals() {
    return goals;
  }

  public void setGoals(List<Goal> goals) {
    this.goals = goals;
  }

  public List<Pomodoro> getPomodoros() {
    return pomodoros;
  }

  public void setPomodoros(List<Pomodoro> pomodoros) {
    this.pomodoros = pomodoros;
  }

  public String getPicture() throws UnsupportedEncodingException {
    return new String(picture, "UTF-8");
  }

  public void setPicture(String picture) throws UnsupportedEncodingException {
    byte[] bytes = picture.getBytes("UTF-8");
    this.picture = bytes;
  }

  public List<Goal> getSub_goals() {
    return sub_goals;
  }

  public void setSub_goals(List<Goal> sub_goals) {
    this.sub_goals = sub_goals;
  }
}

package com.example.polls.dto.user;

import com.example.polls.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

public class UserDTO {
  private UUID id;
  private User user;
  private String username;
  private String password;
  private String email;
  private int active;
  private String roles = "";
  private String permissions = "";
  private boolean isAccountNonExpired;
  private boolean isAccountNonLocked;
  private boolean isCredentialsNonExpired;
  private boolean isEnabled;
  private Date createdAt = new Date();
  private Date updatedAt = new Date();

  public UserDTO(
      String username,
      String password,
      String email,
      String roles,
      String permissions,
      boolean isAccountNonExpired,
      boolean isAccountNonLocked,
      boolean isCredentialsNonExpired,
      boolean isEnabled) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.roles = roles;
    this.permissions = permissions;
    this.active = 1;
    this.isAccountNonExpired = isAccountNonExpired;
    this.isAccountNonLocked = isAccountNonLocked;
    this.isCredentialsNonExpired = isCredentialsNonExpired;
    this.isEnabled = isEnabled;
  }

  protected UserDTO() {}

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getUsername() {
    return username;
  }

  public boolean isAccountNonExpired() {
    return false;
  }

  public boolean isAccountNonLocked() {
    return false;
  }

  public boolean isCredentialsNonExpired() {
    return false;
  }

  public boolean isEnabled() {
    return false;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getActive() {
    return active;
  }

  public void setActive(int active) {
    this.active = active;
  }

  public String getRoles() {
    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt() {
    this.updatedAt = new Date();
  }

  public List<String> getRoleList() {
    if (this.roles.length() > 0) {
      return Arrays.asList(this.roles.split(","));
    }
    return new ArrayList<>();
  }

  public List<String> getPermissionList() {
    if (this.permissions.length() > 0) {
      return Arrays.asList(this.permissions.split(","));
    }
    return new ArrayList<>();
  }
}

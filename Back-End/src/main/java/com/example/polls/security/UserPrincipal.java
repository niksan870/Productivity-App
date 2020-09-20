package com.example.polls.security;

import com.example.polls.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import static com.example.polls.security.ApplicationUserRole.*;

public class UserPrincipal implements UserDetails {
  private Long id;

  private String name;

  private String username;

  private byte[] picture;

  @JsonIgnore private String email;

  @JsonIgnore private String password;

  private Collection<? extends GrantedAuthority> authorities;

  public UserPrincipal(
      Long id,
      String picture,
      String name,
      String username,
      String email,
      String password,
      Collection<? extends GrantedAuthority> authorities)
      throws UnsupportedEncodingException {
    this.id = id;
    byte[] bytes = picture.getBytes("UTF-8");
    this.picture = bytes;
    this.name = name;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserPrincipal create(User user) throws UnsupportedEncodingException {
    //        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
    //                new SimpleGrantedAuthority(role.getName().name())
    //        ).collect(Collectors.toList());
    Set<SimpleGrantedAuthority> authorities = USER.getGrantedAuthorities();

    return new UserPrincipal(
        user.getId(),
        user.getPicture(),
        user.getName(),
        user.getUsername(),
        user.getEmail(),
        user.getPassword(),
        authorities);
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPicture() throws UnsupportedEncodingException {
    return new String(picture, "UTF-8");
  }

  public void setPicture(String picture) throws UnsupportedEncodingException {
    byte[] bytes = picture.getBytes("UTF-8");
    this.picture = bytes;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserPrincipal that = (UserPrincipal) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id);
  }
}

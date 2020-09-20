package com.example.polls.payload;

/** Created by rajeevkumarsingh on 19/08/17. */
public class JwtAuthenticationResponse {
  private String accessToken;
  private String tokenType = "Bearer";
  private String permissions;

  public JwtAuthenticationResponse(String accessToken, String permissions) {
    this.accessToken = accessToken;
    this.permissions = permissions;
  }

  public String getPermissions() {
    return permissions;
  }

  public void setPermissions(String permissions) {
    this.permissions = permissions;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }
}

package com.excilys.jwt;

public class AuthenticationRequestJWT {

  private String username;
  private String password;

  public AuthenticationRequestJWT(String username, String password) {
    super();
    this.username = username;
    this.password = password;
  }

  public AuthenticationRequestJWT() {
    super();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}

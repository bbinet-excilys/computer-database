package com.excilys.jwt;

public class AuthenticationResponseJWT {

  private final String token;

  public AuthenticationResponseJWT(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }
}

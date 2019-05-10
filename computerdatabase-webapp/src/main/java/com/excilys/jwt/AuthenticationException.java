package com.excilys.jwt;

public class AuthenticationException extends RuntimeException {
  private static final long serialVersionUID = 2839255380144629406L;

  public AuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }
}

package com.excilys.model;

import java.util.Collections;
import java.util.Set;

public class User {
  private String         username;
  private String         password;
  private Boolean        enabled;
  private Set<Authority> authorities;

  private User(UserBuilder builder) {
    username    = builder.username;
    password    = builder.password;
    enabled     = builder.enabled;
    authorities = builder.authorities;
  }

  public User(String username, String password, Boolean enabled) {
    super();
    this.username = username;
    this.password = password;
    this.enabled  = enabled;
  }

  public User() {}

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @param username
   *                 the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password
   *                 the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the enabled
   */
  public Boolean getEnabled() {
    return enabled;
  }

  /**
   * @param enabled
   *                the enabled value to set
   */
  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * @return the authorities
   */
  public Set<Authority> getAuthorities() {
    return authorities;
  }

  /**
   * @param authorities
   *                    the authorities set
   */
  public void setAuthorities(Set<Authority> authorities) {
    this.authorities = authorities;
  }

  public static UserBuilder builder() {
    return new UserBuilder();
  }

  public static final class UserBuilder {
    private String         username;
    private String         password;
    private Boolean        enabled;
    private Set<Authority> authorities = Collections.emptySet();

    private UserBuilder() {}

    public UserBuilder withUsername(String username) {
      this.username = username;
      return this;
    }

    public UserBuilder withPassword(String password) {
      this.password = password;
      return this;
    }

    public UserBuilder withEnabled(Boolean enabled) {
      this.enabled = enabled;
      return this;
    }

    public UserBuilder withAuthorities(Set<Authority> authorities) {
      this.authorities = authorities;
      return this;
    }

    public User build() {
      return new User(this);
    }
  }
}

package com.excilys.dto;

public class UserDTO {

  private String  username;
  private String  password;
  private Boolean enabled;
  private String  authorities;

  private UserDTO(Builder builder) {
    username    = builder.username;
    password    = builder.password;
    enabled     = builder.enabled;
    authorities = builder.authorities;
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

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public String getAuthorities() {
    return authorities;
  }

  public void setAuthorities(String authorities) {
    this.authorities = authorities;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String  username;
    private String  password;
    private Boolean enabled;
    private String  authorities;

    private Builder() {}

    public Builder withUsername(String username) {
      this.username = username;
      return this;
    }

    public Builder withPassword(String password) {
      this.password = password;
      return this;
    }

    public Builder withEnabled(Boolean enabled) {
      this.enabled = enabled;
      return this;
    }

    public Builder withAuthorities(String authorities) {
      this.authorities = authorities;
      return this;
    }

    public UserDTO build() {
      return new UserDTO(this);
    }
  }

}

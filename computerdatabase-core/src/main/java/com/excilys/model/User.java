package com.excilys.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {

  private static Logger LOGGER = LoggerFactory.getLogger(User.class);

  private String          username;
  private String          password;
  private Boolean         enabled;
  private List<Authority> authorities;
  private String          token;

  private User(UserBuilder builder) {
    username    = builder.username;
    password    = builder.password;
    enabled     = builder.enabled;
    authorities = builder.authorities;
    token       = builder.token;
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
  @Override
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
  @Override
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
   * @param enabled
   *                the enabled value to set
   */
  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * @param authorities
   *                    the authorities set
   */
  public void setAuthorities(Collection<Authority> authorities) {
    this.authorities = List.copyOf(authorities);
    LOGGER.warn(this.authorities.toString());
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public String toString() {
    return "Name :" + username + " Password:" + password;
  }

  public static UserBuilder builder() {
    return new UserBuilder();
  }

  public static final class UserBuilder {
    private String          username;
    private String          password;
    private Boolean         enabled;
    private List<Authority> authorities = Collections.emptyList();
    private String          token;

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

    public UserBuilder withAuthorities(List<Authority> authorities) {
      this.authorities = authorities;
      return this;
    }

    public UserBuilder withToken(String token) {
      this.token = token;
      return this;
    }

    public User build() {
      return new User(this);
    }
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
    return enabled;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    if (authorities.stream()
                   .anyMatch(authority -> authority.getAuthority()
                                                   .equals(AuthorityName.ROLE_ADMIN))) {
      grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
    return grantedAuthorities;
  }
}

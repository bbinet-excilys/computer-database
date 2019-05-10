package com.excilys.model;

public class Authority {

  private User   username;
  private String authority;

  /**
   * @return the username
   */
  public User getUsername() {
    return username;
  }

  /**
   * @param username
   *                 the username to set
   */
  public void setUsername(User username) {
    this.username = username;
  }

  /**
   * @return the authority
   */
  public String getAuthority() {
    return authority;
  }

  /**
   * @param authority
   *                  the authority to set
   */
  public void setAuthority(String authority) {
    this.authority = authority;
  }

  @Override
  public String toString() {
    return "User :" + username + " - Authority :" + authority;
  }

}

package com.excilys.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.model.User;
import com.excilys.model.User.UserBuilder;

public class UserMapper implements RowMapper<User> {

  @Override
  public User mapRow(ResultSet rs, int rowNum) throws SQLException {
    rs.absolute(rowNum + 1);
    UserBuilder userBuilder = User.builder();
    userBuilder.withUsername(rs.getString("username"));
    userBuilder.withPassword(rs.getString("password"));
    userBuilder.withEnabled(rs.getBoolean("enabled"));
    return userBuilder.build();
  }

}

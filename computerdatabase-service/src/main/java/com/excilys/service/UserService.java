package com.excilys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.excilys.model.Authority;
import com.excilys.model.User;
import com.excilys.persistence.UserDAO;

public class UserService implements AuthenticationProvider {

  private UserDAO userDAO;

  public void setUserDAO(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  public void create(User user) {
    userDAO.create(user);
  }

  public Optional<User> read(String username) {
    return userDAO.read(username);
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String         name     = authentication.getName();
    String         password = authentication.getCredentials().toString();
    Optional<User> optional = userDAO.read(authentication.getName());
    if (optional.isPresent()) {
      Set<Authority>         setAuthorities = optional.get().getAuthorities();
      List<GrantedAuthority> authorities    = new ArrayList();
      setAuthorities.stream().forEach(authority -> authorities.add(new SimpleGrantedAuthority(authority.getAuthority())));
      return new UsernamePasswordAuthenticationToken(name, password, authorities);
    }
    return null;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

}

package com.excilys.jwt;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  protected JWTAuthenticationFilter(String defaultFilterProcessesUrl) {
    super("/**");
  }

  @Override
  protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
    return true;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
    throws AuthenticationException, IOException, ServletException {
    Optional<String> oHeader = Optional.ofNullable(request.getHeader("Authorization"));
    if (oHeader.isPresent()) {
      String stringToken = oHeader.get().substring(7);
    }
    return null;
  }

}

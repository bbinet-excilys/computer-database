package com.excilys.jwt;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.excilys.model.User;
import com.excilys.service.UserService;

public class AuthenticationTokenFilterJwt extends OncePerRequestFilter {

  private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationRequestJWT.class);

  private UserService userService;
  private JWTUtils    jwtUtils;

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  public void setJwtUtils(JWTUtils jwtUtils) {
    this.jwtUtils = jwtUtils;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
    LOGGER.warn("Filter jwt");
    Optional<String> oToken    = Optional.ofNullable(request.getHeader(JWTUtils.jwtHeader));
    Optional<String> oUsername = Optional.empty();
    Optional<User>   oUser     = Optional.empty();
    Optional<String> authToken = oToken.filter(tokenString -> !tokenString.isBlank())
                                       .filter(tokenString -> tokenString.startsWith("Bearer "))
                                       .map(stringToken -> stringToken.substring(7));

    if (authToken.isPresent()) {
      LOGGER.warn("Token present" + authToken.get());
      oUsername = Optional.ofNullable(jwtUtils.getUsernameFromToken(authToken.get()));
    }

    if (oUsername.filter(usernameString -> !usernameString.isBlank()).isPresent() && SecurityContextHolder.getContext().getAuthentication() != null) {
      LOGGER.warn("username" + oUsername.get());
      oUser = userService.read(oUsername.get());
    }

    oUser.ifPresent(user -> {
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      LOGGER.warn(authToString(authentication));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    });
    filterChain.doFilter(request, response);

  }

  private String authToString(Authentication authentication) {
    StringBuilder builder = new StringBuilder();
    builder.append("Subject").append(authentication.getPrincipal()).append("\nCredentials").append(authentication.getCredentials());
    return builder.toString();
  }

}

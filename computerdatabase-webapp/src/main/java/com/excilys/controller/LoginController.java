package com.excilys.controller;

import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.jwt.AuthenticationException;
import com.excilys.jwt.AuthenticationResponseJWT;
import com.excilys.jwt.JWTUtils;
import com.excilys.model.User;
import com.excilys.service.UserService;

@RestController
public class LoginController {

  private final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

  private JWTUtils              jwtUtils;
  private UserService           userService;
  private AuthenticationManager authenticationManager;

  public LoginController(JWTUtils jwtUtils, UserService userService, AuthenticationManager authenticationManager) {
    super();
    LOGGER.warn("Creating LoginController");
    this.jwtUtils              = jwtUtils;
    this.userService           = userService;
    this.authenticationManager = authenticationManager;
  }

  @RequestMapping(value = "/auth", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//  @RequestBody AuthenticationRequestJWT authRequest,
  public ResponseEntity<?> createJWToken(@RequestParam String username,
      @RequestParam String password, HttpServletRequest request)
    throws AuthenticationException {
    LOGGER.warn("Request :" + request.getParameterMap().toString());
    authenticate(username, password);
    final User   user  = userService.read(username).get();
    final String token = jwtUtils.generateToken(user);
//    String token = "";
    return ResponseEntity.ok(new AuthenticationResponseJWT(token));
  }

  @GetMapping("/refresh")
  public ResponseEntity<?> getOrRefreshToken(HttpServletRequest request) {
    LOGGER.warn("Refreshing token");
    Optional<String> authToken = Optional.ofNullable(request.getHeader(JWTUtils.jwtHeader));
    if (authToken.isPresent()) {
      String token          = authToken.get().substring(7);
      String refreshedToken = jwtUtils.refreshToken(token);
      return ResponseEntity.ok(new AuthenticationResponseJWT(refreshedToken));
    }
    return ResponseEntity.badRequest().body(null);
  }

  @ExceptionHandler({ AuthenticationException.class })
  public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
  }

  private void authenticate(String username, String password) {
    Objects.requireNonNull(username);
    Objects.requireNonNull(password);
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
    catch (DisabledException e) {
      throw new AuthenticationException("User is disabled!", e);
    }
    catch (BadCredentialsException e) {
      throw new AuthenticationException("Bad credentials!", e);
    }
  }

}

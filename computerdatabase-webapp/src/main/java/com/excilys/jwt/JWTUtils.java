package com.excilys.jwt;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class JWTUtils {

  Long                expirationInSeconds = 43200L; // 12H
  Map<String, Object> headers;

  public JWTUtils() {
    headers = new HashMap<>();
    headers.put("alg", "HS256");
    headers.put("typ", "JWT");
  }

  public Jwt createJWT(Long userId) {
    Jwt                     token     = new Jwt("", Instant.now(), Instant.now().plusSeconds(expirationInSeconds), new HashMap<String, Object>(),
                                                new HashMap<String, Object>());
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey("");
    return null;
  }

  public boolean validateToken(String tokenString) {
    return false;
  }

}

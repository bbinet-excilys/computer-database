package com.excilys.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import com.excilys.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;

public class JWTUtils {

  public static final String jwtHeader      = "Authorization";
  private final Long         expirationTime = 1000 * 60 * 60 * 12L; // Expiration time in milliseconds
  private String             secretKey;
  private Clock              clock          = DefaultClock.INSTANCE;

  public void setSecretKey(String secretKey) {
    this.secretKey = "31764d5b0d3ed2b584051b461";
  }

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getIssuedAtDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getIssuedAt);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser()
               .setSigningKey("31764d5b0d3ed2b584051b461")
               .parseClaimsJws(token)
               .getBody();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(clock.now());
  }

  private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
    return (lastPasswordReset != null && created.before(lastPasswordReset));
  }

  private Boolean ignoreTokenExpiration(String token) {
    // here you specify tokens, for that the expiration is ignored
    return false;
  }

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap();
    return doGenerateToken(claims, userDetails.getUsername());
  }

  private String doGenerateToken(Map<String, Object> claims, String subject) {
    final Date createdDate    = clock.now();
    final Date expirationDate = calculateExpirationDate(createdDate);

    return Jwts.builder()
               .setClaims(claims)
               .setSubject(subject)
               .setIssuedAt(createdDate)
               .setExpiration(expirationDate)
               .signWith(SignatureAlgorithm.HS512, "31764d5b0d3ed2b584051b461")
               .compact();
  }

  public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
    final Date created = getIssuedAtDateFromToken(token);
    return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
        && (!isTokenExpired(token) || ignoreTokenExpiration(token));
  }

  public String refreshToken(String token) {
    final Date createdDate    = clock.now();
    final Date expirationDate = calculateExpirationDate(createdDate);

    final Claims claims = getAllClaimsFromToken(token);
    claims.setIssuedAt(createdDate);
    claims.setExpiration(expirationDate);

    return Jwts.builder()
               .setClaims(claims)
               .signWith(SignatureAlgorithm.HS512, "31764d5b0d3ed2b584051b461")
               .compact();
  }

  public Boolean validateToken(String token, User user) {
    final String username = getUsernameFromToken(token);
    final Date   created  = getIssuedAtDateFromToken(token);
    // final Date expiration = getExpirationDateFromToken(token);
    return (username.equals(user.getUsername())
        && !isTokenExpired(token));
  }

  private Date calculateExpirationDate(Date createdDate) {
    return new Date(createdDate.getTime() + expirationTime);
  }

}

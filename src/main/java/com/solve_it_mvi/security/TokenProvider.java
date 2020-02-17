package com.solve_it_mvi.security;

import com.solve_it_mvi.annotation.Property;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.solve_it_mvi.security.Constants.REMEMBERME_VALIDITY_SECONDS;

public class TokenProvider {
    private static final Logger LOGGER = Logger.getLogger(TokenProvider.class.getName());
    private static final String AUTHORITIES_KEY = "auth";

    @Inject
    @Property
    private String secretKey;

    @Inject
    @Property
    private long tokenValidityHours;

    private long tokenValidity;
    private long tokenValidityForRememberMe;

    @PostConstruct
    public void init() {
        this.tokenValidity = TimeUnit.HOURS.toMillis(tokenValidityHours);   //10 hours
        this.tokenValidityForRememberMe = TimeUnit.SECONDS.toMillis(REMEMBERME_VALIDITY_SECONDS);   //24 hours
    }

    public String createToken(String username, Set<String> authorities, Boolean rememberMe) {
        long now = (new Date()).getTime();
        long validity = rememberMe ? tokenValidityForRememberMe : tokenValidity;

        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORITIES_KEY, String.join(",", authorities))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setExpiration(new Date(now + validity))
                .compact();
    }

    public JWTCredential getCredential(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        Set<String> authorities
                = new HashSet<>(Arrays.asList(claims.get(AUTHORITIES_KEY).toString().split(",")));

        return new JWTCredential(claims.getSubject(), authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            LOGGER.log(Level.INFO, "Invalid JWT signature: {0}", e.getMessage());
            return false;
        }
    }

}

package com.solve_it_mvi.security;

import com.solve_it_mvi.dto.UserDto;
import io.jsonwebtoken.ExpiredJwtException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.authentication.mechanism.http.RememberMe;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.solve_it_mvi.security.Constants.*;

@RememberMe(
        cookieMaxAgeSeconds = REMEMBERME_VALIDITY_SECONDS,
        isRememberMeExpression = "self.isRememberMe(httpMessageContext)"
)
@RequestScoped
public class JwtAuthenticationMechanism implements HttpAuthenticationMechanism {

    private static final Logger LOGGER = Logger.getLogger(JwtAuthenticationMechanism.class.getName());

    private final IdentityStoreHandler identityStoreHandler;

    private final TokenProvider tokenProvider;

    @Inject
    public JwtAuthenticationMechanism(IdentityStoreHandler identityStoreHandler, TokenProvider tokenProvider) {
        this.identityStoreHandler = identityStoreHandler;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext context) {
        LOGGER.log(Level.INFO, "validateRequest: {0}", request.getRequestURI());
        UserDto userDto = null;

        if (request.getRequestURI().contains("/auth/login")) {
            try {
                JsonReader jsonReader = Json.createReader(new StringReader(getRequestBody(request)));
                JsonObject jsonObject = jsonReader.readObject();
                userDto = new UserDto(jsonObject.getString("username"), jsonObject.getString("password"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String token = extractToken(context);

        if (userDto != null) {
            LOGGER.log(Level.INFO, "credentials : {0}, {1}", new String[]{userDto.getUsername(), userDto.getPassword()});
            CredentialValidationResult result = identityStoreHandler.validate(new UsernamePasswordCredential(userDto.getUsername(), userDto.getPassword()));
            if (result.getStatus() == CredentialValidationResult.Status.VALID) {
                return createToken(result, context);
            }
            return context.responseUnauthorized();
        } else if (token != null) {
            return validateToken(token, context);
        } else if (context.isProtected()) {
            return context.responseUnauthorized();
        }
        return context.doNothing();
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            char[] charBuffer = new char[1024];
            int bytesRead;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
        }
        return stringBuilder.toString();
    }
    private AuthenticationStatus validateToken(String token, HttpMessageContext context) {
        try {
            if (tokenProvider.validateToken(token)) {
                JWTCredential credential = tokenProvider.getCredential(token);
                return context.notifyContainerAboutLogin(credential.getPrincipal(), credential.getAuthorities());
            }
            return context.responseUnauthorized();
        } catch (ExpiredJwtException eje) {
            LOGGER.log(Level.INFO, "Security exception for user {0} - {1}", new String[]{eje.getClaims().getSubject(), eje.getMessage()});
            return context.responseUnauthorized();
        }
    }

    private AuthenticationStatus createToken(CredentialValidationResult result, HttpMessageContext context) {
        if (!isRememberMe(context)) {
            String jwt = tokenProvider.createToken(result.getCallerPrincipal().getName(), result.getCallerGroups(), false);
            context.getResponse().setHeader(AUTHORIZATION_HEADER, BEARER + jwt);
        }
        return context.notifyContainerAboutLogin(result.getCallerPrincipal(), result.getCallerGroups());
    }

    private String extractToken(HttpMessageContext context) {
        String authorizationHeader = context.getRequest().getHeader(AUTHORIZATION_HEADER);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            return authorizationHeader.substring(BEARER.length());
        }
        return null;
    }

    public Boolean isRememberMe(HttpMessageContext context) {
        return Boolean.valueOf(context.getRequest().getParameter("rememberme"));
    }
}

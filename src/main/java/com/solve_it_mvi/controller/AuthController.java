package com.solve_it_mvi.controller;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("auth")
public class AuthController {

    @Inject
    private SecurityContext securityContext;

    @POST
    @Path("login")
    public Response login() {
        LOGGER.log(Level.INFO, "login");
        if (securityContext.getCallerPrincipal() != null) {
            JsonObject result = Json.createObjectBuilder()
                    .add("user", securityContext.getCallerPrincipal().getName())
                    .build();
            return Response.ok(result).build();
        }
        return Response.status(UNAUTHORIZED).build();
    }
}

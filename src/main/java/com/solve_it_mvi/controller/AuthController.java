package com.solve_it_mvi.controller;

import com.solve_it_mvi.model.Role;
import com.solve_it_mvi.model.User;
import com.solve_it_mvi.repository.UserRepository;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("auth")
public class AuthController {

    @Inject
    private SecurityContext securityContext;

    @Inject
    private UserRepository userRepository;

    @POST
    @Path("login")
    public Response login() {
        if (securityContext.getCallerPrincipal() != null) {
            JsonObject result = Json.createObjectBuilder()
                    .add("user", securityContext.getCallerPrincipal().getName())
                    .build();
            return Response.ok(result).build();
        }
        return Response.status(UNAUTHORIZED).build();
    }

    @GET
    @Path("me")
    public Response getByEmail() {
        if (securityContext.getCallerPrincipal() != null) {
            String name = securityContext.getCallerPrincipal().getName();
            List<User> users = userRepository.findByUsername(name);
            Set<String> roles =  users.get(0).getRoles().stream().map(Role::getDisplayName).collect(Collectors.toSet());
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            roles.forEach(jsonArrayBuilder::add);
            JsonArray roleJsonArray = jsonArrayBuilder.build();

            JsonObject result = Json.createObjectBuilder()
                    .add("user", name)
                    .add("roles", roleJsonArray)
                    .build();
            return Response.ok(result).build();
        }
        return Response.status(UNAUTHORIZED).build();
    }

    @POST
    @Path("me/logout")
    public Response logout() {
        return Response.status(OK).build();
    }

}

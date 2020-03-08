package com.solve_it_mvi.controller;

import com.solve_it_mvi.model.User;
import com.solve_it_mvi.repository.UserRepository;
import com.solve_it_mvi.security.Constants;
import jdk.nashorn.internal.objects.annotations.Getter;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("users")
@ApplicationScoped
public class UserController {

    private static final Logger LOG = Logger.getLogger(UserController.class.getName());

    @Inject
    private UserRepository userRepository;

    @POST
    @Path("/signup")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(JsonObject userCreationDto) {
        LOG.log(Level.FINE, "REST request to save Person : {0}", userCreationDto);
        User user = userRepository.create(userCreationDto);
        return Response.ok(user).build();
    }

    @GET
    @Path("/{username}")
    @RolesAllowed({Constants.CUSTOMER, Constants.ADMIN, Constants.DEVELOPER, Constants.OPERATIONS})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByEmail(@PathParam("username") String username) {
        List<User> foundUsers = userRepository.findByUsername(username);
        if (foundUsers.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(foundUsers.get(0)).build();
    }

    @PUT
    @Path("/{username}")
    @RolesAllowed({Constants.CUSTOMER, Constants.ADMIN, Constants.DEVELOPER, Constants.OPERATIONS})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("username") String email, JsonObject userUpdateDto) {
        User updatedUser = userRepository.update(email, userUpdateDto);
        if (updatedUser == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(updatedUser).build();
    }

    @DELETE
    @RolesAllowed({Constants.ADMIN})
    @Path("/{username}")
    public Response delete(@PathParam("username") String username) {
        userRepository.delete(username);
        return Response.ok().build();
    }
}

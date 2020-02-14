package com.solve_it_mvi.controller;

import com.solve_it_mvi.model.User;
import com.solve_it_mvi.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/api/users")
@ApplicationScoped
public class SignupController {

    private static final Logger LOG = Logger.getLogger(SignupController.class.getName());

    @Inject
    private UserRepository userRepository;

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(JsonObject jsonObject) {
        LOG.log(Level.FINE, "REST request to save Person : {0}", jsonObject);
        User user = userRepository.create(jsonObject);
        return Response.ok(user).build();
    }
}

package com.solve_it_mvi.controller;

import com.solve_it_mvi.model.Role;
import com.solve_it_mvi.repository.RoleRepository;
import com.solve_it_mvi.security.Constants;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("roles")
@ApplicationScoped
public class RoleController {

    @Inject
    private RoleRepository roleRepository;

    @POST
    @RolesAllowed(Constants.ADMIN)
    public Response createRole(Role role) {
        roleRepository.create(role);
        return Response.ok(role).build();
    }

}

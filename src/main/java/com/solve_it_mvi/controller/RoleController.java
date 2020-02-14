package com.solve_it_mvi.controller;

import com.solve_it_mvi.model.Role;
import com.solve_it_mvi.repository.RoleRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/api/roles")
@ApplicationScoped
public class RoleController {
    @Inject
    private RoleRepository roleRepository;

    @POST
    public Response createRole(Role role) {
        roleRepository.create(role);
        return Response.ok(role).build();
    }
}

package com.solve_it_mvi.controller;

import com.solve_it_mvi.model.Organisation;
import com.solve_it_mvi.repository.ProjectRepository;
import com.solve_it_mvi.security.Constants;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("projects")
@ApplicationScoped
public class ProjectController {

    @Inject
    private ProjectRepository projectRepository;

    @POST
    @RolesAllowed({Constants.ADMIN,Constants.DEVELOPER})
    public Response createProject(JsonObject projectObjectDto) {
        projectRepository.create(projectObjectDto);
        return Response.ok(projectObjectDto).build();
    }

}

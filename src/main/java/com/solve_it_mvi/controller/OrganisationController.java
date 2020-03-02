package com.solve_it_mvi.controller;

import com.solve_it_mvi.model.Organisation;
import com.solve_it_mvi.repository.OrganisationRepository;
import com.solve_it_mvi.security.Constants;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("organisations")
@ApplicationScoped
public class OrganisationController {

    private final OrganisationRepository organisationRepository;

    @Inject
    public OrganisationController(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    @POST
    @RolesAllowed(Constants.ADMIN)
    public Response createOrganisation(Organisation organisation) {
        organisationRepository.create(organisation);
        return Response.ok(organisation).build();
    }

    @PUT
    @Path("/{orgId}")
    @RolesAllowed(Constants.ADMIN)
    public Response updateOrganisation(@PathParam("orgId") long orgId, JsonObject organisationUpdateDto) {
        Organisation updatedOrg = organisationRepository.update(orgId, organisationUpdateDto);
        return updatedOrg == null ? Response.status(Response.Status.BAD_REQUEST).build() : Response.ok(updatedOrg).build();
    }

}

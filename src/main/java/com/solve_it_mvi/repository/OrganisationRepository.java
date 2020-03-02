package com.solve_it_mvi.repository;
import com.solve_it_mvi.model.Organisation;
import com.solve_it_mvi.model.Role;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.REQUIRED;

@ApplicationScoped
public class OrganisationRepository extends AbstractRepository {
    @Transactional(REQUIRED)
    public void create(Organisation organisation) {

        em.persist(organisation);
    }

    public Organisation update(long orgId, JsonObject organisationUpdateDto) {

        return null;
    }
}

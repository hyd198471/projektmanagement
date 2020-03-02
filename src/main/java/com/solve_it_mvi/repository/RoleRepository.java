package com.solve_it_mvi.repository;

import com.solve_it_mvi.model.Role;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;

@ApplicationScoped
public class RoleRepository extends AbstractRepository{

    @Transactional(REQUIRED)
    public void create(Role role) {
        em.persist(role);
    }

    public Role find(Long id) {
        return em.find(Role.class,
                id);
    }

    public List<Role> findRolesByIds(List<Long> ids) {
        Query query = em.createQuery("select r from Role r where r.id in :ids");
        query.setParameter("ids", ids);
        return query.getResultList();
    }

}

package com.solve_it_mvi.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractRepository {
    @PersistenceContext(unitName = "NewPersistenceUnit")
    protected EntityManager em;
}

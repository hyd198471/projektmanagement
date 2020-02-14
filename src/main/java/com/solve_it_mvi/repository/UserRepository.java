package com.solve_it_mvi.repository;

import com.solve_it_mvi.model.Role;
import com.solve_it_mvi.model.User;
import com.solve_it_mvi.util.PBKDF2Hasher;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;

@ApplicationScoped
public class UserRepository {
    @PersistenceContext(unitName = "NewPersistenceUnit")
    private EntityManager em;

    @Inject
    private RoleRepository roleRepository;

    @Transactional(REQUIRED)
    public User create(JsonObject jsonObject) {
        PBKDF2Hasher hasher = new PBKDF2Hasher();

        User user = new User();
        user.setUserName(jsonObject.getString("username"));
        user.setEncodedPassword(hasher.hash(jsonObject.getString("password").toCharArray()));
        user.setLastLoginDate(Instant.now().toEpochMilli());

        JsonArray roleIdsJson = jsonObject.getJsonArray("roleIds");
        List<Long> roleIds = new ArrayList<>();
        for (int i = 0; i < roleIdsJson.size(); i++) {
            roleIds.add((long) roleIdsJson.getInt(i));
        }

        List<Role> roles = roleRepository.findRolesByIds(roleIds);
        user.setRoles(roles);
        em.persist(user);
        return user;
    }

    public User findByUsername(String username) {
        Query query = em.createQuery("select u from user_ u where u.userName = :username");
        query.setParameter("username",username);
        return (User)query.getSingleResult();
    }

}
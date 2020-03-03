package com.solve_it_mvi.repository;

import com.solve_it_mvi.model.Role;
import com.solve_it_mvi.model.User;
import com.solve_it_mvi.util.PBKDF2Hasher;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;

@ApplicationScoped
public class UserRepository extends AbstractRepository{

    @Inject
    private RoleRepository roleRepository;

    @Transactional(REQUIRED)
    public User create(JsonObject jsonObject) {
        User user = new User();
        createOrUpdateUser(jsonObject, user);
        em.persist(user);
        return user;
    }

    @Transactional(REQUIRED)
    public User update(String username, JsonObject userUpdateDto) {
        List<User> existedUsers = findByUsername(username);
        if (!existedUsers.isEmpty()) {
            User existedUser = existedUsers.get(0);
            createOrUpdateUser(userUpdateDto, existedUser);
            return existedUser;
        }
        return null;
    }

    @Transactional(REQUIRED)
    public User updateLastLogin(User user, long lastLoginTime) {
        user.setLastLoginDate(lastLoginTime);
        em.merge(user);
        return user;
    }

    @Transactional(REQUIRED)
    public void delete(String username) {
        List<User> existedUsers = findByUsername(username);
        if(!existedUsers.isEmpty()) {
            em.remove(existedUsers.get(0));
        }
    }

    public List<User> findByUsername(String username) {
        Query query = em.createQuery("select u from user_ u where u.userName = :username");
        query.setParameter("username", username);
        return query.getResultList();
    }

    private void createOrUpdateUser(JsonObject jsonObject, User user) {
        user.setUserName(jsonObject.getString("username"));
        PBKDF2Hasher hasher = new PBKDF2Hasher();
        user.setEncodedPassword(hasher.hash(jsonObject.getString("password").toCharArray()));
        user.setLastLoginDate(Instant.now().toEpochMilli());

        JsonArray roleIdsJson = jsonObject.getJsonArray("roleIds");
        List<Long> roleIds = new ArrayList<>();
        for (int i = 0; i < roleIdsJson.size(); i++) {
            roleIds.add((long) roleIdsJson.getInt(i));
        }

        List<Role> roles = roleRepository.findRolesByIds(roleIds);
        user.setRoles(roles);
    }
}

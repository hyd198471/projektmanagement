package com.solve_it_mvi.security;

import com.solve_it_mvi.model.Role;
import com.solve_it_mvi.model.User;
import com.solve_it_mvi.repository.RoleRepository;
import com.solve_it_mvi.repository.UserRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.singleton;
import static javax.security.enterprise.identitystore.IdentityStore.ValidationType.PROVIDE_GROUPS;

@RequestScoped
public class AuthorizationIdentityStore implements IdentityStore {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    @Inject
    public AuthorizationIdentityStore(RoleRepository roleRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        String username = validationResult.getCallerPrincipal().getName();
        List<User> users = userRepository.findByUsername(username);

        if (!users.isEmpty()) {
            List<Role> roles = users.get(0).getRoles();
            return roles.stream().map(Role::getDisplayName).collect(Collectors.toSet());
        } else {
            return new HashSet<>();
        }
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return singleton(PROVIDE_GROUPS);
    }

}

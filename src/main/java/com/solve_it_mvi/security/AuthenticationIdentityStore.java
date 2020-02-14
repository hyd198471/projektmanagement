package com.solve_it_mvi.security;

import com.solve_it_mvi.model.User;
import com.solve_it_mvi.repository.UserRepository;
import com.solve_it_mvi.util.PBKDF2Hasher;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

import java.util.List;
import java.util.Set;

import static java.util.Collections.singleton;
import static javax.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;
import static javax.security.enterprise.identitystore.IdentityStore.ValidationType.VALIDATE;

@RequestScoped
public class AuthenticationIdentityStore implements IdentityStore {

    @Inject
    private UserRepository userRepository;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        CredentialValidationResult result;
        if(credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential usernamePassword = (UsernamePasswordCredential) credential;
            List<User> users = userRepository.findByUsername(usernamePassword.getCaller());

            if(!users.isEmpty()) {
                String storedPassword = users.get(0).getEncodedPassword();
                char[] passwordToCheck = usernamePassword.getPasswordAsString().toCharArray();
                if(storedPassword!= null && PBKDF2Hasher.checkPassword(passwordToCheck, storedPassword)) {
                    result = new CredentialValidationResult(usernamePassword.getCaller());
                } else {
                    result = CredentialValidationResult.INVALID_RESULT;
                }
            } else {
                result = NOT_VALIDATED_RESULT;
            }
        } else {
            result = NOT_VALIDATED_RESULT;
        }
        return result;
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return singleton(VALIDATE);
    }
}

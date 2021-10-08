package ru.bugprod.webtable.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.bugprod.webtable.model.exception.AuthenticationException;
import ru.bugprod.webtable.usecase.UserUsecase;

@Component
@RequiredArgsConstructor
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final UserUsecase usercase;


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        Object token = authentication.getCredentials();
        return usercase
                .findByToken(String.valueOf(token))
                .map((customer) -> new User(
                        customer.getUsername(),
                        customer.getPassword(),
                        true,
                        true,
                        true,
                        true,
                        AuthorityUtils.createAuthorityList("USER")))
                .orElseThrow(() -> {
                    throw new AuthenticationException("Not authenticated");
                });

    }
}

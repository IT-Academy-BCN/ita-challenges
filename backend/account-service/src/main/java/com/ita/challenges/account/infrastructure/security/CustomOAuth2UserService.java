package com.ita.challenges.account.infrastructure.security;

import com.ita.challenges.account.domain.model.Role;
import com.ita.challenges.account.domain.model.User;
import com.ita.challenges.account.domain.port.out.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String login = oAuth2User.getAttribute("login");

        User appUser = userRepository.findByUsername(login)
                .orElseGet(() -> {
                    User newUser = new User(login, Role.STUDENT);
                    return userRepository.save(newUser);
                });

        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + appUser.userRole().name())
        );

        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "login");
    }
}
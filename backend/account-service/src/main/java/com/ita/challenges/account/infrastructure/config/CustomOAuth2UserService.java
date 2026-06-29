package com.ita.challenges.account.infrastructure.config;

import com.ita.challenges.account.domain.model.Role;
import com.ita.challenges.account.domain.model.User;
import com.ita.challenges.account.domain.port.out.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        List<SimpleGrantedAuthority> authorities = processOAuth2User(oAuth2User);

        return new DefaultOAuth2User(
                authorities,
                oAuth2User.getAttributes(),
                "login"
        );
    }

    public List<SimpleGrantedAuthority> processOAuth2User(OAuth2User oAuth2User) {
        String login = oAuth2User.getAttribute("login");

        if (login == null) {
            throw new OAuth2AuthenticationException("Login not found in GitHub");
        }

        Optional<User> existingUser = userRepository.findByUsername(login);
        User user;

        if (existingUser.isEmpty()) {
            user = new User(login, Role.STUDENT);
            userRepository.save(user);

        } else {
            user = existingUser.get();
        }

        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.userRole().name())
        );
    }
}
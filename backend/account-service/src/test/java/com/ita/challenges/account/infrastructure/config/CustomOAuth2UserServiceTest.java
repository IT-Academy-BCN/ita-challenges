package com.ita.challenges.account.infrastructure.config;

import com.ita.challenges.account.domain.model.Role;
import com.ita.challenges.account.domain.model.User;
import com.ita.challenges.account.domain.port.out.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomOAuth2UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OAuth2User oAuth2User;

    @InjectMocks
    private CustomOAuth2UserService customOAuth2UserService;

    @Test
    @DisplayName("Should save a new user with STUDENT role if it does not exist in DB")
    void shouldSaveNewUserWhenUserDoesNotExist() {
        String mockUsername = "Spook242";

        when(oAuth2User.getAttribute("login")).thenReturn(mockUsername);
        when(userRepository.findByUsername(mockUsername)).thenReturn(Optional.empty());

        customOAuth2UserService.processOAuth2User(oAuth2User);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository, times(1)).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        assertThat(savedUser.userName()).isEqualTo(mockUsername);
        assertThat(savedUser.userRole()).isEqualTo(Role.STUDENT);
    }

    @Test
    @DisplayName("Should not save anything if the user already exists in DB")
    void shouldNotSaveWhenUserAlreadyExists() {
        String mockUsername = "ExistingUser";

        when(oAuth2User.getAttribute("login")).thenReturn(mockUsername);

        User existingUser = new User(mockUsername, Role.MENTOR);

        when(userRepository.findByUsername(mockUsername)).thenReturn(Optional.of(existingUser));

        customOAuth2UserService.processOAuth2User(oAuth2User);

        verify(userRepository, never()).save(any(User.class));
    }
}
package com.ita.challenges.account.controller.auth;

import com.ita.challenges.account.dto.auth.AuthUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthControllerTest {
    @Test
    void ShouldReturnUsernameFromAuthentication(){
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test-username");

        AuthController controller = new AuthController();

        AuthUserDto result = controller.me(authentication);

        assertNotNull(result);
        assertEquals("test-username", result.username());
    }
}

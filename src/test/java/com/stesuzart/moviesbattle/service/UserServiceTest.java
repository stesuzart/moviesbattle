package com.stesuzart.moviesbattle.service;

import com.stesuzart.moviesbattle.commons.GenerateEntityUtils;
import com.stesuzart.moviesbattle.entity.Game;
import com.stesuzart.moviesbattle.entity.PairMoviesId;
import com.stesuzart.moviesbattle.entity.Round;
import com.stesuzart.moviesbattle.entity.RoundStatus;
import com.stesuzart.moviesbattle.entity.User;
import com.stesuzart.moviesbattle.repository.RoundRepository;
import com.stesuzart.moviesbattle.repository.UserRepository;
import com.stesuzart.moviesbattle.service.impl.RoundServiceImpl;
import com.stesuzart.moviesbattle.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.context.SecurityContextImpl;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;


    @Test
    public void whenCallsGetUser_shouldReturnUser_withSuccess() {
        User user = GenerateEntityUtils.generateUser();
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(authentication.getName()).thenReturn(user.getName());

        Mockito.when(userRepository.findUserByUsername(any())).thenReturn(user);

        User userResponse = userService.getUser();

        Assertions.assertNotNull(userResponse);
        Assertions.assertNotNull(userResponse.getId());
        Assertions.assertEquals(user.getName(), userResponse.getName());

        Mockito.verify(userRepository, times(1)).findUserByUsername(any());
    }
}

package com.guptaaashutosh.fleet_management.service;

import com.guptaaashutosh.fleet_management.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;  // assumed the implementation of UserRepository

    @InjectMocks
    private UserService userService;


    @Test
    public  void givenUserId_whenFindUserById_returnUserDetailOfTheId(){
        BDDMockito.given(userRepository.findById(1L)).willReturn(Optional.of(userDetails));

        User gotUserDetails = userService.getUserById(userDetails.Id);

        Assertions.assertThat(gotUserDetails).isNotNull();
    }
}

package com.mavericksstube.maverickshub.services;

import com.mavericksstube.maverickshub.dtos.requests.CreateUserRequest;
import com.mavericksstube.maverickshub.dtos.response.CreateUserResponse;
import com.mavericksstube.maverickshub.exceptions.UserNotFoundException;
import com.mavericksstube.maverickshub.models.User;
import com.mavericksstube.maverickshub.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
//@AllArgsConstructor              this or use the constructor injection
public class MavericksHubUserService implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public MavericksHubUserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CreateUserResponse register(CreateUserRequest createUserRequest) {
        User user = modelMapper.map(createUserRequest, User.class);
        User savedUser = userRepository.save(user);
        CreateUserResponse response = modelMapper.map(savedUser, CreateUserResponse.class);
        response.setMessage("user registered successfully");
        return response;
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(()->
                new UserNotFoundException(String.format("User with id %d not found", id)));
    }
}
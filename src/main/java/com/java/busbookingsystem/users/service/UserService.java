package com.java.busbookingsystem.users.service;


import com.java.busbookingsystem.auth.helper.UserInfoDetails;
import com.java.busbookingsystem.users.mapper.UserMapper;
import com.java.busbookingsystem.users.model.User;
import com.java.busbookingsystem.users.model.UserDTO;
import com.java.busbookingsystem.users.repository.UserRepository;
import com.java.busbookingsystem.utils.exception.GlobalExceptionWrapper;
import io.micrometer.common.lang.NonNull;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.java.busbookingsystem.utils.constants.InstructorConstants.*;


@Service
public class UserService implements IUserService{

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> findAll() {
        List<User> instructor = this.userRepository.findAll();
        return UserMapper.toDTO(instructor);
    }

    @Override
    public UserDTO save(@NonNull User user) {
        //Check if same user already exists during signup
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new GlobalExceptionWrapper.BadRequestException(DUPLICATE_EMAIL_MESSAGE);
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles("USER");
        User savedUser = this.userRepository.save(user);

        return UserMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO findById(long id) {
        User user = this.userRepository.findById(id).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE, USER.toLowerCase())));
        return UserMapper.toDTO(user);
    }

    public UserDTO fetchSelfInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserInfoDetails) authentication.getPrincipal()).getUsername();
        return  findByEmail(email).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE, USER.toLowerCase())));
    }

    public Optional<UserDTO> findByEmail(@NonNull String emailId) {
        Optional<User> instructor = this.userRepository.findByEmail(emailId);
        return UserMapper.toDTO(instructor);
    }

    @Override
    public String update(long id, @NonNull User entity) {
        UserDTO authenticatedUser = fetchSelfInfo();
        User userEntity = UserMapper.toEntity(authenticatedUser);

        //Allow update by admin to the instructor info.
        if(Arrays.stream(authenticatedUser.getRoles().split(",")).anyMatch(role -> role.trim().equalsIgnoreCase("ADMIN"))){
            userEntity = UserMapper.toEntity(findById(id));
        }

        userEntity.setName(entity.getName());
        userEntity.setAddress(entity.getAddress());
        userEntity.setPassword(encoder.encode(entity.getPassword()));
        this.userRepository.save(userEntity);
        return String.format(UPDATED_SUCCESSFULLY_MESSAGE, USER);
    }

    @Override
    @Transactional
    public String deleteById(long id) {
        UserDTO authenticatedUser = fetchSelfInfo();
        User userEntity = UserMapper.toEntity(authenticatedUser);

        //Allow to delete by admin to the instructor info.
        if(Arrays.stream(authenticatedUser.getRoles().split(",")).anyMatch(role -> role.trim().equalsIgnoreCase("ADMIN"))){
            userEntity = UserMapper.toEntity(findById(id));
        }

        this.userRepository.deleteById(userEntity.getId());
        return String.format(DELETED_SUCCESSFULLY_MESSAGE, USER);
    }

}

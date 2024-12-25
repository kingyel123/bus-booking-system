package com.java.busbookingsystem.users.service;


import com.java.busbookingsystem.auth.helper.UserInfoDetails;
import com.java.busbookingsystem.users.mapper.UserMapper;
import com.java.busbookingsystem.users.model.User;
import com.java.busbookingsystem.users.model.UserDTO;
import com.java.busbookingsystem.users.repository.UserRepository;
import com.java.busbookingsystem.utils.exception.GlobalExceptionWrapper;
import io.micrometer.common.lang.NonNull;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.java.busbookingsystem.utils.constants.UserConstants.*;


@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> findAll() {
        List<User> user = this.userRepository.findAll();
        return UserMapper.toDTO(user);
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
    public UserDTO fetchById(long id) {
        User user = findById(id);
        return UserMapper.toDTO(user);
    }


    private User findById(long id) {
        return this.userRepository.findById(id).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE,
                        USER.toLowerCase())));
    }
@Override
    public User fetchSelfInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserInfoDetails) authentication.getPrincipal()).getUsername();
        return  findByEmail(email).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE, USER.toLowerCase())));
    }

    public Optional<User> findByEmail(@NonNull String emailId) {
    return  this.userRepository.findByEmail(emailId);
    }

    @Override
    public String update(long id, @lombok.NonNull UserDTO userDTO) {
        User authenticatedUser = fetchSelfInfo();

        //Allow update by admin to the instructor info.
        if (Arrays.stream(authenticatedUser.getRoles().split(",")).anyMatch(role -> role.trim().equalsIgnoreCase(
                "ADMIN"))) {
            authenticatedUser = findById(id);
        }



        if (StringUtils.isNotBlank(userDTO.getName())) {
            authenticatedUser.setName(userDTO.getName());
        }

        if (StringUtils.isNotBlank(userDTO.getAddress())) {
            authenticatedUser.setAddress(userDTO.getAddress());
        }
        if (StringUtils.isNotBlank(userDTO.getPassword())){
            authenticatedUser.setPassword(encoder.encode(userDTO.getPassword()));
        }

        this.userRepository.save(authenticatedUser);
        return String.format(UPDATED_SUCCESSFULLY_MESSAGE, USER);
    }

    @Override
    public String updateEntity(User user) {
        this.userRepository.save(user);
        return String.format(UPDATED_SUCCESSFULLY_MESSAGE, USER);
    }


    @Override
    @Transactional
    public String deleteById(long id) {
        User authenticatedUser = fetchSelfInfo();

        //Allow to delete by admin to the instructor info.
        if(Arrays.stream(authenticatedUser.getRoles().split(",")).anyMatch(role -> role.trim().equalsIgnoreCase("ADMIN"))){
            authenticatedUser = findById(id);
        }

        this.userRepository.deleteById(authenticatedUser.getId());
        return String.format(DELETED_SUCCESSFULLY_MESSAGE, USER);
    }

}

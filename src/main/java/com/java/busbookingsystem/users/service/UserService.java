package com.java.busbookingsystem.users.service;

import com.java.busbookingsystem.auth.helper.UserInfoDetails;
import com.java.busbookingsystem.users.mapper.UserMapper;
import com.java.busbookingsystem.users.model.User;
import com.java.busbookingsystem.users.model.UserDTO;
import com.java.busbookingsystem.users.repository.UserRepository;
import com.java.busbookingsystem.utils.exception.GlobalExceptionWrapper;
import jakarta.transaction.Transactional;
import lombok.NonNull;
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
    public User findById(long id) {
        return this.userRepository.findById(id).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE, USER.toLowerCase())));
    }

    public User fetchSelfInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserInfoDetails) authentication.getPrincipal()).getUsername();
        return  findByEmail(email).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE, USER.toLowerCase())));
    }

    private Optional<User> findByEmail(@NonNull String emailId) {
        return this.userRepository.findByEmail(emailId);
    }

    @Override
    public String update(long id, @NonNull User entity) {
        User authenticatedUser = fetchSelfInfo();

        //Allow update by admin to the instructor info.
        if(Arrays.stream(authenticatedUser.getRoles().split(",")).anyMatch(role -> role.trim().equalsIgnoreCase("ADMIN"))){
            authenticatedUser = findById(id);
        }

        authenticatedUser.setName(entity.getName());
        authenticatedUser.setAddress(entity.getAddress());
        authenticatedUser.setEmail(entity.getEmail());
        authenticatedUser.setPassword(encoder.encode(entity.getPassword()));
        this.userRepository.save(authenticatedUser);
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

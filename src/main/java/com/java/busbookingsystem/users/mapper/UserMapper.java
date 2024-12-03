package com.java.busbookingsystem.users.mapper;

import com.java.busbookingsystem.users.model.User;
import com.java.busbookingsystem.users.model.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto, "password");
        return dto;
    }


    public static List<UserDTO> toDTO(List<User> users) {
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }


    public static Optional<UserDTO> toDTO(Optional<User> user) {
        return user.map(UserMapper::toDTO);
    }


    public static User toEntity(UserDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        return user;
    }
}

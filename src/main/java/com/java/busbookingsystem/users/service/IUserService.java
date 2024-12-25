package com.java.busbookingsystem.users.service;


import com.java.busbookingsystem.users.model.User;

import com.java.busbookingsystem.users.model.UserDTO;
import com.java.busbookingsystem.utils.IGenericCrudService;

public interface IUserService extends IGenericCrudService<User, UserDTO> {

        User fetchSelfInfo();

        /**
         * Updates the user entity.
         *
         * @param user The user entity to be updated.
         * @return The confirmation message on whether the user is updated or not.
         */
        String updateEntity(User user);
}
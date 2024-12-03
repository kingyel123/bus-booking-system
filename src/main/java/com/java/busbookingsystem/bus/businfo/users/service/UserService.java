package com.java.busbookingsystem.bus.businfo.users.service;





import com.java.busbookingsystem.bus.businfo.users.constants.UserConstants;
import com.java.busbookingsystem.bus.businfo.users.model.User;
import com.java.busbookingsystem.bus.businfo.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userrepository;

    @Override
    public List<User> findAll() {
        return userrepository.findAll();
    }

    @Override
    public User save(User user) {
        return userrepository.save(user);
    }

    @Override

    public User findById(long id) throws  Exception{
        return userrepository.findById(id).orElseThrow(()->new Exception(UserConstants.NOT_FOUND));
    }

    @Override
    public String update(User user) {
        return userrepository.findById(user.getId())
                .map(existingUser -> {
                    existingUser.setEmail(user.getEmail());
                    existingUser.setPassword(user.getPassword());
                    userrepository.save(existingUser);
                    return UserConstants.UPDATE_SUCCESSFUL;
                })
                .orElse(UserConstants.NOT_FOUND);
    }

    @Override
    public String deleteById(long id) {
        if (!userrepository.existsById(id)) {
            return UserConstants.NOT_FOUND;
        }
        userrepository.deleteById(id);
        return UserConstants.DELETE_SUCCESSFUL;
}
}

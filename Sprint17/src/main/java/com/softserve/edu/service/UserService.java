package com.softserve.edu.service;

import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.User;
import com.softserve.edu.model.User.Role;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getUserById(Long userId);

    User createOrUpdateUser(User user);

    List<User> getAllByRole(Role role);

    boolean addUserToMarathon(User user, Marathon marathon);

    void deleteUserById(Long id);

    boolean deleteUserFromMarathon(User user, Marathon marathon);
}

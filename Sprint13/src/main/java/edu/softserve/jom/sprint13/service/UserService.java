package edu.softserve.jom.sprint13.service;

import edu.softserve.jom.sprint13.entity.Marathon;
import edu.softserve.jom.sprint13.entity.Task;
import edu.softserve.jom.sprint13.entity.User;
import edu.softserve.jom.sprint13.entity.User.Role;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<User> getAll();

    User getUserById(UUID userId);

    User createOrUpdateUser(User user);

    List<User> getAllByRole(Role role);

    boolean addUserToMarathon(User user, Marathon marathon);

}

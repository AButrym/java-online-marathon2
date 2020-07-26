package edu.softserve.jom.sprint13.service.impl;

import edu.softserve.jom.sprint13.entity.Marathon;
import edu.softserve.jom.sprint13.entity.User;
import edu.softserve.jom.sprint13.exception.UserNotFoundException;
import edu.softserve.jom.sprint13.repository.UserRepository;
import edu.softserve.jom.sprint13.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public User createOrUpdateUser(User user) {
        var validator = Validation.buildDefaultValidatorFactory().getValidator();
        var violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        var fromBD = userRepository.findById(user.getId());
        if (fromBD.isPresent()) {
            var oldUser = fromBD.get();
            oldUser.setRole(user.getRole());
            oldUser.setPassword(user.getPassword());
            oldUser.setFirstName(user.getFirstName());
            oldUser.setLastName(user.getLastName());
            oldUser.setEmail(user.getEmail());
            oldUser.getMarathons().retainAll(user.getMarathons());
            oldUser.getMarathons().addAll(user.getMarathons());
            oldUser.getProgresses().retainAll(user.getProgresses());
            oldUser.getProgresses().addAll(user.getProgresses());
            user = oldUser;
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllByRole(User.Role role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    public boolean addUserToMarathon(User user, Marathon marathon) {
        return marathon.getUsers().add(user) && user.getMarathons().add(marathon);
    }
}

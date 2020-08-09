package com.softserve.edu.service.impl;

import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.User;
import com.softserve.edu.exception.MarathonNotFoundException;
import com.softserve.edu.exception.UserNotFoundException;
import com.softserve.edu.repository.MarathonRepository;
import com.softserve.edu.repository.UserRepository;
import com.softserve.edu.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MarathonRepository marathonRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional
    @Override
    public User createOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllByRole(User.Role role) {
        return userRepository.findAllByRole(role);
    }

    @Transactional
    @Override
    public boolean addUserToMarathon(User user, Marathon marathon) {
        if (user.getMarathons().contains(marathon)) {
            return false;
        }
        var marathonId = marathon.getId();
        marathon = marathonRepository.findById(marathonId)
                .orElseThrow(() -> new MarathonNotFoundException(marathonId));
        var userId = user.getId();
        user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        marathon.getUsers().add(user);
        user.getMarathons().add(marathon);
        marathonRepository.save(marathon);
        return true;
    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public boolean deleteUserFromMarathon(User user, Marathon marathon) {
        if (!user.getMarathons().contains(marathon)) {
            return false;
        }
        var marathonId = marathon.getId();
        marathon = marathonRepository.findById(marathonId)
                .orElseThrow(() -> new MarathonNotFoundException(marathonId));
        var userId = user.getId();
        user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        marathon.getUsers().remove(user);
        user.getMarathons().remove(marathon);
        marathonRepository.save(marathon);
        return true;
    }

}

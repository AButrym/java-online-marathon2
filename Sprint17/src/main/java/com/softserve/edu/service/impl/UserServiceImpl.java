package com.softserve.edu.service.impl;

import com.softserve.edu.exception.EntityNotFoundException;
import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.Role;
import com.softserve.edu.model.User;
import com.softserve.edu.repository.MarathonRepository;
import com.softserve.edu.repository.RoleRepository;
import com.softserve.edu.repository.UserRepository;
import com.softserve.edu.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final MarathonRepository marathonRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users.isEmpty() ? new ArrayList<>() : users;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(("No user /w id " + id)));
    }

    public User createOrUpdateUser(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public boolean addUserToMarathon(User user, Marathon marathon) {
        User userEntity = userRepository.getOne(user.getId());
        Marathon marathonEntity = marathonRepository.getOne(marathon.getId());
        if (userEntity != null && marathonEntity != null
                && marathonEntity.getUsers().add(userEntity)) {
            marathonEntity = marathonRepository.save(marathonEntity);
            return marathonEntity != null;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteUserFromMarathon(User user, Marathon marathon) {
        User userEntity = userRepository.getOne(user.getId());
        Marathon marathonEntity = marathonRepository.getOne(marathon.getId());
        if (userEntity != null && marathonEntity != null
                && marathonEntity.getUsers().remove(userEntity)) {
            marathonEntity = marathonRepository.save(marathonEntity);
            return marathonEntity != null;
        } else {
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDetails userDetails = userRepository.getUserByEmail(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("User not Found!");
        }
        return userDetails;
    }

    public void registerAccounts() {
        if (!roleRepository.findAll().isEmpty()) {
            return; // don't repeat init more than once
        }
        Role mentor = new Role();
        mentor.setName("ROLE_MENTOR");
        mentor = roleRepository.save(mentor);

        Role trainee = new Role();
        trainee.setName("ROLE_TRAINEE");
        trainee = roleRepository.save(trainee);

        User user1 = new User();
        user1.setEmail("admin@marathon.com");
        user1.setRole(mentor);
        user1.setFirstName("Mykola");
        user1.setLastName("Demchyna");
        user1.setPassword(passwordEncoder.encode("secret"));
        userRepository.save(user1);

        User user2 = new User();
        user2.setEmail("butrym@marathon.com");
        user2.setRole(trainee);
        user2.setFirstName("Oleksandr");
        user2.setLastName("Butrym");
        user2.setPassword(passwordEncoder.encode("secret"));
        userRepository.save(user2);

        User user3 = new User();
        user3.setEmail("datskov@marathon.com");
        user3.setRole(trainee);
        user3.setFirstName("Sviatoslav");
        user3.setLastName("Datskov");
        user3.setPassword(passwordEncoder.encode("secret"));
        userRepository.save(user3);
    }
}

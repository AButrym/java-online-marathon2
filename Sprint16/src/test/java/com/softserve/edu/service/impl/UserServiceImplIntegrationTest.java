package com.softserve.edu.service.impl;

import com.softserve.edu.Application;
import com.softserve.edu.exception.EntityNotFoundException;
import com.softserve.edu.exception.UserNotFoundException;
import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.User;
import com.softserve.edu.repository.MarathonRepository;
import com.softserve.edu.repository.UserRepository;
import com.softserve.edu.service.UserService;
import com.softserve.edu.service.impl.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserServiceImplIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MarathonRepository marathonRepository;

    private Marathon marathon1;
    private Marathon marathon2;

    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private User user5;

    @BeforeEach
    void before() {
        marathon1 = Marathon.builder()
                .title("marathon1").build();
        marathon1 = marathonRepository.save(marathon1);

        marathon2 = Marathon.builder()
                .title("marathon2").build();
        marathon2 = marathonRepository.save(marathon2);

        user1 = User.builder()
                .role(User.Role.TRAINEE)
                .email("newUser1@email.com")
                .firstName("firstName1")
                .lastName("Alastname")
                .password("pass1").build();
        user1.getMarathons().add(marathon1);
        marathon1.getUsers().add(user1);
        user1 = userRepository.save(user1);

        user2 = User.builder()
                .role(User.Role.TRAINEE)
                .email("newUser2@email.com")
                .firstName("firstName2")
                .lastName("Blastname")
                .password("pass2").build();
        user2.getMarathons().add(marathon2);
        marathon2.getUsers().add(user2);
        user2 = userRepository.save(user2);

        user3 = User.builder()
                .role(User.Role.MENTOR)
                .email("newUser3@email.com")
                .firstName("firstName3")
                .lastName("Clastname")
                .password("pass3").build();
        user3.getMarathons().add(marathon1);
        marathon1.getUsers().add(user3);
        user3 = userRepository.save(user3);

        user4 = User.builder()
                .role(User.Role.MENTOR)
                .email("newUser4@email.com")
                .firstName("firstName4")
                .lastName("Dlastname")
                .password("pass4").build();
        user4.getMarathons().add(marathon2);
        marathon2.getUsers().add(user4);
        user4 = userRepository.save(user4);

        user5 = User.builder()
                .role(User.Role.TRAINEE)
                .email("newUser5@email.com")
                .firstName("firstName5")
                .lastName("Elastname")
                .password("pass5").build();
        user5.getMarathons().add(marathon2);
        marathon2.getUsers().add(user5);
        user5 = userRepository.save(user5);
        System.out.println("Set up");
    }

    @Rollback
    @Test
    void shouldGetAllUsers() {
        List<User> expected = List.of(user1, user2, user3, user4, user5);
        List<User> actual = userService.getAll();
        assertEquals(expected, actual);
    }

    @Rollback
    @Test
    void shouldGetUserById() {
        User expected = user5;
        Long id = user5.getId();
        User actual = userService.getUserById(5L);
        assertEquals(expected, actual);
    }


    @Rollback
    @Test
    void shouldCreateOrUpdateUser() {
        user5.setEmail("UpdateEmailUser5@email.com");
        userService.createOrUpdateUser(user5);
        var actualOpt = userRepository.findByEmail("UpdateEmailUser5@email.com");
        assertTrue(actualOpt.isPresent());
        var actual = actualOpt.get();
        assertEquals(user5, actual);
    }

    @Rollback
    @Test
    void shouldDeleteUserById() {
        Long id = user5.getId();
        List<User> expected = List.of(user1, user2, user3, user4);
        userService.deleteUserById(id);
        List<User> actual = userRepository.findAll();
        assertEquals(expected, actual);
    }

    @Rollback
    @Test
    void shouldGetAllByRoleTrainee() {
        List<User> expected = List.of(user1, user2, user5);
        List<User> actual = userService.getAllByRole(User.Role.TRAINEE);
        Assertions.assertEquals(expected, actual);
    }


    @Rollback
    @Test
    void shouldGetAllByRoleMentor() {
        List<User> expected = List.of(user3, user4);
        List<User> actual = userService.getAllByRole(User.Role.MENTOR);
        Assertions.assertEquals(expected, actual);
    }


    @Rollback
    @Test
    void shouldAddUserToMarathon() {
        assertFalse(user5.getMarathons().contains(marathon1));
        boolean actual = userService.addUserToMarathon(user5, marathon1);
        assertTrue(actual);
        assertTrue(user5.getMarathons().contains(marathon1));
    }

    @Rollback
    @Test
    void shouldDeleteUserFromMarathon() {
        assertTrue(user5.getMarathons().contains(marathon2));
        boolean actual = userService.deleteUserFromMarathon(user5, marathon2);
        assertTrue(actual);
        assertFalse(user5.getMarathons().contains(marathon2));
    }

    @Rollback
    @Test
    void shouldThrowExceptionIfEntityNotFound() {
        Long notExistId = 123456789L;

        Throwable exception = Assertions.assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserById(notExistId)
        );

        assertEquals("No user for id: " + notExistId, exception.getMessage());
        assertEquals(UserNotFoundException.class, exception.getClass());
    }
}

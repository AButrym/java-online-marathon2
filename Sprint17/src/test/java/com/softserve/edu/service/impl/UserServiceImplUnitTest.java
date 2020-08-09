package com.softserve.edu.service.impl;

import com.softserve.edu.exception.UserNotFoundException;
import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.User;
import com.softserve.edu.repository.MarathonRepository;
import com.softserve.edu.repository.UserRepository;
import com.softserve.edu.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UserServiceImplUnitTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final MarathonRepository marathonRepository = mock(MarathonRepository.class);
    private UserService userService;

    List<User> usersInDB;

    private Marathon marathon1;
    private Marathon marathon2;

    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private User user5;


    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, marathonRepository);

        when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        when(marathonRepository.save(Mockito.any(Marathon.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        marathon1 = Marathon.builder()
                .id(1L)
                .title("marathon1").build();
        marathonRepository.save(marathon1);

        marathon2 = Marathon.builder()
                .id(2L)
                .title("marathon2").build();
        marathonRepository.save(marathon2);

        user1 = User.builder()
                .id(1L)
                .role(User.Role.TRAINEE)
                .email("newUser1@email.com")
                .firstName("firstName1")
                .lastName("Alastname")
                .password("pass1").build();
        user1.getMarathons().add(marathon1);
        marathon1.getUsers().add(user1);
        userRepository.save(user1);

        user2 = User.builder()
                .id(2L)
                .role(User.Role.TRAINEE)
                .email("newUser2@email.com")
                .firstName("firstName2")
                .lastName("Blastname")
                .password("pass2").build();
        user2.getMarathons().add(marathon2);
        marathon2.getUsers().add(user2);
        userRepository.save(user2);

        user3 = User.builder()
                .id(3L)
                .role(User.Role.MENTOR)
                .email("newUser3@email.com")
                .firstName("firstName3")
                .lastName("Clastname")
                .password("pass3").build();
        user3.getMarathons().add(marathon1);
        marathon1.getUsers().add(user3);
        userRepository.save(user3);

        user4 = User.builder()
                .id(4L)
                .role(User.Role.MENTOR)
                .email("newUser4@email.com")
                .firstName("firstName4")
                .lastName("Dlastname")
                .password("pass4").build();
        user4.getMarathons().add(marathon2);
        marathon2.getUsers().add(user4);
        userRepository.save(user4);

        user5 = User.builder()
                .id(5L)
                .role(User.Role.TRAINEE)
                .email("newUser5@email.com")
                .firstName("firstName5")
                .lastName("Elastname")
                .password("pass5").build();
        user5.getMarathons().add(marathon2);
        marathon2.getUsers().add(user5);
        userRepository.save(user5);

        usersInDB = List.of(user1, user2, user3, user4, user5);
    }

    @Test
    void shouldGetAllUsers() {
        List<User> expected = List.of(user1, user2, user3, user4, user5);

        when(userRepository.findAll())
                .thenReturn(usersInDB);

        List<User> actual = userService.getAll();

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetUserById() {
        User expected = user5;
        Long id = user5.getId();

        when(userRepository.findById(id))
                .thenReturn(Optional.of(expected));

        User actual = userService.getUserById(id);

        assertEquals(expected, actual);
    }


    @Rollback
    @Test
    void shouldCreateOrUpdateUser() {
        user5.setEmail("UpdateEmailUser5@email.com");

        when(userRepository.findByEmail("UpdateEmailUser5@email.com"))
                .thenReturn(Optional.of(user5));

        userService.createOrUpdateUser(user5);

        var actualOpt = userRepository.findByEmail("UpdateEmailUser5@email.com");
        assertTrue(actualOpt.isPresent());

        var actual = actualOpt.get();
        assertEquals(user5, actual);
    }


    @Test
    void shouldDeleteUserById() {
        Long id = user5.getId();
        List<User> expected = List.of(user1, user2, user3, user4);

        userService.deleteUserById(id);
        when(userRepository.findAll())
                .thenReturn(usersInDB.stream()
                        .filter(u -> !u.getId().equals(id))
                        .collect(Collectors.toList()));

        List<User> actual = userRepository.findAll();

        assertEquals(expected, actual);
        verify(userRepository).deleteById(id);
    }

    @Rollback
    @Test
    void shouldGetAllByRoleTrainee() {
        List<User> expected = List.of(user1, user2, user5);

        when(userRepository.findAllByRole(User.Role.TRAINEE))
                .thenReturn(usersInDB.stream()
                        .filter(u -> u.getRole() == User.Role.TRAINEE)
                        .collect(Collectors.toList()));

        List<User> actual = userService.getAllByRole(User.Role.TRAINEE);

        assertEquals(expected, actual);
    }


    @Test
    void shouldGetAllByRoleMentor() {
        List<User> expected = List.of(user3, user4);

        when(userRepository.findAllByRole(User.Role.MENTOR))
                .thenReturn(usersInDB.stream()
                        .filter(u -> u.getRole() == User.Role.MENTOR)
                        .collect(Collectors.toList()));

        List<User> actual = userService.getAllByRole(User.Role.MENTOR);

        Assertions.assertEquals(expected, actual);
    }


    @Rollback
    @Test
    void shouldAddUserToMarathon() {
        User user = User.builder()
                .id(6L)
                .role(User.Role.TRAINEE)
                .email("newUser@email.com")
                .firstName("firstName")
                .lastName("Lastname")
                .password("pass").build();

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        when(marathonRepository.findById(marathon1.getId()))
                .thenReturn(Optional.of(marathon1));

        boolean actual = userService.addUserToMarathon(user, marathon1);

        assertTrue(actual);
    }

    @Rollback
    @Test
    void shouldDeleteUserFromMarathon() {

        when(userRepository.findById(user5.getId()))
                .thenReturn(Optional.of(user5));
        when(marathonRepository.findById(marathon2.getId()))
                .thenReturn(Optional.of(marathon2));

        boolean actual = userService.deleteUserFromMarathon(user5, marathon2);

        assertTrue(actual);

    }

    @Test
    void shouldThrowExceptionIfEntityNotFound() {
        Long notExistId = 122L;

        Throwable exception = Assertions.assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserById(notExistId));

        assertEquals("No user for id: " + notExistId, exception.getMessage());
        assertEquals(UserNotFoundException.class, exception.getClass());
    }
}

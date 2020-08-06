package com.softserve.edu.repository;

import com.softserve.edu.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private static User user1;
    private static User user2;
    private static User user3;
    private static User user4;
    private static User user5;

    @BeforeAll
    static void init() {
        user1 = new User();
        user1.setRole(User.Role.TRAINEE);
        user1.setEmail("newUser1@email.com");
        user1.setFirstName("Oleksandr");
        user1.setLastName("Butrym");
        user1.setPassword("pass1");

        user2 = new User();
        user2.setRole(User.Role.TRAINEE);
        user2.setEmail("newUser2@email.com");
        user2.setFirstName("Halyna");
        user2.setLastName("Mar");
        user2.setPassword("pass2");

        user3 = new User();
        user3.setRole(User.Role.MENTOR);
        user3.setEmail("newUser3@email.com");
        user3.setFirstName("Nataliia");
        user3.setLastName("Romanenko");
        user3.setPassword("pass3");

        user4 = new User();
        user4.setRole(User.Role.MENTOR);
        user4.setEmail("newUser4@email.com");
        user4.setFirstName("Mykola");
        user4.setLastName("Demchyna");
        user4.setPassword("pass4");

        user5 = new User();
        user5.setRole(User.Role.TRAINEE);
        user5.setEmail("newUser5@email.com");
        user5.setFirstName("Firstname");
        user5.setLastName("Lastname");
        user5.setPassword("pass5");
    }

    void storeUsers() {
        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);
        user3 = userRepository.save(user3);
        user4 = userRepository.save(user4);
        user5 = userRepository.save(user5);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void createStoreTest() {
        var email = "some_01@email.com";
        var role = User.Role.TRAINEE;
        var firstName = "Oleksandr";
        var lastName = "Butrym";
        var password = "pass";

        var user = User.builder()
                .email(email)
                .role(role)
                .firstName(firstName)
                .lastName(lastName)
                .password(password).build();
        userRepository.save(user);

        var actualOpt = userRepository.findById(user.getId());

        assertTrue(actualOpt.isPresent());

        var actual = actualOpt.get();

        assertEquals(email, actual.getEmail());
        assertEquals(role, actual.getRole());
        assertEquals(firstName, actual.getFirstName());
        assertEquals(lastName, actual.getLastName());
        assertEquals(password, actual.getPassword());
    }

    @Test
    void findByEmail() {
        storeUsers();

        var actualOpt = userRepository.findByEmail("newUser5@email.com");

        assertTrue(actualOpt.isPresent());

        var actual = actualOpt.get();

        assertEquals(user5, actual);
    }

    @Test
    void findAllByRoleTest() {
        var trainee1 = User.builder()
                .email("some_01@email.com")
                .role(User.Role.TRAINEE)
                .firstName("Oleksandr")
                .lastName("Butrym")
                .password("password1").build();
        var trainee2 = User.builder()
                .email("some_02@email.com")
                .role(User.Role.TRAINEE)
                .firstName("Halyna")
                .lastName("Mar")
                .password("password2").build();
        var mentor1 = User.builder()
                .email("some_03@email.com")
                .role(User.Role.MENTOR)
                .firstName("Nataliia")
                .lastName("Romanenko")
                .password("password3").build();

        userRepository.save(trainee1);
        userRepository.save(trainee2);
        userRepository.save(mentor1);

        var trainees = userRepository.findAllByRole(User.Role.TRAINEE);
        assertEquals(2, trainees.size());

        var mentors = userRepository.findAllByRole(User.Role.MENTOR);
        assertEquals(1, mentors.size());
        assertEquals("Romanenko", mentors.get(0).getLastName());
    }

    @Test
    void getAllUsersByRoleTrainee() {
        storeUsers();

        List<User> expected = new ArrayList<>();
        expected.add(user1);
        expected.add(user2);
        expected.add(user5);

        List<User> actual = userRepository.findAllByRole(User.Role.TRAINEE);

        assertEquals(expected, actual);
    }

    @Test
    void getAllUsersByRoleMentor() {
        storeUsers();

        List<User> expected = new ArrayList<>();
        expected.add(user3);
        expected.add(user4);

        List<User> actual = userRepository.findAllByRole(User.Role.MENTOR);

        assertEquals(expected, actual);
    }


    @Test
    void getAllUsers() {
        storeUsers();

        List<User> expected = new ArrayList<>();
        expected.add(user1);
        expected.add(user2);
        expected.add(user3);
        expected.add(user4);
        expected.add(user5);

        List<User> actual = userRepository.findAll();

        assertEquals(expected, actual);
    }

    @Test
    void findUserById() {
        storeUsers();
        var id = user5.getId();

        var actual = userRepository.findById(id).orElse(null);

        assertEquals(user5, actual);
    }

    @Test
    void deleteUser() {
        storeUsers();

        List<User> expected = new ArrayList<>();
        expected.add(user1);
        expected.add(user2);
        expected.add(user3);
        expected.add(user4);

        userRepository.delete(user5);

        List<User> actual = userRepository.findAll();

        assertEquals(expected, actual);
    }

    @Test
    void deleteUserById() {
        storeUsers();

        long id = user5.getId();

        List<User> expected = new ArrayList<>();
        expected.add(user1);
        expected.add(user2);
        expected.add(user3);
        expected.add(user4);

        userRepository.deleteById(id);

        List<User> actual = userRepository.findAll();

        assertEquals(expected, actual);
    }

}

package com.softserve.edu.service.impl;

import com.softserve.edu.Application;
import com.softserve.edu.exception.MarathonNotFoundException;
import com.softserve.edu.exception.UserNotFoundException;
import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.User;
import com.softserve.edu.repository.MarathonRepository;
import com.softserve.edu.repository.UserRepository;
import com.softserve.edu.service.MarathonService;
import com.softserve.edu.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
class MarathonServiceImplIntegrationTest {

    @Autowired
    private MarathonRepository marathonRepository;

    @Autowired
    private MarathonService marathonService;

    private Marathon marathon1;
    private Marathon marathon2;

    @BeforeEach
    void setUp() {
        marathon1 = Marathon.builder()
                .id(1L)
                .title("marathon1").build();
        marathon1 = marathonRepository.save(marathon1);

        marathon2 = Marathon.builder()
                .id(2L)
                .title("marathon2").build();
        marathon2 = marathonRepository.save(marathon2);
    }

    @Rollback
    @Test
    void shouldGetAllMarathons() {
        List<Marathon> expected = List.of(marathon1, marathon2);
        List<Marathon> actual = marathonService.getAll();
        assertEquals(expected, actual);
    }

    @Rollback
    @Test
    void shouldGetMarathonById() {
        var expected = marathon2;
        var id = marathon2.getId();
        var actual = marathonService.getMarathonById(id);
        assertEquals(expected, actual);
    }

    @Rollback
    @Test
    void shouldCreateOrUpdateMarathon() {
        marathon1.setTitle("New title");
        var actual = marathonService.createOrUpdate(marathon1);
        assertEquals(marathon1.getTitle(), actual.getTitle());
        assertEquals(marathon1, actual);
    }

    @Rollback
    @Test
    void shouldThrowExceptionIfEntityNotFound() {
        Long notExistId = 123456789L;

        Throwable exception = Assertions.assertThrows(
                MarathonNotFoundException.class,
                () -> marathonService.getMarathonById(notExistId));

        assertEquals("No marathon for id: " + notExistId, exception.getMessage());
        assertEquals(MarathonNotFoundException.class, exception.getClass());
    }
}

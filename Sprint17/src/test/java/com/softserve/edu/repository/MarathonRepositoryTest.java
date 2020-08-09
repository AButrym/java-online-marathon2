package com.softserve.edu.repository;

import com.softserve.edu.model.Marathon;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class MarathonRepositoryTest {

    @Autowired
    private MarathonRepository marathonRepository;

    private static Marathon marathon1;
    private static Marathon marathon2;

    @BeforeAll
    static void init() {
        marathon1 = Marathon.builder()
                .id(1L)
                .title("marathon1").build();

        marathon2 = Marathon.builder()
                .id(2L)
                .title("marathon2").build();
    }

    @BeforeEach
    void storeMarathons() {
        marathon1 = marathonRepository.save(marathon1);
        marathon2 = marathonRepository.save(marathon2);
    }

    @AfterEach
    void tearDown() {
        marathonRepository.deleteAll();
    }

    @Test
    void createStoreTest() {
        var title = "marathon title";

        var marathon = Marathon.builder().title(title).build();
        marathonRepository.save(marathon);

        var actualOpt = marathonRepository.findById(marathon.getId());

        assertTrue(actualOpt.isPresent());

        var actual = actualOpt.get();

        assertEquals(title, actual.getTitle());
    }

    @Test
    void findMarathonById() {
        var id = marathon1.getId();
        var actualOpt = marathonRepository.findById(id);
        assertTrue(actualOpt.isPresent());
        var actual = actualOpt.get();
        assertEquals(marathon1, actual);
    }

    @Test
    void findAllMarathons() {
        List<Marathon> expected = List.of(marathon1, marathon2);

        List<Marathon> actual = marathonRepository.findAll();

        assertEquals(expected, actual);
    }

    @Test
    void deleteMarathonById() {
        long id = marathon2.getId();

        List<Marathon> expected = List.of(marathon1);

        marathonRepository.deleteById(id);

        List<Marathon> actual = marathonRepository.findAll();

        assertEquals(expected, actual);
    }

}
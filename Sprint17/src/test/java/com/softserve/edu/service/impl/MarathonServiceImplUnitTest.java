package com.softserve.edu.service.impl;

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
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class MarathonServiceImplUnitTest {
    private final MarathonRepository marathonRepository = mock(MarathonRepository.class);
    private MarathonService marathonService;

    List<Marathon> marathonsInDB;

    private Marathon marathon1;
    private Marathon marathon2;

    @BeforeEach
    void setUp() {
        marathonService = new MarathonServiceImpl(marathonRepository);

        when(marathonRepository.save(Mockito.any(Marathon.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        marathon1 = Marathon.builder()
                .id(1L)
                .title("marathon1").build();
        marathon1 = marathonRepository.save(marathon1);

        marathon2 = Marathon.builder()
                .id(2L)
                .title("marathon2").build();
        marathon2 = marathonRepository.save(marathon2);

        marathonsInDB = List.of(marathon1, marathon2);
    }

    @Test
    void shouldGetAllMarathons() {
        List<Marathon> expected = List.of(marathon1, marathon2);

        when(marathonRepository.findAll())
                .thenReturn(new ArrayList<>(marathonsInDB));

        List<Marathon> actual = marathonService.getAll();

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetMarathonById() {
        var expected = marathon2;
        var id = marathon2.getId();

        when(marathonRepository.findById(id))
                .thenReturn(Optional.of(expected));

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


    @Test
    void shouldDeleteMarathonById() {
        var id = marathon1.getId();
        List<Marathon> expected = List.of(marathon2);

        marathonService.deleteMarathonById(id);

        when(marathonRepository.findAll())
                .thenReturn(marathonsInDB.stream()
                        .filter(m -> !m.getId().equals(id))
                        .collect(Collectors.toList()));

        List<Marathon> actual = marathonRepository.findAll();

        assertEquals(expected, actual);
        verify(marathonRepository).deleteById(id);
    }

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

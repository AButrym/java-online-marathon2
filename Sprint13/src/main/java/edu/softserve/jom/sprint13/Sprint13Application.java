package edu.softserve.jom.sprint13;

import edu.softserve.jom.sprint13.entity.*;
import edu.softserve.jom.sprint13.exception.EntityNotFoundException;
import edu.softserve.jom.sprint13.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.validation.ConstraintViolationException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@SpringBootApplication
public class Sprint13Application {
    private final MarathonService marathonService;
    private final UserService userService;
    private final SprintService sprintService;
    private final TaskService taskService;
    private final ProgressService progressService;

    public Sprint13Application(
            MarathonService marathonService,
            UserService userService,
            SprintService sprintService,
            TaskService taskService,
            ProgressService progressService
    ) {
        this.marathonService = marathonService;
        this.userService = userService;
        this.sprintService = sprintService;
        this.taskService = taskService;
        this.progressService = progressService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Sprint13Application.class, args);
    }

    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
            System.out.println("*** START demo2 MAIN ***");
            try {
                Marathon marathon1 = Marathon.builder().title("JOM_1").build();
                Marathon marathon2 = Marathon.builder().title("JOM_2").build();

                Instant start = LocalDate.of(2020, 7, 27)
                        .atStartOfDay().toInstant(ZoneOffset.UTC);
                Sprint sprint01 = Sprint.builder()
                        .title("Sprint 01")
                        .startDate(start)
                        .finish(start.plus(Duration.ofDays(3)))
                        .build();
                start = sprint01.getFinish();
                Sprint sprint02 = Sprint.builder()
                        .title("Sprint 02")
                        .startDate(start)
                        .finish(start.plus(Duration.ofDays(3)))
                        .build();

                sprintService.addSprintToMarathon(sprint01, marathon2);
                sprintService.addSprintToMarathon(sprint02, marathon2);

                Instant now = Instant.now();
                Task task01s01 = Task.builder()
                        .title("Task_01 for sprint 01")
                        .created(now)
                        .updated(now)
                        .build();
                Task task02s01 = Task.builder()
                        .title("Task_02 for sprint 02")
                        .created(now)
                        .updated(now)
                        .build();

                taskService.addTaskToSprint(task01s01, sprint01);
                taskService.addTaskToSprint(task02s01, sprint01);

                User student1 = User.builder()
                        .firstName("Oleksandr")
                        .lastName("Butrym")
                        .email("some1@example.com")
                        .password("password1")
                        .role(User.Role.TRAINEE)
                        .build();
                User student2 = User.builder()
                        .firstName("Nazar")
                        .lastName("Panasiuk")
                        .email("some2@example.com")
                        .password("password2")
                        .role(User.Role.TRAINEE)
                        .build();

                User mentor1 = User.builder()
                        .firstName("Nataliia")
                        //.lastName("romanenko2222222222222222222")
                        .lastName("Romanenko")
                        //.email("some3_example.com")
                        .email("some3@example.com")
                        .password("password3")
                        .role(User.Role.MENTOR)
                        .build();

                userService.addUserToMarathon(student1, marathon1);
                userService.addUserToMarathon(mentor1, marathon1);

                userService.addUserToMarathon(student1, marathon2);
                userService.addUserToMarathon(student2, marathon2);
                userService.addUserToMarathon(mentor1, marathon2);

                Progress progress_st1_t01_sp01 = progressService
                        .addTaskForStudent(task01s01, student1);
                Progress progress_st2_t01_sp01 = progressService
                        .addTaskForStudent(task01s01, student2);
                Progress progress_st1_t02_sp01 = progressService
                        .addTaskForStudent(task02s01, student1);
                Progress progress_st2_t02_sp01 = progressService
                        .addTaskForStudent(task02s01, student2);

                // PERSIST
                marathon1 = marathonService.createOrUpdate(marathon1);
                marathon2 = marathonService.createOrUpdate(marathon2);

                // INSPECT
                System.out.println("********************************");
                for (Marathon marathon : marathonService.getAll()) {
                    System.out.println("====" + marathon);
                    for (Sprint sprint : sprintService
                            .getSprintsByMarathonId(marathon.getId())) {
                        System.out.println("==== ====" + sprint);
                    }
                }
                System.out.println("********************************");

                marathonService.deleteMarathonById(marathon2.getId());

                System.out.println("********************************");
                for (Marathon marathon : marathonService.getAll()) {
                    System.out.println("====" + marathon);
                    for (Sprint sprint : sprintService
                            .getSprintsByMarathonId(marathon.getId())) {
                        System.out.println("==== ====" + sprint);
                    }
                }
                System.out.println("********************************");

            } catch (ConstraintViolationException e) {
                System.out.println("---CONSTRAINTS VIOLATIONS---\n" +
                        e.getMessage());
            } catch (EntityNotFoundException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            System.out.println("*** STOP demo2 MAIN ***");
        };
    }
}

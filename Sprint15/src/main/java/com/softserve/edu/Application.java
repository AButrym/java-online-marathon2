package com.softserve.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

/*
	@Bean
	public CommandLineRunner demo() {
		return args -> {
			System.out.println("*** START demo MAIN ***");
			System.out.println("0".repeat(20));
			try {
				Marathon marathon1 = Marathon.builder().title("JOM_1").build();
				Marathon marathon2 = Marathon.builder().title("JOM_2").build();

				System.out.println("1".repeat(20));

				marathon1 = marathonService.createOrUpdate(marathon1);
				marathon2 = marathonService.createOrUpdate(marathon2);

				System.out.println("2".repeat(20));

				Instant start = LocalDate.of(2020, 7, 27)
						.atStartOfDay().toInstant(ZoneOffset.UTC);
				Sprint sprint01 = Sprint.builder()
						.title("Sprint 2_01")
						.startDate(start)
						.finish(start.plus(Duration.ofDays(3)))
						.build();
				start = sprint01.getFinish();
				Sprint sprint02 = Sprint.builder()
						.title("Sprint 2_02")
						.startDate(start)
						.finish(start.plus(Duration.ofDays(3)))
						.build();
				start = LocalDate.of(2020, 7, 27)
						.atStartOfDay().toInstant(ZoneOffset.UTC);
				Sprint sprint03 = Sprint.builder()
						.title("Sprint 1_01")
						.startDate(start)
						.finish(start.plus(Duration.ofDays(3)))
						.build();

				System.out.println("3".repeat(20));

				System.out.println(sprint01.getId() + " "
						+ sprint02.getId() + " "
						+ sprint03.getId());

				var bool01 = sprintService.addSprintToMarathon(sprint01, marathon2);
				var bool02 = sprintService.addSprintToMarathon(sprint02, marathon2);
				var bool03 = sprintService.addSprintToMarathon(sprint03, marathon1);

				System.out.println(bool01 + " " + bool02 + " " + bool03);

				System.out.println(sprint01.getId() + " "
						+ sprint02.getId() + " "
						+ sprint03.getId());

				System.out.println("4".repeat(20));

				Instant now = Instant.now();
				Task task01s01 = Task.builder()
						.title("Task_01 for sprint 01")
						.created(now)
						.updated(now)
						.build();
				Task task02s01 = Task.builder()
						.title("Task_02 for sprint 01")
						.created(now)
						.updated(now)
						.build();

				System.out.println("5".repeat(20));

				System.out.println(task01s01.getId() + " " + task02s01.getId());

				taskService.addTaskToSprint(task01s01, sprint01);
				taskService.addTaskToSprint(task02s01, sprint01);

				System.out.println(task01s01.getId() + " " + task02s01.getId());

				System.out.println("6".repeat(20));

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

				System.out.println("7".repeat(20));

				student1 = userService.createOrUpdateUser(student1);
				student2 = userService.createOrUpdateUser(student2);
				mentor1 = userService.createOrUpdateUser(mentor1);

				System.out.println("8".repeat(20));

				userService.addUserToMarathon(student1, marathon1);
				userService.addUserToMarathon(mentor1, marathon1);

				userService.addUserToMarathon(student1, marathon2);
				userService.addUserToMarathon(student2, marathon2);
				userService.addUserToMarathon(mentor1, marathon2);

				System.out.println("9".repeat(20));

				var progress1 = progressService.addTaskForStudent(task01s01, student1);
				var progress2 = progressService.addTaskForStudent(task01s01, student2);
				var progress3 = progressService.addTaskForStudent(task02s01, student1);
				var progress4 = progressService.addTaskForStudent(task02s01, student2);

				progressService.setStatus(Progress.TaskStatus.PASS, progress1);
				progressService.setStatus(Progress.TaskStatus.SUBMITTED, progress2);
				progressService.setStatus(Progress.TaskStatus.FAIL, progress3);
				progressService.setStatus(Progress.TaskStatus.NEW, progress4);

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

				progressService.allProgressByUserIdAndMarathonId(
						student1.getId(), marathon2.getId())
						.forEach(System.out::println);

			} catch (ConstraintViolationException e) {
				System.out.println("---CONSTRAINTS VIOLATIONS---\n" +
						e.getMessage());
			} catch (EntityNotFoundException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			System.out.println("*** STOP demo MAIN ***");
		};
	}
*/
}

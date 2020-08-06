package com.softserve.edu.controller;

import com.softserve.edu.exception.ProgressNotFoundException;
import com.softserve.edu.exception.TaskNotFoundException;
import com.softserve.edu.exception.UserNotFoundException;
import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.User;
import com.softserve.edu.service.MarathonService;
import com.softserve.edu.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@AllArgsConstructor
public class StudentController {
    private final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final UserService studentService;
    private final MarathonService marathonService;

    @GetMapping("/create-student")
    public String createStudent(Model model) {
        logger.info("GET '/create-student': Create student");
        model.addAttribute("user", new User());
        return "create-student";
    }

    @PostMapping("students/{marathonId}/add")
    public String createStudent(@PathVariable("marathonId") long marathonId,
                                @Validated @ModelAttribute User user,
                                BindingResult result) {
        logger.info("POST 'students/{}/add': Add student[id='{}'] to marathon[id='{}']",
                marathonId, user.getId(), marathonId);
        if (result.hasErrors()) {
            return "create-student";
        }
        studentService.addUserToMarathon(
                studentService.createOrUpdateUser(user),
                marathonService.getMarathonById(marathonId));
        return "redirect:/students/" + marathonId;
    }

    @GetMapping("students/{marathon_id}/add")
    public String createStudent(@RequestParam("user_id") long userId,
                                @PathVariable("marathon_id") long marathonId) {
        logger.info("GET 'students/{}/add': Add student[id='{}'] to marathon[id='{}']",
                marathonId, userId, marathonId);
        studentService.addUserToMarathon(
                studentService.getUserById(userId),
                marathonService.getMarathonById(marathonId));
        return "redirect:/students/" + marathonId;
    }

    @GetMapping("/students/{marathon_id}/edit/{student_id}")
    public String updateStudent(@PathVariable("marathon_id") long marathonId,
                                @PathVariable("student_id") long studentId,
                                Model model) {
        logger.info("GET '/students/{}/edit/{}': Update student[id='{}'] at marathon[id='{}']",
                marathonId, studentId, studentId, marathonId);
        User user = studentService.getUserById(studentId);
        model.addAttribute("user", user);
        return "update-student";
    }

    @PostMapping("/students/{marathon_id}/edit/{student_id}")
    public String updateStudent(@PathVariable("marathon_id") long marathonId, @PathVariable("student_id") long studentId, @Validated @ModelAttribute User user, BindingResult result) {
        logger.info("POST '/students/{}/edit/{}': Update student[id='{}'] at marathon[id='{}']",
                marathonId, studentId, studentId, marathonId);
        if (result.hasErrors()) {
            return "update-marathon";
        }
        studentService.createOrUpdateUser(user);
        return "redirect:/students/" + marathonId;
    }

    @GetMapping("/students/{marathon_id}/delete/{student_id}")
    public String deleteStudent(@PathVariable("marathon_id") long marathonId, @PathVariable("student_id") long studentId) {
        logger.info("GET 'students/{}/delete/{}': Delete student[id='{}'] at marathon[id='{}']",
                marathonId, studentId, studentId, marathonId);
        studentService.deleteUserFromMarathon(
                studentService.getUserById(studentId),
                marathonService.getMarathonById(marathonId));
        return "redirect:/students/" + marathonId;
    }

    @GetMapping("/students")
    public String getAllStudents(Model model) {
        logger.info("GET '/students': Show all students");
        List<User> students = studentService.getAll();
        model.addAttribute("students", students);
        return "students";
    }

    @GetMapping("/students/edit/{id}")
    public String updateStudent(@PathVariable long id, Model model) {
        logger.info("GET '/students/edit/{}': Update student[id='{}']", id, id);
        User user = studentService.getUserById(id);
        model.addAttribute("user", user);
        return "update-student";
    }

    @PostMapping("/students/edit/{id}")
    public String updateStudent(@PathVariable long id, @Validated @ModelAttribute User user, BindingResult result) {
        logger.info("POST '/students/edit/{}': Update student[id='{}']", id, id);
        if (result.hasErrors()) {
            return "update-marathon";
        }
        studentService.createOrUpdateUser(user);
        return "redirect:/students";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable long id) {
        logger.info("GET '/students/delete/{}': delete student[id='{}']", id, id);
        User student = studentService.getUserById(id);
        for (Marathon marathon : student.getMarathons().toArray(Marathon[]::new)) {
            studentService.deleteUserFromMarathon(student, marathon);
        }
        studentService.deleteUserById(id);
        return "redirect:/students";
    }

    @GetMapping("/students/progress/{id}")
    public String getAllStudentsProgress(@PathVariable long id, Model model) {
        model.addAttribute("id", id);
        logger.error("ProgressNotFoundException");
        throw new ProgressNotFoundException(id);
    }

}

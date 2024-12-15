package student.management.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import student.management.StudentManagement.controller.converter.StudentConverter;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourses;
import student.management.StudentManagement.service.StudentService;

@Controller
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  //  Service経由
  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  //  受講生・コース情報を全件取得
  @GetMapping("/students")
  public String getAllStudents(Model model) {
    List<Student> students = service.getAllStudents();
    List<StudentCourses> studentCourses = service.getAllCourses();
    model.addAttribute("studentList", converter.convertStudentDetails(students, studentCourses));
    return "studentList";
  }


  //  students_courses全件取得
  @GetMapping("/students_courses")
  public List<StudentCourses> getAllCourses() {
    return service.getAllCourses();
  }
}

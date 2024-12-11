package student.management.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import student.management.StudentManagement.controller.converter.StudentConverter;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourses;
import student.management.StudentManagement.domain.StudentDetail;
import student.management.StudentManagement.service.StudentService;

@RestController
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
  public List<StudentDetail> getAllStudents() {
    List<Student> students = service.getAllStudents();
    List<StudentCourses> studentCourses = service.getAllCourses();

    return converter.convertStudentDetails(students, studentCourses);
  }


  //  students_courses全件取得
  @GetMapping("/students_courses")
  public List<StudentCourses> getAllCourses() {
    return service.getAllCourses();
  }
}

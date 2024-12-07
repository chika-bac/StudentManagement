package student.management.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourse;
import student.management.StudentManagement.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;

  //  Service経由
  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  //  students全件取得
  @GetMapping("/students")
  public List<Student> getAllStudents() {
//    リクエストの加工処理入力チェックなどもここでやる
    return service.getAllStudents();
  }

  //  students_courses全件取得
  @GetMapping("/students_courses")
  public List<StudentCourse> getAllCourses() {
    return service.getAllCourses();
  }
}

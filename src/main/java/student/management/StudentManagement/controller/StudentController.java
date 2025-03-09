package student.management.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  public List<StudentDetail> getAllStudents(Model model) {
    List<Student> students = service.getAllStudents();
    List<StudentCourses> studentCourses = service.getAllCourses();
    return converter.convertStudentDetails(students, studentCourses);
  }

  //  個別の学生情報を検索
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable("id") String id) {
    return service.searchStudent(id);
  }

  //  学生新規登録
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
    StudentDetail response = service.registerStudent(studentDetail);
    return ResponseEntity.ok(response);
  }

  //  学生情報を更新
  @PostMapping("/updateStudent")
//  ResponseEntity<T>: POSTが成功・失敗したかどうか
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }
}
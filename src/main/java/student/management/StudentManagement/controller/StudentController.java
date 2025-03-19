package student.management.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import student.management.StudentManagement.domain.StudentDetail;
import student.management.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録・更新などを行うREST APIとして受け付けるController
 */
@RestController
public class StudentController {

  private StudentService service;

  //  Service経由
  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生一覧検索機能。全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生一覧（全件）
   */
  @GetMapping("/studentList")
  public List<StudentDetail> getAllStudents() {
    return service.getAllStudents();
  }

  /**
   * 受講生検索。IDに紐づく任意の受講生情報を取得します。
   *
   * @param id 受講生ID
   * @return 受講生
   */
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
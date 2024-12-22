package student.management.StudentManagement.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import student.management.StudentManagement.controller.converter.StudentConverter;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourses;
import student.management.StudentManagement.domain.StudentDetail;
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

  //  学生新規登録画面を表示
  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    model.addAttribute("studentDetail", new StudentDetail());
    return "registerStudent";
  }

  //  学生新規登録
  @PostMapping("/registerStudent")
//      BindingResult result: 入力チェック内容
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
//      registerStudent.htmlを表示
      return "registerStudent";
    }

//    課題：新規受講生をDBに登録する
//    idにUUIDをセット
    String studentId = UUID.randomUUID().toString();
    studentDetail.getStudent().setId(studentId);

//    idDeletedフラグをセット
    studentDetail.getStudent().setDeleted(false);

    String name = studentDetail.getStudent().getName();
    String kanaName = studentDetail.getStudent().getKanaName();
    String nickname = studentDetail.getStudent().getNickname();
    String email = studentDetail.getStudent().getEmail();
    String city = studentDetail.getStudent().getCity();
    int age = studentDetail.getStudent().getAge();
    String gender = studentDetail.getStudent().getGender();

    service.registerStudent(studentId, name, kanaName, nickname, email,
        city, age, gender, null, false);

//    全件画面にリダイレクト
    return "redirect:/students";
  }
}
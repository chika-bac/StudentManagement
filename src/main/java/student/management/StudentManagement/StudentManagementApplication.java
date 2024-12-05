package student.management.StudentManagement;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

  @Autowired
  private StudentRepository studentRepository;
  @Autowired
  private StudentCourseRepository studentCourseRepository;

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }

  //  students全件取得
  @GetMapping("/students")
  public List<Student> getAllStudents() {
//    JSON形式で返す
    return studentRepository.getAllStudents();
  }

  //  課題：students_courses全件取得
  @GetMapping("/students_courses")
  public List<StudentCourse> getAllCourses() {
    return studentCourseRepository.getAllCourses();
  }
}

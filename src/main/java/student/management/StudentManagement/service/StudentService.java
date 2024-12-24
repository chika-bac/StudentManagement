package student.management.StudentManagement.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourses;
import student.management.StudentManagement.domain.StudentDetail;
import student.management.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  //  コンストラクタ
  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> getAllStudents() {
    return repository.getAllStudents();
  }

  public List<StudentCourses> getAllCourses() {
    return repository.getAllCourses();
  }

  public void registerStudent(StudentDetail studentDetail) {
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

    repository.registerStudent(studentId, name, kanaName, nickname, email, city, age, gender,
        null,
        false);
  }

}

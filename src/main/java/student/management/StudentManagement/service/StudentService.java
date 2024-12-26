package student.management.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
//    idにUUIDをセット
    String studentId = UUID.randomUUID().toString();
    studentDetail.getStudent().setId(studentId);

    repository.registerStudent(studentDetail.getStudent());

//    コース情報を登録（studentCoursesはリストなのでループでセット）
    for (StudentCourses studentCourse : studentDetail.getStudentCourses()) {
      studentCourse.setStudentId(studentId);
      studentCourse.setStartDate(LocalDateTime.now());
      studentCourse.setEndDate(LocalDateTime.now().plusYears(1));
      repository.registerStudentCourses(studentCourse);
    }
  }

}

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

  //  学生・コース情報をstudent_idで検索
  public StudentDetail searchStudent(String id) {
//    学生個別ページの情報を取得
    Student student = repository.searchStudent(id);
    List<StudentCourses> studentCourses = repository.searchStudentCourses(student.getId());

//    取得した学生情報をstudentDetailにセット
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourses(studentCourses);
    return studentDetail;
  }

  //  学生情報・コース情報の更新
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
//    学生情報を更新
    repository.updateStudent(studentDetail.getStudent());

//    コース情報を更新
    for (StudentCourses studentCourse : studentDetail.getStudentCourses()) {
      studentCourse.setStudentId(studentDetail.getStudent().getId());
      repository.updateStudentCourses(studentCourse);
    }
  }
}

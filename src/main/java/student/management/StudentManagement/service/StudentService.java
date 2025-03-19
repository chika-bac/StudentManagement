package student.management.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.management.StudentManagement.controller.converter.StudentConverter;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourses;
import student.management.StudentManagement.domain.StudentDetail;
import student.management.StudentManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービス。受講生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生詳細一覧の検索機能。全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生一覧（全件）
   */
  public List<StudentDetail> getAllStudents() {
    List<Student> studentList = repository.getAllStudents();
    List<StudentCourses> studentCoursesList = repository.getAllCourses();
    return converter.convertStudentDetails(studentList, studentCoursesList);
  }

  /**
   * 受講生詳細検索。 IDに紐づく任意の受講生情報を取得した後、受講生に紐づくコース情報を取得して設定します。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  public StudentDetail searchStudent(String id) {
//    学生個別ページの情報を取得
    Student student = repository.searchStudent(id);
    List<StudentCourses> studentCourses = repository.searchStudentCourses(student.getId());
//    取得した学生情報をstudentDetailにセット
    return new StudentDetail(student, studentCourses);
  }

  /**
   * 受講生詳細の登録を行います。 受講生とコース情報を個別に登録し、コース情報には受講生情報を紐づける値とコース開始日・終了日を設定します。
   *
   * @param studentDetail 受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
//    idにUUIDをセット
    String studentId = UUID.randomUUID().toString();
    studentDetail.getStudent().setId(studentId);

    repository.registerStudent(studentDetail.getStudent());

//    コース情報を登録（studentCoursesはリストなのでループでセット）
    studentDetail.getStudentCourseList().forEach(studentCourse -> {
      initStudentCourse(studentCourse, studentId);
      repository.registerStudentCourse(studentCourse);
    });

    return studentDetail;
  }

  /**
   * コース情報を登録する際の初期情報を設定します。
   *
   * @param studentCourse コース情報
   * @param studentId     受講生ID
   */
  private void initStudentCourse(StudentCourses studentCourse, String studentId) {
    LocalDateTime now = LocalDateTime.now();

    studentCourse.setStudentId(studentId);
    studentCourse.setStartDate(now);
    studentCourse.setEndDate(now.plusYears(1));
  }

  /**
   * 受講生詳細の更新を行います。 受講生情報・コース情報をそれぞれ更新します。
   *
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
//    学生情報
    repository.updateStudent(studentDetail.getStudent());

//    コース情報
    studentDetail.getStudentCourseList().forEach(studentCourse -> {
      studentCourse.setStudentId(studentDetail.getStudent().getId());
      repository.updateStudentCourses(studentCourse);
    });
  }
}

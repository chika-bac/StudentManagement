package student.management.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourses;

/**
 * 受講生テーブル・受講生コース情報テーブルと紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return 受講生一覧（全件）
   */
  List<Student> getAllStudents();

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return 受講生コース情報（全件）
   */
  List<StudentCourses> getAllCourses();

  /**
   * 受講生の検索を行います。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  Student searchStudent(String id);

  /**
   * 受講生IDに紐づくコース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づくコース情報
   */
  List<StudentCourses> searchStudentCourses(String studentId);

  /**
   * 受講生を新規登録します。
   *
   * @param student 受講生
   */
  void registerStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。IDは自動採番を行います。
   *
   * @param studentCourses 受講生コース情報
   */
  void registerStudentCourse(StudentCourses studentCourses);

  /**
   * 受講生を更新します。
   *
   * @param student 受講生
   */
  void updateStudent(Student student);

  /**
   * 受講生コース情報（コース名）を更新します。
   *
   * @param studentCourses 受講生コース情報
   */
  void updateStudentCourses(StudentCourses studentCourses);
}




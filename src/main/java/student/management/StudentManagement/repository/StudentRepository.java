package student.management.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
  @Select("SELECT * FROM students WHERE is_deleted = false")
  List<Student> getAllStudents();

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return 受講生コース情報（全件）
   */
  @Select("SELECT * FROM students_courses")
  List<StudentCourses> getAllCourses();

  /**
   * 受講生の検索を行います。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(String id);

  /**
   * 受講生IDに紐づくコース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づくコース情報
   */
  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentCourses> searchStudentCourses(String studentId);

  //  学生情報を追加
  @Insert("INSERT students VALUES(#{id}, #{name}, #{kanaName}, #{nickname}, #{email}, #{city},"
      + " #{age}, #{gender}, #{remark}, false)")
  void registerStudent(Student student);

  //  コース情報を登録
  @Insert("INSERT students_courses(student_id, course_name, start_date, end_date)"
      + " VALUES(#{studentId}, #{courseName}, #{startDate}, #{endDate})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentCourses(StudentCourses studentCourses);

  //  学生情報を更新
  @Update("UPDATE students SET name = #{name}, kana_name = #{kanaName}, nickname =  #{nickname}, "
      + "email = #{email}, city = #{city}, age = #{age}, gender = #{gender}, remark = #{remark}, is_deleted = #{isDeleted} WHERE id = #{id}")
  void updateStudent(Student student);

  //  コース情報を更新
  @Update("UPDATE students_courses SET course_name = #{courseName} WHERE id = #{id}")
  void updateStudentCourses(StudentCourses studentCourses);
}




package student.management.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourses;

@Mapper
public interface StudentRepository {

  //  受講生情報全件取得
  @Select("SELECT * FROM students")
  List<Student> getAllStudents();

  //  コース全件取得
  @Select("SELECT * FROM students_courses")
  List<StudentCourses> getAllCourses();

  //  学生情報を追加
  @Insert("INSERT students VALUES(#{id}, #{name}, #{kanaName}, #{nickname}, #{email}, #{city}, #{age}, #{gender}, #{remark}, false)")
  void registerStudent(Student student);

  //  コース情報を登録
  @Insert("INSERT students_courses(student_id, course_name, start_date, end_date) VALUES(#{studentId}, #{courseName}, #{startDate}, #{endDate})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentCourses(StudentCourses studentCourses);

  //  受講生情報を1件取得
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findStudentById(String id);

}




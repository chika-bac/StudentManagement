package student.management.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
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
  @Insert("INSERT students VALUES(#{id}, #{name}, #{kanaName}, #{nickname}, #{email}, #{city}, #{age}, #{gender}, #{remark}, #{isDeleted})")
  void registerStudent(String id, String name, String kanaName, String nickname, String email,
      String city, int age, String gender, String remark, boolean isDeleted);
}




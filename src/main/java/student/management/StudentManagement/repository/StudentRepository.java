package student.management.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourse;

@Mapper
public interface StudentRepository {

  //  受講生情報全件取得
  @Select("SELECT * FROM students")
  List<Student> getAllStudents();

  //  #{name}はsearchByNameの引数に渡されたものが入る
  @Select("SELECT * FROM students WHERE name = #{name}")
  List<Student> searchByName(String name);

  //  コース全件取得
  @Select("SELECT * FROM students_courses")
  List<StudentCourse> getAllCourses();
}


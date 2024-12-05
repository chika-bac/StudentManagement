package student.management.StudentManagement;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentCourseRepository {

  //  全件取得
  @Select("SELECT * FROM students_courses")
  List<StudentCourse> getAllCourses();
}

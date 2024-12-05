package student.management.StudentManagement;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentRepository {

  //  全件取得
  @Select("SELECT * FROM students")
  List<Student> getAllStudents();

  //  #{name}はsearchByNameの引数に渡されたものが入る
  @Select("SELECT * FROM students WHERE name = #{name}")
  List<Student> searchByName(String name);
}



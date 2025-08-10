package student.management.StudentManagement.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import student.management.StudentManagement.data.Student;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actual = sut.getAllStudents();
    assertThat(actual.size()).isEqualTo(5);
  }

  @Test
  void 受講生の登録が行えること() {
    Student student = new Student();
    student.setId("f7b82a17-71b4-96f4-09d3-1ffc4bf1ba91");
    student.setName("山田太郎");
    student.setKanaName("ヤマダタロウ");
    student.setNickname("タロウ");
    student.setEmail("taro@example.com");
    student.setCity("東京都新宿区");
    student.setAge(22);
    student.setGender("男性");

    sut.registerStudent(student);

    List<Student> actual = sut.getAllStudents();
    assertThat(actual.size()).isEqualTo(6);
  }

}
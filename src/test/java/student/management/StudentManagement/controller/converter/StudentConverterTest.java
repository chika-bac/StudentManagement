package student.management.StudentManagement.controller.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourses;
import student.management.StudentManagement.domain.StudentDetail;
import student.management.StudentManagement.repository.StudentRepository;

class StudentConverterTest {

  @Mock
  private StudentRepository repository;

  private StudentConverter converter;

  @BeforeEach
  void before() {
    converter = new StudentConverter();
  }

  @Test
  void 受講生情報に紐づくコース情報をマッピングして受講生詳細のリストが作成できること() {
//    モックデータを準備（student）
    List<Student> studentList = new ArrayList<>();

    Student student = new Student();
    student.setId("f7b82a17-71b4-96f4-09d3-1ffc4bf1ba91");
    student.setName("山田太郎");
    student.setKanaName("ヤマダタロウ");
    student.setNickname("タロウ");
    student.setEmail("taro@example.com");
    student.setCity("東京都新宿区");
    student.setAge(22);
    student.setGender("男性");

    studentList.add(student);

//    モックデータを準備（studentCourses）
    List<StudentCourses> studentCourses = new ArrayList<>();

    StudentCourses studentCourse = new StudentCourses();
    studentCourse.setId("1");
    studentCourse.setStudentId("f7b82a17-71b4-96f4-09d3-1ffc4bf1ba91");
    studentCourse.setCourseName("Javaフルコース");
    studentCourse.setStartDate(LocalDateTime.now());
    studentCourse.setEndDate(LocalDateTime.now().plusYears(1));

    studentCourses.add(studentCourse);

//    コンバーターを実行
    List<StudentDetail> result = converter.convertStudentDetails(studentList, studentCourses);

//    検証
    assertEquals(1, result.size());
    assertEquals(student, result.get(0).getStudent());
    assertEquals(1, result.get(0).getStudentCourseList().size());
    assertEquals(studentCourse, result.get(0).getStudentCourseList().get(0));
  }
}
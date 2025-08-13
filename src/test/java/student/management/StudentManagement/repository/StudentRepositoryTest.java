package student.management.StudentManagement.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourses;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  //  ========== 全件検索 ==========
  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actual = sut.getAllStudents();
    assertThat(actual.size()).isEqualTo(5);
  }

  @Test
  void コース情報の全件検索が行えること() {
    List<StudentCourses> actual = sut.getAllCourses();
    assertThat(actual.size()).isEqualTo(6);
  }

  //  ========== ID検索 ==========
  @Test
  void 受講生の検索ができること() {
    String id = "11111111-1111-1111-1111-111111111111";
    Student actual = sut.searchStudent(id)
        .orElseThrow(() -> new AssertionError("学生が見つかりません"));

    assertThat(actual.getName()).isEqualTo("山田 太郎");
  }

  @Test
  void 存在しない受講生IDで検索した場合は空を返すこと() {
    String studentId = "99999999-9999-9999-9999-999999999999";
    Optional<Student> actual = sut.searchStudent(studentId);

    assertThat(actual.isEmpty());
  }

  @Test
  void 受講生IDに紐づくコース情報の検索ができること() {
    String studentId = "11111111-1111-1111-1111-111111111111";
    List<StudentCourses> actual = sut.searchStudentCourses(studentId);

    assertThat(actual.size()).isEqualTo(2);
    assertThat(actual.get(0).getCourseName()).isEqualTo("AWSフルコース");
  }

  //  ========== 登録 ==========
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

  @Test
  void 登録時に既存の受講生IDが重複した場合はプライマリーキー制約違反の例外が発生すること() {
    Student student = new Student();
    // DBに存在するIDで登録
    student.setId("11111111-1111-1111-1111-111111111111");
    student.setName("山田太郎");
    student.setKanaName("ヤマダタロウ");
    student.setNickname("タロウ");
    student.setEmail("taro@example.com");
    student.setCity("東京都新宿区");
    student.setAge(22);
    student.setGender("男性");

//    例外発生の検証
    assertThatThrownBy(() -> sut.registerStudent(student))
        .isInstanceOf(DataIntegrityViolationException.class);
  }

  @Test
  void 受講生コース情報の新規登録ができること() {
    StudentCourses studentCourses = setStudentCourses("22222222-2222-2222-2222-222222222222");

    sut.registerStudentCourse(studentCourses);

    List<StudentCourses> actual = sut.searchStudentCourses("22222222-2222-2222-2222-222222222222");
    assertThat(actual.size()).isEqualTo(2);
    assertThat(actual.get(1).getCourseName()).isEqualTo("Webマーケティングコース");
  }

  @Test
  void 存在しない受講生IDでコース情報を登録すると外部キー制約違反の例外が発生すること() {
    //    存在しない受講生IDをセット
    StudentCourses studentCourses = setStudentCourses("99999999-9999-9999-9999-999999999999");

    //    例外発生の検証
    assertThatThrownBy(() -> sut.registerStudentCourse(studentCourses))
        .isInstanceOf(DataIntegrityViolationException.class);
  }

  //  ========== 更新 ==========
  @Test
  void 受講生情報の更新ができること() {
    String id = "33333333-3333-3333-3333-333333333333";
    Student student = sut.searchStudent(id)
        .orElseThrow(() -> new AssertionError("学生が見つかりません"));

    student.setCity("北海道");
    sut.updateStudent(student);

    Student actual = sut.searchStudent(id)
        .orElseThrow(() -> new AssertionError("学生が見つかりません"));
    assertThat(actual.getCity()).isEqualTo("北海道");
  }

  @Test
  void コース情報の更新ができること() {
    String studentId = "44444444-4444-4444-4444-444444444444";
    List<StudentCourses> studentCourses = sut.searchStudentCourses(studentId);
    studentCourses.get(0).setCourseName("デザインコース");

    sut.updateStudentCourses(studentCourses.get(0));

    List<StudentCourses> actual = sut.searchStudentCourses(studentId);
    assertThat(actual.get(0).getCourseName()).isEqualTo("デザインコース");
  }

  private static StudentCourses setStudentCourses(String studentId) {
    StudentCourses studentCourses = new StudentCourses();
    studentCourses.setStudentId(studentId);
    studentCourses.setCourseName("Webマーケティングコース");
    studentCourses.setStartDate(LocalDateTime.now());
    studentCourses.setEndDate(LocalDateTime.now().plusYears(1));
    return studentCourses;
  }
}
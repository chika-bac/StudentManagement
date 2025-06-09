package student.management.StudentManagement.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import student.management.StudentManagement.controller.converter.StudentConverter;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourses;
import student.management.StudentManagement.domain.StudentDetail;
import student.management.StudentManagement.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  //  テスト前に呼ばれる、セットアップ
  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }


  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出せること() {
//    モックデータを準備（repository, converterの呼び出し確認のため）
    List<Student> studentList = new ArrayList<>();
    List<StudentCourses> studentCoursesList = new ArrayList<>();

//    想定の挙動を設定
//    repository.getAllStudents()を呼び出すとstudentListを返す
    when(repository.getAllStudents()).thenReturn(studentList);
    when(repository.getAllCourses()).thenReturn(studentCoursesList);

//    実際にテスト対象のメソッドを呼ぶ
    sut.getAllStudents();

//    確認
//    sut.getAllStudents()でrepository.getAllStudents() が1回呼ばれたか確認
    verify(repository, times(1)).getAllStudents();
    verify(repository, times(1)).getAllCourses();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCoursesList);
  }

  @Test
  void 受講生詳細の検索_リポジトリの処理が適切に呼び出せること() {
    String id = "1";

//    モックデータを準備
    Student student = new Student();
    List<StudentCourses> studentCourses = new ArrayList<>();

//    想定される挙動
    when(repository.searchStudent(id)).thenReturn(Optional.of(student));
    when(repository.searchStudentCourses(student.getId())).thenReturn(studentCourses);

//    実際に呼び出し
    sut.searchStudent(id);

//    確認
    verify(repository, times(1)).searchStudent(id);
    verify(repository, times(1)).searchStudentCourses(student.getId());
  }

  @Test
  void 受講生登録_リポジトリの処理が適切に呼び出せること() {
    //    モックデータを準備
    Student student = new Student();
    List<StudentCourses> studentCourses = new ArrayList<>();
    StudentDetail studentDetail = new StudentDetail(student, studentCourses);

    //    コース情報をセット
    StudentCourses course1 = new StudentCourses();
    StudentCourses course2 = new StudentCourses();
    studentCourses.add(course1);
    studentCourses.add(course2);

//    実際に呼び出し
    sut.registerStudent(studentDetail);

//    確認
    verify(repository, times(1)).registerStudent(studentDetail.getStudent());
    verify(repository, times(1)).registerStudentCourse(course1);
    verify(repository, times(1)).registerStudentCourse(course2);

  }

  @Test
  void 受講生詳細の更新_リポジトリの処理が適切に呼び出せること() {
//    モックデータを準備
    Student student = new Student();
    List<StudentCourses> studentCourses = new ArrayList<>();
    StudentDetail studentDetail = new StudentDetail(student, studentCourses);

    //    コース情報をセット
    StudentCourses course1 = new StudentCourses();
    StudentCourses course2 = new StudentCourses();
    studentCourses.add(course1);
    studentCourses.add(course2);

//    実際に呼び出し
    sut.updateStudent(studentDetail);

//    確認
    verify(repository, times(1)).updateStudent(studentDetail.getStudent());
    verify(repository, times(1)).updateStudentCourses(course1);
    verify(repository, times(1)).updateStudentCourses(course2);

  }

}
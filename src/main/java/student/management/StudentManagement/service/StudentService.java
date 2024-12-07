package student.management.StudentManagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourse;
import student.management.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  //  コンストラクタ
  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> getAllStudents() {
    repository.getAllStudents();
//    課題：年齢が30代のみを抽出、抽出したリストをコントローラーに渡す
    return repository.getAllStudents();
  }

  public List<StudentCourse> getAllCourses() {
//    課題：Javaコースのコース情報を抽出、抽出したリストをコントローラーに渡す
    return repository.getAllCourses();
  }
}

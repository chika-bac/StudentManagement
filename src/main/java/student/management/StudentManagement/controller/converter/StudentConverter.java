package student.management.StudentManagement.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourses;
import student.management.StudentManagement.domain.StudentDetail;

@Component
public class StudentConverter {

  //    全受講生情報・コースのリストを作成
  public List<StudentDetail> convertStudentDetails(List<Student> students,
      List<StudentCourses> studentCourses) {
    List<StudentDetail> studentDetails = new ArrayList<>();

    students.forEach(student -> {
      //      受講生情報・コース情報を持つ受講生データを入れるインスンタンスを作成
      StudentDetail studentDetail = new StudentDetail();
      //      受講生情報は先にセットしておく
      studentDetail.setStudent(student);

      //      受講生に紐づくコース情報を入れるリスト
      List<StudentCourses> convertStudentCourses = studentCourses.stream()
          //      受講生に紐づくコース情報があればリストに追加
          .filter(studentCourse -> student.getId().equals(studentCourse.getStudentId()))
          .collect(Collectors.toList());

      //      コース情報（リスト）を受講生データにセット
      studentDetail.setStudentCourses(convertStudentCourses);
      //      全受講生リストに1受講生のデータを追加
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }
}

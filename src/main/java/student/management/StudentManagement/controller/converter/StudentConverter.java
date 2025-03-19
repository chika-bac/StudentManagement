package student.management.StudentManagement.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourses;
import student.management.StudentManagement.domain.StudentDetail;

/**
 * 受講生詳細を受講生やコース情報、もしくはその逆の変換を行うコンバーターです。
 */
@Component
public class StudentConverter {

  /**
   * 受講生に紐づくコース情報をマッピングします。 コース情報は受講生に対して複数存在するので、ループを回して受講生情報を組み立てます。
   *
   * @param studentList    受講生一覧
   * @param studentCourses コース情報のリスト
   * @return 受講生情報のリスト
   */
  public List<StudentDetail> convertStudentDetails(List<Student> studentList,
      List<StudentCourses> studentCourses) {
    List<StudentDetail> studentDetails = new ArrayList<>();

    studentList.forEach(student -> {
      //      受講生情報・コース情報を持つ受講生データを入れるインスンタンスを作成
      StudentDetail studentDetail = new StudentDetail();
      //      受講生情報は先にセットしておく
      studentDetail.setStudent(student);

      //      受講生に紐づくコース情報を入れるリスト
      List<StudentCourses> convertStudentCourseList = studentCourses.stream()
          //      受講生に紐づくコース情報があればリストに追加
          .filter(studentCourse -> student.getId().equals(studentCourse.getStudentId()))
          .collect(Collectors.toList());

      //      コース情報（リスト）を受講生データにセット
      studentDetail.setStudentCourseList(convertStudentCourseList);
      //      全受講生リストに1受講生のデータを追加
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }
}

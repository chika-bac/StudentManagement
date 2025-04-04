package student.management.StudentManagement.domain;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.data.StudentCourses;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

  @Valid
  private Student student;
  private List<StudentCourses> studentCourseList;
}

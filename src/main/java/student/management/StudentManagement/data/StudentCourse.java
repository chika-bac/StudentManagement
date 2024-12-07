package student.management.StudentManagement.data;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {

  private String studentId;
  private String courseName;
  private Date startDate;
  private Date endDate;
}

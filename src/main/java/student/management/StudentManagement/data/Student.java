package student.management.StudentManagement.data;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class Student {

  private String id;
  @NotNull
  private String name;
  @NotNull
  private String kanaName;
  private String nickname;
  @NotNull
  private String email;
  @NotNull
  private String city;
  @Range(min = 0, max = 100)
  @NotNull
  private int age;
  private String gender;
  private String remark;
  private boolean isDeleted;
}

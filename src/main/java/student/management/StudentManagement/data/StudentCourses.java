package student.management.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourses {

  @Schema(description = "コースID", example = "1")
  private String id;

  @Schema(description = "受講生ID", example = "f7b82a17-71b4-96f4-09d3-1ffc4bf1ba91")
  private String studentId;

  @Schema(description = "コース名", example = "Javaコース")
  @NotNull
  private String courseName;

  @Schema(description = "受講開始日", example = "2025-03-28")
  private LocalDateTime startDate;

  @Schema(description = "受講修了日。受講開始日の1年後の日付が自動で入ります。", example = "2026-03-28")
  private LocalDateTime endDate;
}

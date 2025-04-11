package student.management.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Schema(description = "受講生情報")
@Getter
@Setter
public class Student {

  @Schema(description = "ユーザーID", example = "f7b82a17-71b4-96f4-09d3-1ffc4bf1ba91")
  private String id;

  @Schema(description = "ユーザー名", example = "山田太郎")
  @NotNull
  private String name;

  @Schema(description = "フリガナ", example = "ヤマダタロウ")
  @NotNull
  private String kanaName;

  @Schema(description = "ニックネーム", example = "タロウ")
  private String nickname;

  @Schema(description = "メールアドレス", example = "taro@example.com")
  @NotNull
  private String email;

  @Schema(description = "居住地域", example = "東京都新宿区")
  @NotNull
  private String city;

  @Schema(description = "年齢", example = "22")
  @Range(min = 0, max = 100)
  @NotNull
  private int age;

  @Schema(description = "性別", example = "女性")
  private String gender;

  @Schema(description = "備考", example = "来年転職予定")
  private String remark;

  @Schema(description = "削除フラグ", example = "false")
  private boolean isDeleted;
}

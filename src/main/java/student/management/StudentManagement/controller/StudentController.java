package student.management.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import student.management.StudentManagement.domain.StudentDetail;
import student.management.StudentManagement.exception.TestException;
import student.management.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録・更新などを行うREST APIとして受け付けるController
 */
@Validated
@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  @Operation(
      summary = "一覧検索",
      description = "　受講生管理の一覧を検索します。全件検索を行うので、条件指定は行いません。"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "成功",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = StudentDetail.class)
          )
      ),
      @ApiResponse(
          responseCode = "500",
          description = "API内部でエラーが発生した場合",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class)
          )
      )

  })
  @GetMapping("/studentList")
  public List<StudentDetail> getAllStudents() {
    return service.getAllStudents();
  }

  @Operation(
      summary = "受講生詳細の検索",
      description = "　IDに紐づく任意の受講生情報を取得します。"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "成功",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = StudentDetail.class)
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "ユーザーが見つかりません。",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class)
          )
      ),
      @ApiResponse(
          responseCode = "500",
          description = "IDのバリデーションエラー",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class)
          )
      )
  })
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(
      @Parameter(description = "ユーザーID", required = true, example = "1234")
      @PathVariable("id") @Size(min = 1, max = 36) String id
  ) {
    return service.searchStudent(id);
  }

  @Operation(
      summary = "受講生登録",
      description = "　受講生を登録します。"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "201",
          description = "受講生が登録されました。"
      ),
      @ApiResponse(
          responseCode = "400",
          description = "リクエストデータのバリデーションエラー"
      ),
  })
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(
      @RequestBody
      @Valid
      @Parameter(description = "新規登録する受講生詳細情報", required = true) StudentDetail studentDetail
  ) {
    StudentDetail response = service.registerStudent(studentDetail);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Operation(
      summary = "受講生詳細の更新",
      description = "　受講生詳細情報を更新します。削除フラグの更新もここで行います（論理削除）"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "受講生情報が更新されました。"
      ),
      @ApiResponse(
          responseCode = "400",
          description = "リクエストデータのバリデーションエラー"
      ),
  })
  @PutMapping("/updateStudent")
//  ResponseEntity<T>: POSTが成功・失敗したかどうか
  public ResponseEntity<StudentDetail> updateStudent(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          required = true,
          content = @Content(
              schema = @Schema(implementation = StudentDetail.class)
          )
      )
      @RequestBody
      @Valid
      StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok(studentDetail);
  }

  
  @Operation(
      summary = "例外発生用",
      description = "例外発生用のメソッドです。"
  )
  @ApiResponse(
      responseCode = "400",
      description = "内部APIのエラー"
  )
  @GetMapping("/studentException")
  public String getTestException() throws TestException {
    throw new TestException("現在このAPIは利用できません。新しいURLは「students」です。");
  }
}
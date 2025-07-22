package student.management.StudentManagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import student.management.StudentManagement.data.Student;
import student.management.StudentManagement.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索ができること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/studentList"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));

    verify(service, times(1)).getAllStudents();
  }

  @Test
  void 受講生詳細の検索ができて空で返ってくること() throws Exception {
    String id = "1";

    mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", id))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudent(id);
  }

  @Test
  void 受講生詳細の新規登録ができて空で返ってくること() throws Exception {
    mockMvc.perform(post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON).content(
                """
                    {
                        "student": {
                            "name": "山田花子",
                            "kanaName": "ヤマダハナコ",
                            "nickname": "はなこさん",
                            "email": "test@example.com",
                            "city": "長崎県佐世保市",
                            "age": 20,
                            "gender": "女性",
                            "remark": "テスト"
                        },
                        "studentCourseList": [
                          {
                            "courseName": "Javaフルコース"
                          }
                        ]
                    }
                    """
            ))
        .andExpect(status().isCreated());

    verify(service, times(1)).registerStudent(any());
  }

  @Test
  void 受講生詳細の更新ができて空で返ってくること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON).content(
                """
                    {
                        "student": {
                            "id": "1",
                            "name": "山田花子",
                            "kanaName": "ヤマダハナコ",
                            "nickname": "はなこさん",
                            "email": "test@example.com",
                            "city": "東京都新宿区",
                            "age": 20,
                            "gender": "女性",
                            "remark": "テスト"
                        },
                        "studentCourseList": [
                          {
                            "id": "1",
                            "courseName": "Javaフルコース"
                          }
                        ]
                    }
                    """
            ))
        .andExpect(status().isOk());

    verify(service, times(1)).updateStudent(any());
  }

  @Test
  void 受講生情報を適切に入力した時に入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId("f7b82a17-71b4-96f4-09d3-1ffc4bf1ba91");
    student.setName("山田太郎");
    student.setKanaName("ヤマダタロウ");
    student.setNickname("タロウ");
    student.setEmail("taro@example.com");
    student.setCity("東京都新宿区");
    student.setAge(22);
    student.setGender("男性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生情報のメールアドレスの形式が不正だった場合に入力チェックにかかること() {
    Student student = new Student();
    student.setId("f7b82a17-71b4-96f4-09d3-1ffc4bf1ba91");
    student.setName("山田太郎");
    student.setKanaName("ヤマダタロウ");
    student.setNickname("タロウ");
//    不正なメールアドレス形式をセット
    student.setEmail("taroexample.com");
    student.setCity("東京都新宿区");
    student.setAge(22);
    student.setGender("男性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations)
        .extracting("message")
        .containsOnly("有効なメールアドレスを入力してください。");
  }

  @Test
  void 受講生詳細の例外APIが実行できてステータスが400で返ってくること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/studentException"))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string("現在このAPIは利用できません。新しいURLは「students」です。"));
  }
}
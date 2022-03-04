package team.jlpt.dto;

import lombok.Getter;
import lombok.Setter;
import team.jlpt.config.PasswordConfig;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class MemberDTO {

    @Getter
    @Setter
    public static class Join{
        @NotBlank(message = "이름을 입력해주세요.")
        private String name;

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = PasswordConfig.passwordRegexp, message = "비밀번호는 숫자, 영어, 특수문자 각각 1개 이상 포함하고 5자 ~ 128자로 입력해주세요.")
        private String password;
    }

    @Getter
    @Setter
    public static class Select{
        private String name;
        private String email;
    }

    public static class Update{
        private String name;
        private String password;
    }

}
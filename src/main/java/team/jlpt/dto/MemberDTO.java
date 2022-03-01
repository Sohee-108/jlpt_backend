package team.jlpt.dto;

import lombok.Getter;
import lombok.Setter;

public class MemberDTO {

    @Getter
    @Setter
    public static class Join{
        private String name;
        private String email;
        private String password;
    }

    public static class Update{
        private String name;
        private String password;
    }

}

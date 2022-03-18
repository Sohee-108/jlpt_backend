package team.jlpt.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import team.jlpt.dto.DictionaryDTO;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String email;

    @Setter
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    private LocalDateTime createTime = LocalDateTime.now();

    private String provider;    //구글, 네이버, 카카오 oauth2로그인일때 채워짐

    private String providerId;

    @Transient  //DB에 적용되지않음
    @Setter
    private List<DictionaryDTO.Response> todayWordsResponse;


    //api
    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void changeName(String updateName){
        this.name = updateName;
    }

    @Builder(builderClassName = "Oauth2Register", builderMethodName = "Oauth2Register")
    public Member(String name, String email, String password, String provider, String providerId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.provider = provider;
        this.providerId = providerId;
    }
}
package team.jlpt.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dictionary {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dictionary_id")
    private Long id;

    private String word;

    private String meaning; //의미

    private String speak;   //발음
}

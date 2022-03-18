package team.jlpt.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Learn {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "learn_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dictionary_id")
    private Dictionary dictionary;

    private boolean memorized;

    @Builder
    public Learn(Long id, Member member, Dictionary dictionary, boolean memorized) {
        this.id = id;
        this.member = member;
        this.dictionary = dictionary;
        this.memorized = memorized;
    }
}
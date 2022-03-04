package team.jlpt.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import team.jlpt.dto.MemberDTO;
import team.jlpt.entity.Member;
import team.jlpt.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 회원가입
     * @param memberDTO
     * @return
     */
    public Long join(MemberDTO.Join memberDTO) {
        String password = bCryptPasswordEncoder.encode(memberDTO.getPassword());

        Member member = new Member(
                memberDTO.getName(),
                memberDTO.getEmail(),
                password
        );

        Member saveMember = memberRepository.save(member);
        return saveMember.getId();
    }

    /**
     * 회원조회
     * @param memberId
     * @return
     */
    public MemberDTO.Select select(Long memberId){
        Member member = memberRepository.findById(memberId).orElse(null);

        MemberDTO.Select select = new MemberDTO.Select();
        select.setEmail(member.getEmail());
        select.setName(member.getName());

        return select;
    };

    /**
     * 회원 이름 수정
     * 수정할때 Repository에 저장하지 않아도됨!! JPA가 알아서 update query를 날려줌
     * @param updateName
     */
    public void update(Long memberId, String updateName) {
        Member member = memberRepository.findById(memberId).orElse(null);
        member.changeName(updateName);
    }

    /**
     *
     * @param memberId
     */
    public void delete(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}
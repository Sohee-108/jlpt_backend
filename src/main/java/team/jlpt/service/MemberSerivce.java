package team.jlpt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import team.jlpt.dto.MemberDTO;
import team.jlpt.entity.Member;
import team.jlpt.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberSerivce {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(MemberDTO.Join memberDTO) {

        String password = bCryptPasswordEncoder.encode(memberDTO.getPassword());

        Member member = new Member(
                memberDTO.getName(),
                memberDTO.getEmail(),
                password
        );

        memberRepository.save(member);
    }
}

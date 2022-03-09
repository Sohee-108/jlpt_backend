package team.jlpt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import team.jlpt.entity.Member;
import team.jlpt.repository.MemberRepository;


@Controller
public class MemberController {
    @Autowired private MemberRepository memberRepository;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/loginForm")
    public String loginForm(){
        return "login";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute Member member){
        String encodePassword = bCryptPasswordEncoder.encode(member.getPassword());
        member.setPassword(encodePassword);

        memberRepository.save(member);
        return "redirect:/loginForm";
    }

}

package team.jlpt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.jlpt.dto.MemberDTO;
import team.jlpt.service.MemberSerivce;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberSerivce memberService;

    /**
     * 회원가입 - 저장
     * /api/member
     */
    @PostMapping("")
    public ResponseEntity join(@RequestBody MemberDTO.Join memberDTO){
        memberService.join(memberDTO);
        return ResponseEntity.status(HttpStatus.OK).body(memberDTO);
    }


}

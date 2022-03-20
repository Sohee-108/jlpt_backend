package team.jlpt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.jlpt.dto.MemberDTO;
import team.jlpt.service.MemberService;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    /**
     * 회원가입 - 저장
     * 키워드: PostMapping, @RequestBody, BindingResult
     * 개선점: memberDTO, memberService.join return 값
     * /api/member
     */
    @PostMapping("")
    public ResponseEntity join(@Validated @RequestBody MemberDTO.Join memberDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult);
        }
        Long memberId = memberService.join(memberDTO);
        return ResponseEntity.status(HttpStatus.OK).body(memberId);
    }

    /**
     * RestAPI방식으로 회원 조회
     * 키워드: GetMapping, @PathValirable, ResponseEntity
     * 회원조회
     */
    @GetMapping("/{memberId}")
    public ResponseEntity select(@PathVariable Long memberId){
        MemberDTO.Select select = memberService.select(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(select);
    }

    /**
     * 회원 이름 수정
     */
    @PutMapping("/{memberId}/{updateName}")
    public ResponseEntity update(@PathVariable Long memberId, @PathVariable String updateName){
        memberService.update(memberId, updateName);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * 회원 삭제
     */
    @DeleteMapping("/{memberId}")
    public ResponseEntity delete(@PathVariable Long memberId){
        memberService.delete(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }




}


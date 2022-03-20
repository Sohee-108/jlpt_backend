package team.jlpt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.jlpt.auth.PrincipalDetails;
import team.jlpt.dto.DictionaryDTO;
import team.jlpt.entity.Problem;
import team.jlpt.service.DictionaryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DictionaryApiController {
    private final DictionaryService dictionaryService;

    /**
     * 오늘의 단어 반환
     */
    @GetMapping({"/words", "/words/{count}"})
    public ResponseEntity todayWords(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                     @PathVariable Optional<Integer> count
    ){
        List<DictionaryDTO.Response> todayWords = dictionaryService.getTodayWords(principalDetails, count);
        return ResponseEntity.status(HttpStatus.OK).body(todayWords);
    }

    /**
     * 오늘의 단어 테스트 반환
     */
    @GetMapping("/words/test")
    public ResponseEntity test(@AuthenticationPrincipal PrincipalDetails principalDetails){
        List<Problem> todayTest = dictionaryService.getTodayTest(principalDetails);
        return ResponseEntity.status(HttpStatus.OK).body(todayTest);
    }




}

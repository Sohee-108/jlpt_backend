package team.jlpt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.jlpt.dto.DictionaryDTO;
import team.jlpt.entity.Problem;
import team.jlpt.service.DictionaryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dic")
public class DictionaryApiController {
    private final DictionaryService dictionaryService;

    @GetMapping({"/words/{memberId}", "/words/{memberId}/{count}"})
    public ResponseEntity todayWords(@PathVariable Long memberId,
                                     @PathVariable Optional<Integer> count
    ){
        List<DictionaryDTO.Response> todayWords = dictionaryService.getTodayWords(memberId, count);
        return ResponseEntity.status(HttpStatus.OK).body(todayWords);
    }

    @GetMapping("/test/{memberId}")
    public ResponseEntity test(@PathVariable Long memberId){
        List<Problem> todayTest = dictionaryService.getTodayTest(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(todayTest);
    }
}

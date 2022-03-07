package team.jlpt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.jlpt.dto.DictionaryDTO;
import team.jlpt.service.DictionaryService;
import team.jlpt.util.Crawling;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/learn")
public class DictionaryApiController {
    private final Crawling crawling;
    private final DictionaryService learnService;

    @GetMapping({"/today/{memberId}", "/today/{memberId}/{count}"})
    public ResponseEntity todayWords(@PathVariable Long memberId,
                                     @PathVariable Optional<Integer> count
    ){
        List<DictionaryDTO.Response> todayWords = learnService.getTodayWords(memberId, count);
        return ResponseEntity.status(HttpStatus.OK).body(todayWords);
    }


}

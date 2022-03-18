package team.jlpt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.jlpt.service.LearnService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/memorize")
public class LearnApiController {
    private final LearnService learnService;

    /**
     * 암기완료한 단어
     */
    @PostMapping("/member/{memberId}/word/{wordId}")
    public ResponseEntity memorize(@PathVariable Long memberId, @PathVariable Long wordId){
        learnService.memorized(memberId, wordId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


}

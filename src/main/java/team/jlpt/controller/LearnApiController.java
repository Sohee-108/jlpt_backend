package team.jlpt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.jlpt.auth.PrincipalDetails;
import team.jlpt.service.LearnService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LearnApiController {
    private final LearnService learnService;

    /**
     * 암기완료한 단어
     */
    @PostMapping("/word/{wordId}/memorize")
    public ResponseEntity memorize(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long wordId){
        learnService.memorized(principalDetails, wordId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


}

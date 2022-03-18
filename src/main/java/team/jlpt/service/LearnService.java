package team.jlpt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jlpt.entity.Learn;
import team.jlpt.repository.LearnRepository;

@Service
@RequiredArgsConstructor
public class LearnService {
    private final LearnRepository learnRepository;

    /**
     * 암기 완료처리
     */
    public void memorized(Long memberId, Long wordId) {

    }
}

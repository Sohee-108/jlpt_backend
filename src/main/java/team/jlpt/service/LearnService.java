package team.jlpt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jlpt.auth.PrincipalDetails;
import team.jlpt.dto.DictionaryDTO;
import team.jlpt.entity.Dictionary;
import team.jlpt.entity.Learn;
import team.jlpt.repository.DictionaryRepository;
import team.jlpt.repository.LearnRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LearnService {
    private final DictionaryRepository dictionaryRepository;
    private final LearnRepository learnRepository;

    /**
     * 암기 완료처리
     */
    public void memorized(PrincipalDetails principalDetails, Long wordId) {
        Dictionary dictionary = dictionaryRepository.findById(wordId).orElse(null);

        Learn learn = Learn.builder()
                .member(principalDetails.getMember())
                .dictionary(dictionary)
                .memorized(true).build();

        learnRepository.save(learn);
    }
}

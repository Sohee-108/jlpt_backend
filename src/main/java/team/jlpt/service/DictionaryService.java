package team.jlpt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jlpt.dto.DictionaryDTO;
import team.jlpt.entity.Problem;
import team.jlpt.repository.DictionaryProjection;
import team.jlpt.repository.DictionaryRepository;
import team.jlpt.util.Crawling;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DictionaryService {
    private final Crawling crawling;
    private final DictionaryRepository dictionaryRepository;
    private Map<Long, List<String>> tempMemberMap = new HashMap<>();

    /**
     * 오늘의 단어 반환
     * 회원별 데이터 저장
     */
    public List<DictionaryDTO.Response> getTodayWords(Long memberId, Optional<Integer> optionalCount) {
        int count = 10;
        if(optionalCount.isPresent()){
            count = optionalCount.get();
        }
        List<DictionaryProjection> allNotMemorized = dictionaryRepository.findAllNotMemorized(memberId, count);
        List<DictionaryDTO.Response> todayWords = allNotMemorized.stream()
                .map(dictionary -> DictionaryDTO.entityToDto(dictionary))
                .collect(Collectors.toList());

        List<String> todayWordsString = todayWords.stream()
                .map(todayWord -> todayWord.getWord())
                .collect(Collectors.toList());

        tempMemberMap.put(memberId, todayWordsString);
        //추후 PrincipalDetails에 넣어서 회원별 오늘의 단어 관리

        return todayWords;
    }


    /**
     * 오늘의 단어 테스트 반환
     */
    public List<Problem> getTodayTest(Long memberId) {
        List<String> words = tempMemberMap.get(memberId);
        crawling.init(words);
        List<Problem> problems = crawling.getProblems();
        return problems;
    }
}

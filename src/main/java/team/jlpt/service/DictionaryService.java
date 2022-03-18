package team.jlpt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jlpt.auth.PrincipalDetails;
import team.jlpt.dto.DictionaryDTO;
import team.jlpt.entity.Member;
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

    /**
     * 오늘의 단어 반환
     * 회원별 데이터 저장
     */
    public List<DictionaryDTO.Response> getTodayWords(PrincipalDetails principalDetails, Optional<Integer> optionalCount) {
        int count = 10;
        if(optionalCount.isPresent()){
            count = optionalCount.get();
        }

        Member member = principalDetails.getMember();
        List<DictionaryProjection> entityList = dictionaryRepository.findAllNotMemorized(member.getId(), count);
        List<DictionaryDTO.Response> todayWordsResponse = DictionaryDTO.entityToDtoList(entityList);

        member.setTodayWordsResponse(todayWordsResponse);
        return todayWordsResponse;
    }


    /**
     * 오늘의 단어 테스트 반환
     */
    public List<Problem> getTodayTest(PrincipalDetails principalDetails) {
        List<DictionaryDTO.Response> todayWordsResponse = principalDetails.getMember().getTodayWordsResponse();
        List<String> list = todayWords(todayWordsResponse);
        crawling.init(list);
        List<Problem> problems = crawling.getProblems();
        return problems;
    }


    private List<String> todayWords(List<DictionaryDTO.Response> todayWordsResponse){
        return todayWordsResponse.stream()
                .map(todayWord -> todayWord.getWord())
                .collect(Collectors.toList());
    }
}

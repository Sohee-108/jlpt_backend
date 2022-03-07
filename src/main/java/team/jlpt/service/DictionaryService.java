package team.jlpt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.jlpt.dto.DictionaryDTO;
import team.jlpt.repository.DictionaryProjection;
import team.jlpt.repository.DictionaryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DictionaryService {
    private final DictionaryRepository learnRepository;

    public List<DictionaryDTO.Response> getTodayWords(Long memberId, Optional<Integer> optionalCount) {
        int count = 10;
        if(optionalCount.isPresent()){
            count = optionalCount.get();
        }
        System.out.println(count);
        List<DictionaryProjection> allNotMemorized = learnRepository.findAllNotMemorized(memberId, count);
        List<DictionaryDTO.Response> collect = allNotMemorized.stream()
                .map(dictionary -> DictionaryDTO.entityToDto(dictionary))
                .collect(Collectors.toList());
        return collect;
    }


}

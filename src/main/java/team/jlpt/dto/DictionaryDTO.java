package team.jlpt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import team.jlpt.repository.DictionaryProjection;

import java.util.List;
import java.util.stream.Collectors;

public class DictionaryDTO {
    @Getter
    @ToString
    @Builder
    public static class Response{
        private Long id;
        private String word;
        private String meaning;
        private String speak;
    }

    public static DictionaryDTO.Response entityToDto(DictionaryProjection dictionary){
        return DictionaryDTO.Response.builder()
                .id(dictionary.getDictionary_id())
                .word(dictionary.getWord())
                .meaning(dictionary.getMeaning())
                .speak(dictionary.getSpeak()).build();
    }

    public static List<Response> entityToDtoList(List<DictionaryProjection> entityList){
        List<DictionaryDTO.Response> todayWords = entityList.stream()
                .map(dictionary -> DictionaryDTO.entityToDto(dictionary))
                .collect(Collectors.toList());
        return todayWords;
    }
}

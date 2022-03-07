package team.jlpt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import team.jlpt.repository.DictionaryProjection;

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
}

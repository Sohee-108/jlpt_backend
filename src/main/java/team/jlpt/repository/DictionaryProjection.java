package team.jlpt.repository;

public interface DictionaryProjection {
    Long getDictionary_id();
    String getMeaning();
    String getSpeak();
    String getWord();

    /**
     * DB 각 필드명에 맞추어서 결과를 가져오게된다.
     * getDictionaryId() 는 dictionaryId 라는 필드를 찾게된다. -> null반환
     */
}

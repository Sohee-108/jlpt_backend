package team.jlpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team.jlpt.entity.Dictionary;
import team.jlpt.entity.Learn;

import java.util.List;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {

//    @Query("select d from Learn l right join l.dictionary d where l.id is null")
    @Query(nativeQuery = true, value = "select d.* " +
            "from " +
            "(select m.*, l.* " +
            "from member as m " +
            "right outer join learn as l " +
            "on m.member_id = l.member_id " +
            "where m.member_id = :memberId) as l " +
            "right outer join dictionary as d " +
            "on d.dictionary_id = l.dictionary_id " +
            "where l.dictionary_id is null " +
            "order by random() limit :count")
    List<DictionaryProjection> findAllNotMemorized(@Param("memberId") Long memberId, @Param("count") int count);
}

package team.jlpt;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import team.jlpt.util.Crawling;

import java.util.Arrays;

//@ActiveProfiles({"local"})
@SpringBootTest
public class CrawlingTest {
    @Autowired private Crawling crawling;

    @Test
    void test(){
        String[] words = {"腕を磨く"};
        crawling.init(Arrays.asList(words));
        Assertions.assertThat(crawling.getProblems().size()).isEqualTo(1);
    }

//    @Test
//    void testList(){
//        String[] words = {"科学", "都会", "不味い", "返す", "後", "包む", "守る", "経済", "かなり", "引"};
//        crawling.init(Arrays.asList(words));
//    }
}

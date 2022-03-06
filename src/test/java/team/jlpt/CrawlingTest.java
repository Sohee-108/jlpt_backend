package team.jlpt;

import org.junit.jupiter.api.Test;

public class CrawlingTest {

    @Test
    void testExample(){
        CrawlingExample crawling = new CrawlingExample();
        crawling.init();
    }

    @Test
    void test(){
        String[] words = {"腕を磨く"};
        Crawling crawling = new Crawling(words);
        crawling.init();
    }

    @Test
    void testList(){
        String[] words = {"科学", "都会", "不味い", "返す", "後", "包む", "守る", "経済", "かなり", "引"};
        Crawling crawling = new Crawling(words);
        crawling.init();
    }
}

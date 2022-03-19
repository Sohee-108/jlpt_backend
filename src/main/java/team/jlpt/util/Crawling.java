package team.jlpt.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import team.jlpt.entity.Problem;

import java.util.*;

@Component
public class Crawling {
    private List<String> words;
    private WebDriver driver;
    @Getter
    private List<Problem> problems = new ArrayList<>();

    @Value("${webdriver}")
    private String webDriverValue;


    public void init(List<String> words){
        this.words = words;
        System.setProperty("webdriver.chrome.driver", webDriverValue);

        //속도개선
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");       //팝업안띄움
        options.addArguments("headless");                       //브라우저 안띄움
        options.addArguments("--disable-gpu");
        options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받을거야.

        //linux에서 동작하게끔
        options.addArguments("no-sandbox");
        options.addArguments("disable-dev-shm-usage");

        driver = new ChromeDriver(options);

        try{
            for (String word : words) {
                Problem problem = setData(word);
                problems.add(problem);
            }
            driver.close();
            driver.quit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Problem setData(String word) throws InterruptedException {

        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);    //최대 10초 기다린다.
        driver.get("https://yourei.jp/" + word);    //브라우저에서 url로 이동한다.

//        Thread.sleep(1000); // 단어 10개 테스트시 thread는 33초, webDriverWait은 24초

        webDriverWait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("#sentence-example-list .sentence-list li#sentence-1"))
        );
        // html document에 존재하는지 여부만 체크한다.
        // https://velog.io/@dahunyoo/Some-method-of-ExpectedCondition-on-Selenium-Java


        WebElement sentence = driver.findElement(By.cssSelector("#sentence-example-list .sentence-list li#sentence-1"));
        Problem problem = new Problem();

        // ... 이후는 제거
        int find = sentence.getText().indexOf("...");
        if(find == -1){
            find = sentence.getText().length();
        }
        problem.setSentence(sentence.getText().substring(0, find));

        // 훈독 가져오기
        List<WebElement> rubys = sentence.findElements(By.tagName("ruby"));
        for (WebElement ruby : rubys) {
            String[] split = ruby.getText().split("\\n");
            problem.getRuby().put(split[0], split[1]);
        }

        //정답 가져오기
        List<WebElement> spans = sentence.findElements(By.tagName("span"));
        for (WebElement span : spans) {
            if( !(span.getText().isBlank() || span.getText().equals("...")) ){
                problem.setAnswer(span.getText());
            }
        }

        rubyProcess(problem);
        blankProcess(problem);
        return problem;
    }

    private void rubyProcess(Problem problem) {

        if(problem.getRuby().size() == 0){
            return;
        }

        Collection<String> values = problem.getRuby().values();
        String enter = "\\R";

        for (String value : values) {
            String replaceSentence = problem.getSentence().replace(value, "");
            replaceSentence = replaceSentence.replaceAll(enter, "");
            problem.setSentence(replaceSentence);

            String replaceAnswer = problem.getAnswer().replace(value, "");
            replaceAnswer = replaceAnswer.replaceAll(enter, "");
            problem.setAnswer(replaceAnswer);
        }

    }


    private void blankProcess(Problem problem) {
        String replace = problem.getSentence().replace(
                problem.getAnswer(), " (_____) "
        );

        problem.setSentence(replace);
    }


}




/*
* <li id="sentence-7" class="with-source-title list-group-item sentence odd clickable-sentence">
          <span class="prev-sentence" style="display:none;">院長であり、ここで唯一の医者でもある山浦銅太は五十代後半の男。</span>
          開業する前は、海外の戦地で軍の兵士やゲリラを相手に
               <span class="highlight">///腕を
                    <ruby>//磨
                        <rt>みが</rt>
                    </ruby>
                //い</span>
          てきた人物だ。
          <span class="next-sentence" style="display:none;">医者の前に患者は等しく平等という信念を持ち、誰であろうと差別はしない。</span>
          <span class="next-sentence-preview">...</span>
          <div class="sentence-source-title" style="font-style: inherit;"><a target="_blank" href="https://www.amazon.co.jp/gp/search?ie=UTF8&amp;tag=monokaki0f-22&amp;linkCode=ur2&amp;camp=247&amp;creative=1211&amp;index=books&amp;keywords=%E7%89%87%E5%B1%B1%E6%86%B2%E5%A4%AA%E9%83%8E%20%E7%B4%85%20%E7%AC%AC1%E5%B7%BB%20">片山憲太郎『紅 第1巻』</a><img src="//ir-jp.amazon-adsystem.com/e/ir?t=monokaki0f-22&amp;l=ur2&amp;o=9" width="1" height="1" border="0" alt="" style="border:none !important; margin:0px !important;"></div>
    </li>

    sentence : 開業する前は、海外の戦地で軍の兵士やゲリラを相手に ///腕を //磨
    みが -> rt
    //い てきた人物だ。 ...
    片山憲太郎『紅 第1巻』
    ruby : 磨
    みが
    rt : みが
    span :
    span : 腕を磨
    みが
    い
    span :
    span : ...

*/



/*

<li id="sentence-11" class="with-source-title list-group-item sentence odd clickable-sentence">
      <span class="prev-sentence" style="display:none;">八つには奉行所を退出するが、龍之進はそれから南八丁堀の日川道場へ向かい、一刻ほど剣の稽古に励む。</span>
          <ruby>机上
            <rt>きじよう</rt>
          </ruby>
          の学問が得意でない龍之進は剣の
            <span class="highlight">腕を磨い</span>
          て周りに自分を認めさせるしかなかった。

      <span class="next-sentence" style="display:none;">本勤になったあかつきには父親と同じ定廻りに就きたいと龍之進は思っている。</span>
      <span class="next-sentence-preview">...</span>
      <div class="sentence-source-title" style="font-style: inherit;"><a target="_blank" href="https://www.amazon.co.jp/gp/search?ie=UTF8&amp;tag=monokaki0f-22&amp;linkCode=ur2&amp;camp=247&amp;creative=1211&amp;index=books&amp;keywords=%E5%AE%87%E6%B1%9F%E4%BD%90%E7%9C%9F%E7%90%86%20%E9%AB%AA%E7%B5%90%E3%81%84%E4%BC%8A%E4%B8%89%E6%AC%A1%E6%8D%95%E7%89%A9%E4%BD%99%E8%A9%B1%20%E5%90%9B%E3%82%92%E4%B9%97%E3%81%9B%E3%82%8B%E8%88%9F%20">宇江佐真理『髪結い伊三次捕物余話 君を乗せる舟』</a><img src="//ir-jp.amazon-adsystem.com/e/ir?t=monokaki0f-22&amp;l=ur2&amp;o=9" width="1" height="1" border="0" alt="" style="border:none !important; margin:0px !important;"></div>
</li>

sentence : 机上
きじよう -> rt
の学問が得意でない龍之進は剣の腕を磨いて周りに自分を認めさせるしかなかった。 ...
宇江佐真理『髪結い伊三次捕物余話 君を乗せる舟』
ruby : 机上
きじよう
rt : きじよう
span :
span : 腕を磨い
span :
span : ...


*/



/*
<li id="sentence-15" class="with-source-title list-group-item sentence odd clickable-sentence">
          いくら戦いの
            <span class="highlight">
                <ruby> //腕
                    <rt>うで</rt>
                </ruby>
            を
                <ruby> //磨
                    <rt>みが</rt>
                </ruby>
            い
            </span>
            たって、それだけじゃ英雄になれねえ。
            <span class="next-sentence" style="display:none;">英雄になれるのはほんの一握りで、それには運が必要なんだ。</span>
          <span class="next-sentence-preview">...</span>
          <div class="sentence-source-title" style="font-style: inherit;"><a target="_blank" href="https://www.amazon.co.jp/gp/search?ie=UTF8&amp;tag=monokaki0f-22&amp;linkCode=ur2&amp;camp=247&amp;creative=1211&amp;index=books&amp;keywords=%E5%B1%B1%E6%9C%AC%E5%BC%98%20%E3%82%B5%E3%83%BC%E3%83%A9%E3%81%AE%E5%86%92%E9%99%BA%205%20%E5%B9%B8%E3%81%9B%E3%82%92%E3%81%A4%E3%81%8B%E3%81%BF%E3%81%9F%E3%81%84%20">山本弘『サーラの冒険 5 幸せをつかみたい !』</a><img src="//ir-jp.amazon-adsystem.com/e/ir?t=monokaki0f-22&amp;l=ur2&amp;o=9" width="1" height="1" border="0" alt="" style="border:none !important; margin:0px !important;"></div>
</li>

sentence : いくら戦いの //腕
うで -> rt
を //磨
みが -> rt
いたって、それだけじゃ英雄になれねえ。 ...
山本弘『サーラの冒険 5 幸せをつかみたい !』
ruby : 腕
うで
rt : うで
ruby : 磨
みが
rt : みが
span : 腕
うで
を磨
みが
い
span :
span : ...


*/
package team.jlpt;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class CrawlingExample {

    /**
     * 조회할 URL셋팅 및 Document 객체 로드하기
     */
    private static final String url = "https://finance.naver.com/sise/sise_market_sum.nhn?&page=1";
    public void init(){
        Connection conn = Jsoup.connect(url);

        try{
            Document document = conn.get();
            String header = getHeader(document);
            String data = getDataList(document);

            System.out.println(header);
            System.out.println(data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * header가져오기
     * <table class="type_2">
     *     <thead>
     *         <tr>
     *             <th>N</th>
     *             <th>종목명</th>
     *             <th>현재가</th>
     *             .............
     *         </tr>
     *     </thead>
     * </table>
     */
    private String getHeader(Document document) {
        Elements selects = document.select("table.type_2 thead tr");
        StringBuilder sb = new StringBuilder();
        for (Element select : selects) {
            System.out.println(select);
            System.out.println("=================================");
            for (Element th : select.select("th")) {
                sb.append(th.text());
                sb.append(" ");
            }
        }

        return sb.toString();
    }

    /**
     * data가져오기
     * <table class="type_2">
     *     <tbody>
     *         <tr>
     *             <td>1</td>
     *             <td>
     *                 <a href="/item/main.naver?code=005930" class="title">삼성전자</a>
     *             </td>
     *             <td>71,500</td>
     *             <td>
     *                 <img src="https://ssl.pstatic.net/imgstock/images/images4/ico_down.gif" width="7" height="6" style="margin-right:4px;" alt="하락">
     *                 <span class="tah p11 nv01">1,400</span>
     *             </td>
     *              ....................
     *         </tr>
     *     </tbody>
     * </table>
     */
    private String getDataList(Document document) {
        Elements selects = document.select("table.type_2 tbody");
        StringBuilder sb = new StringBuilder();
        for (Element select : selects) {
            sb.append(select.toString());
            sb.append("\n");
        }
        return sb.toString();
    }


    /* src/test/java/team/jlpt/CrawlingTest.java 실행 결과
    <tr>
     <th scope="col">N</th>
     <th scope="col">종목명</th>
     <th scope="col">현재가</th>
     <th scope="col" class="tr" style="padding-right:8px">전일비</th>
     <th scope="col">등락률</th>
     <th scope="col">액면가</th>
     <th scope="col">시가총액</th>
     <th scope="col">상장주식수</th>
     <th scope="col">외국인비율</th>
     <th scope="col">거래량</th>
     <th scope="col">PER</th>
     <th scope="col">ROE</th>
     <th scope="col">토론실</th>
    </tr>
    =================================
    N 종목명 현재가 전일비 등락률 액면가 시가총액 상장주식수 외국인비율 거래량 PER ROE 토론실
    <tbody>
     <tr>
      <td colspan="10" class="blank_08"></td>
     </tr>
     <tr onmouseover="mouseOver(this)" onmouseout="mouseOut(this)">
      <td class="no">1</td>
      <td><a href="/item/main.naver?code=005930" class="tltle">삼성전자</a></td>
      <td class="number">71,500</td>
      <td class="number"> <img src="https://ssl.pstatic.net/imgstock/images/images4/ico_down.gif" width="7" height="6" style="margin-right:4px;" alt="하락"><span class="tah p11 nv01"> 1,400 </span> </td>
      <td class="number"> <span class="tah p11 nv01"> -1.92% </span> </td>
      <td class="number">100</td>
      <td class="number">4,268,395</td>
      <td class="number">5,969,783</td>
      <td class="number">52.10</td>
      <td class="number">13,357,846</td>
      <td class="number">12.38</td>
      <td class="number">13.92</td>
      <td class="center"><a href="/item/board.naver?code=005930"><img src="https://ssl.pstatic.net/imgstock/images5/ico_debatebl2.gif" width="15" height="13" alt="토론실"></a></td>
     </tr>

     .......
     */

}

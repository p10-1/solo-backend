package org.solo.news.controller;

import org.solo.news.domain.NewsVO;
import org.solo.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    // 생성자 주입
    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService; // 생성자에서 초기화
    }

    @GetMapping({"", "/"})
    public String news(Model model) {
        System.out.println("news mapping");
        List<NewsVO> newsList = newsService.getNewsALL();
        model.addAttribute("newsList", newsList);
        insertNews(); // RSS 피드 파싱 메소드 호출
        return "news"; // 뷰 이름 반환
    }

    private void insertNews() {
        System.out.println("insertNews() 실행");
        String rssUrl = "https://www.mk.co.kr/rss/50300009/";

        try {
            URL url = new URL(rssUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(conn.getInputStream());

            NodeList itemList = doc.getElementsByTagName("item");
            for (int i = 0; i < itemList.getLength(); i++) {
                Node itemNode = itemList.item(i);
                if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element itemElement = (Element) itemNode;

                    NewsVO news = new NewsVO();
                    news.setNo(Integer.parseInt(itemElement.getElementsByTagName("no").item(0).getTextContent()));
                    news.setTitle(itemElement.getElementsByTagName("title").item(0).getTextContent());
                    news.setLink(itemElement.getElementsByTagName("link").item(0).getTextContent());
                    news.setDescription(itemElement.getElementsByTagName("description").item(0).getTextContent());
                    news.setCategory(itemElement.getElementsByTagName("category").item(0).getTextContent());
                    news.setAuthor(itemElement.getElementsByTagName("author").item(0).getTextContent());
                    news.setPubDate(itemElement.getElementsByTagName("pubDate").item(0).getTextContent());

                    newsService.insertNews(news);
                    /* 중복 확인 후 삽입 나중에 추가
                    if (!newsService.isNoExists(news.getNo())) {
                        newsService.insertNews(news);
                    } else {
                        System.out.println("중복된 no 값: " + news.getNo());
                    }
                    */

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

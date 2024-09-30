package org.solo.news.service;

import org.solo.common.pagination.PageRequest;
import org.solo.news.domain.NewsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.solo.news.mapper.NewsMapper;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {

    private final NewsMapper newsMapper;
    private final RestTemplate restTemplate;

    @Autowired
    public NewsServiceImpl(NewsMapper newsMapper) {
        this.newsMapper = newsMapper;
        this.restTemplate = new RestTemplate();
    }

    // RSS 피드에서 뉴스 가져오기
    public List<NewsVO> fetchNews(String rssUrl) {
        System.out.println("fetchNews() 실행(service)" + rssUrl);
        List<NewsVO> newsList = new ArrayList<>();

        try {
            // RestTemplate을 사용하여 RSS 피드 가져오기
            String rssFeed = restTemplate.getForObject(rssUrl, String.class);
            //System.out.println("RSS Feed: " + rssFeed);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(rssFeed.getBytes()));

            NodeList itemList = doc.getElementsByTagName("item");
            for (int i = 0; i < itemList.getLength(); i++) {
                Node itemNode = itemList.item(i);
                if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element itemElement = (Element) itemNode;

                    NewsVO news = new NewsVO();
                    news.setNewsNo(Integer.parseInt(itemElement.getElementsByTagName("no").item(0).getTextContent()));
                    news.setTitle(itemElement.getElementsByTagName("title").item(0).getTextContent());
                    news.setLink(itemElement.getElementsByTagName("link").item(0).getTextContent());
                    news.setCategory(itemElement.getElementsByTagName("category").item(0).getTextContent());

                    // pubDate 변환
                    String pubDateString = itemElement.getElementsByTagName("pubDate").item(0).getTextContent();
                    SimpleDateFormat inputFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss XXX", Locale.ENGLISH); // XXX를 사용
                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // MySQL DATETIME 형식으로 변환
                    Date pubDate = inputFormat.parse(pubDateString);
                    String formattedPubDate = outputFormat.format(pubDate);
                    news.setPubDate(formattedPubDate); // 변환된 날짜를 설정

                    newsList.add(news);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("뉴스 데이터를 가져오는 데 실패했습니다.");
        }
        return newsList; // 가져온 뉴스 리스트 반환
    }

    // 뉴스 리스트를 데이터베이스에 삽입
    public void insertNews(List<NewsVO> newsList) {
        for (NewsVO news : newsList) {
            // 중복 체크
            if (newsMapper.getNewsByNo((int) news.getNewsNo()) == null) {
                newsMapper.insertNews(Collections.singletonList(news));
            } else {
//                System.out.println("중복 데이터 발견: " + news.getNewsNo());
            }
        }
    }

    // page로 조회
    @Override
    public List<NewsVO> getNewsByPage(PageRequest pageRequest) {
        return newsMapper.getNewsByPage(pageRequest.getOffset(), pageRequest.getAmount()); // 페이지와 항목 수에 따라 데이터 가져오기
    }

    // 뉴스 갯수 조회
    @Override
    public int getNewsCount() {
        return newsMapper.getNewsCount(); // 전체 뉴스 수 가져오기
    }

}

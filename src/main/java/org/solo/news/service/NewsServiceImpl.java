package org.solo.news.service;

import org.solo.common.pagination.PageRequest;
import org.solo.news.domain.NewsVO;
import org.springframework.beans.factory.annotation.Value;
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
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {

    private final NewsMapper newsMapper;
    private final RestTemplate restTemplate;

    // RSS URL을 주입받기 위한 필드
    @Value("${newsAPI.rssUrl1}")
    private String rssUrl1;

    @Value("${newsAPI.rssUrl2}")
    private String rssUrl2;

    @Value("${newsAPI.rssUrl3}")
    private String rssUrl3;

    private static final String[] CATEGORIES = {"경제", "증권", "부동산"};

    @Autowired
    public NewsServiceImpl(NewsMapper newsMapper) {
        this.newsMapper = newsMapper;
        this.restTemplate = new RestTemplate();
    }

    // RSS 피드에서 뉴스 가져오기
    @Override
    public List<NewsVO> fetchAllNews() {
        List<NewsVO> combinedNewsList = new ArrayList<>();
        combinedNewsList.addAll(fetchNews(rssUrl1));
        combinedNewsList.addAll(fetchNews(rssUrl2));
        combinedNewsList.addAll(fetchNews(rssUrl3));
        return combinedNewsList;
    }
    @Override
    public List<NewsVO> fetchNews(String rssUrl) {
        List<NewsVO> newsList = new ArrayList<>();

        try {
            String rssFeed = restTemplate.getForObject(rssUrl, String.class);
            Document doc = parseRssFeed(rssFeed);
            NodeList itemList = doc.getElementsByTagName("item");

            for (int i = 0; i < itemList.getLength(); i++) {
                Node itemNode = itemList.item(i);
                if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element itemElement = (Element) itemNode;
                    newsList.add(parseNewsItem(itemElement));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("뉴스 데이터를 가져오는 데 실패했습니다.");
        }
        return newsList;
    }

    // RSS 파싱
    private Document parseRssFeed(String rssFeed) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(rssFeed.getBytes()));
    }

    // 뉴스 태그별로 매핑
    private NewsVO parseNewsItem(Element itemElement) throws Exception {
        NewsVO news = new NewsVO();
        news.setNewsNo(Integer.parseInt(itemElement.getElementsByTagName("no").item(0).getTextContent()));
        news.setTitle(itemElement.getElementsByTagName("title").item(0).getTextContent());
        news.setLink(itemElement.getElementsByTagName("link").item(0).getTextContent());
        news.setCategory(itemElement.getElementsByTagName("category").item(0).getTextContent());
        news.setPubDate(formatPubDate(itemElement.getElementsByTagName("pubDate").item(0).getTextContent()));
        NodeList mediaContentList = itemElement.getElementsByTagName("media:content");
        if (mediaContentList.getLength() > 0) {
            Element mediaContent = (Element) mediaContentList.item(0);
            System.out.println(mediaContent);
            news.setImageUrl(mediaContent.getAttribute("url")); // 이미지 URL 저장
            System.out.println(mediaContent.getAttribute("url"));
        }


        return news;
    }

    // 날짜 포맷팅
    private String formatPubDate(String pubDateString) throws Exception {
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss XXX", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date pubDate = inputFormat.parse(pubDateString);
        return outputFormat.format(pubDate);
    }

    // 뉴스 리스트를 데이터베이스에 삽입
    public void insertNews(List<NewsVO> newsList) {
        for (NewsVO news : newsList) {
            if (newsMapper.getNewsByNo((int) news.getNewsNo()) == null) {
                newsMapper.insertNews(Collections.singletonList(news));
            }
        }
    }

    // 뉴스 조회
    @Override
    public List<NewsVO> getNewsBycategory(PageRequest pageRequest, String category) {
        return getNews(pageRequest, category);
    }

    // 카테고리별 조회
    @Override
    public List<NewsVO> getNewsByPage(PageRequest pageRequest) {
        return getNews(pageRequest, null);
    }

    private List<NewsVO> getNews(PageRequest pageRequest, String category) {
        return category == null
                ? newsMapper.getNewsByPage(pageRequest.getOffset(), pageRequest.getAmount())
                : newsMapper.getNewsBycategory(pageRequest.getOffset(), pageRequest.getAmount(), category);
    }

    // 카테고리별 뉴스 개수 조회
    @Override
    public int getNewsCountBycategory(String category) {
        return newsMapper.getNewsCountBycategory(category);
    }

    // 전체 뉴스 수 가져오기
    @Override
    public int getNewsCount() {
        return newsMapper.getNewsCount();
    }

    // 오늘의 뉴스 가져오기
    @Override
    public Map<String, List<NewsVO>> getTodayNews(LocalDate date) {
        List<NewsVO> todayNews = newsMapper.getTodayNews(date);
        Map<String, List<NewsVO>> selectedNews = new HashMap<>();

        // 각 카테고리 초기화
        for (String category : CATEGORIES) {
            selectedNews.put(category, new ArrayList<>());
        }

        // 카테고리 별로 분류
        for (NewsVO news : todayNews) {
            String category = news.getCategory();
            if (selectedNews.containsKey(category)) {
                selectedNews.get(category).add(news);
            }
        }

        return selectedNews;
    }
}

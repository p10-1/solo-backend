package org.solo.quiz.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.StringEscapeUtils;
import org.solo.quiz.domain.QuizVO;
import org.solo.quiz.mapper.QuizMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.net.URI;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {
    @Autowired
    private ObjectMapper objectMapper;

    private final QuizMapper quizMapper;
//    private final RestTemplate restTemplate;

//    @Value("${quizAPI.url}")
//    String quizAPIUrl;
//    @Value("${quizAPI.serviceKey}")
//    String serviceKey;
//    @Value("${quizAPI.pageNo}")
//    String pageNo;
//    @Value("${quizAPI.numOfRows}")
//    String numOfRows;

    public QuizServiceImpl(QuizMapper quizMapper, RestTemplate restTemplate) {
        this.quizMapper = quizMapper;
//        this.restTemplate = restTemplate;
    }

    public QuizVO createQuiz() {
        int totalCnt = quizMapper.totalCnt();
        int quizNo = (int)(Math.random() * totalCnt) + 1;
        return quizMapper.createQuiz(quizNo);
    }

//    public void fetchQuizzes(String word) {
//        System.out.println(word);
//        String encodeServiceKey = URLEncoder.encode(serviceKey, StandardCharsets.UTF_8);
//        String term = URLEncoder.encode(word, StandardCharsets.UTF_8);
//        URI uri = UriComponentsBuilder.fromUriString(quizAPIUrl)
//                .queryParam("serviceKey", encodeServiceKey)
//                .queryParam("term", term)
//                .queryParam("pageNo", pageNo)
//                .queryParam("numOfRows", numOfRows)
//                .build(true) // URI 객체로 변환
//                .toUri();
//
//        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
//        String xmlResponse = restTemplate.getForObject(uri, String.class);
//        List<QuizVO> quizzes = new ArrayList<>();
//
//        try {
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document document = builder.parse(new ByteArrayInputStream(xmlResponse.getBytes(StandardCharsets.UTF_8)));
//
//            NodeList quizList = document.getElementsByTagName("item");
//            for (int i = 0; i < quizList.getLength(); i++) {
//                Element quizElement = (Element) quizList.item(i);
//                String fnceDictNm = quizElement.getElementsByTagName("fnceDictNm").item(0).getTextContent().trim();
//
//                String descContent = quizElement.getElementsByTagName("ksdFnceDictDescContent").item(0).getTextContent().trim();
//                descContent = StringEscapeUtils.unescapeHtml4(descContent); // HTML 엔티티 디코드
//                descContent = descContent.replaceAll("<[^>]+>", ""); // HTML 태그 제거
//
//                QuizVO quizVO = new QuizVO(fnceDictNm, descContent);
//                quizzes.add(quizVO);
//            }
//            saveQuizzes(quizzes);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void saveQuizzes(List<QuizVO> quizzes) {
//        for (QuizVO quizVO : quizzes) {
//            if (quizMapper.findByTerm(quizVO.getFnceDictNm()) == 0) {
//                quizMapper.fetchQuizzes(quizVO);
//            }
//        }
//    }
}
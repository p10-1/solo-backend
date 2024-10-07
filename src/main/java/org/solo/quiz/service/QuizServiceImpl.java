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
    private final QuizMapper quizMapper;

    public QuizServiceImpl(QuizMapper quizMapper) {
        this.quizMapper = quizMapper;
    }

    @Override
    public QuizVO createQuiz() {
        int totalCnt = quizMapper.totalCnt();
        int quizNo = (int)(Math.random() * totalCnt) + 1;
        return quizMapper.createQuiz(quizNo);
    }

    @Override
    public boolean checkPoint(String userId) {
        return quizMapper.checkPoint(userId) == 1;
    }

    @Override
    public void checkToday(String userId) {
        quizMapper.checkToday(userId);
    }

    @Override
    public void addPoint(String userId) {
        quizMapper.addPoint(userId);
    }

    @Override
    public void resetToday() {
        quizMapper.reset();
    }

}
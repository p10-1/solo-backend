package org.solo.board.controller;

import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.solo.board.service.BoardService;
import org.solo.config.RootConfig;
import org.solo.config.ServletConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class, ServletConfig.class})
@Log4j
class BoardControllerTest {
    @Autowired
    BoardService service;

    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    void list() throws Exception {
        log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
                .andReturn()        // MvcResult 리턴
                .getModelAndView()  // ModelAndView 리턴
                .getModelMap()      // Model 리턴
        );
    }

    @Test
    void create() throws Exception {
        String resultPage = mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/board/create")
                                .param("title", "테스트 새글 제목")
                                .param("content", "테스트 새글 내용")
                                .param("userID", "user1"))
                .andReturn()
                .getModelAndView()
                .getViewName();

        log.info(resultPage);
    }

    @Test
    void get() throws Exception {
        log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/get").param("boardNo", "1"))
                .andReturn()
                .getModelAndView()
                .getModelMap());
    }

    @Test
    void update() throws Exception {
        String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/update")
                        .param("boardNo", "1")
                        .param("title", "수정된 테스트 새글 제목")
                        .param("content", "수정된 테스트 새글 내용")
                        .param("userID", "user0"))
                .andReturn()
                .getModelAndView()
                .getViewName();
        log.info(resultPage);
    }

    @Test
    void delete() throws Exception {
        String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/delete").param("boardNo", "25"))
                .andReturn()
                .getModelAndView()
                .getViewName();
        log.info(resultPage);
    }

}
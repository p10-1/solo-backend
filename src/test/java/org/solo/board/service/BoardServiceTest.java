package org.solo.board.service;

import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.solo.board.domain.BoardVO;
import org.solo.board.dto.BoardDTO;
import org.solo.config.RootConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class})
@Log4j
class BoardServiceTest {

    @Autowired
    private BoardService service;

    @Test
    void getList() {
        for(BoardVO boardVO : service.getList()) {
            log.info(boardVO);
        }
    }

    @Test
    void get() {
        log.info(service.get(2L));
    }

    @Test
    void create() {
        BoardVO boardVO = new BoardVO();
        boardVO.setTitle("새로 작성하는 글111");
        boardVO.setContent("새로 작성하는 내용111");
        boardVO.setUserID("user1");

        service.create(boardVO);

        log.info("생성된 게시물의 번호: " + boardVO.getBoardNo());
    }

    @Test
    void update() {
        BoardVO boardVO = service.get(6L);

        boardVO.setTitle("제목 수정합니다.");
        log.info("update RESULT: " + service.update(boardVO));
    }

    @Test
    void delete() {
        Long boardNo = 2L;
        if(service.get(boardNo) != null) {
            log.info("delete RESULT: " + service.delete(boardNo));
        }
    }

    @Test
    void getAttachment() {
    }

    @Test
    void deleteAttachment() {
    }

    @Test
    void deleteAttachment_bno() {
    }
}
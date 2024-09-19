package org.solo.board.service;

import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
        for(BoardDTO board : service.getList()) {
            log.info(board);
        }
    }

    @Test
    void get() {
        log.info(service.get(2L));
    }

    @Test
    void create() {
        BoardDTO board = new BoardDTO();
        board.setTitle("새로 작성하는 글111");
        board.setContent("새로 작성하는 내용111");
        board.setUserID("user1");

        service.create(board);

        log.info("생성된 게시물의 번호: " + board.getBoardNo());
    }

    @Test
    void update() {
        BoardDTO board = service.get(6L);

        board.setTitle("제목 수정합니다.");
        log.info("update RESULT: " + service.update(board));
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
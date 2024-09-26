package org.solo.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.solo.board.domain.BoardAttachmentVO;

import org.solo.board.domain.BoardVO;
import org.solo.board.service.BoardServiceImpl;
import org.solo.common.util.UploadFiles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

@RestController  // @Controller 대신 @RestController 사용
@Log4j
@RequestMapping("/api/board")  // API 경로로 변경
@RequiredArgsConstructor
public class BoardController  {
    final private BoardServiceImpl boardServiceImpl;

    @GetMapping("/list")
    public ResponseEntity<List<BoardVO>> list() {
        log.info("list");
        List<BoardVO> boardList = boardServiceImpl.getList();
        return ResponseEntity.ok(boardList);  // JSON 형식으로 반환
    }

    @PostMapping("/create")
    public ResponseEntity<Long> create(@RequestBody BoardVO boardVO) {
        log.info("create: " + boardVO);
        boardServiceImpl.create(boardVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(boardVO.getBoardNo());  // 생성된 리소스의 ID 반환
    }

    @GetMapping("/{boardNo}")
    public ResponseEntity<BoardVO> get(@PathVariable("boardNo") Long boardNo) {
        log.info("get: " + boardNo);
        BoardVO boardVO = boardServiceImpl.get(boardNo);
        return ResponseEntity.ok(boardVO);  // JSON 형식으로 반환
    }

    @PutMapping("/{boardNo}")
    public ResponseEntity<String> update(@PathVariable("boardNo") Long boardNo, @RequestBody BoardVO boardVO) {
        log.info("update: " + boardVO);
        boardVO.setBoardNo(boardNo);  // boardNo 설정
        if(boardServiceImpl.update(boardVO)) {
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");  // 리소스가 없을 경우
    }

    @DeleteMapping("/{boardNo}")
    public ResponseEntity<String> delete(@PathVariable("boardNo") Long boardNo) {
        log.info("delete: " + boardNo);
        if(boardServiceImpl.delete(boardNo)) {
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");  // 리소스가 없을 경우
    }

    // 다운로드 기능은 REST API에 적합하지 않으므로 별도의 엔드포인트로 구현 가능
    // @GetMapping("/download/{boardNo}")
    // public void download(@PathVariable("boardNo") Long boardNo, HttpServletResponse response) throws Exception {
    //     BoardAttachmentVO attach = boardServiceImpl.getAttachment(boardNo);
    //     File file = new File(attach.getPath());
    //     UploadFiles.download(response, file, attach.getFilename());
    // }
}

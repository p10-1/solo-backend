package org.solo.board.controller;

import org.solo.board.domain.BoardAttachmentVO;
import org.solo.board.domain.BoardVO;
import org.solo.board.service.BoardService;
import org.solo.common.pagination.Page;
import org.solo.common.pagination.PageRequest;
import org.solo.common.util.UploadFiles;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@RestController
@RequestMapping("api/board")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<Page> getList(PageRequest pageRequest) {
        return ResponseEntity.ok(boardService.getPage(pageRequest));
    }

    @GetMapping("/{no}")
    public ResponseEntity<BoardVO> getById(@PathVariable Long no) {
        return ResponseEntity.ok(boardService.get(no));
    }

    @PostMapping({"","/"})
    public ResponseEntity<BoardVO> create(BoardVO boardVO) {
        return ResponseEntity.ok(boardService.create(boardVO));
    }

    @PutMapping("/{no}")
    public ResponseEntity<BoardVO> update(@PathVariable Long no, BoardVO boardVO) {
        return ResponseEntity.ok(boardService.update(boardVO));
    }
    @DeleteMapping("/{no}")
    public ResponseEntity<BoardVO> delete(@PathVariable Long no) {
        return ResponseEntity.ok(boardService.delete(no));
    }

    @GetMapping("/download/{no}")
    public void download(@PathVariable Long no,  HttpServletResponse response) throws Exception {
        BoardAttachmentVO attachment = boardService.getAttachment(no);
        File file = new File(attachment.getPath());
        UploadFiles.download(response, file, attachment.getFilename());
    }
}

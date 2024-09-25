package org.solo.board.controller;

import org.solo.board.domain.BoardAttachmentVO;
import org.solo.board.domain.BoardVO;
import org.solo.board.domain.CommentVO;
import org.solo.board.service.BoardService;
import org.solo.common.pagination.Page;
import org.solo.common.pagination.PageRequest;
import org.solo.common.util.UploadFiles;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<Page> getList(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int amount,
                                        @RequestParam(required = false) String category,
                                        @RequestParam(required = false) String keyword)  {
        System.out.println("page: " + page + " amount: " + amount + " category: " + category + " keyword: " + keyword);
        PageRequest pageRequest = PageRequest.of(page, amount);
        List<BoardVO> boards = (keyword != null && !keyword.isEmpty())
                ? boardService.getBoardsByPageAndKeyword(pageRequest, category, keyword)
                : boardService.getBoardsByPage(pageRequest);

        int totalBoardsCount = (keyword != null && !keyword.isEmpty())
                ? boardService.getTotalCntByKeyword(category, keyword)
                : boardService.getTotalCnt();

        Page<BoardVO> boardsPage = Page.of(pageRequest, totalBoardsCount, boards);
        return ResponseEntity.ok(boardsPage);
    }

    @GetMapping("/{no}")
    public ResponseEntity<BoardVO> getById(@PathVariable Long no) {
        return ResponseEntity.ok(boardService.get(no));
    }

    @GetMapping("/{no}/comments")
    public ResponseEntity<List<CommentVO>> getComment(@PathVariable Long no) {
        List<CommentVO> comments = boardService.getComments(no);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/comment")
    public ResponseEntity<String> createComment(@RequestBody CommentVO commentVO) {
//        commentVO.setBoardNo(no);
        System.out.println("commentVO: " + commentVO);
        boardService.createComment(commentVO);
        return ResponseEntity.ok("댓글이 성공적으로 작성되었습니다.");
    }

    @PostMapping({"","/"})
    public ResponseEntity<BoardVO> create(BoardVO boardVO) {
        return ResponseEntity.ok(boardService.create(boardVO));
    }

    @PutMapping("/{no}")
    public ResponseEntity<BoardVO> update(@PathVariable Long no, BoardVO boardVO) {
        System.out.println("update controller boardVO: " + boardVO);
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

    @DeleteMapping("/deleteAttachment/{no}")
    public ResponseEntity<Boolean> deleteAttachment(@PathVariable Long no) throws Exception {
        return ResponseEntity.ok(boardService.deleteAttachment(no));
    }


}

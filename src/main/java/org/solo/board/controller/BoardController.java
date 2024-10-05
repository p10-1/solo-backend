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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 게시판을 페이지에 맞게 리스트
    @GetMapping({"", "/"})
    public ResponseEntity<Page<BoardVO>> getList(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "10") int amount,
                                                 @RequestParam(required = false) String category,
                                                 @RequestParam(required = false) String keyword,
                                                 @RequestParam String sort) {
        List<BoardVO> boards;
        List<BoardVO> bestBoards = (page == 1) ? boardService.getBestBoards() : Collections.emptyList();
        PageRequest pageRequest = PageRequest.of(page, amount);
        switch (sort) {
            case "likes":
                boards = (keyword != null && !keyword.isEmpty())
                        ? boardService.getBoardsByPageAndKeywordOrderBylike(pageRequest, category, keyword)
                        : boardService.getBoardsByPageOrderBylike(pageRequest);
                break;
            case "comments":
                boards = (keyword != null && !keyword.isEmpty())
                        ? boardService.getBoardsByPageAndKeywordOrderBycomment(pageRequest, category, keyword)
                        : boardService.getBoardsByPageOrderBycomment(pageRequest);
                break;
            case "views":
                boards = (keyword != null && !keyword.isEmpty())
                        ? boardService.getBoardsByPageAndKeywordOrderByview(pageRequest, category, keyword)
                        : boardService.getBoardsByPageOrderByview(pageRequest);
                break;
            default:
                boards = (keyword != null && !keyword.isEmpty())
                        ? boardService.getBoardsByPageAndKeywordOrderByregDate(pageRequest, category, keyword)
                        : boardService.getBoardsByPageOrderByregDate(pageRequest);
                break;
        }
        if (page == 1 && !bestBoards.isEmpty()) {
            boards.addAll(0, bestBoards);
        }

        int totalBoardsCount = (keyword != null && !keyword.isEmpty())
                ? boardService.getTotalCntByKeyword(category, keyword)
                : boardService.getTotalCnt();

        Page<BoardVO> boardsPage = Page.of(pageRequest, totalBoardsCount, boards);

        return ResponseEntity.ok(boardsPage);
    }

    // 인기글만 추출
    @GetMapping("/best")
    public ResponseEntity<List<BoardVO>> getBest(){
        return ResponseEntity.ok(boardService.getBestBoards());
    }

    // 게시물 상세보기
    @GetMapping("/{no}")
    public ResponseEntity<BoardVO> getById(@PathVariable Long no) {
        return ResponseEntity.ok(boardService.get(no));
    }

    // 해당 게시물에 달린 댓글 리스트
    @GetMapping("/{no}/comments")
    public ResponseEntity<List<CommentVO>> getComment(@PathVariable Long no) {
        List<CommentVO> comments = boardService.getComments(no);
        return ResponseEntity.ok(comments);
    }

    // 댓글 작성
    @PostMapping("/comment")
    public ResponseEntity<String> createComment(@RequestBody CommentVO commentVO) {
        try {
            boardService.createComment(commentVO);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.ok("fail");
        }
    }

    // 게시물 생성
    @PostMapping({"","/"})
    public ResponseEntity<BoardVO> create(BoardVO boardVO) {
        return ResponseEntity.ok(boardService.create(boardVO));
    }

    // 게시물 수정
    @PutMapping("/{no}")
    public ResponseEntity<BoardVO> update(@PathVariable Long no, BoardVO boardVO) {
        return ResponseEntity.ok(boardService.update(boardVO));
    }

    // 게시물 삭제
    @DeleteMapping("/{no}")
    public ResponseEntity<BoardVO> delete(@PathVariable Long no) {
        return ResponseEntity.ok(boardService.delete(no));
    }

    // 첨부파일 다운로드
    @GetMapping("/download/{no}")
    public void download(@PathVariable Long no,  HttpServletResponse response) throws Exception {
        BoardAttachmentVO attachment = boardService.getAttachment(no);
        File file = new File(attachment.getPath());
        UploadFiles.download(response, file, attachment.getFilename());
    }

    // 첨부파일 삭제
    @DeleteMapping("/deleteAttachment/{no}")
    public ResponseEntity<Boolean> deleteAttachment(@PathVariable Long no) throws Exception {
        return ResponseEntity.ok(boardService.deleteAttachment(no));
    }

    // 좋아요
    @GetMapping("/like")
    public ResponseEntity<String> like(@RequestParam Long boardNo, @RequestParam String userName) {
        System.out.println("boardNo: " + boardNo + " userName: " + userName);
        if (boardService.likeCheck(boardNo,userName)) {
            boardService.upLikeCnt(boardNo);
            boardService.likeUpdate(boardNo, userName);
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.ok("fail");
        }
    }

    // 내가 쓴 게시물
    @GetMapping("/mine")
    public ResponseEntity<List<BoardVO>> mine(@RequestParam String userName) {
        List<BoardVO> myBoards = boardService.mine(userName);
        return ResponseEntity.ok(myBoards);
    }
}

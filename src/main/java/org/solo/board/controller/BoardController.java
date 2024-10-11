package org.solo.board.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "Board Controller", tags = "게시판 API")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 게시판을 페이지에 맞게 리스트
    @GetMapping("")
    @ApiOperation(value = "게시판 불러오기", notes = "원하는 조건에 맞는 게시판 목록을 불러옵니다.")
    public ResponseEntity<Page<BoardVO>> getList(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "10") int amount,
                                                 @RequestParam(required = false) String category,
                                                 @RequestParam(required = false) String keyword,
                                                 @RequestParam String sort) {
        List<BoardVO> boards;
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

        int totalBoardsCount = (keyword != null && !keyword.isEmpty())
                ? boardService.getTotalCntByKeyword(category, keyword)
                : boardService.getTotalCnt();

        Page<BoardVO> boardsPage = Page.of(pageRequest, totalBoardsCount, boards);

        return ResponseEntity.ok(boardsPage);
    }

    // 인기글만 추출
    @GetMapping("/best")
    @ApiOperation(value = "인기 게시글 불러오기", notes = "인기 게시글 5개를 불러옵니다.")
    public ResponseEntity<List<BoardVO>> getBest(){
        return ResponseEntity.ok(boardService.getBestBoards());
    }

    // 게시물 상세보기
    @GetMapping("/{boardNo}")
    @ApiOperation(value = "게시글 상세보기", notes = "게시글 번호가 boardNo 인 게시글을 상세하게 보여줍니다.")
    public ResponseEntity<BoardVO> getById(@PathVariable Long boardNo) {
        return ResponseEntity.ok(boardService.get(boardNo));
    }

    // 해당 게시물에 달린 댓글 리스트
    @GetMapping("/{boardNo}/comments")
    @ApiOperation(value = "게시글 댓글 불러오기", notes = "게시글 번호가 boardNo 인 게시글에 달린 댓글을 불러옵니다.")
    public ResponseEntity<List<CommentVO>> getComment(@PathVariable Long boardNo) {
        List<CommentVO> comments = boardService.getComments(boardNo);
        return ResponseEntity.ok(comments);
    }

    // 댓글 작성
    @PostMapping("/comment")
    @ApiOperation(value = "댓글 작성하기", notes = "댓글을 작성합니다.")
    public ResponseEntity<String> createComment(@RequestBody CommentVO commentVO) {
        try {
            boardService.createComment(commentVO);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.ok("fail");
        }
    }

    // 게시물 생성
    @PostMapping("")
    @ApiOperation(value = "게시글 작성하기", notes = "게시글을 작성합니다.")
    public ResponseEntity<BoardVO> create(BoardVO boardVO) {
        return ResponseEntity.ok(boardService.create(boardVO));
    }

    // 게시물 수정
    @PutMapping("/{boardNo}")
    @ApiOperation(value = "게시글 수정하기", notes = "게시글 번호가 boardNo인 게시글을 수정합니다.")
    public ResponseEntity<BoardVO> update(@PathVariable Long boardNo, BoardVO boardVO) {
        return ResponseEntity.ok(boardService.update(boardVO));
    }

    // 게시물 삭제
    @DeleteMapping("/{boardNo}")
    @ApiOperation(value = "게시글 삭제하기", notes = "게시글 번호가 boardNo인 게시글을 삭제합니다.")
    public ResponseEntity<BoardVO> delete(@PathVariable Long boardNo) {
        return ResponseEntity.ok(boardService.delete(boardNo));
    }

    // 첨부파일 다운로드
    @GetMapping("/download/{no}")
    @ApiOperation(value = "첨부파일 받아오기", notes = "글을 작성할 때 업로드한 첨부파일을 받아옵니다.")
    public void download(@PathVariable Long no,  HttpServletResponse response) throws Exception {
        BoardAttachmentVO attachment = boardService.getAttachment(no);
        File file = new File(attachment.getPath());
        UploadFiles.download(response, file, attachment.getFilename());
    }

    // 첨부파일 삭제
    @DeleteMapping("/deleteAttachment/{no}")
    @ApiOperation(value = "첨부파일 삭제하기", notes = "글을 삭제할 때 업로드된 첨부파일을 삭제합니다.")
    public ResponseEntity<Boolean> deleteAttachment(@PathVariable Long no) throws Exception {
        return ResponseEntity.ok(boardService.deleteAttachment(no));
    }

    // 좋아요
    @GetMapping("/like")
    @ApiOperation(value = "게시글 좋아요 누르기", notes = "게시글 좋아요를 누릅니다.")
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
    @ApiOperation(value = "내가 쓴 글 불러오기", notes = "내가 작성한 글을 불러옵니다.")
    public ResponseEntity<List<BoardVO>> mine(@RequestParam String userName) {
        List<BoardVO> myBoards = boardService.mine(userName);
        return ResponseEntity.ok(myBoards);
    }
}

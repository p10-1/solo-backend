package org.solo.board.service;

import org.solo.board.domain.BoardAttachmentVO;
import org.solo.board.domain.BoardVO;
import org.solo.board.domain.CommentVO;
import org.solo.common.pagination.Page;
import org.solo.common.pagination.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardService {
    int getTotalCnt();
    int getTotalCntByKeyword(String category ,String keyword);
    List<BoardVO> getBoardsByPageOrderByregDate(PageRequest pageRequest);
    List<BoardVO> getBoardsByPageOrderBylike(PageRequest pageRequest);
    List<BoardVO> getBoardsByPageOrderByview(PageRequest pageRequest);
    List<BoardVO> getBoardsByPageOrderBycomment(PageRequest pageRequest);
    List<BoardVO> getBoardsByPageAndKeywordOrderByregDate(PageRequest pageRequest, String category, String keyword);
    List<BoardVO> getBoardsByPageAndKeywordOrderBylike(PageRequest pageRequest, String category, String keyword);
    List<BoardVO> getBoardsByPageAndKeywordOrderByview(PageRequest pageRequest, String category, String keyword);
    List<BoardVO> getBoardsByPageAndKeywordOrderBycomment(PageRequest pageRequest, String category, String keyword);
    BoardVO get(Long boardNo);// 2개 이상의 insert 문이 실행될 수 있으므로 트랜잭션 처리 필요
    BoardVO create(BoardVO boardVO);
    BoardVO update(BoardVO boardVO);
    BoardVO delete(Long boardNo);
    BoardAttachmentVO getAttachment(Long boardNo);
    boolean deleteAttachment(Long attachmentNo);
    List<CommentVO> getComments(Long no);
    void createComment(CommentVO commentVO);
    void upLikeCnt(Long boardNo);
    boolean likeCheck(Long boardNo, String userName);
    void likeUpdate(Long boardNo, String userName);
    List<BoardVO> getBestBoards();
    List<BoardVO> mine(String userName);
    void bestBoardPointUp();
}
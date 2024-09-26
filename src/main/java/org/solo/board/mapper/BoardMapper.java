package org.solo.board.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.solo.board.domain.BoardAttachmentVO;
import org.solo.board.domain.BoardVO;
import org.solo.board.domain.CommentVO;
import org.solo.common.pagination.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BoardMapper {
    int getTotalCnt();
    int getTotalCntByKeyword(@Param("category") String category,
                             @Param("keyword") String keyword);
    BoardVO get(Long boardNo);
    void create(BoardVO boardVO);
    int update(BoardVO boardVO);
    int delete(Long boardNo);
    void createAttachment(BoardAttachmentVO attach);
    List<BoardAttachmentVO> getAttachmentList(Long bno);
    BoardAttachmentVO getAttachment(Long boardNo);
    int deleteAttachment(Long attachmentNo);
    List<CommentVO> getComments(Long boardNo);
    void createComment(CommentVO commentVO);
    void upCommentCnt(@Param("boardNo") Long boardNo);
    void upViewCnt(@Param("boardNo") Long boardNo);
    void upLikeCnt(@Param("boardNo") Long boardNo);

    List<BoardVO> getBoardsByPageAndKeywordOrderByregDate(@Param("offset") int offset, @Param("limit") int limit, @Param("category") String category,
                                                @Param("keyword") String keyword);

    List<BoardVO> getBoardsByPageAndKeywordOrderBylike(@Param("offset") int offset, @Param("limit") int limit, @Param("category") String category,
                                             @Param("keyword") String keyword);

    List<BoardVO> getBoardsByPageAndKeywordOrderByview(@Param("offset") int offset, @Param("limit") int limit, @Param("category") String category,
                                             @Param("keyword") String keyword);

    List<BoardVO> getBoardsByPageAndKeywordOrderBycomment(@Param("offset") int offset, @Param("limit") int limit, @Param("category") String category,
                                                @Param("keyword") String keyword);

    List<BoardVO> getBoardsByPageOrderByregDate(@Param("offset") int offset, @Param("limit") int limit);

    List<BoardVO> getBoardsByPageOrderBylike(@Param("offset") int offset, @Param("limit") int limit);

    List<BoardVO> getBoardsByPageOrderByview(@Param("offset") int offset, @Param("limit") int limit);

    List<BoardVO> getBoardsByPageOrderBycomment(@Param("offset") int offset, @Param("limit") int limit);
}
























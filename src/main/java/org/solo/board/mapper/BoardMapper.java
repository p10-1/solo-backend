package org.solo.board.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.solo.board.domain.BoardAttachmentVO;
import org.solo.board.domain.BoardVO;
import org.solo.board.domain.CommentVO;
import org.solo.common.pagination.PageRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BoardMapper {

    // 전체 게시물의 수를 검색하는 메서드
    int getTotalCnt();

    // 키워드 검색 후 게시물의 수를 검색하는 메서드
    int getTotalCntByKeyword(@Param("category") String category, @Param("keyword") String keyword);

    // 게시물 상세보기 메서드
    BoardVO get(Long boardNo);

    // 게시물 생성하는 메서드
    void create(BoardVO boardVO);

    // 게시물 수정하는 메서드
    int update(BoardVO boardVO);

    // 게시물 삭제하는 메서드
    int delete(Long boardNo);

    // 첨부파일을 생성하는 메서드
    void createAttachment(BoardAttachmentVO attach);

    // 게시물의 첨부파일 리스트를 가져오는 메서드
    List<BoardAttachmentVO> getAttachmentList(Long bno);

    // 첨부파일 번호로 첨부파일 가져오는 메서드
    BoardAttachmentVO getAttachment(Long boardNo);

    // 첨부파일 번호로 첨부파일 삭제하는 메서드
    int deleteAttachment(Long attachmentNo);

    // 게시물에 달린 댓글 리스트 가져오는 메서드
    List<CommentVO> getComments(Long boardNo);

    // 댓글을 작성하는 메서드
    void createComment(CommentVO commentVO);

    // 댓글수를 올리는 메서드
    void upCommentCnt(@Param("boardNo") Long boardNo);

    // 조회수를 올리는 메서드
    void upViewCnt(@Param("boardNo") Long boardNo);

    // 좋아요 수를 올리는 메서드
    void upLikeCnt(@Param("boardNo") Long boardNo);

    // 정렬, 검색을 포함한 게시물 리스트 메서드들
    List<BoardVO> getBoardsByPageAndKeywordOrderByregDate(@Param("offset") int offset, @Param("limit") int limit, @Param("category") String category,
                                                @Param("keyword") String keyword);

    List<BoardVO> getBoardsByPageAndKeywordOrderBylike(@Param("offset") int offset, @Param("limit") int limit, @Param("category") String category,
                                             @Param("keyword") String keyword);

    List<BoardVO> getBoardsByPageAndKeywordOrderByview(@Param("offset") int offset, @Param("limit") int limit, @Param("category") String category,
                                             @Param("keyword") String keyword);

    List<BoardVO> getBoardsByPageAndKeywordOrderBycomment(@Param("offset") int offset, @Param("limit") int limit, @Param("category") String category,
                                                @Param("keyword") String keyword);

    // 정렬만 포함한 게시물 리스트 메서드들
    List<BoardVO> getBoardsByPageOrderByregDate(@Param("offset") int offset, @Param("limit") int limit);

    List<BoardVO> getBoardsByPageOrderBylike(@Param("offset") int offset, @Param("limit") int limit);

    List<BoardVO> getBoardsByPageOrderByview(@Param("offset") int offset, @Param("limit") int limit);

    List<BoardVO> getBoardsByPageOrderBycomment(@Param("offset") int offset, @Param("limit") int limit);

    // 좋아요를 눌렀는지 중복 검사하는 메서드
    int likeCheck(@Param("boardNo") Long boardNo, @Param("userName") String userName);

    // 좋아요 수를 올려주는 메서드
    void likeUpdate(@Param("boardNo") Long boardNo, @Param("userName") String userName);

    // 인기글 리스트를 보여주는 메서드
    List<BoardVO> getBest();

    // 내가 작성한 게시물 보여주는 메서드
    List<BoardVO> mine(@Param("userName") String userName);

    void bestBoardPointUp(@Param("userName") String userName);
}
























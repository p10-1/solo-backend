package org.solo.board.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.solo.board.domain.BoardAttachmentVO;
import org.solo.board.domain.BoardVO;
import org.solo.common.pagination.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BoardMapper {
    int getTotalCount();
    List<BoardVO> getPage(PageRequest pageRequest);
    List<BoardVO> getList();
    BoardVO get(Long boardNo);
    void create(BoardVO boardVO);
    int update(BoardVO boardVO);
    int delete(Long boardNo);
    void createAttachment(BoardAttachmentVO attach);
    List<BoardAttachmentVO> getAttachmentList(Long bno);
    BoardAttachmentVO getAttachment(Long boardNo);
    int deleteAttachment(Long boardNo);
}
























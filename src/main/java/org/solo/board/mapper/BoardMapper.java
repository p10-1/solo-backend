package org.solo.board.mapper;

import org.solo.board.domain.BoardAttachmentVO;
import org.solo.board.domain.BoardVO;

import java.util.List;

public interface BoardMapper {
    public List<BoardVO> getList();

    public BoardVO get(Long boardNo);

    public void create(BoardVO board);

    public int update(BoardVO board);

    public int delete(Long boardNo);

    // BoardAttachment 관련
    public void createAttachment(BoardAttachmentVO attach);

    public List<BoardAttachmentVO> getAttachmentList(Long boardNo);

    public BoardAttachmentVO getAttachment(Long no);

    public int deleteAttachment(Long no);

    public int deleteAttachment_boardNo(Long boardNo);
}

package org.solo.board.service;

import org.solo.board.domain.BoardAttachmentVO;
import org.solo.board.dto.BoardDTO;

import java.util.List;

public interface BoardService {
    public List<BoardDTO> getList();
    public BoardDTO get(Long boardNo);
    public void create(BoardDTO board);
    public boolean update(BoardDTO board);
    public boolean delete(Long boardNo);

    public BoardAttachmentVO getAttachment(Long no);
    public boolean deleteAttachment(Long no);
    public int deleteAttachmentBoardNo(Long boardNo);
}

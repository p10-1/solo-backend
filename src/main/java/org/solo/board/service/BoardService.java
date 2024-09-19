package org.solo.board.service;

import org.solo.board.domain.BoardAttachmentVO;
import org.solo.board.domain.BoardVO;

import java.util.List;

public interface BoardService {
    public List<BoardVO> getList();
    public BoardVO get(Long boardNo);
    public void create(BoardVO boardVO);
    public boolean update(BoardVO boardVO);
    public boolean delete(Long boardNo);

//    public BoardAttachmentVO getAttachment(Long no);
//    public boolean deleteAttachment(Long no);
//    public int deleteAttachmentBoardNo(Long boardNo);

}

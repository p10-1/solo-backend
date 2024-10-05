package org.solo.board.service;

import org.solo.board.domain.BoardAttachmentVO;
import org.solo.board.domain.BoardVO;
import org.solo.board.domain.CommentVO;
import org.solo.board.mapper.BoardMapper;
import org.solo.common.pagination.PageRequest;
import org.solo.common.util.UploadFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
//@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final static String BASE_DIR = "/Users/junyoung/Documents/upload/board";
//    private final static String BASE_DIR = "C:\\upload\\board";
    private final BoardMapper boardMapper;

    @Autowired
    public BoardServiceImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @Override
    public int getTotalCnt() {
        return boardMapper.getTotalCnt();
    }

    @Override
    public int getTotalCntByKeyword(String category, String keyword) {
        return boardMapper.getTotalCntByKeyword(category, keyword);
    }

    @Override
    public List<BoardVO> getBoardsByPageOrderByregDate(PageRequest pageRequest){
        return boardMapper.getBoardsByPageOrderByregDate(pageRequest.getOffset(), pageRequest.getAmount());
    }

    @Override
    public List<BoardVO> getBoardsByPageOrderBylike(PageRequest pageRequest){
        return boardMapper.getBoardsByPageOrderBylike(pageRequest.getOffset(), pageRequest.getAmount());
    }

    @Override
    public List<BoardVO> getBoardsByPageOrderByview(PageRequest pageRequest){
        return boardMapper.getBoardsByPageOrderByview(pageRequest.getOffset(), pageRequest.getAmount());
    }

    @Override
    public List<BoardVO> getBoardsByPageOrderBycomment(PageRequest pageRequest){
        return boardMapper.getBoardsByPageOrderBycomment(pageRequest.getOffset(), pageRequest.getAmount());
    }

    @Override
    public List<BoardVO> getBoardsByPageAndKeywordOrderByregDate(PageRequest pageRequest, String category, String keyword) {
        return boardMapper.getBoardsByPageAndKeywordOrderByregDate(pageRequest.getOffset(), pageRequest.getAmount(), category, keyword);
    }

    @Override
    public List<BoardVO> getBoardsByPageAndKeywordOrderBylike(PageRequest pageRequest, String category, String keyword) {
        return boardMapper.getBoardsByPageAndKeywordOrderBylike(pageRequest.getOffset(), pageRequest.getAmount(), category, keyword);
    }

    @Override
    public List<BoardVO> getBoardsByPageAndKeywordOrderByview(PageRequest pageRequest, String category, String keyword) {
        return boardMapper.getBoardsByPageAndKeywordOrderByview(pageRequest.getOffset(), pageRequest.getAmount(), category, keyword);
    }

    @Override
    public List<BoardVO> getBoardsByPageAndKeywordOrderBycomment(PageRequest pageRequest, String category, String keyword) {
        return boardMapper.getBoardsByPageAndKeywordOrderBycomment(pageRequest.getOffset(), pageRequest.getAmount(), category, keyword);
    }

    @Override
    public BoardVO get(Long boardNo) {
        BoardVO boardVO = boardMapper.get(boardNo);
        boardMapper.upViewCnt(boardNo);
        if (boardVO == null) {
            System.out.println("No board found for boardNo: " + boardNo);
        } else {
            System.out.println("boardVO: " + boardVO);
        }
        return Optional.ofNullable(boardVO)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<BoardVO> getBestBoards() {
        List<BoardVO> bests = boardMapper.getBest();
        return bests;
    }

    @Override
    public List<CommentVO> getComments(Long boardNo) {
        return boardMapper.getComments(boardNo);
    }

    @Override
    public void createComment(CommentVO commentVO) {
        Long boardNo = commentVO.getBoardNo();
        boardMapper.upCommentCnt(boardNo);
        boardMapper.createComment(commentVO);
    }


    @Transactional
    @Override
    public BoardVO create(BoardVO boardVO) {
        boardMapper.create(boardVO);
        List<MultipartFile> files = boardVO.getFiles();
        if (files != null && !files.isEmpty()) {
            upload(boardVO.getBoardNo(), files);
        }
        return get(boardVO.getBoardNo());
    }

    private void upload(Long boardNo, List<MultipartFile> files) {
        for (MultipartFile part : files) {
            if (part.isEmpty()) continue;
            try {
                String uploadPath = UploadFiles.upload(BASE_DIR, part);
                BoardAttachmentVO attach = BoardAttachmentVO.from(part, boardNo, uploadPath);
                boardMapper.createAttachment(attach);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public BoardVO update(BoardVO boardVO) {
        int result = boardMapper.update(boardVO);
        List<MultipartFile> files = boardVO.getFiles();
        if (files != null && !files.isEmpty()) {
            upload(boardVO.getBoardNo(), files);
        }
        return get(boardVO.getBoardNo());
    }

    @Override
    public BoardVO delete(Long boardNo) {
        BoardVO board = get(boardNo);
        boardMapper.delete(boardNo);
        return board;
    }

    @Override
    public BoardAttachmentVO getAttachment(Long boardNo) {
        return boardMapper.getAttachment(boardNo);
    }

    @Override
    public boolean deleteAttachment(Long attachmentNo) {
        return boardMapper.deleteAttachment(attachmentNo) == 1;
    }

    @Override
    public void upLikeCnt(Long boardNo) {
        boardMapper.upLikeCnt(boardNo);
    }

    @Override
    public boolean likeCheck(Long boardNo, String userName) {
        return boardMapper.likeCheck(boardNo, userName) == 0;
    }

    @Override
    public void likeUpdate(Long boardNo, String userName) {
        boardMapper.likeUpdate(boardNo, userName);
    }

    @Override
    public List<BoardVO> mine(String userName) {
        return boardMapper.mine(userName);
    }
}

package org.solo.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.solo.board.domain.BoardAttachmentVO;
import org.solo.board.domain.BoardVO;
import org.solo.board.dto.BoardDTO;
import org.solo.board.mapper.BoardMapper;
import org.solo.common.util.UploadFiles;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Log4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final static String BASE_DIR = "c:/upload/solo/board/";
    final private BoardMapper mapper;

    @Override
    public List<BoardDTO> getList() {
        log.info("getList............");
        return mapper.getList().stream()
                .map(BoardDTO::of)
                .toList();
    }

    @Override
    public BoardDTO get(Long boardNo) {
        log.info("get........." + boardNo);
        BoardDTO board = BoardDTO.of(mapper.get(boardNo));
        return Optional.ofNullable(board).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    @Override
    public void create(BoardDTO board) {
        log.info("create......." + board);

        BoardVO boardVO = board.toVo();
        mapper.create(boardVO);
        board.setBoardNo(boardVO.getBoardNo());

        // 파일 업로드 처리
        List<MultipartFile> files = board.getFiles();
        if(files != null && !files.isEmpty()) { // 첨부 파일이 있는 경우
            upload(boardVO.getBoardNo(), files);
        }
    }

    private void upload(Long bno, List<MultipartFile> files) {
        for(MultipartFile part : files) {
            if(part.isEmpty()) continue;
            try {
                String uploadPath = UploadFiles.upload(BASE_DIR, part);
                BoardAttachmentVO attach = BoardAttachmentVO.of(part, bno, uploadPath);
                mapper.createAttachment(attach);
            } catch (IOException e) {
                throw new RuntimeException(e);  // @Transactional에서 감지, 자동 rollback
            }
        }
    }

    @Override
    public boolean update(BoardDTO board) {
        log.info("update........" + board);

        return mapper.update(board.toVo()) == 1;
    }

    @Override
    public boolean delete(Long boardNo) {
        log.info("delete........" + boardNo);

        BoardDTO board = get(boardNo);
        if(!board.getAttaches().isEmpty()) {
            int result = deleteAttachmentBoardNo(boardNo);
            if (result > 0) {
                log.info("FK Delete ............");
            } else {
                log.info("FK Delete Fail ........");
                return false;
            }
        }

        return mapper.delete(boardNo) == 1;
    }

    // 첨부 파일 얻기
    @Override
    public BoardAttachmentVO getAttachment(Long no) {
        return mapper.getAttachment(no);
    }

    // 첨부 파일 삭제
    @Override
    public boolean deleteAttachment(Long no) {
        return mapper.deleteAttachment(no) == 1;
    }

    @Override
    public int deleteAttachmentBoardNo(Long boardNo) {
        return mapper.deleteAttachment_boardNo(boardNo);
    }
}

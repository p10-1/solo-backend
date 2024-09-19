package org.solo.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.solo.board.domain.BoardAttachmentVO;
import org.solo.board.domain.BoardVO;
import org.solo.board.mapper.BoardMapper;
import org.solo.common.util.UploadFiles;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Log4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final static String BASE_DIR = "c:/upload/solo/board/";
    private final BoardMapper boardMapper;

    @Override
    public List<BoardVO> getList() {
        log.info("getList............");
        return boardMapper.getList();
    }

    @Override
    public BoardVO get(Long boardNo) {
        log.info("get........." + boardNo);
        BoardVO boardVO = boardMapper.get(boardNo);
        return boardVO;
    }

    @Override
    public void create(BoardVO boardVO) {
        log.info("create......." + boardVO);

        boardMapper.create(boardVO);
        boardVO.setBoardNo(boardVO.getBoardNo());

        // 파일 업로드 처리
//        List<MultipartFile> files = boardVO.getFiles();
//        if(files != null && !files.isEmpty()) { // 첨부 파일이 있는 경우
//            upload(boardVO.getBoardNo(), files);
//        }
    }

//    public String getFileSize() {
//        return UploadFiles.getFormatSize(size);
//    }

//    private void upload(Long bno, List<MultipartFile> files) {
//        for(MultipartFile part : files) {
//            if(part.isEmpty()) continue;
//            try {
//                String uploadPath = UploadFiles.upload(BASE_DIR, part);
//                BoardAttachmentVO attach = BoardAttachmentVO.of(part, bno, uploadPath);
//                boardMapper.createAttachment(attach);
//            } catch (IOException e) {
//                throw new RuntimeException(e);  // @Transactional에서 감지, 자동 rollback
//            }
//        }
//    }

    @Override
    public boolean update(BoardVO boardVO) {
        log.info("update........" + boardVO);

        return boardMapper.update(boardVO) == 1;
    }

    @Override
    public boolean delete(Long boardNo) {
        log.info("delete........" + boardNo);

        BoardVO boardVO = get(boardNo);
//        if(!boardVO.getAttaches().isEmpty()) {
//            int result = deleteAttachmentBoardNo(boardNo);
//            if (result > 0) {
//                log.info("FK Delete ............");
//            } else {
//                log.info("FK Delete Fail ........");
//                return false;
//            }
//        }

        return boardMapper.delete(boardNo) == 1;
    }

//    // 첨부 파일 얻기
//    @Override
//    public BoardAttachmentVO getAttachment(Long no) {
//        return boardMapper.getAttachment(no);
//    }

//    // 첨부 파일 삭제
//    @Override
//    public boolean deleteAttachment(Long no) {
//        return boardMapper.deleteAttachment(no) == 1;
//    }

//    @Override
//    public int deleteAttachmentBoardNo(Long boardNo) {
//        return boardMapper.deleteAttachment_boardNo(boardNo);
//    }
}

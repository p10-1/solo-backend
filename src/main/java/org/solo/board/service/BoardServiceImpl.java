package org.solo.board.service;

import org.solo.board.domain.BoardAttachmentVO;
import org.solo.board.domain.BoardVO;
import org.solo.board.mapper.BoardMapper;
import org.solo.common.pagination.Page;
import org.solo.common.pagination.PageRequest;
import org.solo.common.util.UploadFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

@Service
@Transactional
//@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final static String BASE_DIR = "junyoung/Documents/upload/board";
    private final BoardMapper boardMapper;

    @Autowired
    public BoardServiceImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @Override
    public Page<BoardVO> getPage(PageRequest pageRequest) {
        List<BoardVO> boards = boardMapper.getPage(pageRequest);
        int totalCount = boardMapper.getTotalCount();
        return Page.of(pageRequest, totalCount, boards);
    }

    @Override
    public List<BoardVO> getList() {
        return boardMapper.getList();
    }

    @Override
    public BoardVO get(Long boardNo) {
        System.out.println("Board get no: " + boardNo);
        BoardVO boardVO = boardMapper.get(boardNo);
        if (boardVO == null) {
            System.out.println("No board found for boardNo: " + boardNo);
        } else {
            System.out.println("boardVO: " + boardVO);
        }
        return Optional.ofNullable(boardVO)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional // 2개 이상의 insert 문이 실행될 수 있으므로 트랜잭션 처리 필요
    @Override
    public BoardVO create(BoardVO boardVO) {
        System.out.println("Board create: " + boardVO);
        boardMapper.create(boardVO);

        // 파일 업로드 처리
        List<MultipartFile> files = boardVO.getFiles(); // BoardVO에 getFiles() 메서드가 있어야 함
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
                // log.error(e.getMessage());
            }
        }
    }

    @Override
    public BoardVO update(BoardVO boardVO) {
        boardMapper.update(boardVO);
        // 파일 업로드 처리
        List<MultipartFile> files = boardVO.getFiles(); // BoardVO에 getFiles() 메서드가 있어야 함
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
    public boolean deleteAttachment(Long boardNo) {
        return boardMapper.deleteAttachment(boardNo) == 1;
    }
}

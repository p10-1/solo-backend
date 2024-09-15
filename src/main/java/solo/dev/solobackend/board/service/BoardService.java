package solo.dev.solobackend.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solo.dev.solobackend.board.dao.BoardDAO;

@Service
public class BoardService {
    private final BoardDAO boardDAO;

    @Autowired
    public BoardService(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }
}

package org.solo.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.solo.board.domain.BoardAttachmentVO;

import org.solo.board.domain.BoardVO;
import org.solo.board.service.BoardServiceImpl;
import org.solo.common.util.UploadFiles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Controller
@Log4j
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    final private BoardServiceImpl boardServiceImpl;

    @GetMapping("/list")
    public void list(Model model) {
        log.info("list");
        model.addAttribute("list", boardServiceImpl.getList());
    }

    @GetMapping("/create")
    public String create() {
        log.info("create");
        return "board/create";
    }

    @PostMapping("/create")
    public String create(BoardVO boardVO, RedirectAttributes ra) {
        log.info("create: " + boardVO);

        boardServiceImpl.create(boardVO);
        ra.addFlashAttribute("result", boardVO.getBoardNo());
        // localhost:8080/board/list + GET
        return "redirect:/board/list";
    }

    @GetMapping({"/get", "/update"})
    public void get(@RequestParam("boardNo") Long boardNo, Model model) {
        log.info("/get or update");
        model.addAttribute("boardVO", boardServiceImpl.get(boardNo));
    }

    @PostMapping("/update")
    public String update(BoardVO boardVO, RedirectAttributes ra) {
        log.info("update: " + boardVO);
        if(boardServiceImpl.update(boardVO)) {
            ra.addFlashAttribute("result", "success");
        }
        return "redirect:/board/list";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("boardNo") Long boardNo, RedirectAttributes ra) {
        log.info("delete......" + boardNo);
        if(boardServiceImpl.delete(boardNo)) {
            ra.addFlashAttribute("result", "success");
        }
        return "redirect:/board/list";
    }

    @GetMapping("/download/{boardNo}")
    @ResponseBody   // view를 사용하지 않고, 직접 내보냄
    public void download(@PathVariable("boardNo") Long boardNo, HttpServletResponse response) throws Exception {
        BoardAttachmentVO attach = boardServiceImpl.getAttachment(boardNo);

        File file = new File(attach.getPath());

        UploadFiles.download(response, file, attach.getFilename());
    }
}

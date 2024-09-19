package org.solo.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.solo.board.domain.BoardAttachmentVO;
import org.solo.board.dto.BoardDTO;
import org.solo.board.service.BoardService;
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
    final private BoardService service;

    @GetMapping("/list")
    public void list(Model model) {
        log.info("list");
        model.addAttribute("list", service.getList());
    }

    @GetMapping("/create")
    public String create() {
        log.info("create");
        return "board/create";
    }

    @PostMapping("/create")
    public String create(BoardDTO board, RedirectAttributes ra) {
        log.info("create: " + board);

        service.create(board);
        ra.addFlashAttribute("result", board.getBoardNo());
        // localhost:8080/board/list + GET
        return "redirect:/board/list";
    }

    @GetMapping({"/get", "/update"})
    public void get(@RequestParam("boardNo") Long boardNo, Model model) {
        log.info("/get or update");
        model.addAttribute("board", service.get(boardNo));
    }

    @PostMapping("/update")
    public String update(BoardDTO board, RedirectAttributes ra) {
        log.info("update: " + board);
        if(service.update(board)) {
            ra.addFlashAttribute("result", "success");
        }
        return "redirect:/board/list";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("boardNo") Long boardNo, RedirectAttributes ra) {
        log.info("delete......" + boardNo);
        if(service.delete(boardNo)) {
            ra.addFlashAttribute("result", "success");
        }
        return "redirect:/board/list";
    }

    @GetMapping("/download/{boardNo}")
    @ResponseBody   // view를 사용하지 않고, 직접 내보냄
    public void download(@PathVariable("boardNo") Long boardNo, HttpServletResponse response) throws Exception {
        BoardAttachmentVO attach = service.getAttachment(boardNo);

        File file = new File(attach.getPath());

        UploadFiles.download(response, file, attach.getFilename());
    }
}

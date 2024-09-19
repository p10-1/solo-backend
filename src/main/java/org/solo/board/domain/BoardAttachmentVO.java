package org.solo.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.solo.common.util.UploadFiles;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Log4j
public class BoardAttachmentVO {
    private Long no;
    private Long boardNo;
    private String filename;
    private String path;
    private String contentType;
    private Long size;
    private Date regDate;

//    public BoardAttachmentVO boardAttachmentVO (MultipartFile part, Long boardNo, String path) {
//        String filename = part.getOriginalFilename();
//        String contentType = part.getContentType();
//        Long size = part.getSize();
//        this.boardNo = getBoardNo();
//        this.path = getPath();
//
//        return ;
//    }
}

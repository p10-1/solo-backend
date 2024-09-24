package org.solo.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardVO {
    private Long boardNo;
    private String title;
    private String content;
    private String userID;
    private Date regDate;
    private Date modDate;
    private int likes;
    private int comments;
    private int views;
    // 첨부파일
    private List<BoardAttachmentVO> attaches;
    // 파일 업로드
    private List<MultipartFile> files;
    public List<MultipartFile> getFiles() {
        return files;
    }
    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
}


//CREATE TABLE board
//        (
//                boardNo			INTEGER AUTO_INCREMENT PRIMARY KEY,
//                title		VARCHAR(200) NOT NULL,
//content		TEXT,
//userID		VARCHAR(50) NOT NULL,
//regDate		DATETIME DEFAULT CURRENT_TIMESTAMP,
//updateDate	DATETIME DEFAULT CURRENT_TIMESTAMP,
//likes       INTEGER DEFAULT 0,
//contents    INTEGER DEFAULT 0,
//views       INTEGER DEFAULT 0
//        );
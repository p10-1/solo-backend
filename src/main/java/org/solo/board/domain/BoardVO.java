package org.solo.board.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
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
    private String userName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime regDate; // LOCALDATETIME
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updateDate;
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

package org.solo.board.domain;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardVO {
    private Long boardNo;
    private String userID;
    private String title;
    private String content;
    private Date regDate;
    private Date modDate;
    private int likes;
    private int views;
    private int comments;
    private List<BoardAttachmentVO> attaches;
}

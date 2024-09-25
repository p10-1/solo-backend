package org.solo.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentVO {
    private Long commentNo;
    private Long boardNo;
    private String userId;
    private String commentText;
    private Date regDate;
}
//
//CREATE TABLE comment
//        (
//    `commentNo`   INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
//    `boardNo`     INT         NOT NULL,
//    `userId`      VARCHAR(50) NOT NULL,
//    `commentText` TEXT        NOT NULL,
//        `regDate`     DATE        NOT NULL,
//FOREIGN KEY (boardNo) REFERENCES board (boardNo)
//        );
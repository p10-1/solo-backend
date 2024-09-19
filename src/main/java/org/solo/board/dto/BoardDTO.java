package org.solo.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.solo.board.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDTO {
    private Long boardNo;
    private String title;
    private String content;
    private String userID;
    private Date regDate;
    private Date modDate;
    private int likes;
    private int views;

    // 첨부 파일
    private List<BoardAttachmentVO> attaches;
    // 실제 업로드된 파일(Multipart) 목록
    List<MultipartFile> files = new ArrayList<>();

    // VO -> DTO 변환
    public static BoardDTO of(BoardVO vo) {
        return vo == null ? null : BoardDTO.builder()
                .boardNo(vo.getBoardNo())
                .title(vo.getTitle())
                .content(vo.getContent())
                .userID(vo.getUserID())
                .attaches(vo.getAttaches())
                .regDate(vo.getRegDate())
                .modDate(vo.getModDate())
                .likes(vo.getLikes())
                .views(vo.getViews())
                .build();
    }

    // DTO -> VO 변환
    public BoardVO toVo() {
        return BoardVO.builder()
                .boardNo(boardNo)
                .title(title)
                .content(content)
                .userID(userID)
                .attaches(attaches)
                .regDate(regDate)
                .modDate(modDate)
                .likes(likes)
                .views(views)
                .build();
    }
}

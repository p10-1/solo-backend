package org.solo.board.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.solo.common.util.UploadFiles;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardAttachmentVO {
    private Long attachmentNo;            //
    private Long bno;           // FK: Board의 no
    private String filename;    // 원본 파일명
    private String path;        // 서버에 저장된 파일 경로
    private String contentType; // 파일 mime-type
    private Long size;          // 파일의 크기
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;       // 등록일

    // 팩토리 메서드
    public static BoardAttachmentVO from(MultipartFile part, Long bno, String path) {

        return builder()
                .bno(bno)
                .filename(part.getOriginalFilename())
                .path(path)
                .contentType(part.getContentType())
                .size(part.getSize())
                .build();
    }

    public String getFileSize() {
        return UploadFiles.getFormatSize(size);
    }
}

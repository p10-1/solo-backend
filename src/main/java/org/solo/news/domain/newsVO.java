package org.solo.news.domain;



import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class newsVO {
    private int newsID;
    private String title;
    private String link;
    private String writer;
    private String author;
    private String newDate;
    private String category;
//    private String content;
}


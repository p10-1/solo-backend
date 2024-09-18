package org.solo.news.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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


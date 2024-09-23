package org.solo.news.domain;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsVO {
    private long no;
    private String title;
    private String link;
    private String category;
    private String author;
    private String pubDate;
    private String description;

}

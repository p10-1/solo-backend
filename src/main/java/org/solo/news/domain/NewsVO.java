package org.solo.news.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

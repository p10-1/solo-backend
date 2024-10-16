package org.solo.news.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsVO {
    private long newsNo;
    private String title;
    private String link;
    private String category;
    private String pubDate;
    private String imageUrl;

}

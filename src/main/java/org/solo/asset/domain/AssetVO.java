package org.solo.asset.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetVO {

    private int userNo;
    private String userID;
    private int cash;
    private int stock;
    private int property;
    private int deposit;
    private String consume;
}
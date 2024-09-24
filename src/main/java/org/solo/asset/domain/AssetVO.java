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

    // 대출 자산 추가
    private Integer loanAmount;   // 대출액
    private String loanPurpose;    // 대출 목적
    private Integer period;    // 대출 기간
}

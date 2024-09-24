package org.solo.asset.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetVO {

    private int userNo;
    private String userID;
    private int cash;// 현금자산: asset : amount
    private int stock;
    private int property;
    private int deposit;
    private String consume;

    // 대출 자산 추가
    private Integer loanAmount;   // 대출액
    private String loanPurpose;    // 대출 목적
    private Integer period;    // 대출 기간

    private LocalDateTime createDate; // 생성일
    private LocalDateTime updateDate; // 수정일
}


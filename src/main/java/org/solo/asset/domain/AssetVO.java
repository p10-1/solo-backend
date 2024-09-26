package org.solo.asset.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetVO {

    private int userNo;
    private String userId;
    private int cash;// 현금자산: asset : amount
    private int stock;
    private int property;
    private int deposit;
    private String consume;

    // 대출 자산 추가
    private Integer loanAmount;   // 대출액
    private String loanPurpose;    // 대출 목적
    private Integer period;    // 대출 기간

    private Date createDate; // 생성일
    private Date updateDate; // 수정일

//    private String userId;
//    private Integer cash;
//    private Integer stock;
//    private Integer property;
//    private Integer deposit;
//    private String consume;
//    private Integer loanAmount;
//    private String loanPurpose;
//    private Integer period;
//    private LocalDateTime createDate;
//    private LocalDateTime updateDate;
}


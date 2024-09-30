package org.solo.asset.domain;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetVO {
    private String userId;        // 사용자 ID
    private String cashBank;      // 현금 은행
    private String cashAccount;    // 현금 계좌
    private String cash;          // 현금 자산 (문자열 형식)

    private String stockBank;     // 주식 은행
    private String stockAccount;   // 주식 계좌
    private String stock;         // 주식 자산 (문자열 형식)

    private String propertyBank;   // 부동산 은행
    private String propertyAccount; // 부동산 계좌
    private String property;       // 부동산 자산 (문자열 형식)

    private String depositBank;    // 예적금 은행
    private String depositAccount;  // 예적금 계좌
    private String deposit;        // 예적금 자산 (문자열 형식)

    private String consume;        // 소비 유형
    private Integer loanAmount;    // 대출액
    private String loanPurpose;     // 대출 목적
    private Integer period;        // 대출 기간 (개월)
    private Integer interest;      // 이자율 (정수형)

    private Date createDate;     // 생성일 (문자열 형식)
    private Date updateDate;     // 수정일 (문자열 형식)
}

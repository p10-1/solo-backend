package org.solo.asset.domain;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetVO {
    private Integer assetNo;      // 새로 추가된 필드
    private String userId;        // 사용자 ID
    private String cashBank;      // 현금 은행
    private String cashAccount;    // 현금 계좌
    private String cash;          // 현금 자산 (문자열 형식)

    private String stockBank;     // 주식 은행
    private String stockAccount;   // 주식 계좌
    private String stock;         // 주식 자산 (문자열 형식)

    private String depositBank;    // 예적금 은행
    private String depositAccount;  // 예적금 계좌
    private String deposit;        // 예적금 자산 (문자열 형식)

    private String insuranceCompany;    //보험회사
    private String insuranceName;       //보험이름
    private String insurance;           // 보험금

    private String type;        // 자산 유형
    private Long loanAmount;
    private String loanPurpose;     // 대출 목적
    private Integer period;        // 대출 기간 (개월)
    private Double interest;      // 이자율 (정수형)

    private Date createDate;     // 생성일 (문자열 형식)



    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

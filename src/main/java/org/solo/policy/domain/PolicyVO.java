package org.solo.policy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyVO {
    private String bizId; // 정책 ID
    private String polyBizTy; // 기관 및 지자체 구분
    private String polyBizSjnm; // 	정책명
    private String polyItcnCn; // 정책 소개
    private String sporCn; // 지원 내용
    private String rqutUrla; // 사이트 주소
    private String polyRlmCd; // 분류
}

//CREATE TABLE `policy` (
//        `policyNo` int NOT NULL AUTO_INCREMENT,
//  `bizId` varchar(100) NOT NULL,
//  `polyBizTy` varchar(100) DEFAULT NULL,
//  `polyBizSjnm` varchar(1000) DEFAULT NULL,
//  `polyItcnCn` varchar(2000) DEFAULT NULL,
//  `sporCn` varchar(3000) DEFAULT NULL,
//  `rqutUrla` varchar(1000) DEFAULT NULL,
//PRIMARY KEY (`policyNo`)
//)
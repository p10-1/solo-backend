package org.solo.product.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO {
    private String dclsMonth; // 공시 제출월
    private String finCoNo; // 금융회사 코드
    private String korCoNm; // 금융회사 명
    private String finPrdtCd; // 금융 상품코드
    private String finPrdtNm; // 금융 상품명
    private String joinWay; // 가입 방법
    private String mtrtInt; // 만기 후 이자율
    private String spclCnd; // 우대 조건
    private String joinMember; // 가입 대상
    private String etcNote; // 기타 유의사항
    private String type; // 예금인지 적금인지
}

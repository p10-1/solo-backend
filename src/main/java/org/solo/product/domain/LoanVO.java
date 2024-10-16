package org.solo.product.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanVO {
    private String dclsMonth; // 공시 제출월
    private String finCoNo; // 금융회사 코드
    private String korCoNm; // 금융회사 명
    private String finPrdtCd; // 금융 상품코드
    private String finPrdtNm; // 금융 상품명
    private String joinWay; // 가입 방법
    private String erlyRpayFee; // 중도상환 수수료
    private String dlyRate; // 연체이자율
    private String loanLmt; // 대출한도
}

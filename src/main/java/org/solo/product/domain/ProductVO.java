package org.solo.product.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO {
    private String dcls_month; // 공시 제출월
    private String kor_co_nm; // 금융회사 명
    private String fin_prdt_nm; // 금융 상품명
    private String join_way; // 가입 방법
    private String mtrt_int; // 만기 후 이자율
    private String spcl_cnd; // 우대 조건
}

package org.solo.product.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionVO {
    private String dclsMonth;
    private String finCoNo;
    private String finPrdtCd;
    private String saveTrm;
    private Double intrRate;
    private Double intrRate2;
    private String type;
}

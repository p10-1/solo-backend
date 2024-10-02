package org.solo.product.service;

import org.solo.common.pagination.PageRequest;
import org.solo.policy.domain.PolicyVO;
import org.solo.product.domain.OptionVO;
import org.solo.product.domain.ProductVO;

import java.util.List;
import java.util.Map;

public interface ProductService {
    void fetchDeposit();
    void fetchSaving();
    void saveProducts(List<ProductVO> products);
    void saveOptions(List<OptionVO> options);
    List<ProductVO> getKbProducts();
    int getTotalCnt();
    int getTotalCntByKeyword(String keyword);
    List<ProductVO> getProductsByPage(PageRequest pageRequest);
    List<ProductVO> getProductsByPageAndKeyword(PageRequest pageRequest, String keyword);
    List<OptionVO> getOption(String finPrdtCd);
//    List<ProductVO> getRecommend(int period);



    //simp son
    List<ProductVO> getRecommend(int period);

}

package org.solo.product.service;

import org.solo.common.pagination.PageRequest;
import org.solo.policy.domain.PolicyVO;
import org.solo.product.domain.ProductVO;

import java.util.List;

public interface ProductService {
    void fetchDeposit();
    void fetchSaving();
    void saveProducts(List<ProductVO> products);
    List<ProductVO> getKbProducts();
    int getTotalCnt();
    int getTotalCntByKeyword(String keyword);
    List<ProductVO> getProductsByPage(PageRequest pageRequest);
    List<ProductVO> getProductsByPageAndKeyword(PageRequest pageRequest, String keyword);
}

package org.solo.product.service;

import org.solo.product.domain.ProductVO;

import java.util.List;

public interface ProductService {
    void fetchDeposit();
    void fetchSaving();
    void saveProducts(List<ProductVO> products);
}

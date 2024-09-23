package org.solo.product.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.solo.policy.domain.PolicyVO;
import org.solo.product.domain.ProductVO;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface ProductMapper {
    void fetchProducts(ProductVO productVO);
    int findByToken(Map<String, Object> productToken);
}

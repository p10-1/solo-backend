package org.solo.product.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.solo.policy.domain.PolicyVO;
import org.solo.product.domain.ProductVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ProductMapper {
    void fetchProducts(ProductVO productVO);
    int findByToken(Map<String, Object> productToken);
    List<ProductVO> getKbProducts();
    int getTotalCnt();
    int getTotalCntByKeyword(String keyword);
    List<ProductVO> getProductsByPage(@Param("offset") int offset, @Param("limit") int limit);
    List<ProductVO> getProductsByPageAndKeyword(@Param("offset") int offset,
                                               @Param("limit") int limit,
                                               @Param("keyword") String keyword);
}

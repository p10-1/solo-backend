package org.solo.product.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.solo.policy.domain.PolicyVO;
import org.solo.product.domain.LoanVO;
import org.solo.product.domain.OptionVO;
import org.solo.product.domain.ProductVO;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ProductMapper {

    // DB에 금융 상품을 저장하는 메서드
    void fetchProducts(ProductVO productVO);

    // 금융 상품의 중복을 체크하는 메서드
    int findByToken(Map<String, Object> productToken);

    // 국민은행 상품을 불러오는 메서드
    List<ProductVO> getKbProducts(@Param("type") String type);

    // 국민은행 대출을 불러오는 메서드
    List<LoanVO> getKbLoans();

    // 전체 상품의 개수 불러오는 메서드
    int getTotalCnt(@Param("type") String type);

    // 대출의 개수를 불러오는 메서드
    int getLoanTotalCnt();

    // 키워드 검색 후 상품의 개수 불러오는 메서드
    int getTotalCntByKeyword(@Param("keyword") String keyword, @Param("type") String type);

    // 키워드 검색후 대출의 개수를 불러오는 메서드
    int getLoanTotalCntByKeyword(@Param("keyword") String keyword);

    // 페이지 당 금융 상품의 리스트 불러오는 메서드
    List<ProductVO> getProductsByPage(@Param("offset") int offset, @Param("limit") int limit, @Param("type") String type);

    // 검색된 페이지 당 금융 상품의 리스트를 불러오는 메서드
    List<ProductVO> getProductsByPageAndKeyword(@Param("offset") int offset,
                                               @Param("limit") int limit,
                                               @Param("keyword") String keyword, @Param("type") String type);

    // 페이지 당 대출의 리스트를 불러오는 메서드
    List<LoanVO> getLoansByPage(@Param("offset") int offset, @Param("limit") int limit);

    // 검색된 페이지 당 대출의 리스트를 불러오는 메서드
    List<LoanVO> getLoansPageAndKeyword(@Param("offset") int offset, @Param("limit") int limit, @Param("keyword") String keyword);

    // 금융 상품의 옵션을 중복 체크하는 메서드
    int findByOptionToken(Map<String, Object> optionToken);

    // DB에 옵셥을 저장하는 메서드
    void fetchOptions(OptionVO optionVO);

    // 대출의 중복을 체크하는 메서드
    int findByLoanToken(Map<String, Object> loanToken);

    // DB에 대출을 저장하는 메서드
    void fetchLoans(LoanVO loanVO);

    // 금융 상품에 따른 옵션 리스트를 불러오는 메서드
    List<OptionVO> getOption(@Param("finPrdtCd") String finPrdtCd);

    // 유저에게 추천 금융 상품을 불러오는 메서드
    List<ProductVO> getRecommend(@Param("userId") String userId);

    // 유저가 대출이 있는지 체크하는 메서드
    int haveLoan(@Param("userId") String userId);

    // 국민은행 상품 2개를 랜덤으로 불러오는 메서드
    List<ProductVO> getKbRand();

}

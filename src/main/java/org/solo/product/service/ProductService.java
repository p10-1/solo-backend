package org.solo.product.service;

import org.solo.common.pagination.PageRequest;
import org.solo.policy.domain.PolicyVO;
import org.solo.product.domain.LoanVO;
import org.solo.product.domain.OptionVO;
import org.solo.product.domain.ProductVO;

import java.util.List;
import java.util.Map;

public interface ProductService {
    void fetchDeposit();
    void fetchSaving();
    void fetchLoan();
    void saveProducts(List<ProductVO> products);
    void saveOptions(List<OptionVO> options);
    void saveLoans(List<LoanVO> loans);
    List<ProductVO> getKbProducts(String type);
    List<LoanVO> getKbLoans();
    int getTotalCnt(String type);
    int getTotalCntByKeyword(String keyword, String type);
    int getLoanTotalCnt();
    int getLoanTotalCntByKeyword(String keyword);
    List<ProductVO> getProductsByPage(PageRequest pageRequest, String type);
    List<ProductVO> getProductsByPageAndKeyword(PageRequest pageRequest, String keyword, String type);
    List<LoanVO> getLoansPageAndKeyword(PageRequest pageRequest, String keyword);
    List<LoanVO> getLoansByPage(PageRequest pageRequest);
    List<OptionVO> getOption(String finPrdtCd);
    List<ProductVO> getRecommend(String userId);



}

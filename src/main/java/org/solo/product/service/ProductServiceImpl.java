package org.solo.product.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solo.common.pagination.PageRequest;
import org.solo.policy.domain.PolicyVO;
import org.solo.product.domain.LoanVO;
import org.solo.product.domain.OptionVO;
import org.solo.product.domain.ProductVO;
import org.solo.product.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private ObjectMapper objectMapper;

    private final ProductMapper productMapper;
    private final RestTemplate restTemplate;

    @Value("${productAPI.depositUrl}") String depositUrl;
    @Value("${productAPI.savingUrl}") String savingUrl;
    @Value("${productAPI.loanUrl}") String loanUrl;
    @Value("${productAPI.auth}") String auth;
    @Value("${productAPI.topFinGrpNo}") String topFinGrpNo;
    @Value("${productAPI.pageNo}") String pageNo;

    public ProductServiceImpl(ProductMapper productMapper, RestTemplate restTemplate) {
        this.productMapper = productMapper;
        this.restTemplate = restTemplate;
    }

    public void fetchDeposit() {
        String requestDepositUrl = UriComponentsBuilder.fromHttpUrl(depositUrl)
                .queryParam("auth", auth)
                .queryParam("topFinGrpNo", topFinGrpNo)
                .queryParam("pageNo", pageNo)
                .toUriString();
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestDepositUrl, String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String jsonResponse = responseEntity.getBody();
                Map<String, Object> responseMap = objectMapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>() {});
                Map<String, Object> results = (Map<String, Object>) responseMap.get("result");
                List<Object> baseList = (List<Object>) results.get("baseList");
                List<Object> optionList = (List<Object>) results.get("optionList");
                List<ProductVO> products = new ArrayList<>();
                List<OptionVO> options = new ArrayList<>();
                String type = "예금";
                for (Object item : baseList) {
                    Map<String, Object> deposit = (Map<String, Object>) item;
                    String dclsMonth = (String) deposit.get("dcls_month");
                    String finCoNo = (String) deposit.get("fin_co_no");
                    String korCoNm = (String) deposit.get("kor_co_nm");
                    String finPrdtCd = (String) deposit.get("fin_prdt_cd");
                    String finPrdtNm = (String) deposit.get("fin_prdt_nm");
                    String joinWay = (String) deposit.get("join_way");
                    String mtrtInt = (String) deposit.get("mtrt_int");
                    String spclCnd = (String) deposit.get("spcl_cnd");
                    String joinMember = (String) deposit.get("join_member");
                    String etcNote = (String) deposit.get("etc_note");

                    ProductVO productVO = new ProductVO(dclsMonth, finCoNo, korCoNm, finPrdtCd, finPrdtNm, joinWay, mtrtInt, spclCnd, joinMember, etcNote, type);
                    products.add(productVO);
                }
                saveProducts(products);
                for (Object item : optionList) {
                    Map<String, Object> option = (Map<String, Object>) item;
                    String dclsMonth = (String) option.get("dcls_month");
                    String forCoNm = (String) option.get("fin_co_no");
                    String finPrdtCd = (String) option.get("fin_prdt_cd");
                    String saveTrm = (String) option.get("save_trm");
                    Object intrRateObj = option.get("intr_rate");
                    Double intrRate = (intrRateObj instanceof Number) ? ((Number) intrRateObj).doubleValue() : null;
                    Object intrRate2Obj = option.get("intr_rate2");
                    Double intrRate2 = (intrRate2Obj instanceof Number) ? ((Number) intrRate2Obj).doubleValue() : null;

                    OptionVO optionVO = new OptionVO(dclsMonth, forCoNm, finPrdtCd, saveTrm, intrRate, intrRate2,type);
                    options.add(optionVO);
                }
                saveOptions(options);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchSaving() {
        String requestSavingUrl = UriComponentsBuilder.fromHttpUrl(savingUrl)
                .queryParam("auth", auth)
                .queryParam("topFinGrpNo", topFinGrpNo)
                .queryParam("pageNo", pageNo)
                .toUriString();
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestSavingUrl, String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String jsonResponse = responseEntity.getBody();
                Map<String, Object> responseMap = objectMapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>() {});
                Map<String, Object> results = (Map<String, Object>) responseMap.get("result");
                List<Object> baseList = (List<Object>) results.get("baseList");
                List<Object> optionList = (List<Object>) results.get("optionList");
                List<ProductVO> products = new ArrayList<>();
                List<OptionVO> options = new ArrayList<>();
                String type = "적금";
                for (Object item : baseList) {
                    Map<String, Object> saving = (Map<String, Object>) item;
                    String dclsMonth = (String) saving.get("dcls_month");
                    String finCoNo = (String) saving.get("fin_co_no");
                    String korCoNm = (String) saving.get("kor_co_nm");
                    String finPrdtCd = (String) saving.get("fin_prdt_cd");
                    String finPrdtNm = (String) saving.get("fin_prdt_nm");
                    String joinWay = (String) saving.get("join_way");
                    String mtrtInt = (String) saving.get("mtrt_int");
                    String spclCnd = (String) saving.get("spcl_cnd");
                    String joinMember = (String) saving.get("join_member");
                    String etcNote = (String) saving.get("etc_note");

                    ProductVO productVO = new ProductVO(dclsMonth, finCoNo, korCoNm, finPrdtCd, finPrdtNm, joinWay, mtrtInt, spclCnd, joinMember, etcNote, type);
                    products.add(productVO);
                }
                saveProducts(products);
                for (Object item : optionList) {
                    Map<String, Object> option = (Map<String, Object>) item;
                    String dclsMonth = (String) option.get("dcls_month");
                    String forCoNm = (String) option.get("fin_co_no");
                    String finPrdtCd = (String) option.get("fin_prdt_cd");
                    String saveTrm = (String) option.get("save_trm");
                    Object intrRateObj = option.get("intr_rate");
                    Double intrRate = (intrRateObj instanceof Number) ? ((Number) intrRateObj).doubleValue() : null; // 안전하게 변환
                    Object intrRate2Obj = option.get("intr_rate2");
                    Double intrRate2 = (intrRate2Obj instanceof Number) ? ((Number) intrRate2Obj).doubleValue() : null; // 안전하게 변환

                    OptionVO optionVO = new OptionVO(dclsMonth, forCoNm, finPrdtCd, saveTrm, intrRate, intrRate2,type);
                    options.add(optionVO);
                }
                saveOptions(options);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchLoan() {
        String requestSavingUrl = UriComponentsBuilder.fromHttpUrl(loanUrl)
                .queryParam("auth", auth)
                .queryParam("topFinGrpNo", topFinGrpNo)
                .queryParam("pageNo", pageNo)
                .toUriString();
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestSavingUrl, String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String jsonResponse = responseEntity.getBody();
                Map<String, Object> responseMap = objectMapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>() {});
                Map<String, Object> results = (Map<String, Object>) responseMap.get("result");
                List<Object> baseList = (List<Object>) results.get("baseList");
                List<LoanVO> loans = new ArrayList<>();
                for (Object item : baseList) {
                    Map<String, Object> loan = (Map<String, Object>) item;
                    String dclsMonth = (String) loan.get("dcls_month");
                    String finCoNo = (String) loan.get("fin_co_no");
                    String korCoNm = (String) loan.get("kor_co_nm");
                    String finPrdtCd = (String) loan.get("fin_prdt_cd");
                    String finPrdtNm = (String) loan.get("fin_prdt_nm");
                    String joinWay = (String) loan.get("join_way");
                    String erlyRpayFee = (String) loan.get("erly_rpay_fee");
                    String dlyRate = (String) loan.get("dly_rate");
                    String loanLmt = (String) loan.get("loan_lmt");

                    LoanVO loanVO = new LoanVO(dclsMonth, finCoNo, korCoNm, finPrdtCd, finPrdtNm, joinWay, erlyRpayFee, dlyRate, loanLmt);
                    loans.add(loanVO);
                }
                saveLoans(loans);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveProducts(List<ProductVO> products) {
        for (ProductVO productVO : products) {
            Map<String, Object> productToken = new HashMap<>();
            productToken.put("finPrdtNm", productVO.getFinPrdtNm());
            productToken.put("mtrtInt", productVO.getMtrtInt());
            productToken.put("etcNote", productVO.getEtcNote());
            productToken.put("type", productVO.getType());
            if (productMapper.findByToken(productToken) == 0) {
                productMapper.fetchProducts(productVO);
            }
        }
    }

    public void saveOptions(List<OptionVO> options) {
        for (OptionVO optionVO : options) {
            Map<String, Object> optionToken = new HashMap<>();
            optionToken.put("dclsMonth", optionVO.getDclsMonth());
            optionToken.put("finCoNo", optionVO.getFinCoNo());
            optionToken.put("finPrdtCd", optionVO.getFinPrdtCd());
            optionToken.put("saveTrm", optionVO.getSaveTrm());
            if (productMapper.findByOptionToken(optionToken) == 0) {
                productMapper.fetchOptions(optionVO);
            }
        }
    }

    public void saveLoans(List<LoanVO> loans) {
        for (LoanVO loanVO : loans) {
            Map<String, Object> loanToken = new HashMap<>();
            loanToken.put("finPrdtCd", loanVO.getFinPrdtCd());
            loanToken.put("dlyRate", loanVO.getDlyRate());
            loanToken.put("loanLmt", loanVO.getLoanLmt());
            if (productMapper.findByLoanToken(loanToken) == 0) {
                productMapper.fetchLoans(loanVO);
            }
        }
    }

    public List<OptionVO> getOption(String finPrdtCd) {
        return productMapper.getOption(finPrdtCd);
    }

    public List<ProductVO> getKbProducts(String type) {
        return productMapper.getKbProducts(type);
    }

    public List<LoanVO> getKbLoans() {
        return productMapper.getKbLoans();
    }

    public int getTotalCnt(String type) {
        return productMapper.getTotalCnt(type);
    }

    public int getTotalCntByKeyword(String keyword, String type) {
        return productMapper.getTotalCntByKeyword(keyword, type);
    }

    public int getLoanTotalCnt() {
        return productMapper.getLoanTotalCnt();
    }

    public int getLoanTotalCntByKeyword(String keyword) {
        return productMapper.getLoanTotalCntByKeyword(keyword);
    }

    public List<ProductVO> getProductsByPage(PageRequest pageRequest, String type) {
        return productMapper.getProductsByPage(pageRequest.getOffset(), pageRequest.getAmount(), type);
    }

    public List<ProductVO> getProductsByPageAndKeyword(PageRequest pageRequest, String keyword, String type) {
        return productMapper.getProductsByPageAndKeyword(pageRequest.getOffset(), pageRequest.getAmount(), keyword, type);
    }

    public List<LoanVO> getLoansByPage(PageRequest pageRequest) {
        return productMapper.getLoansByPage(pageRequest.getOffset(), pageRequest.getAmount());
    }

    public List<LoanVO> getLoansPageAndKeyword(PageRequest pageRequest, String keyword){
        return productMapper.getLoansPageAndKeyword(pageRequest.getOffset(), pageRequest.getAmount(), keyword);
    }

    public List<ProductVO> getRecommend(String userId) {
        if (productMapper.haveLoan(userId) > 0) {
            return productMapper.getRecommend(userId);
        } else {
            return productMapper.getKbRand();
        }
    }
}

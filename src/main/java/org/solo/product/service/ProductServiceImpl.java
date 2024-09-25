package org.solo.product.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.solo.common.pagination.PageRequest;
import org.solo.policy.domain.PolicyVO;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ObjectMapper objectMapper;

    private final ProductMapper productMapper;
    private final RestTemplate restTemplate;

    @Value("${productAPI.depositUrl}") String depositUrl;
    @Value("${productAPI.savingUrl}") String savingUrl;
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
        System.out.println("requestDepositUrl: " + requestDepositUrl);
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestDepositUrl, String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String jsonResponse = responseEntity.getBody();
                Map<String, Object> responseMap = objectMapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>() {});
                Map<String, Object> results = (Map<String, Object>) responseMap.get("result");

                List<Object> baseList = (List<Object>) results.get("baseList");

                List<ProductVO> products = new ArrayList<>();
                String type = "예금";
                for (Object item : baseList) {
                    Map<String, Object> deposit = (Map<String, Object>) item;
                    String dclsMonth = (String) deposit.get("dcls_month");
                    String korCoNm = (String) deposit.get("kor_co_nm");
                    String finPrdtNm = (String) deposit.get("fin_prdt_nm");
                    String joinWay = (String) deposit.get("join_way");
                    String mtrtInt = (String) deposit.get("mtrt_int");
                    String spclCnd = (String) deposit.get("spcl_cnd");
                    String joinMember = (String) deposit.get("join_member");
                    String etcNote = (String) deposit.get("etc_note");

                    ProductVO productVO = new ProductVO(dclsMonth, korCoNm, finPrdtNm, joinWay, mtrtInt, spclCnd, joinMember, etcNote, type);
                    products.add(productVO);
                }
                saveProducts(products);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 예외를 출력하여 문제를 확인
        }
    }

    public void fetchSaving() {
        String requestSavingUrl = UriComponentsBuilder.fromHttpUrl(savingUrl)
                .queryParam("auth", auth)
                .queryParam("topFinGrpNo", topFinGrpNo)
                .queryParam("pageNo", pageNo)
                .toUriString();
        System.out.println("requestSavingUrl: " + requestSavingUrl);
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestSavingUrl, String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String jsonResponse = responseEntity.getBody();
                Map<String, Object> responseMap = objectMapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>() {});
                Map<String, Object> results = (Map<String, Object>) responseMap.get("result");
                System.out.println("results: " + results);
                List<Object> baseList = (List<Object>) results.get("baseList");
                System.out.println("baseList size: " + baseList.size());

                List<ProductVO> products = new ArrayList<>();
                String type = "적금";
                for (Object item : baseList) {
                    Map<String, Object> saving = (Map<String, Object>) item;
                    String dclsMonth = (String) saving.get("dcls_month");
                    String korCoNm = (String) saving.get("kor_co_nm");
                    String finPrdtNm = (String) saving.get("fin_prdt_nm");
                    String joinWay = (String) saving.get("join_way");
                    String mtrtInt = (String) saving.get("mtrt_int");
                    String spclCnd = (String) saving.get("spcl_cnd");
                    String joinMember = (String) saving.get("join_member");
                    String etcNote = (String) saving.get("etc_note");

                    ProductVO productVO = new ProductVO(dclsMonth, korCoNm, finPrdtNm, joinWay, mtrtInt, spclCnd, joinMember, etcNote, type);
                    System.out.println("적금: " + productVO);
                    products.add(productVO);
                }
                saveProducts(products);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 예외를 출력하여 문제를 확인
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

    public List<ProductVO> getKbProducts() {
        return productMapper.getKbProducts();
    }

    public int getTotalCnt() {
        return productMapper.getTotalCnt();
    }

    public int getTotalCntByKeyword(String keyword) {
        return productMapper.getTotalCntByKeyword(keyword);
    }

    public List<ProductVO> getProductsByPage(PageRequest pageRequest) {
        return productMapper.getProductsByPage(pageRequest.getOffset(), pageRequest.getAmount());
    }

    public List<ProductVO> getProductsByPageAndKeyword(PageRequest pageRequest, String keyword) {
        return productMapper.getProductsByPageAndKeyword(pageRequest.getOffset(), pageRequest.getAmount(), keyword);
    }
}

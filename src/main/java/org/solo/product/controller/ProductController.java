package org.solo.product.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.solo.common.pagination.Page;
import org.solo.common.pagination.PageRequest;
import org.solo.product.domain.LoanVO;
import org.solo.product.domain.OptionVO;
import org.solo.product.domain.ProductVO;
import org.solo.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@Api(value = "Product Controller", tags = "금융 상품 API")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 국민은행 예적금 상품 API
    @GetMapping("/kb")
    @ApiOperation(value = "KB 금융상품 불러오기", notes = "예금, 적금, 전세자금대출 등 원하는 KB 금융상품을 불러옵니다.")
    public ResponseEntity<List<?>> kb(@RequestParam(defaultValue = "예금") String type) {
        if (type.equals("대출")) {
            List<LoanVO> kbLoans = productService.getKbLoans();
            return ResponseEntity.ok(kbLoans);
        } else {
            List<ProductVO> kbProducts = productService.getKbProducts(type);
            return ResponseEntity.ok(kbProducts);
        }
    }

    // 정책 리스트 API
    @GetMapping("/list")
    @ApiOperation(value = "금융상품 불러오기", notes = "원하는 조건에 맞는 금융상품 목록을 불러옵니다.")
    public ResponseEntity<Page<?>> getProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int amount,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "예금") String type) {

        PageRequest pageRequest = PageRequest.of(page, amount);
        if (type.equals("대출")) {
            List<LoanVO> loans = (keyword != null && !keyword.isEmpty())
                    ? productService.getLoansPageAndKeyword(pageRequest, keyword)
                    : productService.getLoansByPage(pageRequest);

            int totalLoansCount = (keyword != null && !keyword.isEmpty())
                    ? productService.getLoanTotalCntByKeyword(keyword)
                    : productService.getLoanTotalCnt();

            Page<LoanVO> loansPage = Page.of(pageRequest, totalLoansCount, loans);
            return ResponseEntity.ok(loansPage);
        } else {
            List<ProductVO> products = (keyword != null && !keyword.isEmpty())
                    ? productService.getProductsByPageAndKeyword(pageRequest, keyword, type)
                    : productService.getProductsByPage(pageRequest, type);

            int totalPoliciesCount = (keyword != null && !keyword.isEmpty())
                    ? productService.getTotalCntByKeyword(keyword, type)
                    : productService.getTotalCnt(type);

            Page<ProductVO> productsPage = Page.of(pageRequest, totalPoliciesCount, products);
            return ResponseEntity.ok(productsPage);
        }
    }

    // open API 패치
    @GetMapping("/fetch")
    @ApiOperation(value = "금융상품 DB에 저장하기", notes = "금융감독원 open API를 통해 금융 상품을 불러와 데이터베이스에 저장합니다.")
    public ResponseEntity<Void> fetchProducts(){
        productService.fetchDeposit();
        productService.fetchSaving();
        return ResponseEntity.ok().build();
    }

    // 상품의 옵션 정보 API
    @GetMapping("/option/{finPrdtCd}")
    @ApiOperation(value = "금융상품의 옵션 불러오기", notes = "특정 예적금 상품에 대한 옵션정보를 불러옵니다.")
    public ResponseEntity<List<OptionVO>> getOption(@PathVariable String finPrdtCd) {
        List<OptionVO> options = productService.getOption(finPrdtCd);
        return ResponseEntity.ok(options);
    }

    // 사용자 맞춤 추천 상품 API
    @GetMapping("/recommend")
    @ApiOperation(value = "금융상품 추천하기", notes = "사용자가 대출이 있다면 대출기간에 따른 금융상품을 추천하고 없다면 KB 상품을 추천합니다.")
    public ResponseEntity<List<ProductVO>> getRecommend(@RequestParam String userId) {
        List<ProductVO> recommends = productService.getRecommend(userId);
        return ResponseEntity.ok(recommends);
    }
}

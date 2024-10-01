package org.solo.product.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.solo.common.pagination.Page;
import org.solo.common.pagination.PageRequest;
import org.solo.policy.domain.PolicyVO;
import org.solo.product.domain.OptionVO;
import org.solo.product.domain.ProductVO;
import org.solo.product.service.ProductService;
import org.solo.product.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/kb")
    public ResponseEntity<List<ProductVO>> kb() {
        List<ProductVO> kbProducts = productService.getKbProducts();
        return ResponseEntity.ok(kbProducts);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ProductVO>> getProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int amount,
            @RequestParam(required = false) String keyword) {

        PageRequest pageRequest = PageRequest.of(page, amount);

        List<ProductVO> products = (keyword != null && !keyword.isEmpty())
                ? productService.getProductsByPageAndKeyword(pageRequest, keyword)
                : productService.getProductsByPage(pageRequest);

        int totalPoliciesCount = (keyword != null && !keyword.isEmpty())
                ? productService.getTotalCntByKeyword(keyword)
                : productService.getTotalCnt();

        Page<ProductVO> productsPage = Page.of(pageRequest, totalPoliciesCount, products);

        return ResponseEntity.ok(productsPage);
    }

    @GetMapping("/fetch")
    public ResponseEntity<Void> fetchProducts(){
        productService.fetchDeposit();
        productService.fetchSaving();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/option/{finPrdtCd}")
    public ResponseEntity<List<OptionVO>> getOption(@PathVariable String finPrdtCd) {
        List<OptionVO> options = productService.getOption(finPrdtCd);
        return ResponseEntity.ok(options);
    }
}

package org.solo.product.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ObjectMapper objectMapper;

    private final ProductService productService;
    private final RestTemplate restTemplate;

    @Autowired
    public ProductController(ProductService productService, RestTemplate restTemplate) {
        this.productService = productService;
        this.restTemplate = restTemplate;
    }

    @GetMapping({"","/"})
    public String product(){
        return "product";
    }

    @GetMapping("fetch")
    public ResponseEntity<Void> fetchProducts(){
        productService.fetchDeposit();
        productService.fetchSaving();
        return ResponseEntity.ok().build();
    }
}

package org.solo.product.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final ProductServiceImpl productServiceImpl;
    private final RestTemplate restTemplate;

    @Value("${productAPI.url}") String url;
    @Value("${productAPI.auth}") String auth;
    @Value("${productAPI.topFinGrpNo}") String topFinGrpNo;
    @Value("${productAPI.pageNo}") String pageNo;

    @Autowired
    public ProductController(ProductServiceImpl productServiceImpl, RestTemplate restTemplate) {
        this.productServiceImpl = productServiceImpl;
        this.restTemplate = restTemplate;
    }

    @GetMapping({"","/"})
    public String product(){
        return "product";
    }

    @GetMapping("list")
    public void fetchProducts(){
        System.out.println("api 호출 컨트롤러");
        String requestUrl = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("auth", auth)
                .queryParam("topFinGrpNo", topFinGrpNo)
                .queryParam("pageNo", pageNo)
                .toUriString();
        System.out.println("requestUrl: " + requestUrl);
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUrl, String.class);
            System.out.println("Response Headers: " + responseEntity.getHeaders());

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String jsonResponse = responseEntity.getBody();
                Map<String, Object> responseMap = objectMapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>() {});
                Map<String, Object> results = (Map<String, Object>) responseMap.get("result");

                List<Object> baseList = (List<Object>) results.get("baseList");
//                List<Object> optionList = (List<Object>) results.get("optionList");

                System.out.println("baseList size: " + baseList.size());
//                System.out.println("optionList size: " + optionList.size());

                for (Object o : baseList) {
                    System.out.println(o.getClass());
                }

            }
        } catch (Exception e) {
            e.printStackTrace(); // 예외를 출력하여 문제를 확인
        }

    }
}

package org.solo.policy.controller;
import org.springframework.beans.factory.annotation.Value;
import org.solo.policy.domain.PolicyVO;
import org.solo.policy.service.PolicyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/policy")
public class PolicyController {

    private final PolicyService policyService;
    private final RestTemplate restTemplate;

    @Value("${policyAPI.url}") String url;
    @Value("${policyAPI.openApiVlak}") String openApiVlak;
    @Value("${policyAPI.display}") String display;
    @Value("${policyAPI.pageIndex}") String pageIndex;

    public PolicyController(PolicyService policyService, RestTemplate restTemplate) {
        this.policyService = policyService;
        this.restTemplate = restTemplate;
    }

    @GetMapping({"","/"})
    public String policy(@RequestParam(defaultValue = "1") int page,
                         @RequestParam(required = false) String keyword,
                         Model model) {
        int pageSize = 10;
        int totalPoliciesCnt = keyword != null && !keyword.isEmpty()
                ? policyService.getTotalCntByKeyword(keyword) // 키워드에 따른 총 개수
                : policyService.getTotalCnt(); // 전체 개수

        List<PolicyVO> policies = keyword != null && !keyword.isEmpty()
                ? policyService.getPoliciesByPageAndKeyword(page, pageSize, keyword) // 키워드에 따른 정책 조회
                : policyService.getPoliciesByPage(page, pageSize); // 전체 정책 조회

        model.addAttribute("policies", policies);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) totalPoliciesCnt / pageSize));
        model.addAttribute("keyword", keyword); // 키워드 검색을 모델에 추가
        return "policy";
    }

    public void fetchPolicies() {
        // URL 빌드
        String requestUrl = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("openApiVlak", openApiVlak)
                .queryParam("display", display)
                .queryParam("pageIndex", pageIndex)
                .toUriString();

        // API 호출
        String xmlResponse = restTemplate.getForObject(requestUrl, String.class);
        List<PolicyVO> policies = new ArrayList<>();

        try {
            // XML 파싱
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(xmlResponse.getBytes(StandardCharsets.UTF_8)));

            // youthPolicy 노드 추출
            NodeList policyList = document.getElementsByTagName("youthPolicy");
            for (int i = 0; i < policyList.getLength(); i++) {
                Element policyElement = (Element) policyList.item(i);

                // 필요한 필드만 추출
                String bizId = policyElement.getElementsByTagName("bizId").item(0).getTextContent();
                String polyBizTy = policyElement.getElementsByTagName("polyBizTy").item(0).getTextContent();
                String polyBizSjnm = policyElement.getElementsByTagName("polyBizSjnm").item(0).getTextContent();
                String polyItcnCn = policyElement.getElementsByTagName("polyItcnCn").item(0).getTextContent();
                String sporCn = policyElement.getElementsByTagName("sporCn").item(0).getTextContent();
                String rqutUrla = policyElement.getElementsByTagName("rqutUrla").item(0).getTextContent();

                PolicyVO policyVO = new PolicyVO();
                policyVO.setBizId(bizId);
                policyVO.setPolyBizTy(polyBizTy);
                policyVO.setPolyBizSjnm(polyBizSjnm);
                policyVO.setPolyItcnCn(polyItcnCn);
                policyVO.setSporCn(sporCn);
                policyVO.setRqutUrla(rqutUrla);
                policies.add(policyVO);
            }
            policyService.fetchPolicies(policies);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
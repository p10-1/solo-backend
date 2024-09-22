package org.solo.policy.service;

import org.solo.policy.domain.PolicyVO;
import org.solo.policy.mapper.PolicyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PolicyServiceImpl implements PolicyService {
    private final PolicyMapper policyMapper;
    private final RestTemplate restTemplate;

    @Value("${policyAPI.url}") String url;
    @Value("${policyAPI.openApiVlak}") String openApiVlak;
    @Value("${policyAPI.display}") String display;
    @Value("${policyAPI.pageIndex}") String pageIndex;

    @Autowired
    public PolicyServiceImpl(PolicyMapper policyMapper, RestTemplate restTemplate) {
        this.policyMapper = policyMapper;
        this.restTemplate = restTemplate;
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
            // 데이터베이스에 저장
            savePolicies(policies);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void savePolicies(List<PolicyVO> policies) {
        for (PolicyVO policyVO : policies) {
            Map<String, Object> policyToken = new HashMap<>();
            policyToken.put("bizId", policyVO.getBizId());
            policyToken.put("sporCn", policyVO.getSporCn());
            if (policyMapper.findByToken(policyToken) == 0) {
                policyMapper.fetchPolicies(policyVO);
            }
        }
    }

    public int getTotalCnt() {
        return policyMapper.getTotalCnt();
    }

    public int getTotalCntByKeyword(String keyword) {
        return policyMapper.getTotalCntByKeyword(keyword);
    }

    public List<PolicyVO> getPoliciesByPage(int page, int pageSize) {
        return policyMapper.getPoliciesByPage((page-1) * pageSize, pageSize);
    }

    public List<PolicyVO> getPoliciesByPageAndKeyword(int page, int pageSize, String keyword) {
        int offset = (page - 1) * pageSize;
        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("limit", pageSize);
        params.put("keyword", keyword);

        return policyMapper.getPoliciesByPageAndKeyword(params);
    }
}

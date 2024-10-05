package org.solo.policy.service;

import org.solo.common.pagination.PageRequest;
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

    @Override
    public void fetchPolicies() {
        String requestUrl = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("openApiVlak", openApiVlak)
                .queryParam("display", display)
                .queryParam("pageIndex", pageIndex)
                .toUriString();

        String xmlResponse = restTemplate.getForObject(requestUrl, String.class);
        List<PolicyVO> policies = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(xmlResponse.getBytes(StandardCharsets.UTF_8)));

            NodeList policyList = document.getElementsByTagName("youthPolicy");
            for (int i = 0; i < policyList.getLength(); i++) {
                Element policyElement = (Element) policyList.item(i);

                String bizId = policyElement.getElementsByTagName("bizId").item(0).getTextContent();
                String polyBizTy = policyElement.getElementsByTagName("polyBizTy").item(0).getTextContent();
                String polyBizSjnm = policyElement.getElementsByTagName("polyBizSjnm").item(0).getTextContent();
                String polyItcnCn = policyElement.getElementsByTagName("polyItcnCn").item(0).getTextContent();
                String sporCn = policyElement.getElementsByTagName("sporCn").item(0).getTextContent();
                String rqutUrla = policyElement.getElementsByTagName("rqutUrla").item(0).getTextContent();
                String polyRlmCd = policyElement.getElementsByTagName("polyRlmCd").item(0).getTextContent();
                switch (polyRlmCd) {
                    case "023010":
                        polyRlmCd = "일자리";
                        break;
                    case "023020":
                        polyRlmCd = "주거";
                        break;
                    case "023030":
                        polyRlmCd = "교육";
                        break;
                    case "023040":
                        polyRlmCd = "복지문화";
                        break;
                    case "023050":
                        polyRlmCd = "참여권리";
                        break;
                }
                if (rqutUrla.startsWith("https")) {
                    PolicyVO policyVO = new PolicyVO(bizId, polyBizTy, polyBizSjnm, polyItcnCn, sporCn, rqutUrla,polyRlmCd);
                    policies.add(policyVO);
                } else {
                    continue;
                }
            }
            savePolicies(policies);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void savePolicies(List<PolicyVO> policies) {
        for (PolicyVO policyVO : policies) {
            Map<String, Object> policyToken = new HashMap<>();
            policyToken.put("bizId", policyVO.getBizId());
            policyToken.put("sporCn", policyVO.getSporCn());
            policyToken.put("polyBizSjnm", policyVO.getPolyBizSjnm());
            if (policyMapper.findByToken(policyToken) == 0) {
                policyMapper.fetchPolicies(policyVO);
            }
        }
    }

    @Override
    public int getTotalCnt(String category) {
        return policyMapper.getTotalCnt(category);
    }

    @Override
    public int getTotalCntByKeyword(String keyword, String category) {
        return policyMapper.getTotalCntByKeyword(keyword, category);
    }

    @Override
    public List<PolicyVO> getPoliciesByPage(PageRequest pageRequest, String category) {
        return policyMapper.getPoliciesByPage(pageRequest.getOffset(), pageRequest.getAmount(), category);
    }

    @Override
    public List<PolicyVO> getPoliciesByPageAndKeyword(PageRequest pageRequest, String keyword, String category) {
        return policyMapper.getPoliciesByPageAndKeyword(pageRequest.getOffset(), pageRequest.getAmount(), keyword, category);
    }

    @Override
    public List<PolicyVO> recommendPolicies() {
        return policyMapper.recommendPolicies();
    }
}

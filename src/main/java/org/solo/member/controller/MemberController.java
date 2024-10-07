package org.solo.member.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.solo.member.domain.MemberVO;
import org.solo.member.service.MemberService;
import org.solo.member.service.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Autowired
    private ObjectMapper objectMapper;
    private MemberService memberService;

    @Value("${kakaoAPI.tokenUrl}") String KAKAO_TOKEN_URL;
    @Value("${kakaoAPI.clientId}") String CLIENT_ID;
    @Value("${kakaoAPI.redirectUrl}") String REDIRECT_URI;
    @Value("${kakaoAPI.logoutUrl}") String LOGOUT_REDIRECT_URI;

    @Autowired
    public MemberController(MemberServiceImpl memberServiceImpl) {
        this.memberService = memberServiceImpl;
    }

    // 카카오 로그인
    @GetMapping("/login")
    public ResponseEntity<String> login(HttpSession session) {
        session.invalidate();
        String loginUrl = "https://kauth.kakao.com/oauth/authorize?client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI +
                "&response_type=code";
        System.out.println("로그인 들어왔음.");
        return ResponseEntity.ok(loginUrl);
    }

    // 카카오 로그인
    @RequestMapping(value= "/login/callback", produces = "application/json", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> loginCallback(@RequestParam("code") String code, HttpSession session) throws IOException {
        try {
            String accessToken = getKakaoAccessToken(code);
            session.setAttribute("accessToken", accessToken);

            Map<String, Object> userInfoMap = getKakaoUserInfo(accessToken, session);
            if (userInfoMap == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to retrieve user info.");
            }

            String userId = String.valueOf(userInfoMap.get("id"));
            MemberVO memberVO = memberService.findByKakaoId(userId);

            if (memberVO == null) {
                session.setAttribute("newUserInfo", userInfoMap);
                return ResponseEntity.status(HttpStatus.CREATED).body("{\"status\": \"newUser\"}");
            } else {
                session.setAttribute("userInfo", memberVO);
                return ResponseEntity.ok(memberVO);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }

    }

    // 카카오 사용자 토큰을 받는 함수
    private String getKakaoAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", CLIENT_ID);
        body.add("redirect_uri", REDIRECT_URI);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(KAKAO_TOKEN_URL, HttpMethod.POST, requestEntity, String.class);
            Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
            return (String) responseMap.get("access_token");
        } catch (HttpClientErrorException e) {
            System.err.println("HTTP Error: " + e.getStatusCode());
            System.err.println("Response Body: " + e.getResponseBodyAsString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 카카오 사용자 정보를 받는 함수
    private Map<String, Object> getKakaoUserInfo(String accessToken, HttpSession session) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.GET, requestEntity, String.class);
            Map<String, Object> userInfoMap = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});

            Long userIdLong = (Long) userInfoMap.get("id");
            String userId = String.valueOf(userIdLong);

            Map<String, Object> kakaoAccount = (Map<String, Object>) userInfoMap.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            String email = (String) kakaoAccount.get("email");
            String nickName = (String) profile.get("nickname");

            // 세션에 사용자 정보를 저장
            session.setAttribute("userId", userId);
            session.setAttribute("nickName", nickName);
            session.setAttribute("email", email);
            return userInfoMap;

        } catch (HttpClientErrorException e) {
            System.err.println("HTTP Error: " + e.getStatusCode());
            System.err.println("Response Body: " + e.getResponseBodyAsString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        String logoutUrl = "https://kauth.kakao.com/oauth/logout?client_id=" + CLIENT_ID + "&logout_redirect_uri=" + LOGOUT_REDIRECT_URI;
        return ResponseEntity.ok(logoutUrl);
    }

    // 처음 방문한 사용자의 추가 정보를 받는 함수
    @PostMapping("/firstUser")
    public ResponseEntity<MemberVO> firstUser(@RequestBody Map<String, String> nameAndbirth, HttpSession session) {
        String userName = nameAndbirth.get("userName");
        String birthdate = nameAndbirth.get("birthdate");
        String userId = session.getAttribute("userId").toString();
        String nickName = session.getAttribute("nickName").toString();
        String email = session.getAttribute("email").toString();

        MemberVO newUser = memberService.insertNewUserInfo(userId, nickName, userName, email, birthdate);
        session.setAttribute("userInfo", newUser);
        return ResponseEntity.ok(newUser);
    }

    // 서비스에서 사용할 닉네임 중복체크
    @GetMapping("/checkUser")
    public ResponseEntity<Boolean> checkUser(@RequestParam("nickName") String nickName) {
        boolean isDuplicate = memberService.checkUser(nickName);
        return ResponseEntity.ok(!isDuplicate);
    }

}
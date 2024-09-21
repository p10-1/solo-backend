package org.solo.member.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.solo.member.domain.MemberVO;
import org.solo.member.service.MemberService;
import org.solo.member.service.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public MemberController(MemberServiceImpl memberServiceImpl) {
        this.memberService = memberServiceImpl;
    }

    private final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private final String CLIENT_ID = "a06454d494477b90c006ff1ad7f3de15"; // 카카오 REST API 키
    private final String REDIRECT_URI = "http://localhost:5173/callback"; // 리다이렉트 URI
    private final String LOGOUT_REDIRECT_URI = "http://localhost:5173/";

    @GetMapping("/login")
    public ResponseEntity<String> login(HttpSession session) {
        session.invalidate();
        String loginUrl = "https://kauth.kakao.com/oauth/authorize?client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI +
                "&response_type=code";
        System.out.println("로그인 들어왔음.");
        return ResponseEntity.ok(loginUrl);
    }



    @RequestMapping(value= "/login/callback", produces = "application/json", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> loginCallback(@RequestParam("code") String code, HttpSession session) throws IOException {
        String accessToken = getKakaoAccessToken(code);
        session.setAttribute("accessToken", accessToken);

        Map<String, Object> userInfoMap = getKakaoUserInfo(accessToken, session);
        if (userInfoMap == null) {
//            return "redirect:/error"; // 에러 페이지로 리다이렉트
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to retrieve user info.");
        }

        // Kakao ID를 Long으로 가져오기
        Long kakaoIdLong = (Long) userInfoMap.get("id"); // Long으로 가져오기
        String kakaoId = String.valueOf(kakaoIdLong); // String으로 변환

        System.out.println("loginCallback kakaoId: " + kakaoId);

        // 디비에 해당 카카오 아이디를 가진 사용자가 이미 있는지 검사
        MemberVO memberVO = memberService.findByKakaoId(kakaoId);

        if (memberVO == null) {
            session.setAttribute("newUserInfo", userInfoMap);
            System.out.println("login callback Current session ID: " + session.getId());
            System.out.println("Current session attributes: ");
            for (String attributeName : Collections.list(session.getAttributeNames())) {
                System.out.println(attributeName + ": " + session.getAttribute(attributeName));
            }
            System.out.println("새로운 사용자 call back");
//            return "redirect:http://localhost:5173/first-user"; // 새로운 사용자일 경우 first-user 페이지로 리다이렉트
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"status\": \"newUser\"}"); // JSON 형태로 변경
        } else {
            session.setAttribute("userInfo", memberVO);
            System.out.println("기존 사용자의 userInfo: " + memberVO);
//            return "redirect:http://localhost:5173/"; // 기존 사용자일 경우 홈으로 리다이렉트
            System.out.println("기존 사용자 call back");
            return ResponseEntity.ok(memberVO); // 기존 사용자 정보 반환
        }

    }
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

            // JSON 응답에서 액세스 토큰 추출
            Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
            System.out.println((String) responseMap.get("access_token"));
            return (String) responseMap.get("access_token");
        } catch (HttpClientErrorException e) {
            System.err.println("HTTP Error: " + e.getStatusCode());
            System.err.println("Response Body: " + e.getResponseBodyAsString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace(); // 다른 예외 처리
        }

        return null; // 에러 처리
    }

    private Map<String, Object> getKakaoUserInfo(String accessToken, HttpSession session) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.GET, requestEntity, String.class);

            // JSON 응답에서 사용자 정보 추출
            Map<String, Object> userInfoMap = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});

            // kakaoId를 Long에서 String으로 변환
            Long kakaoIdLong = (Long) userInfoMap.get("id"); // Long 타입으로 가져오기
            String kakaoId = String.valueOf(kakaoIdLong); // String으로 변환

            Map<String, Object> kakaoAccount = (Map<String, Object>) userInfoMap.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            String email = (String) kakaoAccount.get("email");

            String nickName = (String) profile.get("nickname"); // "nickname" 필드로 수정
            String profileImage = (String) profile.get("profile_image_url");

            // 세션에 사용자 정보를 저장
            session.setAttribute("kakaoId", kakaoId); // String으로 저장
            session.setAttribute("nickName", nickName);
            session.setAttribute("profileImage", profileImage);
            session.setAttribute("email", email);
            System.out.println("User Info Map: " + userInfoMap);
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

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        System.out.println("logout 호출");
        // 세션 무효화
        session.invalidate();
        // 카카오 로그아웃 URL
        String logoutUrl = "https://kauth.kakao.com/oauth/logout?client_id=" + CLIENT_ID + "&logout_redirect_uri=" + LOGOUT_REDIRECT_URI;
        return ResponseEntity.ok(logoutUrl);
    }

//    @GetMapping("/InsertFirstUserInfo")
//    public String showFirstUserForm() {
//        return "insertUserInfoForm"; // 사용자 정보를 입력할 JSP 페이지로 이동
//    }

    @PostMapping("/firstUser")
    public ResponseEntity<?> firstUser(@RequestBody Map<String, String> nameAndbirth, HttpSession session) {
        String name = nameAndbirth.get("name");
        String birthDate = nameAndbirth.get("birthDate");
        System.out.println("Received name: " + name + ", birthDate: " + birthDate);
//        Map<String, Object> newUserInfo = (Map<String, Object>) session.getAttribute("newUserInfo");


//        System.out.println("newUserInfo: " + newUserInfo);
//        System.out.println("firstUser Current session ID: " + session.getId());


//        Long kakaoIdLong = (Long) newUserInfo.get("id"); // Long 타입으로 가져오기
//        String kakaoId = String.valueOf(kakaoIdLong); // String으로 변환

//        Map<String, Object> kakaoAccount = (Map<String, Object>) newUserInfo.get("kakao_account");
//        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String kakaoId = session.getAttribute("kakaoId").toString();
        String nickName = session.getAttribute("nickName").toString();
        String profileImage = session.getAttribute("profileImage").toString();
        String email = session.getAttribute("email").toString();
//        String email = (String) kakaoAccount.get("email");
//        String nickName = (String) profile.get("nickname");
//        String profileImage = (String) profile.get("profile_image_url");
        System.out.println("kakaoId: " + kakaoId);
        MemberVO newUser = memberService.insertNewUserInfo(kakaoId, nickName, profileImage, name, email, birthDate);
        session.setAttribute("userInfo", newUser);
        System.out.println("새로 추가된 userInfo: " + newUser);
        return ResponseEntity.ok(newUser); // 새 사용자 정보 반환
    }

}
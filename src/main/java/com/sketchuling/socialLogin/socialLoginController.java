package com.sketchuling.socialLogin;

import com.sketchuling.socialLogin.dto.GoogleInfoResponse;
import com.sketchuling.socialLogin.dto.GoogleResponse;
import com.sketchuling.user.bo.UserBO;
import com.sketchuling.user.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
public class socialLoginController {

    @Autowired
    private Environment env;

    private final UserBO userBO;

    @GetMapping("/user/sign-in/google")
    public String signInGoogle(@RequestParam("code") String authCode,
                               HttpSession session) {
        RestTemplate restTemplate = new RestTemplate();

        // ✅ 환경 변수에서 Google Client 정보 가져오기
        String clientId = env.getProperty("google.client.id");
        String clientSecret = env.getProperty("google.client.pw");
        String redirectUri = env.getProperty("google.redirectUri");  // Google API 콘솔에 등록된 리디렉션 URI와 일치해야 함

        // ✅ Google 토큰 요청 객체 생성
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("code", authCode);
        requestParams.put("client_id", clientId);
        requestParams.put("client_secret", clientSecret);
        requestParams.put("redirect_uri", redirectUri);
        requestParams.put("grant_type", "authorization_code");

        // ✅ Google OAuth 토큰 요청
        try {
            ResponseEntity<GoogleResponse> tokenResponse = restTemplate.postForEntity(
                    "https://oauth2.googleapis.com/token", requestParams, GoogleResponse.class);

            if (!tokenResponse.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("구글 로그인 실패: 토큰 요청 오류");
            }

            String idToken = tokenResponse.getBody().getId_token();

            // ✅ Google 사용자 정보 요청 (GET 요청)
            String tokenInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
            ResponseEntity<GoogleInfoResponse> userInfoResponse = restTemplate.getForEntity(tokenInfoUrl, GoogleInfoResponse.class);

            if (!userInfoResponse.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("구글 로그인 실패: 사용자 정보 요청 오류");
            }

            String name = userInfoResponse.getBody().getName();
            String email = userInfoResponse.getBody().getEmail();
            String loginAPI = "Google";

            // UserDB에서 사용자 정보 확인
            UserEntity user = userBO.getUserByNameAndEmailAndLoginAPI(name, email, loginAPI);

            // 사용자 정보가 없다면 DB에 추가
            if (user == null) {
                userBO.addGoogleUser(name, email, loginAPI);
                // 신규 사용자 추가 후, 로그인 정보 설정
                user = userBO.getUserByNameAndEmailAndLoginAPI(name, email, loginAPI);
            }

            // 세션에 사용자 정보 저장
            session.setAttribute("userLoginId", user.getLoginId());
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("profileImagePath", user.getProfileImagePath());
            session.setAttribute("subscriptionDue", user.getSubscriptionDue());

            // 로그인 후 메인 페이지로 리디렉션
            return "redirect:/main";  // 로그인 후 이동할 페이지 URL을 설정
        } catch (HttpClientErrorException e) {
            // 400 오류 처리: Google OAuth에서 잘못된 요청 처리
            return "error/400";  // 400 오류 페이지
        } catch (Exception e) {
            // 기타 예외 처리
            return "error/500";  // 500 서버 오류 페이지
        }
    }
}

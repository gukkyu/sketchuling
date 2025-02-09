package com.sketchuling.user;

import com.sketchuling.common.NaverAPI;
import com.sketchuling.user.domain.User;
import com.sketchuling.user.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private NaverAPI naverAPI;
    // 테스트용 주석 코드 추가
    @GetMapping("/sign-in")
    public String signIn(Model model) {

        model.addAttribute("naverClientId", naverAPI.getNaverClientId());
        model.addAttribute("kakaoClientId", naverAPI.getKakaoClientId());
        model.addAttribute("naverRedirectUri", naverAPI.getNaverRedirectUri());
        model.addAttribute("kakaoRedirectUri", naverAPI.getKakaoRedirectUri());
        return "user/sign-in";
    }

    @GetMapping("/sign-up")
    public String signUp() {
        return "user/sign-up";
    }

    @GetMapping("/recover")
    public String recover() {
        return "user/recover";
    }

}

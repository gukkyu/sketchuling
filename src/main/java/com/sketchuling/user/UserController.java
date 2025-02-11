package com.sketchuling.user;

import com.sketchuling.socialLogin.dto.GoogleInfoResponse;
import com.sketchuling.socialLogin.dto.GoogleResponse;
import com.sketchuling.user.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.sketchuling.user.bo.UserBO;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserBO userBO;

    @Autowired
    private Environment env;

    @GetMapping("/sign-in")
    public String signIn(Model model) {
        String googleClientId = env.getProperty("google.client.id");
        String reqUrl = "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + googleClientId
                + "&redirect_uri=http://localhost/user/sign-in/google&response_type=code&scope=email%20profile%20openid&access_type=offline";
        model.addAttribute("requestUrl", reqUrl);
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

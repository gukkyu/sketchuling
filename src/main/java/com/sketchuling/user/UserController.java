package com.sketchuling.user;

import com.sketchuling.user.domain.User;
import com.sketchuling.user.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    // 테스트용 주석 코드 추가
    @GetMapping("/sign-in")
    public String signIn() {
        return "user/sign-in";
    }

}

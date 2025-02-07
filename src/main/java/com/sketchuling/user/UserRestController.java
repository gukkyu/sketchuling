package com.sketchuling.user;

import com.sketchuling.user.bo.UserBO;
import com.sketchuling.user.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserBO userBO;

    @GetMapping("/is-duplicate-id")
    public Map<String, Object> isDuplicateId(@RequestParam("loginId") String loginId) {
        Map<String, Object> result = new HashMap<>();
        UserEntity user = userBO.getUserEntityByLoginId(loginId);
        boolean isDuplicate = false;
        if(user != null){
            isDuplicate = true;
        }

        result.put("code", 200);
        result.put("is_duplicate_id", isDuplicate);
        return result;
    }

    @PostMapping("/sign-up")
    public Map<String, Object> signUp(
            @RequestParam("id") String loginId,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("email") String email
    ){
        boolean isSuccess = userBO.addUser(loginId, password, name, email);

        Map<String, Object> result = new HashMap<>();
        if(isSuccess){
            result.put("code", 200);
            result.put("result", "성공");
        } else{
            result.put("code", 500);
            result.put("error_message", "회원가입에 실패했습니다.");
        }
        return result;
    }

    @PostMapping("/sign-in")
    public Map<String, Object> signIn(
            @RequestParam("id") String loginId,
            @RequestParam("password") String password,
            HttpSession session
    ){
        UserEntity user = userBO.getUserByLoginIdAndPassword(loginId, password);
        Map<String, Object> result = new HashMap<>();

        if(user != null){
            result.put("code", 200);
            result.put("result", "성공");
            session.setAttribute("userLoginId", user.getLoginId());
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("profileImagePath", user.getProfileImagePath());
            session.setAttribute("subscriptionDue", user.getSubscriptionDue());
        } else{
            result.put("error_message", "아이디와 비밀번호를 확인해주세요.");
        }
        return result;
    }
}

package com.sketchuling.recover;

import com.sketchuling.common.EncryptUtils;
import com.sketchuling.recover.bo.RecoverBO;
import com.sketchuling.user.bo.UserBO;
import com.sketchuling.user.entity.RecoverEntity;
import com.sketchuling.user.entity.UserEntity;
import com.sketchuling.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/user/recover")
public class RecoverRestController {

    private final RecoverBO recoverBO; // Spring에서 자동 주입
    private final UserBO userBO;
    private final UserRepository userRepository;

    @PostMapping("/id")
    public Map<String, Object> recoverId(@RequestParam("name") String name,
                                         @RequestParam("email") String email) {
        UserEntity user = userBO.getUserByNameAndEmail(name, email);

        Map<String, Object> result = new HashMap<>();
        if (user == null) {
            result.put("code", 404);
            result.put("error_message", "이름과 이메일을 확인해주세요");
        } else if (user.getLoginAPI() != null) {
            result.put("code", 405);
            result.put("error_message", "해당 계정은 소셜 로그인을 통해 로그인 해주세요.");
        } else {
            String id = user.getLoginId();
            recoverBO.idRecover(name, email, id);
            result.put("code", 200);
            result.put("error_message", "메일을 통해 아이디를 확인해주세요");
        }
        return result;
    }

    @PostMapping("/password")
    public Map<String, Object> recoverPassword(@RequestParam("loginId") String loginId,
                                         @RequestParam("email") String email) {
        UserEntity user = userBO.getUserByIdAndEmail(loginId, email);

        Map<String, Object> result = new HashMap<>();
        if (user == null) {
            result.put("code", 404);
            result.put("error_message", "이름과 이메일을 확인해주세요");
        } else if (user.getLoginAPI() != null) {
            result.put("code", 405);
            result.put("error_message", "해당 계정은 소셜 로그인을 통해 로그인 해주세요.");
        } else {
            int userId = user.getId();
            String code = recoverBO.addRecover(userId, loginId, email);
            recoverBO.passwordRecover(loginId, email, code);
            result.put("code", 200);
            result.put("success", "성공");
        }
        return result;
    }

    @PostMapping("/code")
    public Map<String, Object> codeCheck(@RequestParam("loginId") String loginId,
                                         @RequestParam("email") String email,
                                         @RequestParam("code") String code) {
        RecoverEntity recover = recoverBO.getUserByLoginIdAndEmailAndCode(loginId, email, code);

        LocalDateTime now = LocalDateTime.now();


        Map<String, Object> result = new HashMap<>();
        if (recover == null) {
            result.put("code", 404);
            result.put("error_message", "인증번호를 확인해주세요");
        } else {
            LocalDateTime createdAt = recover.getCreatedAt();
            Duration duration = Duration.between(createdAt, now);
            if(duration.toMinutes() >= 5) {
                result.put("code", 405);
                result.put("error_message", "인증코드가 만료되었습니다. 다시 발급받아주세요.");
            } else {
                result.put("code", 200);
                result.put("success", "성공");
            }
        }
        return result;
    }

    @PostMapping("/password/success")
    public Map<String, Object> resetPassword(@RequestParam("loginId") String loginId,
                                               @RequestParam("password") String password) {
        UserEntity user = userBO.getUserEntityByLoginId(loginId);
        String hashedPassword = EncryptUtils.encrypt(password);
        Map<String, Object> result = new HashMap<>();

        if (user == null) {
            result.put("code", 404);
            result.put("error_message", "아이디 오류");
        } else {
            user = user.toBuilder()
                    .password(hashedPassword)
                    .updatedAt(LocalDateTime.now())
                    .build();
            userRepository.save(user);

            result.put("code", 200);
            result.put("message", "비밀번호가 성공적으로 업데이트되었습니다.");
        }
        return result;
    }
}

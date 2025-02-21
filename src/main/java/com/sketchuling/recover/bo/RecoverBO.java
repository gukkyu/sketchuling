package com.sketchuling.recover.bo;

import com.sketchuling.recover.repository.RecoverRepository;
import com.sketchuling.user.entity.RecoverEntity;
import com.sketchuling.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RecoverBO {

    @Autowired
    private RecoverRepository recoverRepository;

    private final JavaMailSender mailSender;

    public void idRecover(String name, String email, String id) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Sketchuling 아이디 복구");
        message.setText(name + "님의 Sketchuling 아이디는 " + id + " 입니다.");
        message.setFrom("gukkyu.comet@gmail.com");

        mailSender.send(message); // 메일 전송
    }

    public String addRecover(int userId, String loginId, String email) {
        Random rand = new Random();
        StringBuilder code = new StringBuilder();
        int randomNumber = rand.nextInt(1000000);
        code.append(randomNumber);
        recoverRepository.save(
                RecoverEntity.builder()
                        .userId(userId)
                        .loginId(loginId)
                        .email(email)
                        .code(code.toString())
                        .build()
        );
        return code.toString();
    }

    public RecoverEntity getUserByLoginIdAndEmailAndCode(String loginId, String email, String code) {
        return recoverRepository.findByLoginIdAndEmailAndCode(loginId, email, code);
    }

    public void passwordRecover(String loginId, String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Sketchuling 비밀번호 재설정");
        message.setText(loginId + "님의 Sketchuling 비밀번호 재설정을 위한 인증코드 6자리 숫자는 " + code + " 입니다.");
        message.setFrom("gukkyu.comet@gmail.com");

        mailSender.send(message); // 메일 전송
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteCode(){
        LocalDate date = LocalDate.now().minusDays(1);
            recoverRepository.deleteByCreatedAt(date);
    }
}

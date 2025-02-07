package com.sketchuling.user.bo;

import com.sketchuling.common.EncryptUtils;
import com.sketchuling.user.entity.UserEntity;
import com.sketchuling.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
@AllArgsConstructor
public class UserBO {
    private final UserRepository userRepository;

    public UserEntity getUserEntityByLoginId(String loginId){
        return userRepository.findByLoginId(loginId).orElse(null);
    }

    public boolean addUser(String loginId, String password, String name, String email){
        String hashedPassword = EncryptUtils.encrypt(password);
        UserEntity user = userRepository.save(
            UserEntity.builder()
                    .loginId(loginId)
                    .password(hashedPassword)
                    .name(name)
                    .email(email)
                    .build()
        );
        return user == null? false : true;
    }
}

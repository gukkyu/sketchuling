package com.sketchuling.user.bo;

import com.sketchuling.common.EncryptUtils;
import com.sketchuling.user.entity.UserEntity;
import com.sketchuling.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public void addGoogleUser(String name, String email, String loginAPI){
        userRepository.save(
                UserEntity.builder()
                        .loginId(email)
                        .name(name)
                        .email(email)
                        .loginAPI(loginAPI)
                        .build()
        );
    }

    public UserEntity getUserByLoginIdAndPassword(String loginId, String password){
        String hashedPassword = EncryptUtils.encrypt(password);
        return userRepository.findByLoginIdAndPassword(loginId, hashedPassword);
    }

    public UserEntity getUserByNameAndEmail(String name, String email){
        return userRepository.findByNameAndEmail(name, email);
    }

    public UserEntity getUserByNameAndEmailAndLoginAPI(String name, String email, String loginAPI){
        return userRepository.findByNameAndEmailAndLoginAPI(name, email, loginAPI);
    }

    public UserEntity getUserByIdAndEmail(String loginId, String email){
        return userRepository.findByLoginIdAndEmail(loginId, email);
    }

}

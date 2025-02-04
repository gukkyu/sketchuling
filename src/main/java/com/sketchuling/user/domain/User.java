package com.sketchuling.user.domain;

import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class User {
    private int id;
    private String loginId;
    private String password;
    private String name;
    private String email;
    private String profileImagePath;
    private Date subscriptionDue;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

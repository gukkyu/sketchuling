package com.sketchuling.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Table(name="user")
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "loginId")
    private String loginId;

    private String password;
    private String name;
    private String email;

    @Column(name = "profileImagePath")
    private String profileImagePath;

    @Column(name = "loginAPI")
    private String loginAPI;

    @Column(name = "subscriptionDue")
    private Date subscriptionDue;

    @CreationTimestamp
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name= "updatedAt")
    private LocalDateTime updatedAt;
}

package com.sketchuling.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Getter
public class NaverAPI {

    @Value("${naver.client_id}")
    public String naverClientId;

    @Value("${naver.client_secret}")
    private String naverClientSecret;

    @Value("${naver.redirect_uri}")
    private String naverRedirectUri;

    @Value("${kakao.client_id}")
    public String kakaoClientId;

    @Value("${kakao.redirect_uri}")
    private String kakaoRedirectUri;


}

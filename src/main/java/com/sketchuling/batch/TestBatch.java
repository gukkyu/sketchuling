package com.sketchuling.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestBatch {

    // 1분에 한번씩 수행
//    @Scheduled(cron = "*/5 * * * * * ")
//    public void test(){
//        log.info("######### batch test");
//    }
}

package com.sketchuling;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
class SketchulingApplicationTests {

	@Test
	void contextLoads() {
		log.warn("##### 테스트");
		int a = 10;
		int b = 20;

		assertEquals(30, a + b);
	}

//	@Test
//	void contextLoads() {
//	}

}

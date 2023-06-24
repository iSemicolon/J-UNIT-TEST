package org.kol.junit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class JunitTestApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void demoTestMethod(){
		assertTrue(true);
	}

}

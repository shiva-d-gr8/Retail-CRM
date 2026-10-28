package com.shivam.crm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.datasource.url=jdbc:h2:mem:crm-test;DB_CLOSE_DELAY=-1",
		"crm.jwt.secret=test-only-secret-key-with-at-least-32-characters",
		"crm.auth.default-password=test-only-password"
})
class CrmApplicationTests {

	@Test
	void contextLoads() {
	}

}

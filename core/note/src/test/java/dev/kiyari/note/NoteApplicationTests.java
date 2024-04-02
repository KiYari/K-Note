package dev.kiyari.note;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

@SpringBootTest
@TestPropertySource(properties = {"spring.datasource.url=jdbc:h2:mem:testdb",
		"spring.datasource.driverClassName=org.h2.Driver"})
class NoteApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void getTimeNow() {
		System.out.println(LocalDateTime.now());
	}

}

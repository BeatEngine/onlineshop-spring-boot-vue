package org.beatengine.onlineshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

@Import(TestDatabaseBaseTest.class)
@ExtendWith(OutputCaptureExtension.class) // For checking spring start logs
@ContextConfiguration(classes = {Onlineshop.class, TestDatabaseBaseTest.class})
@SpringBootTest
class OnlineshopApplicationTests extends TestDatabaseBaseTest{

	@Test
	void contextLoads() {
	}

}

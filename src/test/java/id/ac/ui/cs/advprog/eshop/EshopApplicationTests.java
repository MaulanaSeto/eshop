package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

@SpringBootTest
class EshopApplicationTests {
    @Test
    void contextLoads() {
    }

    @Test
    void testMainMethod() {
        try (MockedStatic<SpringApplication> mockedSpringApplication = Mockito.mockStatic(SpringApplication.class)) {
            mockedSpringApplication.when(() -> SpringApplication.run(
                EshopApplication.class, new String[]{}
            )).thenReturn(null);

            EshopApplication.main(new String[]{});

            mockedSpringApplication.verify(() -> SpringApplication.run(EshopApplication.class, new String[]{}));
        }
    }
}
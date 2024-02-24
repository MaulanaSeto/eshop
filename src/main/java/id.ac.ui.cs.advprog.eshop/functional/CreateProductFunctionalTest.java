package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {
    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d/product/create", testBaseUrl, serverPort);
    }

    @Test
    void oogaBoogaIAmUserCreatingNewProductTest(ChromeDriver driver) {
        driver.get(baseUrl);

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.clear();
        String name = "Malenia, Blade of Miquella";
        nameInput.sendKeys(name);

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        String quantity = "69";
        quantityInput.sendKeys(quantity);

        WebElement submitButton = driver.findElement(By.tagName("button"));
        submitButton.click();

        List<WebElement> cells = driver.findElements(By.tagName("td"));
        System.out.println("First cell value: " + cells.get(0).getText());
        System.out.println("Second cell value: " + cells.get(1).getText());
        assertEquals(name, cells.get(0).getText());
        assertEquals(quantity, cells.get(1).getText());
    }

}
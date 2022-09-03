package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CallbackTest {
    private WebDriver driver;
    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "driver/mac/chromedriver");
    }
    @BeforeEach
    void SetUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }
    @AfterEach
    public void Close(){
        driver.quit();
        driver = null;
    }

    @Test
    public void Test(){
        driver.get("http://localhost:9999");

        List<WebElement> inputs = driver.findElements(By.className("input__control"));

        inputs.get(0).sendKeys("Михаил");
        inputs.get(1).sendKeys("+79291104279");
        driver.findElement(By.className("checkbox__box")).click(); //agreement
        driver.findElement(By.tagName("button")).click(); //submit
        String actual = driver.findElement(By.className("paragraph")).getText(); //success text
        //String actuale = driver.findElement(By.()).getText(); //success text
        String expected = "  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals(expected, actual);
    }

}

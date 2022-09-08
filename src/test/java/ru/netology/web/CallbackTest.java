package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CallbackTest {
    private WebDriver driver;
    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }
    @AfterEach
    public void close(){
        driver.quit();
        driver = null;
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/positiveTests.csv")
    void shouldTestPositiveValues(String testName, String name) {
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys(name);
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79291104279");
        driver.findElement(By.className("checkbox__box")).click(); //agreement
        driver.findElement(By.tagName("button")).click(); //submit

        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText(); //success text
        String expected = "  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals(expected, actual);
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/negativeTestsName.csv")
    void shouldTestNegativeValuesForName(String testName, String name) {
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys(name);
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79291104279");
        driver.findElement(By.className("checkbox__box")).click(); //agreement
        driver.findElement(By.tagName("button")).click(); //submit
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText(); //warning text
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actual);
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/negativeTestsPhone.csv")
    void shouldTestNegativeValuesForPhone(String testName, String phoneNumber) {
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Мария Бычкова");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys(phoneNumber);
        driver.findElement(By.className("checkbox__box")).click(); //agreement
        driver.findElement(By.tagName("button")).click(); //submit

        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText(); //warning text
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, actual);
    }
    @Test
    public void shouldTestEmptyName(){
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79291104279");
        driver.findElement(By.className("checkbox__box")).click(); //agreement
        driver.findElement(By.tagName("button")).click(); //submit

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();; //warning text
        String expected = "Поле обязательно для заполнения";

        assertEquals(expected, actual);
    }
    @Test
    public void shouldTestEmptyPhone(){
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Мария Бычкова");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click(); //agreement
        driver.findElement(By.tagName("button")).click(); //submit

        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();; //warning text
        String expected = "Поле обязательно для заполнения";

        assertEquals(expected, actual);
    }
    @Test
    public void shouldTestEmptyCheckbox(){
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Михаил Иванов");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79291104279");
        driver.findElement(By.tagName("button")).click();

        assertTrue(driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).isDisplayed());
    }
}
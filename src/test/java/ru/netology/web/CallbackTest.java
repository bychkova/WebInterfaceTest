package ru.netology.web;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CallbackTest {

    private WebDriver driver;

    @BeforeAll
    public void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "driver/mac/chromedriver.exe");
    }

    @BeforeEach
    void SetUp(){
        driver = new ChromeDriver();
    }
}

package com.vladislav.onlinertest.test;

import com.vladislav.onlinertest.core.driver.WebDriverSingleton;
import com.vladislav.onlinertest.pages.MainPage;
import com.vladislav.onlinertest.pages.SearchResultsPage;
import com.vladislav.onlinertest.utils.ScreenshotOnFailureExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ScreenshotOnFailureExtension.class)
public class NegativeTest {

    private WebDriver driver;
    private MainPage mainPage;
    private SearchResultsPage searchResultsPage;



    @BeforeEach
    public void setup() {
        this.driver = WebDriverSingleton.getDriver();
        mainPage = new MainPage();
        driver.get("https://www.onliner.by");

    }

    @Test
    public void searchProductsNegativeTest() {
        String expectedName = "Телефо2н Samsung Galaxy S25 SM-S931B 12GB/128GB (голубой)";
        searchResultsPage = mainPage.inputText(expectedName);
        String actualName = searchResultsPage.getActualProductName();
        Assertions.assertEquals(expectedName, actualName, "Название товара не совпадает");
    }

    @AfterEach
    public void tearDown() {
        WebDriver driver = WebDriverSingleton.getDriver();
        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.get("about:blank");
        }
    }

}

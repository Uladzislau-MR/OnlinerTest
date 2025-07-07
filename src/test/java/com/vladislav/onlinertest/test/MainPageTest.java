package com.vladislav.onlinertest.test;


import com.vladislav.onlinertest.core.driver.WebDriverSingleton;
import com.vladislav.onlinertest.models.pages.CartPage;
import com.vladislav.onlinertest.models.pages.ProductPricesPage;
import com.vladislav.onlinertest.models.pages.SearchResultsPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import com.vladislav.onlinertest.models.pages.MainPage;
public class MainPageTest {

    private WebDriver driver;
    private MainPage mainPage;

    @BeforeEach
    public void setup() {
        this.driver = WebDriverSingleton.getDriver();
        this.mainPage = new MainPage(this.driver);
    }

    @Test
    public void secondTest() {
        mainPage.open();
        String expectedProductName = "Телефон Samsung Galaxy S25 SM-S931B 12GB/128GB (голубой)";
        SearchResultsPage resultsPage = mainPage.inputText(expectedProductName);
        String actualProductName = resultsPage.getActualProductName();
        System.out.println(actualProductName);
        resultsPage.openProductPricesPage(expectedProductName, driver)
                        .addProductToCart();
        ProductPricesPage productPricesPage = new ProductPricesPage();
        productPricesPage.openCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.removeFirstProduct();

        Assertions.assertEquals(
                expectedProductName,
                actualProductName,
                "Название найденного товара не совпадает с искомым."
        );
    }

    @AfterEach
    public void tearDown() {
    WebDriverSingleton.closeDriver();
    }
}
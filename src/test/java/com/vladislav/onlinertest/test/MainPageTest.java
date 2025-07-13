package com.vladislav.onlinertest.test;


import com.vladislav.onlinertest.core.driver.WebDriverSingleton;
import com.vladislav.onlinertest.pages.CartPage;
import com.vladislav.onlinertest.pages.ProductPricesPage;
import com.vladislav.onlinertest.pages.SearchResultsPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import com.vladislav.onlinertest.pages.MainPage;
public class MainPageTest {

    private WebDriver driver;
    private MainPage mainPage;
    ProductPricesPage productPricesPage;
    CartPage cartPage;
    @BeforeEach
    public void setup() {
        this.driver = WebDriverSingleton.getDriver();
        this.mainPage = new MainPage();
        this.productPricesPage = new ProductPricesPage();
        this.cartPage = new CartPage();
        driver.get("https://www.onliner.by");

        }


    @Test
    public void secondTest() {

        String expectedProductName = "Телефон Samsung Galaxy S25 SM-S931B 12GB/128GB (голубой)";
        SearchResultsPage resultsPage = mainPage.inputText(expectedProductName);
        String actualProductName = resultsPage.getActualProductName();
        System.out.println(actualProductName);
        resultsPage.openProductPricesPage(expectedProductName);

        productPricesPage.addProductToCart();
        productPricesPage.openCart();
        cartPage.removeFirstProduct();

        Assertions.assertEquals(
                expectedProductName,
                actualProductName,
                "Название найденного товара не совпадает с искомым."
        );
    }

//    @AfterEach
//    public void tearDown() {
//    WebDriverSingleton.closeDriver();
//    }
}
package com.vladislav.onlinertest.test;

import com.vladislav.onlinertest.core.driver.WebDriverSingleton;
import com.vladislav.onlinertest.pages.MainPage;

import com.vladislav.onlinertest.pages.CartPage;
import com.vladislav.onlinertest.pages.ProductPricesPage;
import com.vladislav.onlinertest.pages.SearchResultsPage;
import com.vladislav.onlinertest.utils.ScreenshotOnFailureExtension;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.junit.jupiter.api.extension.ExtendWith;
@ExtendWith(ScreenshotOnFailureExtension.class)
public class MainPageTest {

    private WebDriver driver;
    private MainPage mainPage;
    private SearchResultsPage searchResultsPage;
    private ProductPricesPage productPricesPage;
    private CartPage cartPage;
    private final String PRODUCT_NAME = "Телефон Samsung Galaxy S25 SM-S931B 12GB/128GB (голубой)";


    @BeforeEach
    public void setup() {
        this.driver = WebDriverSingleton.getDriver();
        mainPage = new MainPage();
        searchResultsPage = new SearchResultsPage();
        productPricesPage = new ProductPricesPage();
        driver.get("https://www.onliner.by");
    }

    @Test
    public void searchProducts() {

        searchResultsPage = mainPage.inputText(PRODUCT_NAME);
        String actualName = searchResultsPage.getActualProductName();
        Assertions.assertEquals(PRODUCT_NAME, actualName, "Название товара не совпадает");
    }

    @Test
    public void addingProductIntoCart() {

        searchResultsPage = mainPage.inputText(PRODUCT_NAME);
        productPricesPage = searchResultsPage.openProductPricesPage(PRODUCT_NAME);
        boolean isProductAddedInCart = productPricesPage.addProductToCart();
        Assertions.assertTrue(isProductAddedInCart, "Ошибка добавления товара в корзину");
    }

    @Test
    public void displayingProductInCart() {

        searchResultsPage = mainPage.inputText(PRODUCT_NAME);
        productPricesPage = searchResultsPage.openProductPricesPage(PRODUCT_NAME);
        productPricesPage.addProductToCart();
        cartPage = productPricesPage.openCart();
        Assertions.assertTrue(cartPage.isProductInCart(PRODUCT_NAME), "Продукт " + PRODUCT_NAME + " отсутствует в корзине") ;

    }

    @Test
    public void removingAProductFromTheCart() {

        searchResultsPage = mainPage.inputText(PRODUCT_NAME);
        productPricesPage = searchResultsPage.openProductPricesPage(PRODUCT_NAME);
        productPricesPage.addProductToCart();
        cartPage = productPricesPage.openCart();
        Assertions.assertTrue(cartPage.removeFirstProduct(), "Ошибка добавления товара в корзину");
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
package com.vladislav.onlinertest.models.pages;

import com.vladislav.onlinertest.core.element.Element;
import com.vladislav.onlinertest.models.Product;
import com.vladislav.onlinertest.utils.ProductParser;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Comparator;
import java.util.List;

public class SearchResultsPage {
    private final ProductParser productParser;
    private final Element productLocator =  Element.byXpath("//div[contains(@class, 'result__item_product')]");
    private final Element priceButtonLocator = Element.byXpath("//*[@id=\"search-page\"]/div[2]/ul/li[1]/div/div/div[1]/div/a");


    public SearchResultsPage() {
        this.productParser = new ProductParser();
    }

    @Step("Get list of products on the page")
    public List<Product> getProducts() {

        List<WebElement> productElements = productLocator.getElements();
        return productParser.parseProducts(productElements);
    }
    @Step("Find the cheapest product")
    public Product getCheapestProduct() {
        List<Product> products = getProducts();
        return products.stream()
                .min(Comparator.comparing(Product::getPrice))
                .orElseThrow(() -> new RuntimeException("Failed to find the product with the minimum price."));
    }
    @Step("Find product by name: {productName}")
    public Product getProductByName(String productName) {
        List<Product> products = getProducts();

        return products.stream()
                .filter(product -> product.getName().equalsIgnoreCase(productName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product with name '" + productName + "' not found."));
    }

    @Step("Get product card WebElement by product name: {productName}")
    private WebElement getProductCardByName(String productName) {

        List<WebElement> cards = productLocator.getElements();

        for (WebElement card : cards) {
            try {
                WebElement titleElement = card.findElement(By.cssSelector(".product__title-link"));
                String actualTitle = titleElement.getText();

                if (actualTitle.equalsIgnoreCase(productName)) {
                    return card;

                }
            } catch (NoSuchElementException e) {

            }
        }
        throw new NoSuchElementException("Product with name '" + productName + "' not found in search results.");
    }


    @Step("Get actual product name for product: {productName}")
    public String getActualProductName(String productName) {
        WebElement productCard = getProductCardByName(productName);
        WebElement titleElement = productCard.findElement(By.cssSelector(".product__title-link"));
        return titleElement.getText();
    }
    @Step("Open product prices page ")
    public ProductPricesPage openProductPricesPage(String name, WebDriver driver ) {
        WebElement element = getProductCardByName(name);
       priceButtonLocator.clickNested(element);
     return new  ProductPricesPage(driver);
    }


}

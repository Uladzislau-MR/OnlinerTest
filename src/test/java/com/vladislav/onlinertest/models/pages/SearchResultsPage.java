package com.vladislav.onlinertest.models.pages;

import com.vladislav.onlinertest.core.element.Element;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

public class SearchResultsPage {

    private final Element productLocator = Element.byXpath("//div[contains(@class, 'result__item_product')]");
    private final Element priceButtonLocator = Element.byXpath("//*[@id=\"search-page\"]/div[2]/ul/li[1]/div/div/div[1]/div/a");
    private final Element titleElement = Element.byCss(".product__title-link");


    @Step("Get actual product name")
    public String getActualProductName() {
        return productLocator.getNestedElementByLocator(titleElement.getBy()).getText();
    }

    @Step("Open product prices page ")
    public ProductPricesPage openProductPricesPage(String name) {
        WebElement element = productLocator.getProductCardByName(name);
        priceButtonLocator.clickNested(element);
        return new ProductPricesPage();
    }


}

package com.vladislav.onlinertest.models.pages;

import com.vladislav.onlinertest.core.element.Element;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class CartPage {
    private final WebDriver driver;
    Element product = Element.byXpath(".//a[contains(@class, 'cart-form__button_remove')]");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

@Step("Remove product")
    public void removeFirstProduct() {

        WebElement productElement = product.getElement();
        Actions actions = new Actions(driver);
        actions.moveToElement(productElement).click().perform();

    }


}

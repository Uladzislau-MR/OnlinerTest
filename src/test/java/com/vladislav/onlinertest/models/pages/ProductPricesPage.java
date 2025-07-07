package com.vladislav.onlinertest.models.pages;

import com.vladislav.onlinertest.core.element.Element;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

public class ProductPricesPage {
    private final Element sortByDropdown = Element.byCss(".input-style__real");
    private final Element cart = Element.byXpath("((//div[contains(@class, 'offers-list__item')])[1]//a[contains(@class, 'offers-list__button_cart') and normalize-space()='В корзину'])[2]");
    private final Element toCart = Element.byXpath("(//div[contains(@class, 'offers-list__item')])[1]//a[normalize-space()='Перейти в корзину']");
    private final Element sort = Element.byXpath("//div[@class='input-style__faux']");

    @Step("Sort products by price ascending")
    public void sortByPriceAscending() {
       sortByDropdown.selectSortByValue("price:asc");
    }

    @Step("Add product to cart")
    public void addProductToCart() {
        sortByPriceAscending();
     try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        cart.waitClickableJS();
    }

    @Step("Open cart page")
    public  void openCart() {
        toCart.waitClickableClick();
    }


}
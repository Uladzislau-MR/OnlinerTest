package com.vladislav.onlinertest.pages;

import com.vladislav.onlinertest.core.element.Element;
import io.qameta.allure.Step;


public class ProductPricesPage {
    private final Element sortByDropdown = Element.byCss(".input-style__real");
    private final Element cart = Element.byXpath("((//div[contains(@class, 'offers-list__item')])[1]//a[contains(@class, 'offers-list__button_cart') and normalize-space()='В корзину'])[2]");
    private final Element toCart = Element.byXpath("//a[contains(@class, 'button-style button-style_another button-style_base-')]");

    private final Element sort = Element.byXpath("//div[@class='input-style__faux']");
    private final Element sortCheck = Element.byXpath("//div[@class = 'offers-filter__part offers-filter__part_2']");

    @Step("Sort products by price ascending")
    public void sortByPriceAscending() {
       sortByDropdown.selectSortByValue("price:asc");
    }

    @Step("Add product to cart")
    public void addProductToCart() {
        sortByPriceAscending();



     try {
            Thread.sleep(5000);
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
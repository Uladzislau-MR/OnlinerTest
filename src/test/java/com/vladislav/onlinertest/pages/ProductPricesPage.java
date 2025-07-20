package com.vladislav.onlinertest.pages;

import com.vladislav.onlinertest.core.element.Element;
import io.qameta.allure.Step;


public class ProductPricesPage {
    private final Element sortByDropdown = Element.byCss(".input-style__real");
    private final Element cart = Element.byXpath("((//div[contains(@class, 'offers-list__item')])[1]//a[contains(@class, 'offers-list__button_cart') and normalize-space()='В корзину'])[2]");
    private final Element goToCartButton = Element.byXpath("//a[contains(@class, 'button-style button-style_another button-style_base-')]");
    private final Element numberSelector = Element.byCss(".product-recommended__input");
    private final Element text = Element.byXpath("//div[@class='product-recommended__subheader' and contains(text(), 'Товар добавлен в корзину')]");


    @Step("Sort products by price ascending")
    public void sortByPriceAscending() {
        sortByDropdown.selectSortByValue("price:asc");
    }

    @Step("Add product to cart")
    public boolean addProductToCart() {
        sortByPriceAscending();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        cart.waitClickableJS();
        int num = numberSelector.getNumberFromLocator();
        boolean isOne = false;
        boolean isCorrectText = false;
        if (num == 1) {
            isOne = true;
        }
        String value = "Товар добавлен в корзину";
        String valueFromPage = text.getText();
        if (value.equals(valueFromPage)) {
            isCorrectText = true;
        }

        boolean isGoToCartButtonDisplayed = goToCartButton.isDisplayed();
        return  isOne && isGoToCartButtonDisplayed && isCorrectText;
       }

    @Step("Open cart page")
    public CartPage openCart() {
        goToCartButton.waitClickableClick();
        return  new CartPage();
    }


}
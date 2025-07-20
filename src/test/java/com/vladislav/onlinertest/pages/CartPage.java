package com.vladislav.onlinertest.pages;

import com.vladislav.onlinertest.core.element.Element;
import io.qameta.allure.Step;

public class CartPage {

    Element product = Element.byXpath(".//a[contains(@class,'cart-form__button_remove')]");
    Element closeButton = Element.byXpath("//a[contains(@class,'cart-form__link_small') and contains(text(), 'Закрыть')]");
    Element cart = Element.byXpath("//div[contains(@class,'cart-form__title') and contains(text(), 'Корзина')]");
    Element emptyCart = Element.byXpath("//div[@class = 'cart-message__title cart-message__title_big']");

    public boolean isProductInCart(String productName) {
        boolean isCorrectText = false;
        boolean isCorrectProduct = isCorrectProduct(productName);
        String value = "Корзина";
        String valueFromPage = cart.getText();
        if (value.equals(valueFromPage)) {
            isCorrectText = true;
        }
        return isCorrectText && isCorrectProduct;
           }

    public boolean isCorrectProduct(String productName) {
        Element productInCart = Element.byXpath(String.format("//a[contains(@href,'https://catalog.onliner.by') and contains(text(), '%s')]", productName));
        boolean isCorrectText = false;
        if (productInCart.isDisplayed()) isCorrectText = true;
        return isCorrectText;
    }


    @Step("Remove product")
    public boolean removeFirstProduct() {
        String text = "Ваша корзина пуста";
        boolean isCartEmpty = false;
        product.clickElementByAction();
        closeButton.waitClickableClick();
        if (text.equals(emptyCart.getText())) {
            isCartEmpty = true;
        }
        return isCartEmpty;
    }


}



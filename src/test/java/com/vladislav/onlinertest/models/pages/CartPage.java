package com.vladislav.onlinertest.models.pages;

import com.vladislav.onlinertest.core.element.Element;
import io.qameta.allure.Step;

public class CartPage {

    Element product = Element.byXpath(".//a[contains(@class, 'cart-form__button_remove')]");
    Element closeButton = Element.byXpath("//a[contains(@class,' cart-form__link_small') and contains(text(), 'Закрыть')]");



@Step("Remove product")
    public void removeFirstProduct() {
    product.clickElementByAction();
    closeButton.waitClickableClick();
    }


}

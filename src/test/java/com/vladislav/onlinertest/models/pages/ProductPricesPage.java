package com.vladislav.onlinertest.models.pages;

import com.vladislav.onlinertest.core.element.Element;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;



public class ProductPricesPage {
    private final Element sortByDropdown = Element.byCss(".input-style__real");
    private final Element cart = Element.byXpath("((//div[contains(@class, 'offers-list__item')])[1]//a[contains(@class, 'button-style') and contains(text(), 'В корзину')])[2]");
    private final Element toCart = Element.byXpath("(//div[contains(@class, 'offers-list__item')])[1]//a[normalize-space()='Перейти в корзину']");

    private final WebDriver driver;

    public ProductPricesPage(WebDriver driver) {
        this.driver = driver;
    }


    public void selectSortByValue(String value) {
        WebElement dropdown = sortByDropdown.getElement();
        Select select = new Select(dropdown);
        select.selectByValue(value);
        sortByDropdown.dropDownMenuClick(dropdown);
    }
    @Step("Sort products by price ascending")
    public void sortByPriceAscending() {

        selectSortByValue("price:asc");
    }
    @Step("Add product to cart")
    public void addProductToCart() {
        sortByPriceAscending();
        cart.waitClickableJS();

    }
    @Step("Open cart page")
    public  void openCart() {
        toCart.waitClickableClick();
    }


}
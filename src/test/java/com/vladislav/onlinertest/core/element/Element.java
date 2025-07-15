package com.vladislav.onlinertest.core.element;


import com.vladislav.onlinertest.core.driver.WebDriverSingleton;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Element {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By by;

    public Element(By by) {
        this.driver = WebDriverSingleton.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.by = by;
    }

    public static Element byXpath(String xpath, Object... params) {
        String locator = String.format(xpath, params);
        return new Element(By.xpath(locator));
    }

    public By getBy() {
        return by;
    }

    public static Element byCss(String css, Object... params) {
        String locator = String.format(css, params);
        return new Element(By.cssSelector(locator));

    }

    public void waitVisibility() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }


    public void waitClickableJS() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public void waitClickableClick() {
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }


    public void enterText(String text) {
        WebElement inputField = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1];", inputField, text);
        js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", inputField);
    }

    public String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }


    @Step("Get actual product name for product: {productName}")
    public String getActualProductName(String productName, Element element) {
        WebElement productCard = getProductCardByName(productName);
        WebElement titleElement = productCard.findElement(element.by);
        return titleElement.getText();
    }

    public WebElement getElement() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public List<WebElement> getElements() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }


    public WebElement getNestedElement(WebElement nested) {
        return nested.findElement(by);
    }

    public WebElement getNestedElementByLocator(By by) {
        WebElement outer = getElement();
        return outer.findElement(by);
    }

    public String getTextFromNestedElement(WebElement nested) {
        return getNestedElement(nested).getText();
    }

    public void switchFrame() {
       WebElement frameElement = getElement();
       wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
    }

    public void dropDownMenuClick(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                element
        );
    }

    public void selectSortByValue(String value) {
        WebElement dropdown = getElement();
        Select select = new Select(dropdown);
        select.selectByValue(value);
        dropDownMenuClick(dropdown);
           }

    public void waitForExactTextToAppear(String expectedText) {
        long startTime = System.nanoTime();
        WebDriverWait specificWait = new WebDriverWait(driver, Duration.ofSeconds(30));

        specificWait.until(d -> {

            WebElement element = d.findElement(this.by);
            String currentText = element.getText().trim();
            return currentText.equals(expectedText);
        });

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        System.out.println("Видимый UI элемент '" + this.by.toString() + "' успешно обновился до текста: '" + expectedText + "'.");
        System.out.println("Вэйтер для видимого UI ждал: " + duration + " мс.");

    }


    public void clickNested(WebElement parent) {
        WebElement nested = parent.findElement(by);
        wait.until(ExpectedConditions.elementToBeClickable(nested)).click();
    }

    public void clickElementByAction() {
        WebElement productElement = getElement();
        Actions actions = new Actions(driver);
        actions.moveToElement(productElement).click().perform();
    }

    @Step("Get product card WebElement by product name: {productName}")
    public WebElement getProductCardByName(String productName) {

        List<WebElement> cards = getElements();

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


}


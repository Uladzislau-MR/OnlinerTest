package com.vladislav.onlinertest.core.element;


import com.vladislav.onlinertest.core.driver.WebDriverSingleton;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Element {
   private final WebDriver driver;
    private  final WebDriverWait wait;
    private final By by;

    public Element(By by) {
        this.driver = WebDriverSingleton.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.by = by;
    }

    public static Element byXpath(String xpath, Object ...  params){
        String locator = String.format(xpath, params);
        return new Element(By.xpath(locator));

    }

    public static Element byCss(String css, Object ...  params){
        String locator = String.format(css, params);
        return new Element(By.cssSelector(locator));

    }

    public void waitVisibilityClick() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        element.click();
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
    public WebElement getElement() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
    public List<WebElement> getElements() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public WebElement getNestedElement(WebElement nested) {
        return nested.findElement(by);
    }



    public String getTextFromNestedElement(WebElement nested) {

        return getNestedElement(nested).getText();
    }

    public void switchFrame(WebElement frameElement) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));

    }
    public void clickElementWithJs(WebElement element) {
        WebDriver driver = ((WrapsDriver) element).getWrappedDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public void dropDownMenuClick(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                element
        );
    }



    public void clickNested(WebElement parent) {
        WebElement nested = parent.findElement(by);
        wait.until(ExpectedConditions.elementToBeClickable(nested)).click();
    }


    public String getAttributeFromNestedElement(WebElement nested, String attributeName ) {
        return getNestedElement(nested).getAttribute(attributeName);

    }


    public void clickElementByJs(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }


}


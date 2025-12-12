package com.vladislav.onlinertest.utils;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverListener;
import io.qameta.allure.Allure;


@Log4j2
public class CustomWebDriverListener implements WebDriverListener {

    @Override
    public void afterAccept(Alert alert) {
        log.info("Alert accepted.");
    }

    @Override
    public void afterDismiss(Alert alert) {
        log.info("Alert dismissed.");
    }

    @Override
    public void afterTo(WebDriver.Navigation navigation, String url) {
        log.info("Navigated to URL: " + url);
        Allure.step("Navigated to URL: " + url);
    }

    @Override
    public void afterBack(WebDriver.Navigation navigation) {
        log.info("Navigated back.");
        Allure.step("Navigated back.");
    }

    @Override
    public void afterForward(WebDriver.Navigation navigation) {
        log.info("Navigated forward.");
    }

    @Override
    public void afterRefresh(WebDriver.Navigation navigation) {
        log.info("Page refreshed.");
        Allure.step("Page refreshed.");
    }

    @Override
    public void beforeFindElement(WebDriver driver, By by) {
        log.debug("Finding element: " + by);
        Allure.step("Finding element " + by.toString());
    }

    @Override
    public void afterFindElement(WebDriver driver, By by, WebElement result) {

        String elementInfo = "";
        try {
                 if (result != null) {
                elementInfo = " (Tag: " + result.getTagName() + ", Text: '" + result.getText().trim().replace("\n", " ") + "')";
            }
        } catch (WebDriverException e) {
                elementInfo = " (Info not available, element might be stale)";
        }

        log.info("Listener: Found element: " + by.toString() + elementInfo);
        Allure.step("Listener: Element found: " + by.toString() + elementInfo);
    }
}

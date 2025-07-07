package com.vladislav.onlinertest.core.driver;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
}

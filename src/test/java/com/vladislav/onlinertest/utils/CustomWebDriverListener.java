package com.vladislav.onlinertest.utils;

import io.qameta.allure.Allure;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.WebDriverListener;
@Log4j2
public class CustomWebDriverListener implements WebDriverListener {


    public void afterTo(WebDriver.Navigation navigation, String url) {
        log.info("Navigated to URL:  {}" + url);

    }
}

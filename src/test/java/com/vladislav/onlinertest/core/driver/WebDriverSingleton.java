package com.vladislav.onlinertest.core.driver;

import com.vladislav.onlinertest.utils.CustomWebDriverListener;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Log4j2
public class WebDriverSingleton {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private static final List<WebDriver> drivers = Collections.synchronizedList(new ArrayList<>());
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutdown hook triggered. Closing all WebDriver instances.");
            for (WebDriver driver : drivers) {
                try {
                    driver.quit();
                } catch (Exception e) {
                    log.error("Error while quitting WebDriver in shutdown hook: " + e.getMessage());
                }
            }
        }));
    }

    private WebDriverSingleton() {
    }

    public static synchronized WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            log.info("Creating a new Webdriver instance for thread: " + Thread.currentThread().getId());
            WebDriver baseDriver = WebDriverFactory.getDriver();
            WebDriverListener listener = new CustomWebDriverListener();
            WebDriver decoratedDriver = new EventFiringDecorator(listener).decorate(baseDriver);

            // 3. Добавляем созданный драйвер в наш общий список
            drivers.add(decoratedDriver);

            driverThreadLocal.set(decoratedDriver);
        }
        return driverThreadLocal.get();
    }


    public static synchronized void closeDriver() {
        log.info("Manually closing WebDriver for thread: " + Thread.currentThread().getId());
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
                      drivers.remove(driver);
            } catch (Exception e) {
                log.error("Error while quitting WebDriver: " + e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }
}






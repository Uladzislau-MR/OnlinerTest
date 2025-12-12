package com.vladislav.onlinertest.utils;

import com.vladislav.onlinertest.core.driver.WebDriverSingleton;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.nio.charset.StandardCharsets;


public class ScreenshotOnFailureExtension implements TestExecutionExceptionHandler {

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {

        WebDriver driver = WebDriverSingleton.getDriver();

        if (driver instanceof TakesScreenshot) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            Allure.getLifecycle().addAttachment(
                    "Screenshot on failure",
                    "image/png",
                    "png",
                    screenshot
            );
        }

        if (throwable != null) {
            Allure.getLifecycle().addAttachment(
                    "Exception message",
                    "text/plain",
                    ".txt",
                    throwable.getMessage().getBytes(StandardCharsets.UTF_8)
            );
        }


        throw throwable;
    }
}
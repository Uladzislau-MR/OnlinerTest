package com.vladislav.onlinertest.core.driver;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;

@Log4j2
public class WebDriverFactory {

    private static final String HOST = System.getProperty("host");
    private static final String DOWNLOADS_PATH = FileUtils.getTempDirectoryPath();

    public static WebDriver getDriver() {
        WebDriver driver;
        String browser = System.getProperty("browser");
        if (browser == null) {
            log.warn("Browser property is not set. Defaulting to 'chrome'.");
            browser = "chrome";
        }

        switch (browser.toLowerCase()) {
            case "chrome": {
                ChromeOptions options = getChromeOptions();
                if (HOST != null) {
                    driver = createRemoteDriver(options);
                } else {
                    System.setProperty("webdriver.chrome.verboseLogging", "true");

                    File logFile = new File("chromedriver.log");
                    ChromeDriverService service = new ChromeDriverService.Builder()
                            .withLogFile(logFile)
                            .build();

                    log.info("ChromeDriver log file will be created at: " + logFile.getAbsolutePath());
                    driver = new ChromeDriver(service, options);
                }
                break;
            }
            case "firefox": {
                FirefoxOptions options = getFirefoxOptions();
                if (HOST != null) {
                    driver = createRemoteDriver(options);
                } else {
                    driver = new FirefoxDriver(options);
                }
                break;
            }
            default:
                throw new RuntimeException("Unsupported browser: " + browser);
        }

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        log.info("Created WebDriver instance " + driver.getClass().getSimpleName());
        return driver;
    }

    private static WebDriver createRemoteDriver(MutableCapabilities options) {
        try {
            log.info("Creating RemoteWebDriver instance on host: " + HOST);
            return new RemoteWebDriver(new URL(HOST), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException("invalid selenium Grid URL", e);
        }
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir", DOWNLOADS_PATH);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream");
        profile.setPreference("dom.webdriver.enabled", false);
        profile.setPreference("devtools.chrome.enabled", false);
        profile.setPreference("useAutomationExtension", false);
        firefoxOptions.setProfile(profile);
        return firefoxOptions;
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-software-rasterizer");
        options.addArguments("--disable-features=VizDisplayCompositor");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--dns-prefetch-disable");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--user-data-dir=" + FileUtils.getTempDirectoryPath() + File.separator + "chrome-profile-" + System.currentTimeMillis());
        return options;
    }
}
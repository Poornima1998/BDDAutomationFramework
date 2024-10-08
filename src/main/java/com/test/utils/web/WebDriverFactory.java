package com.test.utils.web;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import com.test.utils.Utils.Config;

public class WebDriverFactory {

    private static WebDriver driver;
    private static String DriverPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources").toString();

    public enum BrowserType {
        chrome,
        firefox,
        edge,
        safari
    }

    /**
     * Method to get instance of WebDriver using default parameters.
     * 
     * @return driver - WebDriver instance
     * @throws MalformedURLException
     */
    public static WebDriver get() throws MalformedURLException {

        String browserName = (System.getProperty("BROWSER_NAME") != null ? System.getProperty("BROWSER_NAME")
                : !Config.readProperty("browser").toLowerCase().trim().isEmpty()
                        ? Config.readProperty("browser").toLowerCase().trim()
                        : "chrome").toLowerCase();
        BrowserType browserType = BrowserType.valueOf(browserName);
        return getDriver(browserType);
    }

    /**
     * Method to get instance of WebDriver using specific browser setup.
     * 
     * @param browserSetup - Browser setup string
     * @return driver - WebDriver instance
     * @throws MalformedURLException
     */
    public static WebDriver get(String browserSetup) throws MalformedURLException {
        BrowserType browserType = BrowserType.valueOf(browserSetup.toLowerCase());
        return getDriver(browserType);
    }

    /**
     * Creates a WebDriver instance based on the browser type.
     * 
     * @param browserType - Enum representing the browser type
     * @return driver - WebDriver instance
     */
    public static WebDriver createWebDriver(BrowserType browserType) {
        if (driver != null) {
            return driver;
        }

        switch (browserType) {
            case chrome:
                System.setProperty("webdriver.chrome.driver", DriverPath + File.separatorChar + "chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();
                driver = new ChromeDriver(chromeOptions);
                break;

            case firefox:
                System.setProperty("webdriver.gecko.driver", DriverPath + File.separatorChar + "geckodriver.exe");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case edge:
                System.setProperty("webdriver.edge.driver", DriverPath + File.separatorChar + "msedgedriver.exe");
                EdgeOptions edgeOptions = new EdgeOptions();
                driver = new EdgeDriver(edgeOptions);
                break;

            case safari:
                SafariOptions safariOptions = new SafariOptions();
                driver = new SafariDriver(safariOptions);
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }

        // Set default implicit wait
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Maximize the browser window
        driver.manage().window().maximize();

        return driver;
    }

    /**
     * Returns the WebDriver instance based on the browser type.
     * 
     * @param browserType - Enum representing the browser type
     * @return driver - WebDriver instance
     */
    public static WebDriver getDriver(BrowserType browserType) {
        if (driver == null) {
            driver = createWebDriver(browserType);
        }
        return driver;
    }

    /**
     * Quits the WebDriver instance and cleans up.
     */
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

}

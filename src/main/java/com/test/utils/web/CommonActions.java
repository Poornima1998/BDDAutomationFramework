package com.test.utils.web;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonActions {

    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor to initialize driver and wait
    public CommonActions(WebDriver driver) {
        this.driver = driver;
     // Use Duration for WebDriverWait
      this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Type text into an input field
    public void type(By locator, String text, String fieldName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
        System.out.println("Typed '" + text + "' into " + fieldName);
    }

    // Click on an element
    public void click(By locator, String elementName) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
        System.out.println("Clicked on " + elementName);
    }

    // Send keys to an input field
    public void sendKeys(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.sendKeys(text);
    }
}

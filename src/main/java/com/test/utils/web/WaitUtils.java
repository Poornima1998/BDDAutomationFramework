package com.test.utils.web;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.utils.Utils;
import com.test.utils.Utils.Config;

public class WaitUtils {
	
	
	static int maxElementWait = Integer.parseInt(Config.readProperty("maxElementWait"));
	
	public static boolean waitForElement(WebDriver driver, WebElement webElement, int... maxWaitArray) {
		int maxWait = maxWaitArray.length > 0 ? maxWaitArray[0] : maxElementWait;
		int retryCount = 0;
		boolean staleElementException = false;
		boolean statusOfElementToBeReturned = false;
		long startTime = System.currentTimeMillis();
		long time = System.currentTimeMillis() - startTime;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxWait));
		do {
			try {
				wait.until(ExpectedConditions.visibilityOf(webElement));
				statusOfElementToBeReturned = true;
				System.out.println("Web element found: " + Utils.getLocatorInfo(webElement));
			} catch (StaleElementReferenceException e1) {
				System.err.println("selectDropDownByVisibleText - Stale element exception");
				staleElementException = true;
			}
			retryCount++;
		} while (retryCount == 1 && staleElementException);
		return statusOfElementToBeReturned;
	}
	
	public static void waitForElementToBeClickable(WebDriver driver, WebElement webElement, String elementDescription,
			int... timeOutArray) {
		int timeout = timeOutArray.length > 0 ? timeOutArray[0] : maxElementWait;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.elementToBeClickable(webElement));
			System.out.println(elementDescription + " is clickable. " + Utils.getLocatorInfo(webElement));
		} catch (NoSuchElementException e) {
			 System.err.println(elementDescription + " is not clickable. " + Utils.getLocatorInfo(webElement) + e);
		}
	}

}

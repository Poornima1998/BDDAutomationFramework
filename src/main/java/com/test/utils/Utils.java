package com.test.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;

import com.test.utils.web.PageObjectRegistry;
import com.test.utils.web.WebUtils;

public class Utils {

	public static class Config {

		private static final String PROPERTIES_FILE_PATH = "./src/main/resources/config.properties";

		public static String readProperty(String property) {
			String value = null;
			Properties prop = new Properties();
			InputStream input = null;
			try {
				input = new FileInputStream(PROPERTIES_FILE_PATH);
				// load a properties file
				prop.load(input);
				// get the property value
				value = (prop.getProperty(property));
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return value;
		}
	}

	public static String getBrowserName(WebDriver driver) throws Exception {
		String browserName = ((RemoteWebDriver) driver).getCapabilities().getBrowserName();
		return browserName;
	}

	public static String getCurrentTestName() throws Exception {
		return Reporter.getCurrentTestResult().getName();
	}
	
    public static String getBrowserVersion(WebDriver driver) {
       String version = ((RemoteWebDriver) driver).getCapabilities().getBrowserVersion();
        return version;  
    }
    
    public static String getOperatingSystem() {
        return System.getProperty("os.name");
    }

    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }
    
    
    
    
    
    

	/// locator utils

	public static String getLocatorInfo(WebElement webElement) {
		String locatorInfo;
		try {
			// This is just a simulated approach. You would replace this with your actual
			// logic to retrieve XPath or Name.
			locatorInfo = webElement.toString(); // This will return the element's info, including the XPath or CSS
													// selector
			// You can parse this string to extract only the relevant part (XPath, CSS
			// selector, etc.)
			int start = locatorInfo.indexOf("->") + 3;
			locatorInfo = locatorInfo.substring(start, locatorInfo.length() - 1);
		} catch (Exception e) {
			locatorInfo = "Unknown Locator"; // Fallback if parsing fails
		}
		return locatorInfo;
	}

	/**
	 * 
	 * @param element
	 * @return
	 */
	public static String getFieldNameOrMethodName(WebElement element) {
		// Iterate over all registered page objects
		for (Object pageObject : PageObjectRegistry.getPageObjects()) {
			Class<?> clazz = pageObject.getClass();

			// Check fields with @FindBy annotation
			for (Field field : clazz.getDeclaredFields()) {
				if (field.isAnnotationPresent(FindBy.class)) {
					field.setAccessible(true);
					try {
						if (field.get(pageObject) == element) {
							return field.getName();
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}

			for (Method method : clazz.getDeclaredMethods()) {
                if (method.getReturnType().equals(By.class)) {
                    try {
                        // Check if the method's return value matches the WebElement's locator
                        By result = (By) method.invoke(pageObject,"test");  // No argument needed
                        if (element.equals(getWebElementFromLocator(pageObject, result))) {
                            return method.getName();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
		return "null";
	}
	
	/**
     * Gets a WebElement from a By locator on a given page object.
     * 
     * @param pageObject The page object where the locator is applied.
     * @param locator The By locator to search for.
     * @return The WebElement corresponding to the locator.
     */
    private static WebElement getWebElementFromLocator(Object pageObject, By locator) {
        for (Field field : pageObject.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(FindBy.class)) {
                field.setAccessible(true);
                try {
                    WebElement webElement = (WebElement) field.get(pageObject);
                    if (webElement != null && webElement.equals(locator)) {
                        return webElement;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
	
}

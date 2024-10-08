package com.test.utils.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.test.utils.Log;
import com.test.utils.FileUtils;
import com.test.utils.Utils;
import com.test.utils.Utils.Config;

public class WebUtils {
	
//	private static WebDriver driver;

	private static final String NOT_FOUND_IN_PAGE=" not found in page!!";
	private static final String CLICK_STALE_ELEMENT_EXCEPTION="click-stale element exception ";
	private static final String FAILED_TO_CLICK_ON="Failed to click on ";
	private static final String IS_DISABLED=" is disabled";
	private static final String IS_DISPLAYED=" is displayed";
	private static final String IS_NOT_DISPLAYED=" is not displayed";
	private static final String IS_NOT_SELECTED=" is not selected";
	static int maxElementWait = Integer.parseInt(Config.readProperty("maxElementWait"));
		
	
	/**
	 * @param driver
	 * @param element                - Element need to be clicked
	 * @param elementDescription     - Description of the element
	 * @param timeOutArray           - Custom timeout in secs
	 */
	public static void click(WebDriver driver, WebElement element, String elementDescription, int... timeOutArray) {
	    int timeout = timeOutArray.length > 0 ? timeOutArray[0] : maxElementWait;
	    int retryCount = 0;
	    boolean staleElementException = false;
	    String fieldName = Utils.getFieldNameOrMethodName(element);
	    String xpath = Utils.getLocatorInfo(element);
	    do {
	        try {
	            if (!WaitUtils.waitForElement(driver, element, timeout)) {
	                throw new NoSuchElementException(elementDescription + NOT_FOUND_IN_PAGE);
	            }
	            WaitUtils.waitForElementToBeClickable(driver, element, elementDescription, timeout);
	            element.click();
	            String msg = "Clicked on Element ["+  fieldName + "]"+ " with locator [" + xpath +"]";
	            Log.message(msg);
	            return; // Click successful, exit method
	        } catch (NoSuchElementException e) {
	        	Log.failsoft(FAILED_TO_CLICK_ON + elementDescription + " " + e);
	            return; // Element not found, exit method
	        } catch (ElementClickInterceptedException e) {
	            Actions actions = new Actions(driver);
	            actions.moveToElement(element).pause(1000).click().build().perform();
	            return; // Click successful using Actions, exit method
	        } catch (StaleElementReferenceException e) {
	        	Log.failsoft(CLICK_STALE_ELEMENT_EXCEPTION + elementDescription);
	            staleElementException = true;
	        }
	        retryCount++;
	    } while (retryCount == 1 && staleElementException);
	}
	
	/**
	 * @param driver
	 * @param element                - text field in what text to be cleared
	 * @param elementDescription     - Description of the element
	 * @param timeOutArray           - Custom timeout in secs 
	 */
	public static void clear(WebDriver driver, WebElement element, String elementDescription, int... timeOutArray) {
	    int timeout = timeOutArray.length > 0 ? timeOutArray[0] : maxElementWait;
	    String fieldName = Utils.getFieldNameOrMethodName(element);
	    String xpath = Utils.getLocatorInfo(element);
	    if (!WaitUtils.waitForElement(driver, element, timeout)) {
            throw new NoSuchElementException(elementDescription + NOT_FOUND_IN_PAGE);
        }
	    try {
	    	element.clear();
	    	String msg = "Cleared on Element ["+  fieldName + "]"+ " with locator [" + xpath +"]";
	    	Log.message(msg);
	    } catch(NoSuchElementException e) {
	    	Log.failsoft("Failed to clear on " + fieldName + " with locator [" + xpath +"]" + e);
	    }
	}
	
	/**
	 * @param driver
	 * @param element                - text field in what text to be cleared
	 * @param elementDescription     - Description of the element
	 * @param timeOutArray           - Custom timeout in secs 
	 */
	public static boolean checkElement(WebDriver driver, WebElement element, String elementDescription, int... timeOutArray) {
	    int timeout = timeOutArray.length > 0 ? timeOutArray[0] : maxElementWait;
	    try {
	    	WaitUtils.waitForElement(driver, element, timeout);    	
	    	Log.message("CheckElement on Element ["+  elementDescription + "]"+ " with locator [" + Utils.getLocatorInfo(element) +"] " + IS_DISPLAYED);
	    	return element.isDisplayed();
	    }catch(NoSuchElementException e) {
	    	Log.failsoft(elementDescription + NOT_FOUND_IN_PAGE + " " + e);
	    	return false;
	    }catch(Exception e) {
	    	Log.failsoft("Failed to checkelement on " + elementDescription + " " + e);
	    	return false;
	    }
	}
	
	/**
	 * Used to time on the web element
	 * 
	 * @param driver
	 * @param txt                - text field in which text to be entered
	 * @param textToType         - text to be entered
	 * @param elementDescription - Description of the element
	 * @param timeOutArray       - custom timeout in secs
	 */
	public static void type(WebDriver driver, WebElement element, String textToType, String elementDescription,
			int... timeOutArray) {
		int timeout = timeOutArray.length > 0 ? timeOutArray[0] : maxElementWait;
		int retryCount = 0;
		boolean staleElementException = false;
		String fieldName = Utils.getFieldNameOrMethodName(element);
		String xpath = Utils.getLocatorInfo(element);
		do {
			if (!WaitUtils.waitForElement(driver, element, timeout))
				throw new NoSuchElementException(elementDescription + NOT_FOUND_IN_PAGE);
			{
				try {
					element.clear();
					element.sendKeys(textToType);
					String msg = "Typed (TestData:"+textToType+")  on Element ["+  fieldName + "]"+ " with locator [" + xpath +"]";
			    	Log.message(msg);
					break;
				} catch (NoSuchElementException e) {
					Log.failsoft("Failed to enter value " + textToType + " in " + elementDescription + " " + e);
				} catch (ElementNotInteractableException e) {
					JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
					jsExecutor.executeScript("arguments[0].value='" + textToType + "';", element);
					Log.message("type - Element Not Interactable exception");
				} catch (StaleElementReferenceException e1) {
					Log.message("type - Stale element exception");
					staleElementException = true;
				}
				retryCount++;
			}
		} while (retryCount == 1 && staleElementException);

	}
	
	
	/**
	 * @param driver
	 * @param element                - text field in what text to be cleared
	 * @param elementDescription     - Description of the element
	 * @param timeOutArray           - Custom timeout in secs 
	 */
	public static void select(WebDriver driver, WebElement element, String option, String elementDescription, int... timeOutArray) {
		int timeout = timeOutArray.length > 0 ? timeOutArray[0] : maxElementWait;
	    int retryCount = 0;
	    boolean staleElementException = false;
	    do {
	        try {
	            if (!WaitUtils.waitForElement(driver, element, timeout)) {
	                throw new NoSuchElementException(elementDescription + NOT_FOUND_IN_PAGE);
	            }
	            Select select= new Select(element);
	            select.selectByVisibleText(option);
	            Log.message("Selected option (" + option + ") on Element ["+  element.getTagName() + "]"+ " with locator [" + element +"]");
	        } catch (NoSuchElementException e) {
	            Log.failsoft(elementDescription + IS_NOT_SELECTED + e);
	        } catch (StaleElementReferenceException e) {
	        	Log.failsoft(elementDescription + IS_NOT_SELECTED + e);
	            Log.failsoft("Stale element exception " + e);
	            staleElementException = true;
	        }
	        retryCount++;
	    } while (retryCount == 1 && staleElementException);
	}
	
	/**
		 * Used to get the current URL of the page
		 * 
		 * @param driver             - WebDriver instance
		 * @param elementDescription - Description of the action
		 * @param timeOutArray       - Custom timeout in seconds
		 * @return String            - Current URL of the page
		 */
		public static String getCurrentUrl(WebDriver driver, String elementDescription, int... timeOutArray) {
		    int timeout = timeOutArray.length > 0 ? timeOutArray[0] : maxElementWait;
		    int retryCount = 0;
		    boolean staleElementException = false;
		    String currentUrl = "";
		
		    do {
		        try {
		            // Wait for the page to load or be in an interactable state
		//            WaitUtils.waitForPageLoad(driver, timeout);
		
		            // Get the current URL
		            currentUrl = driver.getCurrentUrl();
		            Log.message("Current URL: " + currentUrl);
		            break; // Exit the loop if successful
		
		        } catch (NoSuchElementException e) {
		            Log.failsoft("Failed to get the current URL: " + elementDescription + " " + e);
		            break; // Exit the loop if no URL is found
		
		        } catch (StaleElementReferenceException e) {
		            Log.message("getCurrentUrl - Stale element exception");
		            staleElementException = true;
		            retryCount++; // Increment the retry count for stale element exception
		
		        } catch (Exception e) {
		            Log.failsoft("An unexpected error occurred while getting the current URL: " + elementDescription + " " + e);
		            break; // Exit the loop on any other unexpected exception
		
		        }
		    } while (retryCount < 3 && staleElementException); // Retry up to 3 times for stale element exception
		
		    return currentUrl;
		}
		
		public static void navigateToURL(WebDriver driver, String URL)	{
			try {
				driver.get(URL);
				Log.message("Navigate to URL : " + URL);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public static String getText(WebDriver driver, WebElement element, String elementDescription, int... timeOutArray)	{
			int timeout = timeOutArray.length > 0 ? timeOutArray[0] : maxElementWait;
			String text="";
			int retryCount = 0;
		    boolean staleElementException = false;
		    int elementCount = 0;

		    do {
		        try {
		            // Wait for the parent element to be present
		            if (!WaitUtils.waitForElement(driver, element, timeout)) {
		                throw new NoSuchElementException(elementDescription + NOT_FOUND_IN_PAGE);
		            }

		            text = element.getText();
		            if(!text.isEmpty()) {
		            	Log.message("Found " + elementCount + " " + elementDescription);
		            	break;
		            } else {
		            	Log.failsoft("Found " + elementCount + " " + elementDescription);
		            	break;
		            }

		        } catch (NoSuchElementException e) {
		            Log.failsoft("Failed to find elements " + elementDescription + " " + e);
		            break; // Exit the loop if no elements are found

		        } catch (StaleElementReferenceException e) {
		            Log.message("getObjectCount - Stale element exception");
		            staleElementException = true;
		            retryCount++; // Increment the retry count for stale element exception

		        } catch (Exception e) {
		            Log.failsoft("An unexpected error occurred while counting elements " + elementDescription + " " + e);
		            break; // Exit the loop on any other unexpected exception

		        }
		    } while (retryCount < 3 && staleElementException); // Retry up to 3 times for stale element exception

		    return text;
		}
		
		
		public static String takeScreenshot(WebDriver driver, String FolderPath, String ScreenshotName) {
			String screenShotFolderPath = Paths.get(System.getProperty("user.dir"), "Data").toString();
		    String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		    String screenshotFileName = "Screenshot_" + timestamp + ".png";
		    String screenshotPath = screenShotFolderPath + File.separator + screenshotFileName;
		    
		    // Take screenshot
		    File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		    
		    try {
		        // Save screenshot to file
		        FileUtils.copyFile(screenshot, new File(screenshotPath));
		        // Log the screenshot capture action
		        Log.message("Screenshot taken: " + screenshotFileName);
		    } catch (IOException e) {
		        // Log the error if screenshot saving fails
		        Log.message("Error saving screenshot: " + e.getMessage());
		    }
		    
		    return screenshotFileName;
		}
		
		/**
		 * Switches to the specified frame using the given WebElement
		 * 
		 * @param driver
		 * @param element
		 * @throws Exception
		 */
		public static void swichWindow(WebDriver driver, WebElement element) throws Exception{
			try {
				TargetLocator target = driver.switchTo();
				target.frame(element);
			}catch(NoSuchElementException e) {
				Log.failsoft("Failed to switch to the element. Frame element: " + element);
				e.printStackTrace();
			}catch(Exception e) {
				Log.failsoft("An unexpected error occurred while switching to the frame" + element);
				e.printStackTrace();
			}
		}
		
		/**
		 * Switches to the specified frame using the given index
		 * 
		 * @param driver
		 * @param element
		 * @throws Exception
		 */
		public static void swichWindow(WebDriver driver, String Index) throws Exception{
			try {
				TargetLocator target = driver.switchTo();
				target.frame(Integer.parseInt(Index));
			}catch(NoSuchElementException e) {
				Log.failsoft("Failed to switch to the element. Frame index: " + Index);
				e.printStackTrace();
			}catch(Exception e) {
				Log.failsoft("An unexpected error occurred while switching to the frame");
				e.printStackTrace();
			}
		}
				    
}

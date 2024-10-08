package com.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.test.utils.Log;
import com.test.utils.PropertiesReader;
import com.test.utils.web.CommonActions;
import com.test.utils.web.PageObjectRegistry;
import com.test.utils.web.WebUtils;

public class PG_Login {
	
	// WebDriver instance to interact with the browser
	private WebDriver driver;
	private CommonActions commonActions;
	
	// Locator strings fetched from properties
    private By usernameXpath = By.xpath(PropertiesReader.get("username_xpath"));
    private By passwordXpath = By.xpath(PropertiesReader.get("password_xpath"));
    private By loginButtonXpath = By.xpath(PropertiesReader.get("login_button_xpath"));
	
	/**
	 * Constructor for PG_Login
	 * 
	 * @param driver - - WebDriver instance used to interact with the browser
	 * Initializes web elements and registers this page object with PageObjectRegistry.
	 */
	public PG_Login(WebDriver driver) {
		this.driver=driver;
		this.commonActions = new CommonActions(driver);
		// Register the page object for future reference and reflection
        PageObjectRegistry.registerPageObject(this);
	}
	
	/**
     * Performs login to the application
     * 
     * @param AppURL - URL of the application to login
     * @param prm_Username - Username for login
     * @param prm_Password - Password for login
     */
	
	public void Login(String appURL, String username, String password) {
        driver.get(appURL);
        commonActions.type(usernameXpath, username, "Username");
        commonActions.type(passwordXpath, password, "Password");
        commonActions.click(loginButtonXpath, "Sign In Button");
    }
    
//	public void Login(String AppURL, String prm_Username, String prm_Password) {
//		try {
//		Log.message("Start of bc_Login");
//		driver.get(AppURL);
//		commonActions.type(usernameXpath, prm_Username, "Username");
//		Thread.sleep(1000);
//		commonActions.type(passwordXpath, prm_Password, "Password");
//        Thread.sleep(1000);
//        commonActions.click(loginButtonXpath, "Sign In Button");
//        Thread.sleep(1000);
//		Log.message("End of bc_Login");
//		} catch (InterruptedException e) {
//			Log.printExceptionDetails(e, this.getClass().getName(), driver);
//		}
//	}
}

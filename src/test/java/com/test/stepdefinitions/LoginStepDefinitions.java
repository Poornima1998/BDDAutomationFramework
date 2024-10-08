package com.test.stepdefinitions;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;

import com.test.pages.PG_Login;
import com.test.utils.web.WebDriverFactory;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class LoginStepDefinitions {
	    private WebDriver driver;
	    private PG_Login pg_Login;

	    @Given("I open the login page at {string}")
	    public void i_open_the_login_page_at(String appUrl) throws MalformedURLException {
	        driver = WebDriverFactory.get();
	        driver.get(appUrl);
	        pg_Login = new PG_Login(driver);
	    }

	    @When("I enter the username {string} and password {string}")
	    public void i_enter_the_username_and_password(String username, String password) {
	        pg_Login.Login(driver.getCurrentUrl(), username, password);
	    }

	    @And("I click the login button")
	    public void i_click_the_login_button() {
	        // Login button click is part of the PG_Login class
	    }

	    @Then("I should be logged in successfully")
	    public void i_should_be_logged_in_successfully() {
	        // Add assertion logic here for successful login, like checking the next page title or URL
	    }
	}


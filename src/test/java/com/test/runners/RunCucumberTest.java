package com.test.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features", // Path to the feature files
    glue = {"com.test.stepdefinitions"}, // Path to the step definition files
    plugin = {"pretty", "html:target/cucumber-reports.html"}, // Reports generation
    monochrome = true
)
public class RunCucumberTest extends AbstractTestNGCucumberTests {
    // This class will be empty and will just use annotations
}

Feature: Login to SauceDemo

  Scenario Outline: Login to the SauceDemo site with valid credentials
    Given I open the login page at "<AppURL>"
    When I enter the username "<Username>" and password "<Password>"
    And I click the login button
    Then I should be logged in successfully

    Examples:
      | AppURL                        | Username      | Password     |
      | https://www.saucedemo.com/     | standard_user | secret_sauce |


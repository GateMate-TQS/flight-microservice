package testweb.cucumber;

import java.time.Duration;

import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;

import io.cucumber.java.en.When;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import io.github.bonigarcia.wdm.WebDriverManager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.*;
import testweb.pages.IndexPage;
import testweb.pages.LoginPage;
import testweb.pages.AllFlightsPage;
import testweb.pages.FlightTrackerPage;
import testweb.pages.FlightDetailsPage;
import testweb.pages.PurchasePage;
import testweb.pages.UserTicketsPage;
import testweb.pages.AdminPage;
import java.util.concurrent.TimeUnit;
import static org.hamcrest.CoreMatchers.is;


@ExtendWith(SeleniumJupiter.class)
public class FlightSteps {
    private WebDriver driver;
    private WebDriverWait wait;
    private IndexPage indexPage;
    private LoginPage loginPage;
    private AllFlightsPage allFlightsPage;
    private FlightTrackerPage flightTrackerPage;
    private FlightDetailsPage flightDetailsPage;
    private PurchasePage purchasePage;
    private AdminPage adminPage;
    private UserTicketsPage userTicketsPage;

    private String pass = "";

    @Before
    public void setUp() {
        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.setAcceptInsecureCerts(true);
            driver = new ChromeDriver(options);
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize WebDriver: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("the user is on the homepage")
    public void the_user_is_on_the_homepage() {
        indexPage = new IndexPage(driver);

        assertEquals("http://localhost/", driver.getCurrentUrl());

    }

    @When("the user clicks on the login button")
    public void the_user_clicks_on_the_login_button() {
        loginPage = indexPage.clickLogin();
    }

    @Then("the user is redirected to the login page")
    public void the_user_is_redirected_to_the_login_page() {
        assertEquals("http://localhost/login", driver.getCurrentUrl());
        loginPage = new LoginPage(driver);
    }

    @Given("the user enters {string} as email")
    public void the_user_enters_as_email(String email) {
        loginPage.enterUsername(email);
    }
    
    @Given("the user enters {string} as password")
    public void the_user_enters_as_password(String password) {
        loginPage.enterPassword(password);
    }



    @When("the user clicks to login")
    public void the_user_clicks_on_the_submit_button_login() {
        try {
            loginPage.submit();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("the user loggedin and is redirected to the homepage")
    public void the_user_is_redirected_to_the_homepage() {
    
        assertEquals("http://localhost/", driver.getCurrentUrl());
        assertTrue(indexPage.isLoggedAsUser());
    }
 

    @Then("the user loggedin and is redirected to the admin homepage")
    public void the_user_is_redirected_to_the_admin_homepage() {
        assertEquals("http://localhost/", driver.getCurrentUrl());

        assertTrue(indexPage.isLoggedAsAdmin());
    }

    @When("the user clicks on the all flights button")
    public void the_user_clicks_on_the_all_flights_button() {
        allFlightsPage = indexPage.clickAllFlights();
    }

    @Then("the user is redirected to the all flights page")
    public void the_user_is_redirected_to_the_all_flights_page() {
        assertEquals("http://localhost/allflights", driver.getCurrentUrl());
    }

    @Given("the first flight")
    public void the_fist_flight() {

        assertTrue(allFlightsPage.isAflight());
    }

    @When("the user clicks on the details of the first flight")
    public void the_user_clicks_on_the_details() {
        flightDetailsPage = allFlightsPage.clicksOnFirstFlight();
    }

    @Then("the user is redirected to the details page of the flight")
    public void the_user_is_redirected_to_the_details_page_of_the_flight() {
        assertEquals("http://localhost/flightInfo/TK8104", driver.getCurrentUrl());
    }

    @Given("the user selects {string} as the from location")
    public void the_user_selects_as_the_from_location(String from) {
        allFlightsPage.filterTheFromLocation(from);
    }
    
    @Given("the user wants to purchase the flight")
    public void the_user_tries_to_purchase_the_flight() {
        assertTrue(true);
        
    }

    @Given("the user in his tickers page")
    public void the_user_in_his_tickets_page() {
        assertTrue(true);
    }

    @When("the user clicks in the purchase button")
    public void the_user_clicks_in_the_purchase_ticket_button() {
        flightDetailsPage.clickPurchase();
    }

    @Then("the user is redirected to the ticket purchase page") 
    public void the_user_is_redirected_to_the_ticket_purchase_page() {
        assertEquals("http://localhost/ticketPurchase/TK8104", driver.getCurrentUrl());
        purchasePage = new PurchasePage(driver);
    }

    @Given("the user enters {string} as name on the purchase form")
    public void the_user_enters_as_name_on_the_purchase_form(String name) {
        purchasePage.enterName(name);
    }

    @Given("the user enters {string} as email on the purchase form")
    public void the_user_enters_as_email_on_the_purchase_form(String email) {
        purchasePage.enterEmail(email);
    }

    @Given("the user enters {string} as cc Number on the purchase form")
    public void the_user_enters_as_cc_number_on_the_purchase_form(String ccNumber) {
        purchasePage.enterCcNumber(ccNumber);
    }

    @Given("the user enters {string} as credit card Number on the purchase form")
    public void the_user_enters_as_credit_card_number_on_the_purchase_form(String ccNumber) {
        purchasePage.enterCreditCardNumber(ccNumber);
    }

    @Given("the user selects {string} in the card type on the purchase form")
    public void the_user_selects_as_card_type_on_the_purchase_form(String cardType) {
        purchasePage.selectCardType(cardType);
    }

    @Given("the user enters {string} as month on the purchase form")
    public void the_user_enters_as_month_on_the_purchase_form(String month) {
        purchasePage.enterMonth(month);
    }

    @Given("the user enters {string} as year on the purchase form")
    public void the_user_enters_as_year_on_the_purchase_form(String year) {
        purchasePage.enterYear(year);
    }

    @Given("the user enters {string} as name of card on the purchase form")
    public void the_user_enters_as_name_on_card_on_the_purchase_form(String name) {
        purchasePage.enterNameOnCard(name);
    }

    @When("the user clicks in the final purchase button")
    public void the_user_clicks_in_the_final_purchase_ticket_button() {
        try {
            userTicketsPage = purchasePage.clickfinalPurchase();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("the user is redirected to his tickets page")
    public void the_user_is_redirected_to_his_tickets_page() {
        assertEquals("http://localhost/UserTickets", driver.getCurrentUrl());
         
    }

    @Given("the admin is in the admin page")
    public void the_admin_in_his_tickets_page() {
        assertTrue(true);
    }


    @When("the admin clicks in his icon")
    public void the_user_clicks_in_his_icon() {
        indexPage.clickintheIcon();
    }


    @Then("the admin clicks in the Admin Page button")
    public void the_flight_is_checked_in() {
        adminPage = indexPage.clickAdminPage();
    }

    @When("the admin is redirected to the admin page")
    public void the_user_is_redirected_to_the_admin_page() {
        assertEquals("http://localhost/admin", driver.getCurrentUrl());
    }

    @Given("the admin wants to check in a ticket")
    public void the_user_wants_to_check_in_a_ticket() {
        assertTrue(true);
    }

    @When("the admin clicks in the checkin button")
    public void the_admin_clicks_in_the_check_in_button() {
        adminPage.clickCheckin();
    }

    @Then("the admin has access to the forms") 
    public void the_admin_has_access_to_the_forms()
    {
        assertTrue(adminPage.isCheckinForm());
    }

    @Given("the admin enters {string} as ticket id on the checkin form")
    public void the_admin_enters_as_ticket_id_on_the_checkin_form(String ticketid) {
        adminPage.enterticketId(ticketid);
    }

    @Given("the admin enters {string} as name on the checkin form")
    public void the_admin_enters_as_name_on_the_checkin_form(String name) {
        adminPage.enterName(name);
    }

    @Given("the admin enters {string} as flight iata on the checkin form") 
    public void the_admin_enters_as_flight_iata_on_the_checkin_form(String flightiata) {
        adminPage.enterFlightiata(flightiata);
    }

    @Given("the admin enters {string} as number of bags on the checkin form")
    public void the_admin_enters_as_number_of_bags_on_the_checkin_form(String nrbags) {
        adminPage.enterNrbags(nrbags);
    }

    @Given("the admin enters {string} as weight of the bag on the checkin form")
    public void the_admin_enters_as_weight_of_bag_on_the_checkin_form(String weightBag) {
        adminPage.enterWeightBag(weightBag);
    }

    @When("the admin clicks in the submit button")
    public void the_admin_clicks_in_the_submit_button() {
        adminPage.submitCheckin();
    }


    @Then("the checkin is completed successfully")
    public void the_checkin_is_completed() {
        assertThat(driver.switchTo().alert().getText(), is("Check-in successful!"));
    }

    @When("the user clicks in the check in button")
    public void the_user_clicks_in_the_check_in_button() {
        try {
            userTicketsPage.clickFirstFlight();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("the flight is checked in")
    public void the_user_is_redirected_to_the_checkin_page() {
        assertTrue(userTicketsPage.isCheckedIn());
    }

}

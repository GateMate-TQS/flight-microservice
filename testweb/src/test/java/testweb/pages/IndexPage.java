package testweb.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class IndexPage {

    @FindBy(linkText = "Login")
    private WebElement loginButton;

    private WebDriver driver;

    @FindBy(linkText="All Flights")
    private WebElement allflightsButton;

    @FindBy(linkText="Flight Tracker")
    private WebElement flighttrackerButton;

    public IndexPage(WebDriver driver) {
        String url = "http://localhost/";
        driver.get(url);
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }


    public LoginPage clickLogin() {
        loginButton.click();
        return new LoginPage(driver);
        
    }

    public AllFlightsPage clickAllFlights() {
        allflightsButton.click();
        return new AllFlightsPage(driver);
    }   

    public FlightTrackerPage clickFlightTracker() {
        flighttrackerButton.click();
        return new FlightTrackerPage(driver);
    }


    public boolean isLoggedAsAdmin(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); 

        try {
            WebElement user_photo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='User']")));
            user_photo.click();

            WebElement mytickets = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='root']/div/div/div/div[2]/div/div[3]/div/a[2]/button")));
          
            return true;

        } catch (Exception e) {
            return false;
        }
    }
     
    public boolean isLoggedAsUser(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); 

        try {
            WebElement user_photo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='User']")));
            user_photo.click();

            WebElement mytickets = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(.,'My Tickets')]")));
            mytickets.click();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public void clickintheIcon(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); 

        WebElement user = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='User']")));
        user.click();
    }

    public AdminPage clickAdminPage(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); 

        WebElement admin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(.,'Admin Page')]")));
        admin.click();
        return new AdminPage(driver);
    }



}
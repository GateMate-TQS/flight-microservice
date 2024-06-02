package testweb.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class AllFlightsPage {

    private WebDriver driver;

    @FindBy(id="flightIata")
    private WebElement flightIata;

    @FindBy(xpath="//div[@id='root']/div/div[2]/div[2]/div[2]/div[2]/select")
    private WebElement fromDropdown;


    @FindBy(xpath="//div[@id='root']/div/div[2]/div[2]/div[2]/div[3]/select)")
    private WebElement toDropdown;

    @FindBy(xpath="//div[4]/select")
    private WebElement companyDropdown;

    @FindBy(xpath="//div[5]/button")
    private WebElement searchButton;

    @FindBy(xpath="//div[6]/button")
    private WebElement clearFilters;

    private WebElement firstFlight;

    public AllFlightsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public boolean isAflight() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); 

        firstFlight = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='root']/div/div[2]/div[2]/div[3]/a/div/div/div/h3")));
        System.out.println("First flight: " + firstFlight.getText());
        return firstFlight.isDisplayed();
    }

    public FlightDetailsPage clicksOnFirstFlight() {

        firstFlight.click();
        return new FlightDetailsPage(driver);
    }

    public void filterTheFromLocation(String from) {
        try {
            // Sleep for 2 seconds (2000 milliseconds) to wait for the value
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        if (!getSelectedFrom().equals(from)) {
            Select dropdown = new Select(fromDropdown);
            dropdown.selectByVisibleText(from);
        }
    }

    public String getSelectedFrom() {
        Select dropdown = new Select(fromDropdown);
        return dropdown.getFirstSelectedOption().getText();
    }

}
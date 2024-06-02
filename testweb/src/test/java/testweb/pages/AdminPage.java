package testweb.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AdminPage {

    private WebDriver driver;

    @FindBy(xpath="//div[@id='root']/div/div/div/div/div[2]/button")
    private WebElement checkinButton;

    private WebElement checkinSubmitForm;

    private WebElement ticketidField;

    private WebElement name;

    private WebElement email;

    private WebElement flightiata;

    private WebElement nrbags;

    private WebElement weightBag;


    public AdminPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void clickCheckin() {
        checkinButton.click();
    }

    public boolean isCheckinForm() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            checkinSubmitForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='root']/div/div[2]/div/div/form/button")));
            
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void enterticketId(String ticketid) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        ticketidField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ticketid")));

        ticketidField.sendKeys(ticketid);
    }

    public void enterName(String name_) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        name = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));

        name.sendKeys(name_);
    }

    public void enterFlightiata(String iata_) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        flightiata = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flightIata")));

        flightiata.sendKeys(iata_);
    }

    public void enterNrbags(String nrbags_) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        nrbags = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("numberOfBags")));

        nrbags.sendKeys(nrbags_);
    }

    public void enterWeightBag(String weightBag_) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        weightBag = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("bagWeight1")));

        weightBag.sendKeys(weightBag_);
    }

    public void submitCheckin() {
        checkinSubmitForm.click();
    }

}

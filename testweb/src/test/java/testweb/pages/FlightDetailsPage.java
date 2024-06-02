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


public class FlightDetailsPage {

    private WebDriver driver;

    @FindBy(xpath="//div[@id='root']/div/div[2]/div[2]/div[4]/a/button")
    private WebElement purchaseButton;

    public FlightDetailsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

  
    public void clickPurchase() {
        purchaseButton.click();
    }
}
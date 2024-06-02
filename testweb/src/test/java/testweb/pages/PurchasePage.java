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


public class PurchasePage {

    private WebDriver driver;

    @FindBy(xpath="//div[@id='root']/div/div[2]/div[2]/div[4]/a/button")
    private WebElement purchaseButton;

    @FindBy(id="name")
    private WebElement nameField;

    @FindBy(id="email")
    private WebElement emailField;

    @FindBy(id="ccNumber")
    private WebElement ccNumberField;

    @FindBy(id="cardType")      
    private WebElement cardTypeFieldDropdown;

    @FindBy(id="creditCardNumber")
    private WebElement creditCardNumberField;

    @FindBy(id="month")
    private WebElement monthField;

    @FindBy(id="year")
    private WebElement yearField;

    @FindBy(id="nameOnCard")
    private WebElement nameOnCardField;

    @FindBy(xpath="//button[contains(.,'Purchase Ticket')]")
    private WebElement purchaseTicketButton;

    public PurchasePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
  
    public UserTicketsPage clickfinalPurchase() {
        purchaseTicketButton.click();
        UserTicketsPage userTicketsPage = new UserTicketsPage(driver);

        return userTicketsPage;
    }

    public void enterName(String name) {
        nameField.sendKeys(name);
    }

    public void enterEmail(String email) {
        emailField.sendKeys(email);
    }

    public void enterCcNumber(String ccNumber) {
        ccNumberField.sendKeys(ccNumber);
    }

    public void selectCardType(String cardType) {
        Select dropdown = new Select(cardTypeFieldDropdown);
        dropdown.selectByVisibleText(cardType);
    }
    
    public void enterCreditCardNumber(String creditCardNumber) {
        creditCardNumberField.sendKeys(creditCardNumber);
    }

    public void enterMonth(String month) {
        monthField.sendKeys(month);
    }

    public void enterYear(String year) {
        yearField.sendKeys(year);
    }

    public void enterNameOnCard(String nameOnCard) {
        nameOnCardField.sendKeys(nameOnCard);
    }


}
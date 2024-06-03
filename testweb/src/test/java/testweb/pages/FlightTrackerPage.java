package testweb.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;


public class FlightTrackerPage {

    private WebDriver driver;


    @FindBy(id="flightIata")
    private WebElement flightIata_;

    @FindBy(xpath="//div[@id='root']/div/div[2]/div/div[3]/div/div[2]/select")
    private WebElement companyDropdown;

    @FindBy(xpath="//div[4]/div/div[2]/select")
    private WebElement fromDropdown;

    @FindBy(xpath="//div[5]/div/div[2]/select")
    private WebElement toDropdown;

    @FindBy(xpath="//button[contains(.,'Apply filters')]")
    private WebElement applyFiltersButton;


    public FlightTrackerPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

}   
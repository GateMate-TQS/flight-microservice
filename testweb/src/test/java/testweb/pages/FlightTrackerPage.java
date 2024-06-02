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

    public void enterFlightIata(String flightIata) {
        flightIata_.sendKeys(flightIata);
    }

    public void selectCompany(String company) {
        System.out.println("Selected company: " + getSelectedCompany());

        if (!getSelectedCompany().equals(company)) {
            Select dropdown = new Select(companyDropdown);
            dropdown.selectByVisibleText(company);
        }

    }
    public String getSelectedCompany() {
        Select dropdown = new Select(companyDropdown);
        return dropdown.getFirstSelectedOption().getText();
    }

    public void selectFrom(String from) {
        System.out.println("Selected from: " + getSelectedFrom());

        if (!getSelectedFrom().equals(from)) {
            Select dropdown = new Select(fromDropdown);
            dropdown.selectByVisibleText(from);
        }

    }
    public String getSelectedFrom() {
        Select dropdown = new Select(fromDropdown);
        return dropdown.getFirstSelectedOption().getText();
    }

    public void selectTo(String to) {
        System.out.println("Selected to: " + getSelectedTo());

        if (!getSelectedTo().equals(to)) {
            Select dropdown = new Select(toDropdown);
            dropdown.selectByVisibleText(to);
        }

    }

    public String getSelectedTo() {
        Select dropdown = new Select(toDropdown);
        return dropdown.getFirstSelectedOption().getText();
    }

}   
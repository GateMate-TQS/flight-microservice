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
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class UserTicketsPage {

    private WebDriver driver;

    private WebElement firstFlight;

    public UserTicketsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void clickFirstFlight() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); 

        firstFlight = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='root']/div/div[2]/div/div/div/div[2]/button")));
        
        firstFlight.click();
    }

    public boolean isCheckedIn() {
        // System.out.println("Checked in: " + firstFlight.getText());
        // System.out.println("Checked in: " + firstFlight.getCssValue("background-color"));
        // String bgcolor = firstFlight.getCssValue("background-color");
        // if (bgcolor.equals("rgba(29, 78, 216, 1)")) {
        //     return true;
        // }

        // return false;

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement firstFlight = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='root']/div/div[2]/div/div/div/div[2]/div/div[2]")));

            System.out.println("Checked in: " + firstFlight.getText());
            System.out.println("Checked in: " + firstFlight.getCssValue("background-color"));

            String bgcolor = firstFlight.getCssValue("background-color");
            return bgcolor.equals("rgba(34, 197, 94, 1)");
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            return false;
        }

    }

}
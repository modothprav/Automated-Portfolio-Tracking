package yahooFinance;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;

public class YahooLogin extends BasePage {
    
    // Object Repository
    @FindBy(id = "login-username")
    private WebElement usernameField;

    @FindBy(id = "login-passwd")
    private WebElement passwordField;

    @FindBy(id = "login-signin")
    private WebElement nextButton;

    @FindBy(id = "persistent")
    private WebElement sessionCheckBox;

    /**
     * Constructor
     */
    public YahooLogin() {
        if (!driver.getTitle().equals("Yahoo")) {
            throw new IllegalStateException("This is not the Yahoo Login Page, current Page is " 
            + driver.getCurrentUrl()); 
        }

        PageFactory.initElements(driver, this);
    }

    private void enterUsername(String username) {
        this.usernameField.sendKeys(username);
    }

    private void enterPassword(String password) {
        this.passwordField.sendKeys(password);
    }

    public void clickCheckBox() {
        this.sessionCheckBox.click();
    }

    private void clickNextButton() {
        this.nextButton.click();
    }

    public YahooHome login() {
        Properties credentials = new Properties();
        try {
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir").toString() + config.getProperty("credentials.file"));
            credentials.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Enter username
        this.enterUsername(credentials.getProperty("yahoo.username"));
        this.clickNextButton();

        // Enter password
        this.enterPassword(credentials.getProperty("yahoo.password"));
        this.clickNextButton();

        return new YahooHome();
    }


    
}

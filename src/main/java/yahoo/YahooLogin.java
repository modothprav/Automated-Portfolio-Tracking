package yahoo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    /**
     * Toggles the 'Stay Signed in' checkbox
     */
    public void clickCheckBox() {
        this.sessionCheckBox.click();
    }

    /**
     * Click the 'Next' button in the Yahoo login page
     */
    private void clickNextButton() {
        this.nextButton.click();
    }

    /**
     * Load the credentials properties file and uses the values within
     * to enter the username and password and log into the user's Yahoo
     * Account. Returns a Yahoo Home page object.
     * @return YahooHome
     */
    public YahooHome login() {
        String username = System.getenv("YAHOO_USERNAME");
        String password = System.getenv("YAHOO_PASSWORD");

        // Set values from a properties file
        if (username == null || password == null) {
            Properties credentials = new Properties();
            try {
                FileInputStream ip = new FileInputStream(System.getProperty("user.dir").toString() + config.getProperty("credentials.file"));
                credentials.load(ip);

                username = credentials.getProperty("yahoo.username");
                password = credentials.getProperty("yahoo.password");

                credentials.clear();
                System.out.println("Read credentials from Properties file");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Enter username
        this.usernameField.sendKeys(username);;
        username = "";
        this.clickNextButton();

        // Enter password
        this.passwordField.sendKeys(password);;
        password = "";
        this.clickNextButton();

        String pageTitle = "Yahoo | Mail, Weather, Search, Politics, News, Finance, Sports & Videos";
        new WebDriverWait(driver, 8).until(ExpectedConditions.titleIs(pageTitle));
        return new YahooHome();
    }
    
}

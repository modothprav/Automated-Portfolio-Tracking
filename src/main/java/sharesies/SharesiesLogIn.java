package sharesies;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;

public class SharesiesLogIn extends BasePage {

    // Object Reposotiry
    @FindBy(name = "email")
    private WebElement emailField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(xpath = "//button[text()='Log in']")
    private WebElement logInButton;

    /**
     * Constructor
     */
    public SharesiesLogIn() {
        if (!driver.getTitle().equals("Sharesies")) {
            throw new IllegalStateException("This is not the Sharesies Home Page, current Page is " 
            + driver.getCurrentUrl()); 
        }

        PageFactory.initElements(driver, this);
    }

    /**
     * Obtains the necessary credentials required to log into the user's 
     * account. Then enters the Username, password and clicks the Log In 
     * button. Returns a Sharesies Application page.
     * 
     * @return SharesiesApp
     */
    public SharesiesApp logIn() {
        String username = System.getenv("SHARESIES_USERNAME");
        String password = System.getenv("SHARESIES_PASSWORD");

        // Set values from a properties file
        if (username == null || password == null) {
            Properties credentials = new Properties();
            try {
                // Load Credentials file
                String filePath = System.getProperty("user.dir").toString() + config.getProperty("credentials.file");
                FileInputStream ip = new FileInputStream(filePath);
                credentials.load(ip);

                // Set username and password
                username = credentials.getProperty("sharesies.username");
                password = credentials.getProperty("sharesies.password");

                credentials.clear();
                System.out.println("Read credentials from Properties file");
            } catch (IOException e) {
                e.printStackTrace();
            }  
        } 

        emailField.sendKeys(username);
        passwordField.sendKeys(password);
        // Clear values
        username = ""; password = "";

        logInButton.click();

        return new SharesiesApp();
    }
}

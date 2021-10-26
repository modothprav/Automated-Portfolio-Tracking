package sharesies;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        Properties credentials = new Properties();
        try {
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir").toString() + config.getProperty("credentials.file"));
            credentials.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        emailField.sendKeys(credentials.getProperty("sharesies.username"));
        passwordField.sendKeys(credentials.getProperty("sharesies.password"));
        
        credentials.clear();
        logInButton.click();

        return new SharesiesApp();
    }
}

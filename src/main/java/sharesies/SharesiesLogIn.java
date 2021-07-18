package sharesies;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;

public class SharesiesLogIn extends BasePage {
    
    private String USERNAME;
    private String PASSWORD;

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
     * Saves the Login credentials in the USERNAME and PASSWORD fileds.
     * The crednetials are read from a file whose file path is specified 
     * in config.properties.
     */
    private void getCredentials() {
        Path filePath = Paths.get(System.getProperty("user.dir").toString() + config.getProperty("credentials.file"));
        try {
            // Reads the file and slipts it into its username and password components
            String[] credentials = Files.readAllLines(filePath).get(2).split(",");
            
            this.USERNAME = credentials[0];
            this.PASSWORD = credentials[1];
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }

    /**
     * Obtains the necessary credentials required to log into the user's 
     * account. Then enters the Username, password and clicks the Log In 
     * button. Returns a Sharesies Application page.
     * 
     * @return SharesiesApp
     */
    public SharesiesApp logIn() {
        this.getCredentials();
        
        emailField.sendKeys(USERNAME);
        passwordField.sendKeys(PASSWORD);

        logInButton.click();
        return new SharesiesApp();
    }
}

package sharesies;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
     * Saves the Login credential in the USERNAME and PASSWORD fileds.
     * The crednetials are read from a file whose file path is specified 
     * in config.properties.
     */
    private void getCredentials() {
        Path filePath = Paths.get(config.getProperty("credentials.file"));
        try {
            String[] credentials = Files.readAllLines(filePath).get(2).split(",");
            this.USERNAME = credentials[0];
            this.PASSWORD = credentials[1];
        } catch (IOException e) {
            e.printStackTrace();
        }

        
    }
}

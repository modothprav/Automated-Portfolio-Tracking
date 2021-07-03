package sharesies;

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

    public SharesiesLogIn() {
        if (!driver.getTitle().equals("Sharesies")) {
            throw new IllegalStateException("This is not the Sharesies Home Page, current Page is " 
            + driver.getCurrentUrl()); 
        }

        PageFactory.initElements(driver, this);
    }
}

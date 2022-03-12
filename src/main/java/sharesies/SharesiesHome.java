package sharesies;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;

/**
 * SharesiesHomePage
 */
public class SharesiesHome extends BasePage {

    // Object Repository
    @FindBy(xpath = "//ul[contains(@class, 'Header_appList')]/li[contains(@class, 'Header_login')]")
    private WebElement loginButton;

    /**
     * Constructor
     */
    public SharesiesHome() {
        if (!driver.getTitle().equals("Sharesies New Zealand | Shares made easy")) {
            throw new IllegalStateException("This is not the Sharesies Home Page, current Page is " 
            + driver.getCurrentUrl()); 
        }
        PageFactory.initElements(driver, this);
    }

    /**
     * Clicks the Log In Button on the Sharesies Home page.
     * Returns a Sharesies Login Page
     * 
     * @return SharesiesLogIn
     */
    public SharesiesLogIn clickLoginButton() {
        loginButton.click();
        return new SharesiesLogIn();
    }
}
package yahoo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BasePage;

public class YahooHome extends BasePage{

    // Object Repository
    @FindBy(id = "root_3")
    private WebElement financeLink;

    @FindBy(id = "profile-signout-link")
    private WebElement signOutLink;

    @FindBy(id = "ybarAccountMenuOpener")
    private WebElement profileButton;

    @FindBy(xpath = "//*[text()='Sign in']")
    private WebElement signInButton;

    /**
     * Constructor
     */
    public YahooHome() {
        PageFactory.initElements(driver, this);
    }

    /**
     * Clicks the 'Finance' link available at the navigaiton bar on 
     * the Yahoo Home page. Returns a YahooFinance page object.
     * @return YahooFinance
     */
    public YahooFinance goToYahooFinance() {
        this.financeLink.click();
        String pageTitle = "Yahoo Finance - Stock Market Live, Quotes, Business & Finance News";
        new WebDriverWait(driver, 20).until(ExpectedConditions.titleIs(pageTitle));
        return new YahooFinance();
    }

    /**
     * Clicks the profile button on the Yahoo home page.
     * Is a helper method for signing out a user.
     */
    private void clickProfile() {
        this.profileButton.click();
    }

    /**
     * Clicks the Sign out button from the profile dropdown menu.
     */
    private void clickSignOut() {
        this.signOutLink.click();
    }

    /**
     * Logs out a user from their Yahoo account. Uses helper
     * methods and returns a Yahoo home page for an 
     * unauthenticated user.
     * @return YahooHome
     */
    public YahooHome signOut() {
        this.clickProfile();
        this.clickSignOut();
        return new YahooHome();
    }

    /**
     * Clicks the Sign in button on the Yahoo Home page.
     * Will take you to the login page and return a YahooLogin
     * page object. Note: this is only available for 
     * unauthenticated users.
     * @return YahooLogin
     */
    public YahooLogin clickSignIn() {
        signInButton.click();
        return new YahooLogin();
    }
    
    
}

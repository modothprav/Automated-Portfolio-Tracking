package yahoo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

    public YahooFinance goToYahooFinance() {
        this.financeLink.click();
        return new YahooFinance();
    }

    private void clickProfile() {
        this.profileButton.click();
    }

    private void clickSignOut() {
        this.signOutLink.click();
    }

    public YahooHome signOut() {
        this.clickProfile();
        this.clickSignOut();
        return new YahooHome();
    }

    public YahooLogin clickSignIn() {
        signInButton.click();
        return new YahooLogin();
    }
    
    
}

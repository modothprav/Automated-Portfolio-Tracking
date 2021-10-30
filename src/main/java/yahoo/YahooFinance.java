package yahoo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;

public class YahooFinance extends BasePage {
    
    // Object Repository
    @FindBy(xpath = "//a[@href='/portfolios']")
    private WebElement portfolioLink;

    @FindBy(id = "uh-profile")
    private WebElement profileButton;

    @FindBy(id = "uh-signedout")
    private WebElement signOutButton;

    @FindBy(id = "uh-signedin")
    private WebElement signInButton;

    public YahooFinance() {
        if (!driver.getTitle().equals("Yahoo Finance – stock market live, quotes, business & finance news")) {
            throw new IllegalStateException("This is not the Yahoo Finance Page, current Page is " 
            + driver.getCurrentUrl()); 
        }

        PageFactory.initElements(driver, this);
    }

    public void goToPortfolioPage() {
        this.portfolioLink.click();
    }

    private void clickProfile() {
        this.profileButton.click();
    }

    private void clickSignOut() {
        this.signOutButton.click();
    }

    public YahooHome signOut() {
        this.clickProfile();
        this.clickSignOut();
        return new YahooHome();
    }


}

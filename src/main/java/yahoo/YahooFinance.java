package yahoo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BasePage;

public class YahooFinance extends BasePage {
    
    // Object Repository
    @FindBy(xpath = "//a[@href='/portfolios']")
    private WebElement portfolioLink;

    @FindBy(id = "header-profile-button")
    private WebElement profileButton;

    @FindBy(id = "profile-signout-link")
    private WebElement signOutButton;

    @FindBy(id = "uh-signedin")
    private WebElement signInButton;

    /**
     * Constructor
     */
    public YahooFinance() {
        if (!driver.getTitle().equals("Yahoo Finance - Stock Market Live, Quotes, Business & Finance News")) {
            throw new IllegalStateException("This is not the Yahoo Finance Page, current Page is " 
            + driver.getCurrentUrl()); 
        }

        PageFactory.initElements(driver, this);
    }

    /**
     * Clicks the Portfolio link availabe at the Yahoo finance
     * pages. Returns a page object model containing all portfolios
     * in the user's account.
     * @return YahooPortfolios
     */
    public YahooPortfolios goToPortfolioPage() {
        this.portfolioLink.click();
        String pageTitle = "Stock Portfolio & Tracker - Yahoo Finance";
        new WebDriverWait(driver, 8).until(ExpectedConditions.titleIs(pageTitle));
        return new YahooPortfolios();
    }

    /**
     * Clicks the Profile button for the current user. 
     * A helper method for signing out a user.
     */
    private void clickProfile() {
        this.profileButton.click();
    }

    /**
     * Clicks the sign out button available from the profile 
     * dropdown menu. A helper method for signing out a user.
     */
    private void clickSignOut() {
        this.signOutButton.click();
    }

    /**
     * Signs out a user from their yahoo account. Uses the two 
     * helper methods to perform this action. Returns a Yahoo 
     * home page object.
     * @return YahooHome
     */
    public YahooHome signOut() {
        this.clickProfile();
        this.clickSignOut();
        // Click Sign out confirmation button
        driver.findElement(By.xpath("//input[@data-logout='yes']")).click();
        
        String pageTitle = "Yahoo | Mail, Weather, Search, Politics, News, Finance, Sports & Videos";
        new WebDriverWait(driver, 8).until(ExpectedConditions.titleIs(pageTitle));
        return new YahooHome();
    }


}

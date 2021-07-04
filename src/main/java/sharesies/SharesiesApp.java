package sharesies;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;

public class SharesiesApp extends BasePage{
    
    // Object Repository
    @FindBy(xpath = "//*[text()='Invest']")
    private WebElement investButton;

    @FindBy(xpath = "//*[text()='Wallet']")
    private WebElement walletButton;

    @FindBy(xpath = "//*[text()='Share the aroha")
    private WebElement shareButton;

    @FindBy(xpath = "//*[text()='Settings']")
    private WebElement settingsButton;

    @FindBy(xpath = "//*[text()='Logout']")
    private WebElement logoutButton;

    /**
     * Constructor
     */
    public SharesiesApp() {
        if (!driver.getTitle().equals("Sharesies")) {
            throw new IllegalStateException("This is not the Sharesies Home Page, current Page is " 
            + driver.getCurrentUrl()); 
        }

        PageFactory.initElements(driver, this);
    }

    /**
     * Click the Settings button on the Sharesies Application page.
     * Once clicked will take you to the Settings Page.
     * 
     * @return SharesiesSettings
     */
    public SharesiesSettings clickSettings() {
        settingsButton.click();
        return new SharesiesSettings();
    }

    /**
     * Clicks the Logout button on the Sharesies Application page,
     * and logs out of the users account returning back to the 
     * Shareseies Login Page.
     * 
     * @return SharesiesLogIn
     */
    public SharesiesLogIn clickLogOut() {
        logoutButton.click();
        return new SharesiesLogIn();
    }


}

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
    @FindBy(xpath = "//nav[@id='mainNavigation']/div[@class='external']/a")
    private WebElement loginButton;

    /**
     * Constructor
     */
    public SharesiesHome() {
        if (!driver.getTitle().equals("Sharesies | Investing made easy")) {
            throw new IllegalStateException("This is not the Sharesies Home Page, current Page is " 
            + driver.getCurrentUrl()); 
        }
        PageFactory.initElements(driver, this);
    }

    /**
     * Clicks the Log In Button on the Sharesies Home page and switches to the
     * newly opened tab and closes the previous one. Returns a Sharesies Login Page
     * 
     * @return SharesiesLogIn
     */
    public SharesiesLogIn clickLoginButton() {
        String originalWindow = driver.getWindowHandle();
        
        loginButton.click();

        // Close and swticth the driver to the new tab
        driver.close();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        return new SharesiesLogIn();
    }
}
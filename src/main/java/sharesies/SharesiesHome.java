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

    public SharesiesHome() {
        if (!driver.getTitle().equals("Sharesies | Investing made easy")) {
            throw new IllegalStateException("This is not the Sharesies Home Page, current Page is " 
            + driver.getCurrentUrl()); 
        }
        PageFactory.initElements(driver, this);
    }

}
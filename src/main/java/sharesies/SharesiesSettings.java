package sharesies;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;

public class SharesiesSettings extends BasePage {
    
    // Object Repository
    @FindBy(xpath = "//*[text()='Reports']")
    private WebElement reportsButton;

    /**
     * Constructor
     */
    public SharesiesSettings() {
        if (!driver.getTitle().equals("Sharesies")) {
            throw new IllegalStateException("This is not the Sharesies Home Page, current Page is " 
            + driver.getCurrentUrl()); 
        }

        PageFactory.initElements(driver, this);
    }

    /**
     * Clicks the Reports button within the Settings page on 
     * Sharesies application, will then present the Reports Page.
     * 
     * @return SharesiesReports
     */
    public SharesiesReports clickReports() {
        reportsButton.click();
        return new SharesiesReports();
    }
    
}

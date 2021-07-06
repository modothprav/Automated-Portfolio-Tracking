package sharesies;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;

public class SharesiesReports extends BasePage {
    
    // Object Repository
    @FindBy(name = "from-month")
    private WebElement fromMonthField;

    @FindBy(name = "to-month")
    private WebElement toMonthField;

    @FindBy(name = "from-year")
    private WebElement fromYearField;

    @FindBy(name = "to-year")
    private WebElement toYearField;

    @FindBy(xpath = "//div[text()='Transaction report (CSV)']")
    private WebElement csvReport;

    @FindBy(xpath = "//button[text()='Export report']")
    private WebElement exportButton;


    public SharesiesReports() {
        if (!driver.getTitle().equals("Sharesies")) {
            throw new IllegalStateException("This is not the Sharesies Home Page, current Page is " 
            + driver.getCurrentUrl()); 
        }

        PageFactory.initElements(driver, this);
    }
}
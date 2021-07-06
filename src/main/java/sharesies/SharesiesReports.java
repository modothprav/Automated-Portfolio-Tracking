package sharesies;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

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


    /**
     * Constructor
     */
    public SharesiesReports() {
        if (!driver.getTitle().equals("Sharesies")) {
            throw new IllegalStateException("This is not the Sharesies Home Page, current Page is " 
            + driver.getCurrentUrl()); 
        }

        PageFactory.initElements(driver, this);
    }

    /**
     * Select the starting Month period of the transaction hisroty.
     * Specify the full month with the correct spelling starting 
     * with a capital letter e.g. January
     * 
     * @param month String which represents a month of the year
     */
    public void selectFromMonth(String month) {
        Select fromMonth = new Select(this.fromMonthField);
        fromMonth.selectByVisibleText(month);
    }
    
    /**
     * Select the ending Month period of the transaction hisroty.
     * Specify the full month with the correct spelling starting 
     * with a capital letter e.g. January
     * 
     * @param month String which represents a month of the year
     */
    public void selectToMonth(String month) {
        Select toMonth = new Select(this.toMonthField);
        toMonth.selectByVisibleText(month);
    }

    /**
     * Select the starting Year period of the transaction hisroty.
     * Specify the full year e.g. 2021
     * 
     * @param year String which represents the year
     */
    public void selectFromYear(String year) {
        Select fromYear = new Select(this.fromYearField);
        fromYear.selectByVisibleText(year);
    }

    /**
     * Select the ending Year period of the transaction hisroty.
     * Specify the full year e.g. 2021
     * 
     * @param year String which represents the year
     */
    public void selectToYear(String year) {
        Select toYear = new Select(this.toYearField);
        toYear.selectByVisibleText(year);
    }

    /**
     * Click the CSV Report export type on the reports page 
     */
    public void clickCSVReport() {
        this.csvReport.click();
    }

    /**
     * If the period infromation and report type have all been 
     * entered the export button can be clicked to export the 
     * requested report type within the given period. 
     */
    public void clickExport() {
        this.exportButton.click();
    }
}
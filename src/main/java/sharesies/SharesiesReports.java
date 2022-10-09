package sharesies;

import java.io.File;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

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
    private void selectFromMonth(String month) {
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
    private void selectToMonth(String month) {
        Select toMonth = new Select(this.toMonthField);
        toMonth.selectByVisibleText(month);
    }

    /**
     * Select the starting Year period of the transaction hisroty.
     * Specify the full year e.g. 2021
     * 
     * @param year String which represents the year
     */
    private void selectFromYear(String year) {
        Select fromYear = new Select(this.fromYearField);
        fromYear.selectByVisibleText(year);
    }

    /**
     * Select the ending Year period of the transaction hisroty.
     * Specify the full year e.g. 2021
     * 
     * @param year String which represents the year
     */
    private void selectToYear(String year) {
        Select toYear = new Select(this.toYearField);
        toYear.selectByVisibleText(year);
    }

    /**
     * Selects the start and end periods for month and year values 
     * period the transactions history should include.
     * @param fromMonth String The starting month e.g. "January"
     * @param toMonth String The ending month e.g. "October"
     * @param fromYear String The starting year e.g. "2021"
     * @param toYear String The ending year e.g. "2000"
     */
    private void enterReportDetails(String fromMonth, String toMonth, String fromYear, String toYear) {
        this.selectFromMonth(fromMonth);
        this.selectToMonth(toMonth);
        this.selectFromYear(fromYear);
        this.selectToYear(toYear);
    }

    /**
     * Click the CSV Report export type option on the reports page 
     */
    private void clickCSVReport() {
        this.csvReport.click();
    }

    /**
     * If the duration and report type have all been 
     * entered, the export button can be clicked to export the 
     * requested report type within the given period. 
     */
    private void clickExport() {
        this.exportButton.click();
    }

    /**
     * Using the helper methods enters the given time period values in
     * their designated fields on the web page. Then clicks the 'CSV Report'
     * option. Finally after all the required information is selected, the
     * export button is cliked. Once the button is clicked the WebDriver will
     * wait until it can confirm the file is downloaded in the path specifed
     * in the config.
     * 
     * @param fromMonth String The starting month e.g. "January"
     * @param toMonth String The ending month e.g. "October"
     * @param fromYear String The starting year e.g. "2021"
     * @param toYear String The ending year e.g. "2000"
     */
    public void downloadCSVReport(String fromMonth, String toMonth, String fromYear, String toYear) {
        // Enter and select required details
        this.enterReportDetails(fromMonth, toMonth, fromYear, toYear);
        this.clickCSVReport();
        this.clickExport();

        // Extract file name and folder path from the absolute file path
        String fileName = BasePage.config.getProperty("reports.csv.file");
        String folderPath = System.getProperty("user.dir");
        new WebDriverWait(driver, 15).until(new FileDownloaded(folderPath, fileName));
    }

    /**
     * A helper class which allows the creation of a custom ExpectedContiion
     * that can be used to prompt the WebDriver to wait until our custom
     * contition has been met. In this case the custom condition checks
     * whether the specfied file is present in the given folder and will return
     * true if present or false otherwise.
     */
    private class FileDownloaded implements ExpectedCondition {

        private String folderPath;
        private String filename;

        public FileDownloaded(String folderPath, String filename) {
            this.folderPath = folderPath;
            this.filename = filename;
        }

        @Override
        public Boolean apply(Object arg0) {
            File dir = new File(this.folderPath);
            File[] dirContents = dir.listFiles();

            for (int i = 0; i < dirContents.length; i++) {
                if (dirContents[i].getName().equals(this.filename)) {
                    // File has been found
                    return true;
                }
            }

            return false;
        }
    }
}
package yahoo;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;

import base.BasePage;

public class YahooPortfolioData extends BasePage {

    // Object Repository
    @FindBy(xpath = "//span[text()='My holdings']")
    private WebElement holdingsTab;

    /**
     * Constructor
     */
    public YahooPortfolioData() {
        PageFactory.initElements(driver, this);
    }

    /**
     * Clicks the holdings tab of the portfolio where,
     * all transactions can be viewed.
     */
    public void clickHoldingsTab() {
        this.holdingsTab.click();

        // Check if holdings page is loaded
        By checkHoldingPath = By.xpath("//div[@id='Lead-3-Portfolios-Proxy']");
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(checkHoldingPath));
    }

    /**
     * Clicks the dropdown button to view all individual
     * transactions for the current stock and returns true. If its the first transaction
     * to be entered for the stock, then the add lot button will be cliked
     * within the row hence performing a dropdown action and a false value will be returned. 
     * @param stockRow int Row number of the Stock to view transactions for
     * @return boolean True if the dropdown button is clicked false otherwise.
     */
    public boolean clickDropdown(int stockRow) {
        WebElement button;
        boolean result = true;
        try {
            button = driver.findElement(By.xpath("//table/tbody["+ stockRow +"]/tr[2]/td[1]/button"));
        } catch (NoSuchElementException e) {
            result = false;
            button = driver.findElement(By.xpath("//table/tbody[" + stockRow + "]/tr[2]/td[9]/button"));
        } 

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView();", button);
        executor.executeScript("arguments[0].click();", button);

        return result;
    }

    /**
     * Returns all of the Stock row elements in the portfolio
     * @return List<WebElement> A list of WebElement objects
     */
    public List<WebElement> getStockHoldings() {
        return driver.findElements(By.xpath("//table/tbody[*]"));
    }

    /**
     * Adds another individual transaction row to be filled in for 
     * a signle stock. Will wait until that element is loaded beofre
     * proceeding with execution. 
     * @param stockRow int The Row number of the stock to add another transaction for.
     */
    public void addLot(int stockRow) {
        // Element paths
        By transactions = By.xpath("//table/tbody["+ stockRow +"]/tr[3]/td/table/tbody/*");
        By buttonPath = By.xpath("//table/tbody["+ stockRow +"]/tr[3]/td/table/tbody/tr[last()]/td/button");

        // Add new transaction row
        int count = driver.findElements(transactions).size();
        WebElement addLotButton = driver.findElement(buttonPath);

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", addLotButton);

        // Check if button clicked and row loaded, if not try again
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.numberOfElementsToBe(transactions, count + 1));
        } catch (Exception e) {
            executor.executeScript("arguments[0].click();", addLotButton);
            new WebDriverWait(driver, 10).until(ExpectedConditions.numberOfElementsToBe(transactions, count + 1));
        }
        
    }

    /**
     * Enters the transaction date for a stock. The transaction date is 
     * entered in the final transaction row of a stock. Also checks if the date 
     * value has been entered correctly.
     * @param stockRow int The Row number of the stock to enter the transaction value for
     * @param date LocalDate The LocalDate object 
     */
    private void enterDate(int stockRow, LocalDate date) {
        By dateInputPath = By.xpath("//table/tbody[" + stockRow + "]/tr[3]/td/table/tbody/tr[last()-1]/td[1]/input[@type='date']");
        WebElement dateInput = driver.findElement(dateInputPath);

        String value = date.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        dateInput.sendKeys(value);
        dateInput.sendKeys(Keys.TAB);

        // The date value is saved in a different format
        String savedValue = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        By savedIconPath = By.xpath("//table/tbody["+ stockRow +"]/tr[3]/td/table/tbody/tr[last()]/td/span/span[text()='Saved']");
        new WebDriverWait(driver, 15).until(ExpectedConditions.textToBe(savedIconPath, "Saved"));

        // Check if the value has been entered in the specified field, if not then enter again
        try {
            new WebDriverWait(driver, 4).until(ExpectedConditions.attributeToBe(dateInput, "value", savedValue));
        } catch (Exception e) {
            System.out.println("Value not entered");
            dateInput.clear();
            dateInput.sendKeys(value);
            dateInput.sendKeys(Keys.TAB);

            // Wait for the entered values to be saved, then check again
            new WebDriverWait(driver, 15).until(ExpectedConditions.textToBe(savedIconPath, "Saved"));
            new WebDriverWait(driver, 4).until(ExpectedConditions.attributeToBe(dateInput, "value", savedValue));
        }
    }

    /**
     * Enters the number of shares bought in a single transaction. The shares 
     * value is entered in the final transaction row of the stock.
     * @param stockRow int The Row number of the stock to enter the transaction value for.
     * @param shares int The numner of shares
     */
    private void enterShares(int stockRow, double shares) {
        By numSharesPath = By.xpath("//table/tbody[" + stockRow + "]/tr[3]/td/table/tbody/tr[last()-1]/td[2]/input[@type='number']");
        WebElement sharesInput = driver.findElement(numSharesPath);
        sharesInput.sendKeys(String.valueOf(shares));

        this.checkEntry(sharesInput, String.valueOf(shares), stockRow);
        
    }

    /**
     * Enters the price paid for a single share of stock. This value 
     * is entered in the final transaction row of the stock.
     * @param stockRow int The Row number of the stock to enter the transaction value for.
     * @param price int The Price of a single share.
     */
    private void enterPrice(int stockRow, double price) {
        By pricePath = By.xpath("//table/tbody[" + stockRow + "]/tr[3]/td/table/tbody/tr[last()-1]/td[3]/input[@type='number']");
        WebElement priceInput = driver.findElement(pricePath);
        priceInput.sendKeys(String.valueOf(price));

        this.checkEntry(priceInput, String.valueOf(price), stockRow);
    }

    /**
     * Enters any additional notes for the transaction. The value is entered
     * in the final transaction row of the stock. 
     * @param stockRow int The Row number of the stock to enter the transaction value for
     * @param notes String Notes to add on the current transaction
     */
    private void enterNotes(int stockRow, String notes) {
        By notesPath = By.xpath("//table/tbody["+ stockRow +"]/tr[3]/td/table/tbody/tr[last()-1]/td[8]/input[@type='text']");
        WebElement notesInput = driver.findElement(notesPath);
        notesInput.sendKeys(notes);
        notesInput.sendKeys(Keys.SHIFT, Keys.TAB);
        this.checkEntry(notesInput, notes, stockRow);
    }

    /**
     * Checks whether if the given text has been entered into the given input element.
     * If the value in the input box doesn't match with the given text then the method
     * will try to enter in the text again. Finally will perform one last check 
     * and wait for the saved Icon to appear below the transaction row, before proceeding. 
     * @param inputBox WebElement The element being checked for
     * @param text String The text to be checked.
     * @param stockRow int The Row number of the stock for which the transaction value is entered.
     */
    private void checkEntry(WebElement inputBox, String text, int stockRow) {
        try {
            new WebDriverWait(driver, 4).until(ExpectedConditions.attributeToBe(inputBox, "value", text));
        } catch (Exception e) {
            System.out.println("Value not entered");
            inputBox.clear();
            inputBox.sendKeys(text);
            new WebDriverWait(driver, 4).until(ExpectedConditions.attributeToBe(inputBox, "value", text));
        }

        By savedIconPath = By.xpath("//table/tbody["+ stockRow +"]/tr[3]/td/table/tbody/tr[last()]/td/span/span[text()='Saved']");
        new WebDriverWait(driver, 15).until(ExpectedConditions.textToBe(savedIconPath, "Saved"));
    }
    /**
     * Enters all the Transactions values for the given Stock. The 
     * values include Date, number of shares, price and notes. These
     * values will be added to the final transaction row of the given stock.
     * @param stockRow int The Row number of the stock to enter the transaction values for
     * @param date String The raw value of the date to be entered without seperators 
     * e.g. 121020 is 12/10/20
     * @param shares int The numner of shares
     * @param price int The Price of a single share.
     * @param notes String any Notes to add on the current transaction
     */
    public void enterTransaction(int stockRow, LocalDate date, double shares, double price, String notes) {
        this.enterDate(stockRow, date);
        this.enterShares(stockRow, shares);
        this.enterPrice(stockRow, price);
        this.enterNotes(stockRow, notes);
    }

    /**
     * Deletes the final transaction row for the given stock.
     * @param stockRow int The Row number of the stock to delete the transaction for.
     */
    public void deleteTransaction(int stockRow) {
        By deletePath = By.xpath("//table/tbody["+ stockRow +"]/tr[3]/td/table/tbody/tr[last()-1]/td[last()]/button");
        WebElement deleteButton = driver.findElement(deletePath);
        deleteButton.click();
    }

    /**
     * Gets the row number by the given stock name in the holdings
     * table. If the stock is not found on the holdings table -1 is
     * returned.
     * @param name String The name of the stock.
     * @return int Row number of the stock in the holdings table.
     */
    public int getStockRow(String name) {
        List<WebElement> stocks = this.getStockHoldings();

        for (int i = 1; i < stocks.size() + 1; i++) {
            By namePath = By.xpath("//table/tbody["+ i +"]/tr[2]/td[2]/div/a");
            String stockName = driver.findElement(namePath).getText();
            if (stockName.equals(name)) { return i; }
        }
        return -1;
    }



}

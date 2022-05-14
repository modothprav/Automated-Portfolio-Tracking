package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BasePage {
    private final static String CONFIGPATH = "/src/main/java/config/config.properties";
    protected static WebDriver driver;
    public static Properties config;

    /**
     * Constructor
     */
    public BasePage(){
        // Read the config file into the properties variable
        try {
            BasePage.config = new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir").toString() + CONFIGPATH);
            BasePage.config.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();            
        }
    }

    /**
     * Initialize the Webdriver according to the specifications provided in the 
     * config.properties file. Deletes all cookies, sets the page load times
     * and opens the webpage with the desired url.
     */
    public void initialize(String configURL) {
        String driverPath = System.getProperty("user.dir") + config.getProperty("driver.chromedriver.path");
        System.setProperty("webdriver.chrome.driver", driverPath);
        
        // Initialize Chrome options
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--window-size=1580,1280");

        // Initialize Driver
        driver = new ChromeDriver(chromeOptions);

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get(config.getProperty(configURL));
    }

    /**
     * Closes the Web browser.
     */
    public void tearDown() {
        driver.quit();
    }
}

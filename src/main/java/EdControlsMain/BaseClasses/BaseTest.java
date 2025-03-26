package EdControlsMain.BaseClasses;

import java.io.IOException;
import java.time.Duration;

import EdControlsMain.Resources.DataReader;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.PageFactory;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    public static WebDriver driver;
    public LoginPage loginPage;
    DataReader dataReader = new DataReader(driver);

    public BaseTest(WebDriver driver) {
        BaseTest.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //initialization the driver
    public WebDriver initializeDriver() throws IOException {
        //Map<String, String> data = dataReader.readPropertiesFile();
        String browserName = DataReader.getValueFromJsonFile("browsers[0].browserName");
        if (browserName.contains("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-gpu");
            options.addArguments("user-data-dir=/tmp/chrome-user-data");

            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().setSize(new Dimension(1440, 900));

        } else if (browserName.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();

        } else if (browserName.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();

        } else if (browserName.equalsIgnoreCase("safari")) {
            driver = new SafariDriver();
        } else {
            System.out.println("Browser is not initialized");
        }

        // Clear cache and cookies after launching the browser
        //clearCacheAndCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Wrap the driver with the highlighting listener
         driver = new EventFiringDecorator<>(new HighlightListener(driver)).decorate(driver);

        return driver;
    }

    public static void clearCacheAndCookies() {
        try {
            driver.manage().deleteAllCookies(); // Deletes all cookies
            driver.get("chrome://settings/clearBrowserData"); // Opens cache clearing settings

            System.out.println("Cleared Cache and Cookies successfully.");
        } catch (Exception e) {
            System.out.println("Failed to clear cache and cookies: " + e.getMessage());
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void launchApplication() throws IOException {
        driver = initializeDriver();
        loginPage = new LoginPage(driver);
        loginPage.loginApplication();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        System.out.println("Test is done");
        Thread.sleep(3000);
        //clearCacheAndCookies();
        // driver.quit();
      //  driver.close();
    }

}

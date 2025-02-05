package dhruvakumar.PageObjects;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import dhruvakumar.Resources.DataReader;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.decorators.WebDriverDecorator;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
    public static WebDriver driver;
    public LoginPage loginPage;
    DataReader dataReader = new DataReader(driver);

    public BaseTest(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //initialization the driver
    public WebDriver initializeDriver() throws IOException {
        //Map<String, String> data = dataReader.readPropertiesFile();
        String browserName = dataReader.readJsonFile("browserName");
        if (browserName.contains("chrome")) {
            ChromeOptions options = new ChromeOptions();
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
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
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Wrap the driver with the highlighting listener
     //   driver = new EventFiringDecorator<>(new HighlightListener(driver)).decorate(driver);


        return driver;
    }


    @BeforeMethod(alwaysRun = true)
    public void launchApplication() throws IOException {
        driver = initializeDriver();
        loginPage = new LoginPage(driver);
        loginPage.loginApplication();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("Test is done");
    //    driver.quit();
    }

}

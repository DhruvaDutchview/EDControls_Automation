package EdControlsMain.EDFragments;

import EdControlsMain.BaseClasses.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.function.Function;

public class WaitUtilsFragment extends BaseTest {
    static WebDriver driver;
    private static WebDriverWait wait;

    public WaitUtilsFragment(WebDriver driver) {
        super(driver);
    }

    public static WebElement waitForElementAppear(By locator, WebDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return ele;
    }

    public static WebElement waitElementToBeVisible(By locator, WebDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust timeout as needed
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForElementToBeVisible(WebElement element){
        wait = new WebDriverWait(driver, Duration.ofSeconds(6)); // Adjust timeout as needed
        return wait.until(ExpectedConditions.visibilityOf(element));
    }


    // Waiting for web element to visible/appear
    public static WebElement waitForWebElementAppear(WebElement ele) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOf(ele));
        return element;
    }

    public static void waitForWebElementByAppear(WebElement ele) {

        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20)) // Maximum wait time
                .pollingEvery(Duration.ofSeconds(5)) // Polling interval
                .ignoring(NoSuchElementException.class); // Ignore NoSuchElementException
        WebElement element = wait.until(ExpectedConditions.visibilityOf(ele));
    }

    public static WebElement waitForWebElementToAppear(WebDriver driver, WebElement leftMenu) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))  // Maximum wait time
                .pollingEvery(Duration.ofMillis(500)) // Check every 500ms
                .ignoring(NoSuchElementException.class)  // Ignore NoSuchElementException
                .ignoring(StaleElementReferenceException.class); // Ignore StaleElementReferenceException

        return wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                try {
                    WebElement projectDropdown = leftMenu.findElement(By.xpath("//div[@class='project-dropdown']"));
                    if (projectDropdown.isDisplayed()) {
                        return projectDropdown;
                    } else {
                        return null;
                    }
                } catch (StaleElementReferenceException e) {
                    return null;  // Retry in the next polling cycle
                }
            }
        });
    }

    public static WebElement waitForWebElementAppearFluentWait(WebElement ele) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20)) // Maximum wait time
                .pollingEvery(Duration.ofSeconds(5)) // Polling interval
                .ignoring(NoSuchElementException.class).ignoring(TimeoutException.class); // Ignore NoSuchElementException

        WebElement element = wait.until(ExpectedConditions.visibilityOf(ele));
        return element;
    }

    public static void waitForWebElementToClickable(WebElement ele) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
         wait.until(ExpectedConditions.elementToBeClickable(ele));
    }
    public static WebElement waitForWebElementToClick(WebElement ele) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.elementToBeClickable(ele));
    }

    public static WebElement waitForWebElementToClickableBy(By locator, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Waiting for web element to disappear
    public static Boolean waitForElementDisAppear(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));

    }

    public static WebElement presenceOfElementLocated(By locator) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20)) // Maximum wait time
                .pollingEvery(Duration.ofSeconds(5)) // Polling interval
                .ignoring(NoSuchElementException.class);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static boolean isElementPresent(String xpath) {
        try {
            return driver.findElements(By.xpath(xpath)).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }


}


package EdControlsMain.ReusableFunctions;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import EdControlsMain.Resources.DataReader;
import EdControlsMain.BaseClasses.BaseTest;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static io.restassured.RestAssured.*;

public class ReusableMethods extends BaseTest {

    static WebDriver driver;
    static DataReader dataReader = new DataReader(driver);

    public ReusableMethods(WebDriver driver) {
        super(driver);
        ReusableMethods.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Waiting for web element to visible/appear using PageFactory method
    public void waitForElementAppear(By findBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));

    }

    public static WebElement waitForElementToBeVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust timeout as needed
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    // Waiting for web element to visible/appear
    public static WebElement waitForWebElementAppear(WebElement ele) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOf(ele));
        // wait.until(ExpectedConditions.elementToBeClickable(ele));
	/*	FluentWait<WebDriver> wait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(20)) // Maximum wait time
				.pollingEvery(Duration.ofSeconds(5)) // Polling interval
				.ignoring(NoSuchElementException.class); // Ignore NoSuchElementException

		WebElement element = wait.until(ExpectedConditions.visibilityOf(ele));
     */
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

    public static String checkingToastMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        // wait.until(ExpectedConditions.elementToBeClickable(ele));
	/*	FluentWait<WebDriver> wait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(20)) // Maximum wait time
				.pollingEvery(Duration.ofSeconds(5)) // Polling interval
				.ignoring(NoSuchElementException.class); // Ignore NoSuchElementException

		WebElement element = wait.until(ExpectedConditions.visibilityOf(ele));
     */
        try {
            WebElement toastElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='MuiAlert-message']")));
            String toastMessage = toastElement.getText();
            return toastMessage;
        } catch (TimeoutException e) {
            System.out.println("Toast message did not appear.");
        }
        return null;
    }

    // Waiting for web element to disappear
    public static Boolean waitForElementDisAppear(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));

    }

    public static int getCount(WebElement element) {
        String elementText = element.getText();
        // Extract the number from the string (assuming format like "audit (0)")
        int count = Integer.parseInt(elementText.replaceAll("[^0-9]", ""));
        return count;
    }

    // Taking screenshot
    public static String getScreenshot(String testCaseName, WebDriver driver) throws Exception {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destinationPath = System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
        FileUtils.copyFile(source, new File(destinationPath));
        return destinationPath;
    }

    public static WebElement presenceOfElementLocated(By locator) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20)) // Maximum wait time
                .pollingEvery(Duration.ofSeconds(5)) // Polling interval
                .ignoring(NoSuchElementException.class);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

   /* public static String generateRandomString() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }*/


        public static String generateRandomString() {
            int counter = 1; // Start from 1
            String prefix = "Automation ";
            String number = String.format("%02d", counter); // Format number with leading zeros (01, 02, ... 100)

            if (counter < 100) {
                counter++; // Increment counter
            } else {
                counter = 1; // Reset after 100 (optional)
            }
            return prefix + number;
        }

    public static void testGooglePlacesAutocomplete() {

        RestAssured.baseURI = "https://maps.googleapis.com";
        Response response =
                given()
                        .queryParam("1s", "benga")
                        .queryParam("4s", "en-GB")
                        .queryParam("15e", "3")
                        .queryParam("21m", "1")
                        .queryParam("2e", "1")
                        .queryParam("r_url", "https://dev.edcontrols.com/")
                        .queryParam("key", "AIzaSyBq4CirW3EMe1qNP9VbYTpJtaNPUDsyhJw") // Replace with a valid API key
                        .when()
                        .get("/maps/api/place/js/AutocompletionService.GetPredictionsJson")
                        .then()
                        .statusCode(200) // Validate response status
                        .extract()
                        .response();

        JsonPath jsonPath = new JsonPath(response.asString());

        // Extract the list of prediction descriptions
        List<String> predictions = jsonPath.getList("predictions.description");

        // Check if "Bengaluru" is present
        String selectedLocation = null;
        for (String location : predictions) {
            if (location.contains("Bengaluru")) {
                selectedLocation = location;
                break;
            }
        }

        // If Bengaluru is not found, pick a random location from the list
        if (selectedLocation == null && !predictions.isEmpty()) {
            Random random = new Random();
            selectedLocation = predictions.get(random.nextInt(predictions.size()));
        }

        // Print the selected location
        System.out.println("Selected Location: " + selectedLocation);

        // Optional: Assert that a location was selected
        Assert.assertNotNull(selectedLocation, "No location found in predictions!");
    }

}

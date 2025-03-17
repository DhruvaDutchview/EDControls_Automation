package EdControlsMain.ReusableFunctions;

import java.io.*;
import java.time.Duration;
import java.util.HashSet;
import java.util.Random;
import java.util.function.Function;

import EdControlsMain.Resources.DataReader;
import EdControlsMain.BaseClasses.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReusableMethods extends BaseTest {
    private static final String FILE_PATH = "used_numbers.txt";
    private static final String BASE_NAME = "Automation project ";
    static WebDriver driver;
    static DataReader dataReader = new DataReader(driver);

    public ReusableMethods(WebDriver driver) {
        super(driver);
        ReusableMethods.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public static String checkingToastMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            WebElement toastElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='MuiAlert-message']")));
            String toastMessage = toastElement.getText();
            return toastMessage;
        } catch (TimeoutException e) {
            System.out.println("Toast message did not appear.");
        }
        return null;
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

    /*
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
    }*/

    public static void clearSingleElement(WebElement element) throws InterruptedException {
        element.click(); // Ensure the field is focused
        Thread.sleep(500);

        // Select all existing text and delete
        element.sendKeys(Keys.chord(Keys.COMMAND, "a")); // For Mac (optional)
        Thread.sleep(500);
        element.sendKeys(Keys.BACK_SPACE);
        Thread.sleep(500);
    }
    // **Helper method to clear text, press Enter, and enter new text**
    public static void updateField(WebElement element, String newValue) throws InterruptedException {
        element.click(); // Ensure the field is focused
        Thread.sleep(500);

        // Select all existing text and delete
        element.sendKeys(Keys.chord(Keys.COMMAND, "a")); // For Mac (optional)
        Thread.sleep(500);
        element.sendKeys(Keys.BACK_SPACE);
        Thread.sleep(500);

        // Enter new text
        element.sendKeys(newValue);
        Thread.sleep(500);

        // Press Enter to confirm (ONLY for projectName)
        if (element.getAttribute("id").equals("projectName")) {
            element.sendKeys(Keys.ENTER);
            Thread.sleep(500);
        }

        // Move focus away
        element.sendKeys(Keys.TAB);
    }

    public static void scrollUntilElementVisible(WebElement element) throws Exception {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(500); // Allow time for scroll
    }

    // Generating project name dynamically
    public static String generateProjectName() {
        HashSet<Integer> usedNumbers = loadUsedNumbers();
        int randomNumber;

        do {
            randomNumber = new Random().nextInt(1000) + 1; // Generates a number between 1 and 1000
        } while (usedNumbers.contains(randomNumber));

        usedNumbers.add(randomNumber);
        saveUsedNumbers(usedNumbers);

        return BASE_NAME + randomNumber;
    }

    private static HashSet<Integer> loadUsedNumbers() {
        HashSet<Integer> numbers = new HashSet<>();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    numbers.add(Integer.parseInt(line.trim()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return numbers;
    }

    private static void saveUsedNumbers(HashSet<Integer> numbers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Integer num : numbers) {
                writer.write(num + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

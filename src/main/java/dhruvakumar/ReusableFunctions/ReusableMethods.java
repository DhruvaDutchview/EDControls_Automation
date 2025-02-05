package dhruvakumar.ReusableFunctions;

import java.io.File;
import java.time.Duration;
import java.util.*;

import dhruvakumar.Resources.DataReader;
import dhruvakumar.PageObjects.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReusableMethods extends BaseTest {

	static WebDriver driver;
	static DataReader dataReader = new DataReader(driver);

	public ReusableMethods(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	// Waiting for web element to visible/appear using PageFactory method
	public void waitForElementAppear(By findBy)
	{
	 WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
	 wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	
	}

	public static WebElement waitForElementToBeVisible(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust timeout as needed
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// Waiting for web element to visible/appear
	public static WebElement waitForWebElementAppear(WebElement ele)
	{
	   WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
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

	public static WebElement waitForWebElementToClickable(WebElement ele)
	{
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(ele));
		// wait.until(ExpectedConditions.elementToBeClickable(ele));
	/*	FluentWait<WebDriver> wait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(20)) // Maximum wait time
				.pollingEvery(Duration.ofSeconds(5)) // Polling interval
				.ignoring(NoSuchElementException.class); // Ignore NoSuchElementException

		WebElement element = wait.until(ExpectedConditions.visibilityOf(ele));
     */
		return element;
	}

	public static String checkingToastMessage()
	{
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(20));
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
	public void waitForElementDisAppear(WebElement element)
	{
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOf(element));

	
	}

	public static String[] trimmingText (WebElement element){
		String elementText = element.getText();
		elementText = elementText.replace("\n", " "); // Replace \n with a space, or "" if unnecessary
		String array[] = elementText.split(" ");
		return array;
	}

   // Taking screenshot
	public static String getScreenshot(String testCaseName, WebDriver driver) throws Exception {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destinationPath = System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
		FileUtils.copyFile(source, new File(destinationPath));
		return destinationPath;
	}

	public static void navigateToProject () throws Exception {
		String projectName = dataReader.readJsonFile("projectName");
		WebElement projectSearch = driver.findElement(By.xpath("//input[@id='search']"));
		ReusableMethods.waitForWebElementAppear(projectSearch);
		projectSearch.sendKeys(projectName);
		projectSearch.sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		List<WebElement> projectsList = driver.findElements(By.xpath("//p[@class='name']"));
		for (WebElement  project : projectsList)
		{
			String projectText = project.getText();
			if (projectText.equalsIgnoreCase(projectName))
			{
				System.out.println(projectText);
				project.click();
				System.err.println("we are inside "+ projectName+" successfully");
			}
		}

	}
}

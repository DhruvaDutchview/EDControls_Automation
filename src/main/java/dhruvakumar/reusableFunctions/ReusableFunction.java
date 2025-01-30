package dhruvakumar.reusableFunctions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dhruvakumar.pageobjects.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReusableFunction extends BaseTest {

	static WebDriver driver;
	
	public ReusableFunction(WebDriver driver) 
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	//Method to read JSON File
	public static JsonNode readJsonFile () {
		try {
			String filePath = System.getProperty("user.dir") + "/src/main/java/dhruvakumar/Resources/GlobalData.json";
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readTree(new File(filePath));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Method to read properties file and return values as a Map
	public static Map<String, String> readPropertiesFile() {
		Map<String, String> propertiesMap = new HashMap<>();
		try {
			String filePath = System.getProperty("user.dir") + "/src/main/java/dhruvakumar/Resources/GlobalData.properties";
			FileInputStream fileInput = new FileInputStream(filePath);
			Properties prop = new Properties();
			prop.load(fileInput);

			// Store values in HashMap
			propertiesMap.put("browserName", prop.getProperty("browserName"));
			propertiesMap.put("email", prop.getProperty("email"));
			propertiesMap.put("password", prop.getProperty("password"));
			propertiesMap.put("url", prop.getProperty("url"));
			fileInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return propertiesMap;
	}

	public void waitForElementAppear(By findBy)
	{
	 WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
	 wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	
	}
	
	public static void waitForWebElementAppear(WebElement ele)
	{
	// WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
	 //wait.until(ExpectedConditions.visibilityOf(ele));
	// wait.until(ExpectedConditions.elementToBeClickable(ele));
		FluentWait<WebDriver> wait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(20)) // Maximum wait time
				.pollingEvery(Duration.ofSeconds(5)) // Polling interval
				.ignoring(NoSuchElementException.class); // Ignore NoSuchElementException

		// Wait for a specific element to be visible
		WebElement element = wait.until(ExpectedConditions.visibilityOf(ele));

	}
	
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

	public static void navigateToProject () throws Exception {
		JsonNode jsonNode = readJsonFile ();
		String projectName = jsonNode.get(3).get("projectName").asText();
		WebElement projectSearch = driver.findElement(By.xpath("//input[@id='search']"));
		ReusableFunction.waitForWebElementAppear(projectSearch);
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

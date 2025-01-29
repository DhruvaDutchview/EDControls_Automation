package dhruvakumar.ReusableFunctions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.PageFactoryFinder;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dhruvakumar.pageobjects.CartPage;
import dhruvakumar.pageobjects.OrderPage;

public class ReusableFunction {

	WebDriver driver;
	
	public ReusableFunction(WebDriver driver) 
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	//Method to read JSON File
	public JsonNode readJsonFile () {
		try {
			String filePath = System.getProperty("user.dir") + "/src/main/java/dhruvakumar/Resources/GlobalData.json";
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(new File(filePath));

			// Extract browserName,email, password, and URL
			String browserName = jsonNode.get(0).get("browserName").asText();
			String email = jsonNode.get(1).get("email").asText();
			String password = jsonNode.get(1).get("password").asText();
			String url = jsonNode.get(2).get("url").asText();
			return jsonNode;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Method to read properties file and return values as a Map
	public Map<String, String> readPropertiesFile() {
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

	@FindBy(css="button[routerlink='/dashboard/cart']")
	WebElement cartHeader;
	
	@FindBy(css="button[routerlink='/dashboard/myorders']")
	WebElement orderHeader;
	
	public void waitForElementAppear(By findBy)
	{
	 WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
	 wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	
	}
	
	public void waitForWebElementAppear(WebElement ele)
	{
	 WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
	 wait.until(ExpectedConditions.visibilityOf(ele));
	
	}
	
	public void waitForElementDisAppear(WebElement element)
	{
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOf(element));

	
	}
	
	public CartPage goToCartPage()
	{
		cartHeader.click();
		CartPage cartPage=new CartPage(driver);
		return cartPage;

	}
	
	public OrderPage goToOrderPage()
	{
		orderHeader.click();
		OrderPage orderPage=new OrderPage(driver);
		return orderPage;

	}
	
}

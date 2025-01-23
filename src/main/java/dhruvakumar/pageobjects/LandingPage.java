package dhruvakumar.pageobjects;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import dhruvakumar.data;

import dhruvakumar.ReusableFunctions.ReusableFunction;


public class LandingPage extends ReusableFunction,BaseTest  {

	WebDriver driver;
	public LandingPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
//	WebElement userEmail= driver.findElement(By.id("userEmail"));
//	WebElement userPassword = driver.findElement(By.id("userPassword"));
	
	//pageFactory
	@FindBy(id="user-name")
	WebElement userEmail;
	
	@FindBy(id="passwd")
	WebElement userPassword;
	
	@FindBy(id="butn-login")
	WebElement login;
	
	/*@FindBy(css="[class*='flyInOut']")
	WebElement errorMessage;
	
	
	public String getErrorMessage()
	{
		waitForWebElementAppear(errorMessage);
		return errorMessage.getText();
		
	}*/
	Properties prop = new Properties();

	FileInputStream fis = new FileInputStream(
			System.getProperty("user.dir") + "/src/test/java/dhruvakumar/data/PurchaseOrder.json");
		prop.load(fis);
	String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser");*/
	public void loginApplication(String email,String password)
	{
		userEmail.sendKeys(email);
		userPassword.sendKeys(password);
		login.click();
		//ProductCatalog productCatalog = new ProductCatalog(driver);
		//return productCatalog;
	}
	
	public void goTo()
	{
		//driver.get("https://rahulshettyacademy.com/client");
		driver.get("https://dev.edcontrols.com/#/auth?view=login");
	
	}

	
}

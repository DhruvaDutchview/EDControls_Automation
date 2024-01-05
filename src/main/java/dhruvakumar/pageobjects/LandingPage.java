package dhruvakumar.pageobjects;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import dhruvakumar.ReusableFunctions.ReusableFunction;


public class LandingPage extends ReusableFunction {

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
	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(id="userPassword")
	WebElement userPassword;
	
	@FindBy(id="login")
	WebElement login;
	
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMessage;
	
	
	public String getErrorMessage()
	{
		waitForWebElementAppear(errorMessage);
		return errorMessage.getText();
		
	}
	public ProductCatalog loginApplication(String email,String password)
	{
		userEmail.sendKeys(email);
		userPassword.sendKeys(password);
		login.click();
		ProductCatalog productCatalog = new ProductCatalog(driver);
		return productCatalog;
	}
	
	public void goTo()
	{
		driver.get("https://rahulshettyacademy.com/client");
	
	}

	
}

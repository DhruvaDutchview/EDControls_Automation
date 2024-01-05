package dhruvakumar.pageobjects;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import dhruvakumar.ReusableFunctions.ReusableFunction;


public class CheckOutPage extends ReusableFunction {

	WebDriver driver;
	public CheckOutPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	//pageFactory
	
	@FindBy(xpath="//input[@placeholder='Select Country']")
	WebElement country;
	
	@FindBy(xpath="//a[@class='btnn action__submit ng-star-inserted']")
	WebElement submit;
	
	@FindBy(css="button[class*='ta-item']:nth-child(3)")
	WebElement selectCountry;
	
	By results = By.cssSelector(".ta-results");
	
	public void selectCountry(String CountryName)
	{
		Actions action = new Actions(driver);
		action.sendKeys(country, CountryName).build().perform();
		waitForElementAppear(results);
		selectCountry.click();

	//	driver.findElement(By.xpath("//a[@class='btnn action__submit ng-star-inserted']")).click();
	}
	
	public ConfirmationPage submitOrder()
	{
		submit.click();
		ConfirmationPage confirmationPage = new ConfirmationPage(driver);
		return confirmationPage;
	}

}

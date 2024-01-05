package dhruvakumar.pageobjects;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import dhruvakumar.ReusableFunctions.ReusableFunction;


public class OrderPage extends ReusableFunction {

	WebDriver driver;
	public OrderPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	//pageFactory
	
	@FindBy(css="tr td:nth-child(3)")
	List<WebElement> productNames;
	
	
	public Boolean verifyOrderdiaplay(String productName)
	{
		Boolean match = productNames.stream().anyMatch(cart -> cart.getText().equalsIgnoreCase(productName));
		return match;
	
	}

	

}

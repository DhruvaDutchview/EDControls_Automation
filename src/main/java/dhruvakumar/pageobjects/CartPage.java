package dhruvakumar.pageobjects;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import dhruvakumar.ReusableFunctions.ReusableFunction;


public class CartPage extends ReusableFunction {

	WebDriver driver;
	public CartPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	//pageFactory
	
	@FindBy(css="div[class='cartSection'] h3")
	List<WebElement> cartProducts;
	
	@FindBy(css=".totalRow button")
	WebElement checkoutElement;
	
	
	public Boolean verifyProductdiaplay(String productName)
	{
		Boolean match = cartProducts.stream().anyMatch(cart -> cart.getText().contains(productName));
		return match;
	}
	
	public CheckOutPage goToCheckOut()
	{
		checkoutElement.click();
		CheckOutPage checkOutPage= new CheckOutPage(driver);
		return checkOutPage;
	}
	

}

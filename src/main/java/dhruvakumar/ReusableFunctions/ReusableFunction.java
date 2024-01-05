package dhruvakumar.ReusableFunctions;

import java.time.Duration;

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

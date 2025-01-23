package dhruvakumar.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.sun.net.httpserver.Authenticator.Retry;

import dhruvakumar.TestComponenets.BaseTest;
import dhruvakumar.pageobjects.CartPage;
import dhruvakumar.pageobjects.CheckOutPage;
import dhruvakumar.pageobjects.ConfirmationPage;
import dhruvakumar.pageobjects.ProductCatalog;

public class ErrorValidationTest extends BaseTest {


	  @Test (groups = {"ErrorHandling"})
	  public void loginErrorValidation() throws IOException
	  {
		ProductCatalog productCatalog=landingPage.loginApplication("dhkr4325@gmail.com", "Iamking@17");
        Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());		
	  }
	  
	  
	  
	  @Test
	  public void productErrorValidation() throws IOException
	  {
		String productName="IPHONE 13 PRO";
		ProductCatalog productCatalog=landingPage.loginApplication("dhkr@gmail.com", "Dhruvad@17");
		List<WebElement> products = productCatalog.getProductList();
		productCatalog.addProductToCart(productName);
		CartPage cartPage=productCatalog.goToCartPage();
		Boolean match=cartPage.verifyProductdiaplay("IPHONE 13 PRO");
		Assert.assertTrue(match);
	   
	  }
	  
	  
	  
	  
}

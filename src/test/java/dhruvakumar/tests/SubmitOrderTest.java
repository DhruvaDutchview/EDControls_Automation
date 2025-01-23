package dhruvakumar.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import dhruvakumar.TestComponenets.BaseTest;
import dhruvakumar.pageobjects.CartPage;
import dhruvakumar.pageobjects.CheckOutPage;
import dhruvakumar.pageobjects.ConfirmationPage;
import dhruvakumar.pageobjects.LandingPage;
import dhruvakumar.pageobjects.OrderPage;
import dhruvakumar.pageobjects.ProductCatalog;

public class SubmitOrderTest extends BaseTest {

	  String productName="IPHONE 13 PRO";
	String filePath = System.getProperty("user.dir") + "/src/test/java/dhruvakumar/data/PurchaseOrder.json";

	@Test
	  public void submitOrder(HashMap<String, String> input) throws IOException
	  {

		  landingPage.loginApplication(input.get("email"), input.get("password"));

		//  ProductCatalog productCatalog=landingPage.loginApplication(input.get("email"), input.get("password"));

		/*// Products Page(POM:- Product Catalog)
		List<WebElement> products = productCatalog.getProductList();
		productCatalog.addProductToCart(input.get("productName"));
		CartPage cartPage=productCatalog.goToCartPage();
		
		// cart page (POM:- cartPage)
		Boolean match=cartPage.verifyProductdiaplay(input.get("productName"));
		Assert.assertTrue(match);
		CheckOutPage checkOutPage = cartPage.goToCheckOut();
		
		//checkout page (POM:- checkOutPage)
		checkOutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkOutPage.submitOrder();
		
		//Confirmation page (POM:- ConfirmationPage)
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		}

     @Test (dependsOnMethods = {"submitOrder"})
     public void OrderHistoryTest()
     {
 		ProductCatalog productCatalog=landingPage.loginApplication("dhkr45@gmail.com", "Dhruvad@17");
 		OrderPage orderPage = productCatalog.goToOrderPage();
 		Boolean match= orderPage.verifyOrderdiaplay(productName);
 		Assert.assertTrue(match);
 		
    	 
     }

     @DataProvider
     public Object[][] getData() throws IOException, ParseException
     {
    	 
    	 
    	 
//    	 HashMap<String, String> map= new HashMap<String, String>();
//    	 map.put("email", "dhkr45@gmail.com");
//    	 map.put("password", "Dhruvad@17");
//    	 map.put("productName", "ZARA COAT 3");
//    	 
//    	 HashMap<String, String> map1= new HashMap<String, String>();
//    	 map1.put("email", "dhkr@gmail.com");
//    	 map1.put("password", "Dhruvad@17");
//    	 map1.put("productName", "ADIDAS ORIGINAL");

    	 List<HashMap<String, String>> data= getJsonDataToMap(filePath);
    	 return new Object [][] {{data.get(0)},{data.get(1)}};
    	 
    	 */
     }
 

}

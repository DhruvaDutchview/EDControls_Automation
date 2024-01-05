package dhruvakumar.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import dhruvakumar.pageobjects.LandingPage;


public class StandAloneClass {

	
	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "D:\\Automation-Selenium\\chromedriver-win32\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://rahulshettyacademy.com/client");
		Thread.sleep(1000);
		
		LandingPage landingPage=new LandingPage(driver);
		//login page
		driver.findElement(By.id("userEmail")).sendKeys("dhkr45@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Dhruvad@17");
        driver.findElement(By.id("login")).click();
        
        //2nd page:- selecting item and proccesing to cart
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
        List<WebElement> products=driver.findElements(By.cssSelector(".mb-3"));
        WebElement prod=products.stream().filter(product->product.findElement(By.cssSelector("b")).getText().equals("ZARA COAT 3"))
        .findFirst().orElse(null);
        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
        
        //handling toast
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
        
        driver.findElement(By.cssSelector("button[routerlink='/dashboard/cart']")).click();
        
        //cart page
        List<WebElement> cartProducts=driver.findElements(By.cssSelector("div[class='cartSection'] h3"));
        Boolean match=cartProducts.stream().anyMatch(cart->cart.getText().contains("ZARA COAT 3"));
        Assert.assertTrue(match);
      
        driver.findElement(By.cssSelector(".totalRow button")).click();
        
//        driver.findElement(By.xpath("//input[@placeholder='Select Country']")).sendKeys("ind");
//        List<WebElement> country=driver.findElements(By.xpath("//section[contains(@class,'ta-results')]"));
        
        Actions action=new Actions(driver);
        action.sendKeys(driver.findElement(By.xpath("//input[@placeholder='Select Country']")), "Ind").build().perform();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".ta-results"))));
        
        driver.findElement(By.cssSelector("button[class*='ta-item']:nth-child(3)")).click();       
        
        driver.findElement(By.xpath("//a[@class='btnn action__submit ng-star-inserted']")).click();
        
       String confirmMessage= driver.findElement(By.cssSelector(".hero-primary")).getText();
        
       Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
       
       driver.quit();
        
        
        
        
        
        
        
        
		
		
		
		
	}

}

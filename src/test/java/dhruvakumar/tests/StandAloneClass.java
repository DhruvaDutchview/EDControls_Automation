package dhruvakumar.tests;

import java.time.Duration;
import java.util.List;

import dhruvakumar.pageobjects.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneClass extends BaseTest {

    public StandAloneClass(WebDriver driver) {
        super(driver);
    }

    public static void main(String[] args) throws InterruptedException {
        // Set up WebDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://rahulshettyacademy.com/client");
        Thread.sleep(1000);

        // Login page
        driver.findElement(By.id("userEmail")).sendKeys("dhkr45@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Dhruvad@17");
        driver.findElement(By.id("login")).click();
        Thread.sleep(3000);
        // Wait for products to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

        // Debugging: Log the size of the products list
        System.out.println("Number of products found: " + products.size());

        // Find the product "IPHONE 13 PRO"
        WebElement prod = products.stream()
                .filter(product -> {
                    String productName = product.findElement(By.cssSelector("b")).getText();
                    System.out.println("Found product: " + productName); // Debugging log
                    return productName.equals("IPHONE 13 PRO");
                })
                .findFirst()
                .orElse(null);

        if (prod == null) {
            System.out.println("Product IPHONE 13 PRO not found!");
            driver.quit();
            return; // Exit to avoid NullPointerException
        }

        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

        // Handle toast notification
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

        // Navigate to cart
        driver.findElement(By.cssSelector("button[routerlink='/dashboard/cart']")).click();

        // Cart page
        List<WebElement> cartProducts = driver.findElements(By.cssSelector("div[class='cartSection'] h3"));
        boolean match = cartProducts.stream().anyMatch(cart -> cart.getText().contains("IPHONE 13 PRO"));
        Assert.assertTrue(match);

        driver.findElement(By.cssSelector(".totalRow button")).click();

        // Select country
        Actions action = new Actions(driver);
        action.sendKeys(driver.findElement(By.xpath("//input[@placeholder='Select Country']")), "Ind").build().perform();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".ta-results"))));
        driver.findElement(By.cssSelector("button[class*='ta-item']:nth-child(3)")).click();

        // Place order
        driver.findElement(By.xpath("//a[@class='btnn action__submit ng-star-inserted']")).click();

        // Verify confirmation message
        String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

        //driver.quit();
    }
}

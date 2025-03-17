package EdControlsMain.EdContainers;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.ReusableFunctions.ReusableMethods;
import EdControlsMain.ReusableFunctions.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BulkContainer extends BaseTest {

    public BulkContainer(WebDriver driver) {
        super(driver);
    }

    public static Integer ticketBulkSelection() throws Exception {
        ProjectContainer.navigateToProject();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement selectElement = WaitUtils.presenceOfElementLocated(By.xpath("//div[@class='select-all-action ']"));
        WebElement selectAllCheckbox = selectElement.findElement(By.xpath("//label[@class='checkbox-container']"));

        // Debugging - Check if element is displayed and enabled
        System.out.println("Is element displayed? " + selectAllCheckbox.isDisplayed());
        System.out.println("Is element enabled? " + selectAllCheckbox.isEnabled());

        // If regular click doesn't work, try clicking the parent label
        try {
            Thread.sleep(2000);
            selectAllCheckbox.click();
        } catch (Exception e) {
            System.out.println("Regular click failed, trying JavaScript click...");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", selectAllCheckbox);
        }
       // Thread.sleep(2000);
        // Verify if checkbox is selected
        WebElement actualCheckbox = driver.findElement(By.xpath("//div[@class='bulk-action ']//input[@id='select-all']"));
        if (actualCheckbox.isSelected()) {
            System.out.println("Checkbox is enabled");
        } else {
            System.out.println("Checkbox is NOT enabled");
        }
        WebElement count = driver.findElement(By.xpath("//div[@class='bulk-action ']//div[@class='display-selected-tickets']"));
        Integer TicketsCount = ReusableMethods.getCount(count);
        return TicketsCount;
    }

    public static void bulkConfirmation(){
        WebElement ele = driver.findElement(By.xpath("//div[@class='bulk-edit']//div[@class='bulk-edit__container']"));
        WebElement ele2 = ele.findElement(By.xpath("//div[@class='bulk-edit__body']"));
        Integer confirmCode = ReusableMethods.getCount(ele2);
        ele.findElement(By.id("bulk-captcha")).sendKeys(Integer.toString(confirmCode));
        WebElement confirmButton = driver.findElement(By.xpath("//button[text()='Confirm']"));
        confirmButton.click();
    }


}

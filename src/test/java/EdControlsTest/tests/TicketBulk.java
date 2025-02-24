package EdControlsTest.tests;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.EdFragments.BulkFragment;
import EdControlsMain.EdFragments.ProjectContainer;
import EdControlsMain.ReusableFunctions.ReusableMethods;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.security.Key;
import java.time.Duration;

public class TicketBulk extends BaseTest {

    public TicketBulk() {
        super(null); // Temporary, driver will be initialized in BaseTest
    }

    public TicketBulk(WebDriver driver) {
        super(driver);
    }

    @Test
    public void addingBulkTag() throws Exception {
        Integer selectedTicketsCount = BulkFragment.ticketBulkSelection();
        System.out.println(selectedTicketsCount + " Ticket's Selected");
        WebElement tagContainer = driver.findElement(By.id("filter-ticket-tags"));
        tagContainer.click();
        Thread.sleep(2000);
        WebElement addTagContainer = driver.findElement(By.id("tags-bulk"));
        addTagContainer.click();
        addTagContainer.sendKeys("Tag bulk automation");
        addTagContainer.sendKeys(Keys.ENTER);
        WebElement doneButton = driver.findElement(By.xpath("//button[text()='Done']"));
        doneButton.click();
        if (selectedTicketsCount >= 20) {
            Thread.sleep(2000);
            BulkFragment.bulkConfirmation();
        }
        String confirmationMessage = ReusableMethods.checkingToastMessage();
        System.out.println(confirmationMessage);

    }


}

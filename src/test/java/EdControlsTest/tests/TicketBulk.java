package EdControlsTest.tests;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.EdContainers.BulkContainer;
import EdControlsMain.ReusableFunctions.ReusableMethods;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class TicketBulk extends BaseTest {

    public TicketBulk() {
        super(null); // Temporary, driver will be initialized in BaseTest
    }

    public TicketBulk(WebDriver driver) {
        super(driver);
    }

    static Actions action;
    @Test
    public void addingBulkTag() throws Exception {
        Integer selectedTicketsCount = BulkContainer.ticketBulkSelection();
        System.out.println(selectedTicketsCount + " Ticket's Selected");
        WebElement tagContainer = driver.findElement(By.id("filter-ticket-tags"));
        tagContainer.click();
        Thread.sleep(2000);
        WebElement addTagContainer = driver.findElement(By.id("tags-bulk"));
        new Actions(driver).click(addTagContainer).sendKeys("Tag bulk automation").build().perform();
       /* addTagContainer.click();
        addTagContainer.sendKeys("Tag bulk automation");
        addTagContainer.sendKeys(Keys.ENTER);*/
        WebElement doneButton = driver.findElement(By.xpath("//button[text()='Done']"));
        doneButton.click();
        if (selectedTicketsCount >= 20) {
            Thread.sleep(2000);
            BulkContainer.bulkConfirmation();
        }
        String confirmationMessage = ReusableMethods.checkingToastMessage();
        System.out.println(confirmationMessage);

    }


}

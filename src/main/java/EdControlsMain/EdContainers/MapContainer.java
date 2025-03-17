package EdControlsMain.EdContainers;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.ReusableFunctions.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MapContainer extends BaseTest {

    public MapContainer(WebDriver driver) {
        super(driver);
    }

    public static void navigateToMap() throws Exception
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement ticketLeftPanel = driver.findElement(By.xpath("//div[@class='ticket-filters']"));
        WaitUtils.waitForWebElementAppear(ticketLeftPanel);
        WebElement ticketLeftPanel1  = ticketLeftPanel.findElement(By.xpath("//div[contains(@class,'MuiPaper-root MuiAccordion-root filter-inner-accordian')]"));
        WebElement ticketLeftPanel2 = ticketLeftPanel1.findElement(By.xpath("//div[contains(@class,'MuiButtonBase-root')] //div[@class='MuiAccordionSummary-content'] //div[contains(@class,'body-accordian')]"));
        List<WebElement> mapGroupsList = ticketLeftPanel2.findElements(By.xpath("//div/span[not(contains(@class, 'count-container'))]"));
       /*  WebElement ticketLeftPanel = driver.findElement(By.xpath("//div[@class='ticket-filters']//div[contains(@class,'body-accordian')]"));
        List<WebElement> mapGroupsList = ticketLeftPanel.findElements(By.xpath("//div/span[not(contains(@class, 'count-container'))]"));*/
        System.out.println(mapGroupsList.size());
        for (WebElement mapGroups : mapGroupsList)
        {
            String mapGroupText = mapGroups.getText();
            if (mapGroupText.contains("LG - 2"))
            {
                mapGroups.click();
                Thread.sleep(2000);
                WebElement ele = mapGroups.findElement(By.xpath("//div[contains(@class,'MuiCollapse-root MuiCollapse-entered')] //div[@class='MuiAccordionDetails-root']"));
                List<WebElement> mapsList = ele.findElements(By.xpath("//div[@class='map-container']"));
                for (WebElement maps: mapsList)
                {
                    String mapsText = maps.getText();
                    Thread.sleep(2000);
                    if (mapsText.contains("work-contract-service")){
                        System.out.println(mapsText);
                        WebElement mapImageElement = maps.findElement(By.xpath(".//figure[@class='map-thumbImage']"));
                        WaitUtils.waitForWebElementToClickable(mapImageElement);
                        mapImageElement.click();
                        return;
                    }
                }
                break;
            }

        }

    }

    public static String createLibraryGroup(String libraryGroupName) throws InterruptedException {
        WebElement libraryLeftPanel = driver.findElement(By.xpath("//div[@id='panel2d-content']//div[@class='library-filter__container ']"));
        WebElement createNewGroup = driver.findElement(By.id("add-new-group-library"));
        createNewGroup.click();
        Thread.sleep(2000);
        WebElement createGroup = createNewGroup.findElement(By.xpath("//input[@type='text']"));
        WaitUtils.waitForWebElementByAppear(createGroup);
        createGroup.sendKeys(libraryGroupName + Keys.ENTER);
        Thread.sleep(2000);
        List<WebElement> libraryGroups = libraryLeftPanel.findElements(By.xpath("//div[@class='group-container']"));
        for (WebElement libraryGroup : libraryGroups) {
            System.out.println("Group Name :" + libraryGroup.getText());
            if (libraryGroup.getText().contains(libraryGroupName)) {
                System.err.println(libraryGroupName + ": Created Successfully");
            }
        }
        return libraryGroupName;
    }

    public static void deleteLibraryGroup(String libraryGroupName) throws InterruptedException {
        WebElement libraryLeftPanel = driver.findElement(By.xpath("//div[@id='panel2d-content']//div[@class='library-filter__container ']"));
        List<WebElement> libraryGroups = libraryLeftPanel.findElements(By.xpath(".//div[@class='group-container']"));
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        for (WebElement libraryGroup : libraryGroups) {
            System.out.println("Checking Group Name: " + libraryGroup.getText());
            Thread.sleep(1000);
            if (libraryGroup.getText().contains(libraryGroupName)) {
                System.out.println("Found the expected library group: " + libraryGroupName);

                // Debug: Print full HTML of the library group
                //System.out.println("Library Group HTML: " + libraryGroup.getAttribute("outerHTML"));

                try {
                    // Use a more general hover target
                    WebElement hoverTarget = libraryGroup.findElement(By.xpath(".//*[self::span or self::div]"));
                    actions.moveToElement(hoverTarget).perform();
                    Thread.sleep(1000);

                    // Try finding and clicking the delete button
                    WebElement deleteElement = wait.until(ExpectedConditions.visibilityOf(
                            libraryGroup.findElement(By.xpath(".//img[contains(@class,'delete-container')]"))));
                    deleteElement.click();
                    System.out.println("Delete button clicked successfully.");
                    break;
                } catch (Exception e) {
                    System.out.println("Delete button not found: " + e.getMessage());
                }
            }
        }
        Thread.sleep(1000);
        WebElement dialogElement = wait.until(ExpectedConditions.visibilityOf(
                driver.findElement(By.xpath("//div[@role='dialog']"))));
        dialogElement.findElement(By.xpath("//div[contains(@class,'MuiDialogActions-root')]//button[@id='dialog-ok']")).click();
    }

}

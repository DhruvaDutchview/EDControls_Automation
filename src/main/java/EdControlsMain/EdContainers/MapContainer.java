package EdControlsMain.EdContainers;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.ReusableFunctions.ReusableMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
        ReusableMethods.waitForWebElementAppear(ticketLeftPanel);
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
                        ReusableMethods.waitForWebElementToClickable(mapImageElement);
                        mapImageElement.click();
                        return;
                    }
                }
                break;
            }

        }




    }


}

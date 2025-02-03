package dhruvakumar.PageObjects;

import dhruvakumar.ReusableFunctions.ReusableMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Maps extends BaseTest {

    public Maps(WebDriver driver) {
        super(driver);
    }

    public static void navigateToMap() throws Exception
    {
        WebElement ticketLeftPanel = driver.findElement(By.xpath("//div[@class='ticket-filters']"));
        ReusableMethods.waitForWebElementAppear(ticketLeftPanel);
        List<WebElement> mapGroupsList = ticketLeftPanel.findElements(By.xpath("//div[contains(@class,'MuiPaper-root MuiAccordion-root filter-inner-accordian')]"));
        //System.out.println(mapGroupsList.size());
        Thread.sleep(2000);
        for (WebElement mapGroups : mapGroupsList)
        {
            String mapGroupText = mapGroups.getText();
            Thread.sleep(2000);
            if (mapGroupText.contains("LG - 1"))
            {
                mapGroups.click();
                WebElement ele = mapGroups.findElement(By.xpath("//div[contains(@class,'MuiCollapse-root MuiCollapse-entered')] //div[@class='MuiAccordionDetails-root']"));
                Thread.sleep(2000);
                List<WebElement> mapsList = ele.findElements(By.xpath("//div[@class='map-container']"));
                for (WebElement maps: mapsList)
                {
                    String mapsText = maps.getText();
                    if (mapsText.contains("Building-1")){
                        System.out.println(mapsText);
                        maps.findElement(By.xpath("//figure[@class='map-thumbImage']")).click();
                    }
                    break;
                }
                break;
            }

        }




    }


}

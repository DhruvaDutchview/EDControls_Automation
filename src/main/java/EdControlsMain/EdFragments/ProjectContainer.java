package dhruvakumar.EdFragments;

import dhruvakumar.BaseClasses.BaseTest;
import dhruvakumar.Resources.DataReader;
import dhruvakumar.ReusableFunctions.ReusableMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProjectContainer extends BaseTest {

    public ProjectContainer(WebDriver driver) {
        super(driver);
    }

    public static void navigateToProject () throws Exception {
        String projectName = DataReader.readJsonFile("projectName");
        WebElement projectSearch = driver.findElement(By.xpath("//input[@id='search']"));
        ReusableMethods.waitForWebElementAppear(projectSearch);
        projectSearch.sendKeys(projectName);
        projectSearch.sendKeys(Keys.ENTER);
        Thread.sleep(3000);
        List<WebElement> projectsList = driver.findElements(By.xpath("//p[@class='name']"));
        for (WebElement  project : projectsList)
        {
            String projectText = project.getText();
            if (projectText.equalsIgnoreCase(projectName))
            {
                System.out.println(projectText);
                project.click();
                System.err.println("we are inside "+ projectName+" successfully");
            }
        }

    }
}

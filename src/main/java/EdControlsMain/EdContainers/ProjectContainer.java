package EdControlsMain.EdContainers;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.Resources.DataReader;
import EdControlsMain.ReusableFunctions.ReusableMethods;
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
        String projectName = DataReader.getValueFromJsonFile("projectName");
        WebElement projectSearch = driver.findElement(By.xpath("//input[@id='search']"));
        ReusableMethods.waitForWebElementAppear(projectSearch);
        projectSearch.sendKeys(projectName + Keys.ENTER);
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

    public static void addUsersOnNewProject() throws Exception {
        WebElement addUsersElement = driver.findElement(By.xpath("//div[@class='new-form-width__screen2']"));
        ReusableMethods.waitForWebElementAppear(addUsersElement);
        Thread.sleep(2000);
        WebElement projectAccountable = addUsersElement.findElement(By.id("pj-accountable"));
        projectAccountable.sendKeys(DataReader.getValueFromJsonFile("projectAccountable") + Keys.ENTER);
        Thread.sleep(2000);
        WebElement projectSupport = addUsersElement.findElement(By.id("pj-support"));
        projectSupport.sendKeys(DataReader.getValueFromJsonFile("projectSupport")+ Keys.ENTER);
        Thread.sleep(2000);
        WebElement projectInformed = addUsersElement.findElement(By.id("pj-informed"));
        projectInformed.sendKeys(DataReader.getValueFromJsonFile("projectInformed")+ Keys.ENTER);
        Thread.sleep(2000);
        WebElement projectConsulted = addUsersElement.findElement(By.id("pj-consulted"));
        projectConsulted.sendKeys(DataReader.getValueFromJsonFile("projectConsulted")+ Keys.ENTER);
    }
}

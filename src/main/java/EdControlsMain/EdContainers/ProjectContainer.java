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

    public static void editingProject(String projectName) throws Exception {
     //   String projectName = DataReader.getValueFromJsonFile("projectName");
        WebElement projectSearch = driver.findElement(By.xpath("//input[@id='search']"));
        ReusableMethods.waitForWebElementAppear(projectSearch);
        projectSearch.sendKeys(projectName + Keys.ENTER);
        Thread.sleep(2000);
        List<WebElement> projectsList = driver.findElements(By.xpath("//p[@class='name']"));
        for (WebElement  project : projectsList)
        {
            String projectText = project.getText();
            Thread.sleep(2000);
            if (projectText.equalsIgnoreCase(projectName))
            {
                System.out.println(projectText);
                WebElement editProjectElement = driver.findElement(By.id("project-edit-btn"));
                editProjectElement.click();
                break;
                //System.err.println("we are inside "+ projectName+" successfully");
            }
        }
    }


    public static void initiateNewProjectCreation() throws Exception {
        WebElement newProjectElement = driver.findElement(By.id("create-new-project"));
        ReusableMethods.waitForWebElementAppear(newProjectElement);
        Thread.sleep(3000);
        newProjectElement.click();
    }

}

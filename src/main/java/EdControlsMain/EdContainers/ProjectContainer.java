package EdControlsMain.EdContainers;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.Resources.DataReader;
import EdControlsMain.ReusableFunctions.ReusableMethods;
import EdControlsMain.ReusableFunctions.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProjectContainer extends BaseTest {

    public ProjectContainer(WebDriver driver) {
        super(driver);
    }

    public static void navigateToProject () throws Exception {
        String projectName = DataReader.getValueFromJsonFile("projectName");
        WebElement projectSearch = driver.findElement(By.xpath("//input[@id='search']"));
        WaitUtils.waitForWebElementAppear(projectSearch);
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

    public static void editProject(String projectName) throws Exception {
        Thread.sleep(2000);
        WebElement projectSearch = driver.findElement(By.xpath("//input[@id='search']"));
        WaitUtils.waitForWebElementAppear(projectSearch);
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
        WaitUtils.waitForWebElementAppear(newProjectElement);
        Thread.sleep(3000);
        newProjectElement.click();
    }

    public static void projectArchiveDearchive(List<WebElement> projectNameElements, String projectName) throws Exception {
        for (WebElement project : projectNameElements) {
            if (project.getText().equalsIgnoreCase(projectName)) {
                if (driver.findElement(By.xpath("//p[@class='user-role']")).getText().contains("Administrator")) {
                    System.out.println("User Role in:" +projectName + " is Administrator");
                    ProjectContainer.editProject(projectName);
                    WebElement deArchiveElement = driver.findElement(By.id("projArchive"));
                    deArchiveElement.click();
                }
                break;
            }
        }
    }

    public static void navigateToModule(WebElement element) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
        Thread.sleep(2000);
    }

}

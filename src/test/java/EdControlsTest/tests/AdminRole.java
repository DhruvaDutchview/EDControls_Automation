package EdControlsTest.tests;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.EdContainers.ProjectContainer;
import EdControlsMain.Resources.DataReader;
import EdControlsMain.ReusableFunctions.ReusableMethods;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

public class AdminRole extends BaseTest {

    public AdminRole() {
        super(null); // Temporary, driver will be initialized in BaseTest
    }

    public AdminRole(WebDriver driver) {
        super(driver);
    }


    // 1st test case
    @Test
    public void createProject() throws Exception {
        WebElement newProjectElement = driver.findElement(By.id("create-new-project"));
        ReusableMethods.waitForWebElementAppear(newProjectElement);
        Thread.sleep(3000);
        newProjectElement.click();
        WebElement newProjectLeftPanel = driver.findElement(By.xpath("//div[@class='new-project-form__left']"));
        String contractSelected = newProjectLeftPanel.findElement(By.xpath("//div[@class='MuiListItemText-root']")).getText();
        System.out.println("Contract Name : " + contractSelected);
        WebElement projectName = newProjectLeftPanel.findElement(By.id("projectName"));
        String projectText = "Automation Project";
        projectName.sendKeys(projectText);
        Thread.sleep(2000);
        newProjectLeftPanel.findElement(By.id("referenceName")).sendKeys("Automation Project refrence");
        Thread.sleep(2000);
        newProjectLeftPanel.findElement(By.id("internalPurchaseNumber")).sendKeys("Automation invoice");
        Thread.sleep(2000);
        WebElement location = newProjectLeftPanel.findElement(By.id("google-maps-select"));
        location.sendKeys("Bengaluru" + Keys.ENTER);
        //ReusableMethods.testGooglePlacesAutocomplete();
        Thread.sleep(2000);
        WebElement geoMapToggleElement = newProjectLeftPanel.findElement(By.xpath("//label[contains(@class,'project-toggle-switch_mt')]"));
        WebElement geoMapToggle = geoMapToggleElement.findElement(By.xpath("//span[contains(@class,'MuiSwitch-switchBase')]"));
        String toggleText = geoMapToggle.getDomAttribute("class");
        if (toggleText.contains("jss14 Mui-checked")) {
            System.out.println("Include geographical map in the Tickets module Toggle enabled");
        } else {
            System.out.println("isMandatoryProof Toggle not enabled");
            geoMapToggleElement.findElement(By.xpath("//input[@id='geoMapSwitch']")).click();
        }
        Thread.sleep(2000);
        driver.findElement(By.id("mandatoryTicketProofSwitch")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("pj-update-btn")).click();
        Thread.sleep(2000);

        //Adding users
        ProjectContainer.addUsersOnNewProject();
        Thread.sleep(2000);
        WebElement createProject = driver.findElement(By.xpath("//button[@id='pj-update-btn']"));
        createProject.click();

        // Checking the New ticket creation confirmation message is displayed or not
        String toastMessage = ReusableMethods.checkingToastMessage();
        System.err.println(toastMessage);
        Thread.sleep(2000);

        // Verifying that project is created or not
        WebElement projectSearch = driver.findElement(By.xpath("//input[@id='search']"));
        ReusableMethods.waitForWebElementByAppear(projectSearch);
        projectSearch.sendKeys(projectText + Keys.ENTER);
        List<WebElement> projectsList = driver.findElements(By.xpath("//p[@class='name']"));
        for (WebElement project : projectsList) {
            String projectCreated = project.getText();
            if (projectCreated.equalsIgnoreCase(projectText)) {
                System.out.println(projectText);
                System.err.println("Project " + projectText + " created successfully");
                // Checking if logged user is admin of that project
                String roleOfTheUser = driver.findElement(By.xpath("//p[@class='user-role']")).getText();
                if (roleOfTheUser.contains("Administrator")) {
                    System.out.println("Role of The User : " + roleOfTheUser);
                }
            }
        }


    }


}

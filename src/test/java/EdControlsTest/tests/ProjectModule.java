package EdControlsTest.tests;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.EdContainers.AuditContainer;
import EdControlsMain.EdContainers.ProjectContainer;
import EdControlsMain.EdContainers.TicketContainer;
import EdControlsMain.ReusableFunctions.ReusableMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.awt.geom.RectangularShape;
import java.time.Duration;
import java.util.List;

public class ProjectModule extends BaseTest {

    public ProjectModule() {
        super(null); // Temporary, driver will be initialized in BaseTest
    }
    public ProjectModule(WebDriver driver) {
        super(driver);
    }

    @Test
    public void multiProjectSelectOnTicketScreen() throws Exception {
        ProjectContainer.navigateToProject();
        Thread.sleep(2000);
        // Printing the total tickets after all projects selected
        Integer ticketsCount = TicketContainer.getTicketsCount();
        System.out.println("Tickets :"+ticketsCount);
        Thread.sleep(2000);
       // WebElement leftMenu = driver.findElement(By.xpath("//div[@id='left-menu']//div[@id='left-menu-scrollbar']"));
        WebElement leftMenu = driver.findElement(By.xpath("//div[@class='left-menu__project-container']//div[@class='left-menu__project-dropdown']"));
        WebElement projectContainer = leftMenu.findElement(By.xpath("//span[@class='projectname-tooltip']"));
        projectContainer.click();
        String currentSelectedProject = projectContainer.getText();
        System.out.println("Current Project Selected :"+currentSelectedProject);
        WebElement projectDropdown = leftMenu.findElement(By.xpath("//div[@class='project-dropdown']"));
        ReusableMethods.waitForWebElementByAppear(projectDropdown);
        WebElement contractName = projectDropdown.findElement(By.xpath("//div[@class='contract-detail']"));
        System.out.println(contractName.getText());
        List<WebElement> projectsList = projectDropdown.findElements(By.xpath("//div[@class='project-list']//ul/li"));
        System.out.println(projectsList.size());

        //let's select another project
        for (WebElement projects: projectsList)
        {
            if (!projects.getText().contains("Select all")){
                System.err.println("Project Name :"+projects.getText());
            }
            WebElement projectElement = projects.findElement(By.xpath("//div[@class='project-list__container']//span[@class='MuiIconButton-label']"));
            WebElement projectCheckbox = projectElement.findElement(By.xpath("//input[@type='checkbox']"));
            Thread.sleep(2000);
            if (projects.getDomAttribute("class").contains("select-all-project"))
            {
                Thread.sleep(2000);
                projectCheckbox.click();
                Thread.sleep(2000);
                projectDropdown.findElement(By.xpath("//div[@class='apply-project']//button[contains(@class,'btn--print')]")).click();
                break;
            }
        }

        // Printing the total tickets after all projects selected
        Integer ticketsCount2 = TicketContainer.getTicketsCount();
        System.out.println("Tickets present in multi project :"+ticketsCount2);

        //Confirming that, it's in 'read-only' mode

    }

    @Test
    public void multiProjectSelectOnAuditScreen() throws Exception {
        ProjectContainer.navigateToProject();
        Thread.sleep(2000);
        WebElement auditElement = driver.findElement(By.xpath("//div[@class='subHeader__container'] //li[@id='ed-audits']"));
        auditElement.click();
        // Printing the total audits after all projects selected
        Integer auditsCount = AuditContainer.getAuditsCount();
        System.out.println("Audits :"+auditsCount);
        Thread.sleep(2000);
        // WebElement leftMenu = driver.findElement(By.xpath("//div[@id='left-menu']//div[@id='left-menu-scrollbar']"));
        WebElement leftMenu = driver.findElement(By.xpath("//div[@class='left-menu__project-container']//div[@class='left-menu__project-dropdown']"));
        WebElement projectContainer = leftMenu.findElement(By.xpath("//span[@class='projectname-tooltip']"));
        projectContainer.click();
        String currentSelectedProject = projectContainer.getText();
        System.out.println("Current Project Selected :"+currentSelectedProject);
        WebElement projectDropdown = leftMenu.findElement(By.xpath("//div[@class='project-dropdown']"));
        ReusableMethods.waitForWebElementByAppear(projectDropdown);
        WebElement contractName = projectDropdown.findElement(By.xpath("//div[@class='contract-detail']"));
        System.out.println(contractName.getText());
        List<WebElement> projectsList = projectDropdown.findElements(By.xpath("//div[@class='project-list']//ul/li"));
        System.out.println(projectsList.size());

        //let's select another project
        for (WebElement projects: projectsList)
        {
            if (!projects.getText().contains("Select all")){
                System.err.println("Project Name :"+projects.getText());
            }
            WebElement projectElement = projects.findElement(By.xpath("//div[@class='project-list__container']//span[@class='MuiIconButton-label']"));
            WebElement projectCheckbox = projectElement.findElement(By.xpath("//input[@type='checkbox']"));
            Thread.sleep(2000);
            if (projects.getDomAttribute("class").contains("select-all-project"))
            {
                Thread.sleep(2000);
                projectCheckbox.click();
                Thread.sleep(2000);
                projectDropdown.findElement(By.xpath("//div[@class='apply-project']//button[contains(@class,'btn--print')]")).click();
                break;
            }
        }

        // Printing the total tickets after all projects selected
        Integer auditsCount2 = AuditContainer.getAuditsCount();
        System.out.println("Audits after multi projects selection :"+auditsCount2);

    }

}

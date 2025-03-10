package EdControlsTest.tests;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.EdContainers.ProjectContainer;
import EdControlsMain.EdContainers.UsersContainer;
import EdControlsMain.ReusableFunctions.DateFragment;
import EdControlsMain.ReusableFunctions.ReusableMethods;
import org.openqa.selenium.*;
import org.testng.annotations.Test;

import java.util.List;

public class AdminRole extends BaseTest {

    public AdminRole() {
        super(null); // Temporary, driver will be initialized in BaseTest
    }

    public AdminRole(WebDriver driver) {
        super(driver);
    }

    private String projectText; // Store project name for reuse

    // Test case 1 (Admin should be able to create the project)
    @Test
    public void createProject() throws Exception {
        ProjectContainer.initiateNewProjectCreation();
        Thread.sleep(2000);
        WebElement newProjectLeftPanel = driver.findElement(By.xpath("//div[@class='new-project-form__left']"));
        String contractSelected = newProjectLeftPanel.findElement(By.xpath("//div[@class='MuiListItemText-root']")).getText();
        System.out.println("Contract Name : " + contractSelected);
        WebElement projectName = newProjectLeftPanel.findElement(By.id("projectName"));
        projectText = "Automation Project";
        projectName.sendKeys(projectText);
        Thread.sleep(2000);
        newProjectLeftPanel.findElement(By.id("referenceName")).sendKeys("Automation Project reference");
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
        DateFragment.projectDatePicker(30);
        Thread.sleep(2000);
        driver.findElement(By.id("pj-update-btn")).click();
        Thread.sleep(2000);

        //Adding only accountable
        UsersContainer.addProjectAccountable();
        Thread.sleep(2000);
        WebElement createProject = driver.findElement(By.xpath("//button[@id='pj-update-btn']"));
        createProject.click();

        // Checking the New project creation confirmation message is displayed or not
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
                    break;
                }
            }
        }


    }

    // Test Case 2 (Admin should be able to edit the project details)
    @Test(dependsOnMethods = "createProject")
    public void checkProjectEditable() throws Exception {
        Thread.sleep(2000);
        ProjectContainer.editingProject(projectText); // Using stored project name
        Thread.sleep(2000);
        WebElement newProjectLeftPanel = driver.findElement(By.id("projectNameHeader"));
        if (newProjectLeftPanel.isDisplayed()) {
            System.out.println("Admin user can edit project");
        }
    }


    // Test case 3 (Admin should be able to add/edit accountable, support, consulted and informed user)
    @Test
    public void addUsersOnNewProject() throws Exception {
        ProjectContainer.initiateNewProjectCreation();
        Thread.sleep(2000);
        WebElement newProjectLeftPanel = driver.findElement(By.xpath("//div[@class='new-project-form__left']"));
        String contractSelected = newProjectLeftPanel.findElement(By.xpath("//div[@class='MuiListItemText-root']")).getText();
        System.out.println("Contract Name : " + contractSelected);
        WebElement projectName = newProjectLeftPanel.findElement(By.id("projectName"));
        String projectText = "Automation Project";
        projectName.sendKeys(projectText);
        Thread.sleep(2000);
        newProjectLeftPanel.findElement(By.id("referenceName")).sendKeys("Automation Project reference");
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

        //Adding all the users
        UsersContainer.addUsersOnNewProject();
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

    // Test Case 4 (He should be able to edit the project name, reference name, invoice name and reference name)
    @Test (dependsOnMethods = "createProject")
    public void editProject() throws Exception {
        Thread.sleep(2000);
        ProjectContainer.editingProject(projectText); // Using stored project name
        Thread.sleep(2000);
        WebElement editProjectLeftPanel = driver.findElement(By.xpath("//div[@class='project-form__container']"));
        String contractSelected = editProjectLeftPanel.findElement(By.id("contractSelected")).getText();
        System.out.println("Contract Name : " + contractSelected);
        WebElement projectName = editProjectLeftPanel.findElement(By.id("projectName"));
        String replaceProjectName = "Automation Project Dev";
        updateField(projectName, replaceProjectName);
        Thread.sleep(2000);
        String replaceReference = "Project reference";
        WebElement projectReference = editProjectLeftPanel.findElement(By.id("referenceName"));
        updateField(projectReference, replaceReference);
        Thread.sleep(2000);
        String replacePurchaseNumber = "12345";
        WebElement purchaseNumber = editProjectLeftPanel.findElement(By.id("internalPurchaseNumber"));
        updateField(purchaseNumber, replacePurchaseNumber);
        Thread.sleep(2000);
        driver.findElement(By.id("pj-save-btn")).click();
        // Checking the project edited confirmation message is displayed or not
        String toastMessage = ReusableMethods.checkingToastMessage();
        System.err.println(toastMessage);
        Thread.sleep(2000);

    }

    // **Helper method to clear text, press Enter, and enter new text**
    private void updateField(WebElement element, String newValue) throws InterruptedException {
        element.clear();  // First, clear the field
        Thread.sleep(500); // Small delay

        element.sendKeys(Keys.CONTROL + "a");  // Select all text
        element.sendKeys(Keys.BACK_SPACE);  // Delete the text
        Thread.sleep(500); // Wait for UI update

        element.sendKeys(Keys.ENTER); // Press Enter to confirm clearing
        Thread.sleep(500); // Wait for UI update

        element.sendKeys(newValue);  // Enter the new text
        element.sendKeys(Keys.TAB);  // Move focus away to trigger any UI updates
    }





}





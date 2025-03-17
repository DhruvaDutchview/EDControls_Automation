package EdControlsTest.tests;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.EdContainers.MapContainer;
import EdControlsMain.EdContainers.ProjectContainer;
import EdControlsMain.EdContainers.TemplateContainer;
import EdControlsMain.EdContainers.UsersContainer;
import EdControlsMain.ReusableFunctions.DateFragment;
import EdControlsMain.ReusableFunctions.FileUploadHelper;
import EdControlsMain.ReusableFunctions.ReusableMethods;
import EdControlsMain.ReusableFunctions.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminRole extends BaseTest {

    public AdminRole() {
        super(null); // Temporary, driver will be initialized in BaseTest
    }

    public AdminRole(WebDriver driver) {
        super(driver);
    }

    private String projectText;  // Store project name for reuse
    private String libraryGroupName;
    private String templateGroupName;



    // Test case 1 (Admin should be able to create the project)
    @Test(priority = 1)
    public void createProject() throws Exception {
        ProjectContainer.initiateNewProjectCreation();
        Thread.sleep(2000);
        WebElement newProjectLeftPanel = driver.findElement(By.xpath("//div[@class='new-project-form__left']"));
        // Capture Contract Name
        System.out.println("Contract Name : " + newProjectLeftPanel.findElement(By.xpath("//div[@class='MuiListItemText-root']")).getText());

        // Enter project details

        projectText = ReusableMethods.generateProjectName();
        newProjectLeftPanel.findElement(By.id("projectName")).sendKeys(projectText);
        newProjectLeftPanel.findElement(By.id("referenceName")).sendKeys("Automation Project reference");
        newProjectLeftPanel.findElement(By.id("internalPurchaseNumber")).sendKeys("Automation invoice");

        // Select Location
        newProjectLeftPanel.findElement(By.id("google-maps-select")).sendKeys("Bengaluru" + Keys.ENTER);
        Thread.sleep(2000);

        // Handle GeoMap Toggle
        WebElement geoMapToggleElement = newProjectLeftPanel.findElement(By.xpath("//label[contains(@class,'project-toggle-switch_mt')]"));
        WebElement geoMapToggle = geoMapToggleElement.findElement(By.xpath("//span[contains(@class,'MuiSwitch-switchBase')]"));

        if (!geoMapToggle.getDomAttribute("class").contains("Mui-checked")) {
            geoMapToggleElement.findElement(By.xpath("//input[@id='geoMapSwitch']")).click();
        }

        // Click mandatory proof switch & update button
        driver.findElement(By.id("mandatoryTicketProofSwitch")).click();
        DateFragment.projectDatePicker("Yes", "No", 20);
        driver.findElement(By.id("pj-update-btn")).click();
        Thread.sleep(2000);

        // Adding only accountable
        UsersContainer.addProjectAccountable();
        Thread.sleep(2000);

        // Click create project button
        driver.findElement(By.id("pj-update-btn")).click();
        Thread.sleep(2000);

        // Validate project creation
        System.err.println(ReusableMethods.checkingToastMessage());

        WebElement projectSearch = driver.findElement(By.id("search"));
        WaitUtils.waitForWebElementByAppear(projectSearch);
        projectSearch.sendKeys(projectText + Keys.ENTER);

        for (WebElement project : driver.findElements(By.xpath("//p[@class='name']"))) {
            if (project.getText().equalsIgnoreCase(projectText)) {
                System.err.println("Project " + projectText + " created successfully");

                // Check if the logged-in user is an Administrator
                if (driver.findElement(By.xpath("//p[@class='user-role']")).getText().contains("Administrator")) {
                    System.out.println("User Role: Administrator");
                }
                break;
            }
        }
    }


    // Test Case 2 (Admin should be able to edit the project details)
    @Test(dependsOnMethods = "createProject", priority = 2)
    public void checkProjectEditable() throws Exception {
        Thread.sleep(2000);
        ProjectContainer.editProject(projectText); // Using stored project name
        Thread.sleep(2000);
        WebElement newProjectLeftPanel = driver.findElement(By.id("projectNameHeader"));
        if (newProjectLeftPanel.isDisplayed()) {
            System.out.println("Admin user can edit project");
        }
    }


    // Test case 3 (Admin should be able to add/edit accountable, support, consulted and informed user)
    @Test(priority = 3)
    public void addUsersOnNewProject() throws Exception {
        // Check if projectText is empty; if so, create a new project
        if (projectText == null || projectText.isEmpty()) {
            createProject();
        }

        ProjectContainer.initiateNewProjectCreation();
        Thread.sleep(2000);
        WebElement newProjectLeftPanel = driver.findElement(By.xpath("//div[@class='new-project-form__left']"));
        String contractSelected = newProjectLeftPanel.findElement(By.xpath("//div[@class='MuiListItemText-root']")).getText();
        System.out.println("Contract Name : " + contractSelected);

        WebElement projectName = newProjectLeftPanel.findElement(By.id("projectName"));
        projectName.sendKeys(projectText);
        Thread.sleep(2000);

        newProjectLeftPanel.findElement(By.id("referenceName")).sendKeys("Automation Project reference");
        Thread.sleep(2000);
        newProjectLeftPanel.findElement(By.id("internalPurchaseNumber")).sendKeys("Automation invoice");
        Thread.sleep(2000);

        WebElement location = newProjectLeftPanel.findElement(By.id("google-maps-select"));
        location.sendKeys("Bengaluru" + Keys.ENTER);
        Thread.sleep(2000);

        WebElement geoMapToggleElement = newProjectLeftPanel.findElement(By.xpath("//label[contains(@class,'project-toggle-switch_mt')]"));
        WebElement geoMapToggle = geoMapToggleElement.findElement(By.xpath("//span[contains(@class,'MuiSwitch-switchBase')]"));
        String toggleText = geoMapToggle.getDomAttribute("class");

        if (!toggleText.contains("Mui-checked")) {
            System.out.println("GeoMap Toggle is not enabled, enabling now...");
            geoMapToggleElement.findElement(By.xpath("//input[@id='geoMapSwitch']")).click();
        }

        Thread.sleep(2000);
        driver.findElement(By.id("mandatoryTicketProofSwitch")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("pj-update-btn")).click();
        Thread.sleep(2000);

        UsersContainer.addUsersOnNewProject();
        Thread.sleep(2000);

        driver.findElement(By.id("pj-update-btn")).click();

        String toastMessage = ReusableMethods.checkingToastMessage();
        System.err.println(toastMessage);
        Thread.sleep(2000);

        WebElement projectSearch = driver.findElement(By.id("search"));
        WaitUtils.waitForWebElementByAppear(projectSearch);
        projectSearch.sendKeys(projectText + Keys.ENTER);

        List<WebElement> projectsList = driver.findElements(By.xpath("//p[@class='name']"));
        for (WebElement project : projectsList) {
            String projectCreated = project.getText();
            if (projectCreated.equalsIgnoreCase(projectText)) {
                System.out.println(projectText);
                System.err.println("Project " + projectText + " created successfully");
                String roleOfTheUser = driver.findElement(By.xpath("//p[@class='user-role']")).getText();
                if (roleOfTheUser.contains("Administrator")) {
                    System.out.println("Role of The User : " + roleOfTheUser);
                }
            }
        }

    }

    // Test Case 4 (He should be able to edit the project name, reference name, invoice name and reference name)
    @Test(dependsOnMethods = "createProject", priority = 4)
    public void editProject() throws Exception {
        if (projectText == null || projectText.isEmpty()) {
            createProject();
        }
        ProjectContainer.editProject(projectText); // Using stored project name
        WebElement editProjectLeftPanel = driver.findElement(By.xpath("//div[@class='project-form__container']"));
        WaitUtils.waitForWebElementByAppear(editProjectLeftPanel);
        String contractSelected = editProjectLeftPanel.findElement(By.id("contractSelected")).getText();
        System.out.println("Contract Name : " + contractSelected);
        WebElement projectName = editProjectLeftPanel.findElement(By.id("projectName"));
        String replaceProjectName = "Dhruva's Automation";
        ReusableMethods.updateField(projectName, replaceProjectName);
        Thread.sleep(2000);
        String replaceReference = "Replaced reference";
        WebElement projectReference = editProjectLeftPanel.findElement(By.id("referenceName"));
        ReusableMethods.updateField(projectReference, replaceReference);
        Thread.sleep(2000);
        String replacePurchaseNumber = "12345";
        WebElement purchaseNumber = editProjectLeftPanel.findElement(By.id("internalPurchaseNumber"));
        ReusableMethods.updateField(purchaseNumber, replacePurchaseNumber);
        Thread.sleep(2000);
        WebElement saveProject = driver.findElement(By.id("pj-save-btn"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveProject);
        // Checking the project edited confirmation message is displayed or not
        String toastMessage = ReusableMethods.checkingToastMessage();
        System.err.println(toastMessage);
        Thread.sleep(2000);

    }

    // Test case 5 (He should be able to add/edit the end date of the project)
    @Test(priority = 5)
    public void addDueDate() throws Exception {
        if (projectText == null || projectText.isEmpty()) {
            createProject();
        }
        Thread.sleep(2000);
        ProjectContainer.editProject(projectText); // Using stored project name
        WebElement editProjectLeftPanel = driver.findElement(By.xpath("//div[@class='project-form__container']"));
        WaitUtils.waitForWebElementByAppear(editProjectLeftPanel);
        WebElement endDateElement = driver.findElement(By.id("pj-end-date"));
        WebElement startDateElement = driver.findElement(By.id("pj-start-date"));
        Thread.sleep(2000);
        String endDateValue = endDateElement.getText().trim();
        System.out.println("End Date before modification: " + endDateValue);

        // Regex pattern to match a valid date format (e.g., "21 Apr 2025")
        String datePattern = "\\d{1,2} [A-Za-z]{3} \\d{4}";
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(endDateValue);

        // if end date is present, let's clear the date and new date
        if (matcher.matches()) {
            // Click the remove date button to clear the existing date
            WebElement removeDateButton = driver.findElement(By.xpath("//button[@class='remove-date-btn']//*[name()='svg']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('click', { bubbles: true }));", removeDateButton);
            Thread.sleep(2000);

            // Re-locate and check if the end date is cleared
            endDateElement = driver.findElement(By.id("pj-end-date"));
            String endDateValue2 = endDateElement.getText().trim();
            if (endDateValue2.contains("End date")) {
                System.out.println("End date cleared successfully and Selecting a new date");
                DateFragment.projectDatePicker("No", "Yes", 50);  // Add new date
            } else {
                System.err.println("End date is still present!");
            }
            Thread.sleep(2000);
            WebElement saveProject = driver.findElement(By.id("pj-save-btn"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveProject);
        } else {
            System.out.println("Adding end date");
            DateFragment.projectDatePicker("No", "Yes", 40);
        }
        System.out.println("Start Date selected: " + startDateElement.getText());
        System.out.println("Due Date selected: " + endDateElement.getText());
        Thread.sleep(2000);
        WebElement saveProject = driver.findElement(By.id("pj-save-btn"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveProject);

        // Checking the project edited confirmation message
        String toastMessage = ReusableMethods.checkingToastMessage();
        System.err.println(toastMessage);
        Thread.sleep(3000);
    }

    // Test case 6 (Admin should be able to archive the project)
    @Test(priority = 6)
    public void archiveProject() throws Exception {
        String projectName = "Automation Project - Dhruv";
        boolean projectFound = false;

        // Before archiving, checking if the project exists in the active list
        for (WebElement project : driver.findElements(By.xpath("//p[@class='name']"))) {
            if (project.getText().equalsIgnoreCase(projectName)) {
                projectFound = true;
                if (driver.findElement(By.xpath("//p[@class='user-role']")).getText().contains("Administrator")) {
                    System.out.println("User Role: Administrator");
                }
                break;
            }
        }

        // If project not found, try de-archiving first
        if (!projectFound) {
            System.out.println("Project not found in active list, attempting to de-archive...");
            deArchiveProject();
            Thread.sleep(2000); // Adding a small wait to ensure UI updates
        }

        // Proceed with archiving after confirming the project is in the active list
        //ProjectContainer.editProject(projectName);
        //Thread.sleep(2000);
        ProjectContainer.projectArchiveDearchive(driver.findElements(By.xpath("//p[@class='name']")), projectName);
      /*  WebElement archiveElement = driver.findElement(By.id("projArchive"));
        ReusableMethods.waitForWebElementByAppear(archiveElement);
        archiveElement.click();*/

        // Checking the project archive confirmation message
        String toastMessage = ReusableMethods.checkingToastMessage();
        System.err.println(toastMessage);
        Thread.sleep(3000);

        // clear the search
        WebElement projectSearch = WaitUtils.waitForElementAppear(By.xpath("//input[@id='search']"));
        ReusableMethods.clearSingleElement(projectSearch);
        Thread.sleep(2000);
    }

    // Test case 7 (Admin should be able to de-archive the project)
    @Test(dependsOnMethods = "archiveProject", priority = 7)
    public void deArchiveProject() throws Exception {
        WebElement archiveElement = driver.findElement(By.xpath("//label[@for='archive']"));
        archiveElement.click();
        String projectName = "Automation Project - Dhruv";
        List<WebElement> projectNameElements = driver.findElements(By.xpath("//p[@class='name']"));

        for (WebElement projectElement : projectNameElements) {
            String expectedProject = projectElement.getText();
            Thread.sleep(2000);
            if (expectedProject.contains(projectName)) {
                ProjectContainer.projectArchiveDearchive(projectNameElements, projectName);
                break;
            }
        }

        // Checking the project archive confirmation message
        String toastMessage = ReusableMethods.checkingToastMessage();
        System.err.println(toastMessage);
        Thread.sleep(2000);

        // Re-locate the archive element again before clicking
        archiveElement = driver.findElement(By.xpath("//label[@for='archive']"));
        // Use JavaScriptExecutor to click
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", archiveElement);

        // clear the search
        WebElement projectSearch = WaitUtils.waitForElementAppear(By.xpath("//input[@id='search']"));
        ReusableMethods.clearSingleElement(projectSearch);
        Thread.sleep(2000);
    }

    // Test case 8 (Admin should be able to add/edit the project and company logo)
    @Test
    public void addEditLogo() throws Exception {
      /*  if (projectText == null || projectText.isEmpty()) {
            createProject();
        }*/
        ProjectContainer.editProject("Test acc");
        WebElement imageUploadElement = driver.findElement(By.xpath("//div[@class='project-form__right']//div[@class='project-right-img-actions']"));
        List<WebElement> imageElements = imageUploadElement.findElements(By.xpath("//div[@class='ml-0']"));
        for (WebElement imageElement : imageElements) {
            String imageElementText = imageElement.getText();
            System.out.println(imageElementText);
            if (imageElementText.contains("PROJECT IMAGE")) {
                imageElement.click();
                Thread.sleep(2000);
                FileUploadHelper.uploadLatestImageUsingAppleScript();
                Thread.sleep(2000);
                break;
            }
        }

    }

    // Test Case 9 (Should be able to create a library group)
    @Test
    public void createLibraryGroup() throws Exception {
        ProjectContainer.navigateToProject();
        Thread.sleep(1000);
        ProjectContainer.navigateToModule(driver.findElement(By.id("ed-maps")));
        Thread.sleep(2000);
        libraryGroupName = MapContainer.createLibraryGroup("Library Group 1");
        System.err.println(ReusableMethods.checkingToastMessage());
    }

    // Deleting a library group
    @Test(dependsOnMethods = "createLibraryGroup")
    public void deleteLibraryGroup() throws Exception {
        ProjectContainer.navigateToProject();
        Thread.sleep(1000);
        ProjectContainer.navigateToModule(driver.findElement(By.id("ed-maps")));
        Thread.sleep(2000);
        MapContainer.deleteLibraryGroup(libraryGroupName);
        System.err.println(ReusableMethods.checkingToastMessage());
    }

    // Test Case 10 (Admin should be able to create a template group)
    @Test
    public void createTemplateGroup() throws Exception {
        ProjectContainer.navigateToProject();
        Thread.sleep(2000);
        ProjectContainer.navigateToModule(driver.findElement(By.id("ed-templates")));
        templateGroupName =  TemplateContainer.createTemplateGroup("TG - Automation");
        System.err.println(ReusableMethods.checkingToastMessage());
    }

    // Test Case 11 (Should be able to create a template)
    @Test
    public void createTemplate() throws Exception {
        ProjectContainer.navigateToProject();
        Thread.sleep(2000);
        ProjectContainer.navigateToModule(driver.findElement(By.id("ed-templates")));
        Thread.sleep(2000);
        TemplateContainer.createAreaTemplate("TG - Automation", "Automation Template");
    }


}





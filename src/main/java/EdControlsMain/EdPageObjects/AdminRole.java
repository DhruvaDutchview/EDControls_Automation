package EdControlsMain.EdPageObjects;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.EDFragments.DateFragment;
import EdControlsMain.EDFragments.ImagesFragment;
import EdControlsMain.ReusableFunctions.ReusableMethods;
import EdControlsMain.EDFragments.WaitUtilsFragment;
import org.openqa.selenium.*;

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
    private String userName;
    private String replaceUser;

    public void createProject() throws Exception {
        projectText = ProjectContainer.createProject();
        WebElement projectSearch = driver.findElement(By.xpath("//input[@id='search']"));
        WaitUtilsFragment.waitForWebElementAppear(projectSearch);
        projectSearch.sendKeys(projectText + Keys.ENTER);

        WaitUtilsFragment.waitForElementToBeVisible(driver.findElement(By.id("add-user-btn")));
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

    public void checkProjectEditable() throws Exception {
        Thread.sleep(2000);
        ProjectContainer.editProject(projectText); // Using stored project name
        Thread.sleep(2000);
        WebElement newProjectLeftPanel = driver.findElement(By.id("projectNameHeader"));
        if (newProjectLeftPanel.isDisplayed()) {
            System.out.println("Admin user can edit project");
        }
    }

    public void addUsersOnNewProject() throws Exception {
        ProjectContainer.initiateNewProjectCreation();
        Thread.sleep(2000);
        // Capture Contract Name
        System.out.println("Contract Name : " + driver.findElement(By.xpath("//div[@class='MuiListItemText-root']")).getText());

        // Enter project details

        projectText = ProjectContainer.getUniqueName(ProjectContainer.NameType.AUTOMATION_PROJECT);
        driver.findElement(By.id("projectName")).sendKeys(projectText);
        driver.findElement(By.id("referenceName")).sendKeys("Automation Project reference");
        driver.findElement(By.id("internalPurchaseNumber")).sendKeys("Automation invoice");
        driver.findElement(By.id("google-maps-select")).sendKeys("Bengaluru" + Keys.ENTER);
        Thread.sleep(2000);

        // Handle GeoMap Toggle
        WebElement geoMapToggleElement = driver.findElement(By.id("geoMapSwitch"));
        WebElement geoMapToggle = geoMapToggleElement.findElement(By.xpath("//span[contains(@class,'MuiSwitch-switchBase')]"));
        if (!geoMapToggle.getDomAttribute("class").contains("Mui-checked")) {
            geoMapToggleElement.click();
        }

        // Click mandatory proof switch & update button
        driver.findElement(By.id("mandatoryTicketProofSwitch")).click();
        DateFragment.projectDatePicker("Yes", "No", 20);
        Thread.sleep(2000);
        WaitUtilsFragment.waitForWebElementToClick(driver.findElement(By.id("pj-update-btn"))).click();

        UsersContainer.addUsersOnNewProject();
        Thread.sleep(2000);

        driver.findElement(By.id("pj-update-btn")).click();

        String toastMessage = ReusableMethods.checkingToastMessage();
        System.err.println(toastMessage);
        Thread.sleep(2000);

        WebElement projectSearch = driver.findElement(By.id("search"));
        WaitUtilsFragment.waitForWebElementByAppear(projectSearch);
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

    public void editProject() throws Exception {
        ProjectContainer.editProject(projectText); // Using stored project name
        System.out.println("Contract Name : " + WaitUtilsFragment.waitForElementToBeVisible(driver.findElement(By.xpath("//div[@class='MuiListItemText-root']"))).getText());
        WebElement projectName = driver.findElement(By.id("projectName"));
        String replaceProjectName = "Dhruva's Automation";
        ReusableMethods.updateField(projectName, replaceProjectName);
        Thread.sleep(2000);
        WebElement projectReference = driver.findElement(By.id("referenceName"));
        ReusableMethods.updateField(projectReference, "Replaced reference");
        Thread.sleep(2000);
        WebElement purchaseNumber = driver.findElement(By.id("internalPurchaseNumber"));
        ReusableMethods.updateField(purchaseNumber, "12345");
        Thread.sleep(2000);
        WebElement saveProject = driver.findElement(By.id("pj-save-btn"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveProject);
        // Checking the project edited confirmation message is displayed or not
        String toastMessage = ReusableMethods.checkingToastMessage();
        System.err.println(toastMessage);
        Thread.sleep(2000);
        projectText = replaceProjectName;
    }

    public void addEditLogo() throws Exception {
        ProjectContainer.editProject(projectText);
        Thread.sleep(2000);
        ImagesFragment.uploadImagesOnProject();
        Thread.sleep(1000);
        UsersContainer.addUsersOnExistingProject();
        Thread.sleep(1000);
        WebElement saveProject = driver.findElement(By.id("pj-save-btn"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", saveProject);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveProject);
        // WebElement confirmEdit = driver.findElement(By.id("dialog-ok"));
        //  ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmEdit);
    }

    public void addDueDate() throws Exception {
        ProjectContainer.editProject(projectText); // Using stored project name
        WebElement endDateElement = WaitUtilsFragment.waitForElementToBeVisible(driver.findElement(By.id("pj-end-date")));
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

    public void archiveProject() throws Exception {
        String projectName = projectText;
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

        // If a project not found, try de-archiving first
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
       // WebElement projectSearch = WaitUtilsFragment.waitForElementAppear(By.xpath("//input[@id='search']"), driver);
        WebElement closeSearch = WaitUtilsFragment.waitForWebElementToClick(driver.findElement(By.xpath("//div[@class='search_container']/*[name()='svg']")));
        // Trigger a real click event for SVG using JavaScript
        String jsScript = "var event = new MouseEvent('click', {bubbles: true, cancelable: true, view: window});"
                + "arguments[0].dispatchEvent(event);";
        ((JavascriptExecutor) driver).executeScript(jsScript, closeSearch);
       // ReusableMethods.clearSingleElement(projectSearch);
        Thread.sleep(2000);
    }

    public void deArchiveProject() throws Exception {
        WebElement archiveElement = driver.findElement(By.xpath("//input[@id='archive']/following-sibling::label"));
        archiveElement.click();
        String projectName = projectText;
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
        archiveElement = driver.findElement(By.xpath("//input[@id='archive']/following-sibling::label"));
        // Use JavaScriptExecutor to click
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Thread.sleep(2000);
        js.executeScript("arguments[0].click();", archiveElement);

        // clear the search
       // WebElement projectSearch = WaitUtilsFragment.waitForElementAppear(By.xpath("//input[@id='search']"), driver);
        WebElement closeSearch = WaitUtilsFragment.waitForWebElementToClick(driver.findElement(By.xpath("//div[@class='search_container']/*[name()='svg']")));
        // Trigger a real click event for SVG using JavaScript
        String jsScript = "var event = new MouseEvent('click', {bubbles: true, cancelable: true, view: window});"
                + "arguments[0].dispatchEvent(event);";
        ((JavascriptExecutor) driver).executeScript(jsScript, closeSearch);
        //ReusableMethods.clearSingleElement(projectSearch);
        Thread.sleep(2000);
    }

    public void createLibraryGroup() throws Exception {
        ProjectContainer.navigateToProject(projectText);
        Thread.sleep(1000);
        ProjectContainer.navigateToModule(driver.findElement(By.id("ed-maps")));
        Thread.sleep(2000);
        libraryGroupName = ProjectContainer.getUniqueName(ProjectContainer.NameType.LIBRARY_GROUP);
        MapContainer.createLibraryGroup(libraryGroupName);
        System.err.println(ReusableMethods.checkingToastMessage());
    }

    public void deleteLibraryGroup() throws Exception {
        ProjectContainer.navigateToProject(projectText);
        Thread.sleep(1000);
        ProjectContainer.navigateToModule(driver.findElement(By.id("ed-maps")));
        Thread.sleep(2000);
        MapContainer.deleteLibraryGroup(libraryGroupName);
        System.err.println(ReusableMethods.checkingToastMessage());
    }

    public void createTemplateGroup() throws Exception {
        ProjectContainer.navigateToProject(projectText);
        Thread.sleep(2000);
        ProjectContainer.navigateToModule(driver.findElement(By.id("ed-templates")));
        templateGroupName = ProjectContainer.getUniqueName(ProjectContainer.NameType.TEMPLATE_GROUP);
        TemplateContainer.createTemplateGroup(templateGroupName);
        System.err.println(ReusableMethods.checkingToastMessage());
    }

    public void createAreaTemplate() throws Exception {
        ProjectContainer.navigateToProject("Automation Project - Dhruv");
        Thread.sleep(2000);
        ProjectContainer.navigateToModule(driver.findElement(By.id("ed-templates")));
        Thread.sleep(2000);
        TemplateContainer.createAreaTemplate(templateGroupName,"Automation Template (Area)", "area" );
    }

    public void createObjectTemplate() throws Exception {
        ProjectContainer.navigateToProject("Automation Project - Dhruv");
        Thread.sleep(2000);
        ProjectContainer.navigateToModule(driver.findElement(By.id("ed-templates")));
        Thread.sleep(2000);
        TemplateContainer.createObjectTemplate(templateGroupName, "Automation Template (Object)");
    }

    public void replaceUserInUserManagement() throws Exception {
        WebElement currentUser = driver.findElement(By.id("mh-current-user"));
        currentUser.click();
        Thread.sleep(2000);

        WebElement dropdown = WaitUtilsFragment.waitForWebElementToClick(driver.findElement(By.id("menu-list-grow")));
        WebElement contactInfo = WaitUtilsFragment.waitForWebElementToClick(dropdown.findElement(By.xpath("//li[contains(text(),'Contract information')]")));
        contactInfo.click();

        WebElement userContainer = WaitUtilsFragment.waitForElementToBeVisible(driver.findElement(By.xpath("//div[@class='users-table']")));
        WebElement userBody = userContainer.findElement(By.xpath("//section[@class='users-body']"));

        //Printing all the users from a user's list
        List<WebElement> usersList = UsersContainer.getUsersFromUsersManagement(userBody);

        // randomly searching and select the user
        WebElement modifyingUser = usersList.get(4).findElement(By.xpath(".//span[@class='email']"));
        WebElement searchElement = driver.findElement(By.id("search"));
        searchElement.sendKeys(modifyingUser.getText());

        //Click on this user, So re-fetching users list
        List<WebElement> usersList2 = UsersContainer.getUsersFromUsersManagement(userBody);
        // List<WebElement> usersList2 = userBody.findElements(By.xpath(".//div[contains(@class,'user-data ')]"));  // Updated to use .//

        // Iterate through each user row
        for (WebElement users : usersList2) {
            WebElement userEmailElement = users.findElement(By.xpath(".//span[@class='email']"));  // Updated to use .//
            WebElement userNameElement = users.findElement(By.xpath(".//span"));  // Updated to use .//

            userName = userNameElement.getText();
            String userEmail = userEmailElement.getText();
            System.err.println("User Name: " + userName + " | User Email: " + userEmail);
            if (!usersList2.isEmpty()) {
                usersList2.get(0).click();
                System.out.println("Selecting first user :" + userName);
            } else {
                System.out.println("Searched users list is empty");
            }
        }

        //Selecting projects
        Thread.sleep(2000);
        WebElement selectProjectElement = WaitUtilsFragment.waitForWebElementAppear(driver.findElement(By.xpath("//div[@class='projects-roles']//label[@class='checkbox-container']")));
        WebElement selectAll = selectProjectElement.findElement(By.xpath(".//span[@class='checkmark']"));
        if (selectAll.isDisplayed()) {
            try {
                selectAll.click();
                System.out.println("Check box is selected : " + selectAll.isSelected());
            } catch (Exception e) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", selectAll);
                System.out.println("Check box is selected : " + selectAll.isSelected());
            }
        }

        Thread.sleep(2000);
        // Click the Replace button
        WebElement buttonContainer = driver.findElement(By.xpath("//section[@class='action-buttons']"));
        List<WebElement> buttonElements = buttonContainer.findElements(By.xpath("//button[@type='button']"));
        for (WebElement button : buttonElements) {
            String buttonText = button.getText();
            if (buttonText.contains("Replace")) {
                button.click();
                break;
            }
        }

        // Replacing the user
        WebElement replacePopup = WaitUtilsFragment.waitForElementToBeVisible(driver.findElement(By.xpath("//div[@class='replace-popup']")));
        WebElement emailReplaceElement = driver.findElement(By.id("email-input-replace"));
        Thread.sleep(1000);
        replaceUser = "replaceuser@mailinator.com";
        emailReplaceElement.sendKeys(replaceUser + Keys.ENTER);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[contains(@class,'MuiButton-contained')]")).click();
        Thread.sleep(2000);
        BulkContainer.bulkConfirm();
        System.out.println(ReusableMethods.checkingToastMessage());
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[contains(text(), 'OK')]")).click();

    }

    public void removeUserInUserManagement() throws Exception {
        WebElement currentUser = driver.findElement(By.id("mh-current-user"));
        currentUser.click();
        Thread.sleep(2000);

        WebElement dropdown = WaitUtilsFragment.waitForWebElementToClick(driver.findElement(By.id("menu-list-grow")));
        WebElement contactInfo = WaitUtilsFragment.waitForWebElementToClick(dropdown.findElement(By.xpath("//li[contains(text(),'Contract information')]")));
        contactInfo.click();

        WebElement userContainer = WaitUtilsFragment.waitForElementToBeVisible(driver.findElement(By.xpath("//div[@class='users-table']")));
        WebElement userBody = userContainer.findElement(By.xpath("//section[@class='users-body']"));

        //Printing all the users from a user's list
        List<WebElement> usersList = UsersContainer.getUsersFromUsersManagement(userBody);
        for (WebElement users : usersList) {
            WebElement userEmailElement = users.findElement(By.xpath(".//span[@class='email']"));  // Updated to use .//
            WebElement userNameElement = users.findElement(By.xpath(".//span"));  // Updated to use .//

            userName = userNameElement.getText();
            String userEmail = userEmailElement.getText();
            if (!usersList.isEmpty()) {
                // fetching and removing replaced user
                if (userEmail.contains(replaceUser)) {
                    userEmailElement.click();
                }
            } else {
                System.out.println("Searched users list is empty");
            }
        }

        Thread.sleep(2000);
        //click on select all checkboxes of projects
        WebElement selectProjectElement = WaitUtilsFragment.waitForWebElementAppear(driver.findElement(By.xpath("//div[@class='projects-roles']//label[@class='checkbox-container']")));
        WebElement selectAll = selectProjectElement.findElement(By.xpath(".//span[@class='checkmark']"));
        if (selectAll.isDisplayed()) {
            try {
                selectAll.click();
                System.out.println("Check box is selected : " + selectAll.isSelected());
            } catch (Exception e) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", selectAll);
                System.out.println("Check box is selected : " + selectAll.isSelected());
            }
        }
        Thread.sleep(2000);

        // Click on remove user
        WebElement buttonContainer = driver.findElement(By.xpath("//section[@class='action-buttons']"));
        List<WebElement> buttonElements = buttonContainer.findElements(By.xpath("//button[@type='button']"));
        for (WebElement button : buttonElements) {
            String buttonText = button.getText();
            if (buttonText.contains("Remove")) {
                button.click();
                break;
            }
        }
        Thread.sleep(2000);

        //Removing the user
        WebElement replacePopup = WaitUtilsFragment.waitForElementToBeVisible(driver.findElement(By.xpath("//div[@class='remove-popup']")));
        Thread.sleep(1000);

        // checking if removing user is accountable or responsible or both
        WebElement responsibleUserElement = null;
        try {
            responsibleUserElement = WaitUtilsFragment.waitForWebElementToClick(driver.findElement(By.id("email-input-remove-res")));
            if (responsibleUserElement.isDisplayed()) {
                responsibleUserElement.click();
                responsibleUserElement.sendKeys("replacedresponsible@gmail.com" + Keys.ENTER);
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            System.out.println("Responsible user input not found, skipping...");
        }

        // Check if accountable user removal field is visible
        WebElement accountableElement = null;
        try {
            accountableElement = WaitUtilsFragment.waitForWebElementToClick(driver.findElement(By.id("email-input-remove-acc")));
            if (accountableElement.isDisplayed()) {
                accountableElement.click();
                accountableElement.sendKeys("replacedacc@mailinator.com" + Keys.ENTER);
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            System.out.println("Accountable user input not found, skipping...");
        }

        // If neither responsible nor accountable user fields are visible
        if (responsibleUserElement == null && accountableElement == null) {
            System.out.println("Neither Responsible nor Accountable user input fields are found, skipping this section.");
        }
        // Remove the user completely
           /* // Wait until the dropdown options become visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            List<WebElement> dropdownOptions = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//div[contains(@aria-activedescendant, 'email-input-remove-res-option')]") // Adjust based on actual structure
            ));

            // Loop through the options and select "No Responsible"
            for (WebElement option : dropdownOptions) {
                if (option.getText().trim().equalsIgnoreCase("No Responsible")) {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(option).click().perform();
                    break;
                }
            }
            // Wait for dropdown options to be visible
            Thread.sleep(2000); // You can replace this with an explicit wait

          // Locate and click the "No Responsible" option
         //   WebElement noResponsibleOption = driver.findElement(By.xpath("//li[contains(text(),'No Responsible')]"));
          //  noResponsibleOption.click();*/
        driver.findElement(By.xpath("//button[contains(@class,'MuiButton-contained')]")).click();
        Thread.sleep(2000);
        BulkContainer.bulkConfirm();
        System.out.println(ReusableMethods.checkingToastMessage());
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[contains(text(), 'OK')]")).click();
    }

    public void editContractInfo() throws InterruptedException {
        WebElement currentUser = driver.findElement(By.id("mh-current-user"));
        currentUser.click();
        Thread.sleep(2000);

        WebElement dropdown = WaitUtilsFragment.waitForWebElementToClick(driver.findElement(By.id("menu-list-grow")));
        WebElement contactInfo = WaitUtilsFragment.waitForWebElementToClick(dropdown.findElement(By.xpath("//li[contains(text(),'Contract information')]")));
        contactInfo.click();
        Thread.sleep(1000);
        WebElement contactInfoScreen = WaitUtilsFragment.waitForWebElementToClick(driver.findElement(By.linkText("Contract information")));
        contactInfoScreen.click();
        Thread.sleep(2000);

        WebElement contractContainer = WaitUtilsFragment.waitForWebElementAppear(driver.findElement(By.xpath("//div[@class='contract-info__form']")));
        System.out.println("Contract Name : " + contractContainer.findElement(By.xpath("//input[@id='contractName']")).getDomAttribute("value"));

        WebElement referName = contractContainer.findElement(By.id("refName"));
        referName.click();
        referName.sendKeys("Automation 101");
        Thread.sleep(2000);
        WebElement contractPerson = contractContainer.findElement(By.id("contactPerson"));
        contractPerson.click();
        contractPerson.sendKeys("Dhruva Kumar KR");


    }


}





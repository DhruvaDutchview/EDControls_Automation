package EdControlsMain.EdPageObjects;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.EDFragments.CategoryFragment;
import EdControlsMain.Resources.DataReader;
import EdControlsMain.EDFragments.WaitUtilsFragment;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class TemplateContainer extends BaseTest {

    static WebDriverWait wait;

    public TemplateContainer(WebDriver driver) {
        super(driver);
    }

    public static void selectAreaAuditTemplate() throws Exception {
        Thread.sleep(2000);
        WebElement auditLeftPanel = driver.findElement(By.xpath("//div[@class='audits-filter']"));
        WaitUtilsFragment.waitForWebElementAppear(auditLeftPanel);
        List<WebElement> templateGroupsList = auditLeftPanel.findElements(By.xpath("//div[contains(@class,'MuiPaper-root MuiAccordion-root filter-inner-accordian')]"));
        for (WebElement templateGroups : templateGroupsList) {
            String templateGroupsText = templateGroups.getText();
            Thread.sleep(2000);
            if (templateGroupsText.contains("Star")) {
                templateGroups.click();
                WebElement ele = templateGroups.findElement(By.xpath("//div[contains(@class,'MuiCollapse-root MuiCollapse-entered')] //div[@class='MuiAccordionDetails-root']"));
                WebElement ele2 = ele.findElement(By.xpath("//div[@class='audit-left-template']"));
                List<WebElement> templateList = ele2.findElements(By.xpath("//div[@id='single-template']"));
                System.out.println(templateList.size());
                for (WebElement templates : templateList) {
                    String templatesText = templates.getText();
                    if (templatesText.contains("Star (area audit)")) {
                        System.out.println(templatesText);
                        templates.click();
                        return;
                    }
                }

            }

        }

    }

    public static void selectObjectAuditTemplate() throws Exception {
        Thread.sleep(2000);
        WebElement auditLeftPanel = driver.findElement(By.xpath("//div[@class='audits-filter']"));
        WaitUtilsFragment.waitForWebElementAppear(auditLeftPanel);
        List<WebElement> templateGroupsList = auditLeftPanel.findElements(By.xpath("//div[contains(@class,'MuiPaper-root MuiAccordion-root filter-inner-accordian')]"));
        for (WebElement templateGroups : templateGroupsList) {
            String templateGroupsText = templateGroups.getText();
            Thread.sleep(2000);
            if (templateGroupsText.contains("Star")) {
                templateGroups.click();
                WebElement ele = templateGroups.findElement(By.xpath("//div[contains(@class,'MuiCollapse-root MuiCollapse-entered')] //div[@class='MuiAccordionDetails-root']"));
                WebElement ele2 = ele.findElement(By.xpath("//div[@class='audit-left-template']"));
                List<WebElement> templateList = ele2.findElements(By.xpath("//div[@id='single-template']"));
                System.out.println(templateList.size());
                for (WebElement templates : templateList) {
                    String templatesText = templates.getText();
                    if (templatesText.contains("Star (Object audit)")) {
                        System.err.println(templatesText);
                        templates.click();
                        return;
                    }
                }

            }

        }

    }

    public static String createTemplateGroup(String templateGroupName) throws Exception {
        WebElement tempElement = driver.findElement(By.id("template-new-group"));
        tempElement.click();
        WebElement createNewGroup = tempElement.findElement(By.xpath("//input[@type='text']"));
        WaitUtilsFragment.waitForWebElementByAppear(createNewGroup);
        Thread.sleep(2000);
        createNewGroup.sendKeys(templateGroupName + Keys.ENTER);
        Thread.sleep(2000);
        WebElement templateLeftPanel = driver.findElement(By.xpath("//div[@class='template-filters']//div[contains(@class,'MuiAccordionDetails-root')]"));
        Thread.sleep(2000);
        try {
            WebElement showMoreButton = templateLeftPanel.findElement(By.xpath("//span[@class='show-more']"));
            if (showMoreButton.isDisplayed()) {
                showMoreButton.click();
            } else {
                System.out.println("Show More button is not visible, skipping click.");
            }
        } catch (Exception e) {
            System.out.println("Show More button not found, skipping click.");
        }
        List<WebElement> templateGroups = templateLeftPanel.findElements(By.xpath("//div[@class='group-item ']"));
        Thread.sleep(2000);
        System.out.println("Template Groups : " + templateGroups.size());
        for (WebElement templateGroup : templateGroups) {
            System.out.println(templateGroup.getText());
            Thread.sleep(2000);
            if (templateGroup.getText().contains(templateGroupName)) {
                System.err.println("Template group: " + templateGroup.getText() + "created");
            }
        }
        return templateGroupName;
    }

    public static void createAreaTemplate(String templateGroupName, String templateName) throws Exception {
        WebElement templateLeftPanel = driver.findElement(By.xpath("//div[@class='template-filters']//div[contains(@class,'MuiAccordionDetails-root')]"));
        templateLeftPanel.findElement(By.xpath("//span[@class='show-more']")).click();
        List<WebElement> templateGroups = templateLeftPanel.findElements(By.xpath("//div[@class='group-item ']"));
        Thread.sleep(2000);
        System.out.println("Template Groups : " + templateGroups.size());
        for (WebElement templateGroup : templateGroups) {
            System.out.println(templateGroup.getText());
            if (templateGroup.getText().contains(templateGroupName)) {
                System.err.println("Template group: " + templateGroup.getText() + "found");
                templateGroup.click();
                Thread.sleep(2000);
                driver.findElement(By.id("ed-butn-new_template")).click();
                break;
            }
        }
        WebElement tempNameElement = WaitUtilsFragment.waitForElementToBeVisible(driver.findElement(By.id("input_audit_title")));
        tempNameElement.click();
        Thread.sleep(2000);
        tempNameElement.sendKeys(templateName + Keys.ENTER);
        Thread.sleep(2000);
        WebElement tagElement = driver.findElement(By.xpath("//div[@class='temp-tag-container']//div[@role='combobox']//input[@type='text']"));
        tagElement.click();
        Thread.sleep(2000);
        tagElement.sendKeys("Tag Automation template " + Keys.ENTER);
        Thread.sleep(2000);
        WebElement templateInformed = driver.findElement(By.id("template-informed"));
        templateInformed.click();
        templateInformed.sendKeys(DataReader.getValueFromJsonFile("projectInformed") + Keys.ENTER);
        Thread.sleep(2000);

        WebElement templateBody = driver.findElement(By.xpath("//div[@class='addEditTemplate__body']"));
       // Locate the category container once
        WebElement categoryContainer = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(templateBody.findElement(By.xpath("//div[@data-rbd-droppable-id='droppable']"))));

        // Maximum categories to process
        int maxCategories = 2;

        // Loop through categories
        for (int index = 0; index < maxCategories; index++) {
            // Find the category elements dynamically based on index
            List<WebElement> categoryElements = categoryContainer.findElements(By.xpath("//div[@data-rbd-draggable-id='cat-" + index + "']"));

            if (!categoryElements.isEmpty()) {
                WebElement categoryElement = categoryElements.get(0); // Pick the first matching element

                System.err.println(categoryElement.getText());
                if (categoryElement.getText().contains("Category name")){
                    WebElement catEle = categoryContainer.findElement(By.xpath("//div[@data-rbd-draggable-id='cat-" + index + "']"));
                    WebElement categoryLeftPanel = catEle.findElement(By.xpath("//div[contains(@class,'accordian__header--left')]"));

                    WebElement categoryNameElement =categoryLeftPanel.findElement(By.xpath(".//span"));
                    Thread.sleep(2000);
                    categoryNameElement.click();

                    // Locate the input field that appears after clicking
                    Thread.sleep(2000);
                    WebElement categoryInputField = WaitUtilsFragment.waitForWebElementToClick(categoryLeftPanel.findElement(By.id("cat-name-change")));

                    // Enter new category name
                    categoryInputField.sendKeys("Category " + (index + 1));
                }
                // Print the category attribute for verification
                System.out.println("Processing Category: " + categoryElement.getAttribute("data-rbd-draggable-id"));

                System.out.println("Renamed Category " + index + " to: Category " + (index + 1));
                // Click on "Add Category" button
                WebElement addCategoryButton = WaitUtilsFragment.waitForWebElementToClick(templateBody.findElement(By.xpath("//button[contains(text(), 'Add category')]")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addCategoryButton);
                addCategoryButton.click();
            } else {
                System.out.println("Category with index " + index + " not found.");
            }
        }

    }

    public static void createTemplate(String templateGroupName, String templateName) throws Exception {
        WebElement templateLeftPanel = driver.findElement(By.xpath("//div[@class='template-filters']//div[contains(@class,'MuiAccordionDetails-root')]"));
        templateLeftPanel.findElement(By.xpath("//span[@class='show-more']")).click();
        List<WebElement> templateGroups = templateLeftPanel.findElements(By.xpath("//div[@class='group-item ']"));
        Thread.sleep(2000);
        System.out.println("Template Groups : " + templateGroups.size());

        for (WebElement templateGroup : templateGroups) {
            System.out.println(templateGroup.getText());
            if (templateGroup.getText().contains(templateGroupName)) {
                System.err.println("Template group: " + templateGroup.getText() + " found");
                templateGroup.click();
                Thread.sleep(2000);
                driver.findElement(By.id("ed-butn-new_template")).click();
                break;
            }
        }

        WebElement tempNameElement = WaitUtilsFragment.waitForElementToBeVisible(driver.findElement(By.id("input_audit_title")));
        tempNameElement.click();
        Thread.sleep(2000);
        tempNameElement.sendKeys(templateName + Keys.ENTER);
        Thread.sleep(2000);

        WebElement tagElement = driver.findElement(By.xpath("//div[@class='temp-tag-container']//div[@role='combobox']//input[@type='text']"));
        tagElement.click();
        Thread.sleep(2000);
        tagElement.sendKeys("Tag Automation template " + Keys.ENTER);
        Thread.sleep(2000);

        WebElement templateInformed = driver.findElement(By.id("template-informed"));
        templateInformed.click();
        templateInformed.sendKeys(DataReader.getValueFromJsonFile("projectInformed") + Keys.ENTER);
        Thread.sleep(2000);

        WebElement templateBody = driver.findElement(By.xpath("//div[@class='addEditTemplate__body']"));

        // Locate the category container once
        WebElement categoryContainer = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(templateBody.findElement(By.xpath("//div[@data-rbd-droppable-id='droppable']"))));

        // Adding categories
        int maxCategories = 2;
        for (int index = 0; index <= maxCategories; index++) {
            // Locate the category inside the container dynamically
            WebElement categoryOrder = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOf(categoryContainer.findElement(By.xpath("//div[@data-rbd-draggable-id='cat-" + index + "']"))));
            Thread.sleep(2000);

            // Locate the left panel inside the category
            WebElement categoryLeftPanel = categoryOrder.findElement(By.xpath("//div[contains(@class,'accordian__header--left')]"));
            Thread.sleep(2000);

            // Click the span/div to reveal the input field
            WebElement categoryNameElement = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(categoryLeftPanel.findElement(By.xpath("//div[@class='accordian__header']//div[@class='accordian__header--left']//span"))));
            categoryNameElement.click();

            // Locate all category input fields
         //   List<WebElement> categoryInputFields = new WebDriverWait(driver, Duration.ofSeconds(10))
       //             .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("cat-name-change")));
            Thread.sleep(2000);
            List<WebElement> categoryInputFields = categoryOrder.findElements(By.xpath("//input[@id='cat-name-change']"));

            int categoryIndex = 1; // Start naming from "Category 1"
            for (WebElement categoryInputField : categoryInputFields) {
                // Check if the input field already has text
                String existingText = categoryInputField.getAttribute("value").trim();

                if (existingText.isEmpty()) {
                    // Enter new category name only if it's empty
                    categoryInputField.sendKeys("Category " + categoryIndex);
                    System.out.println("Renamed Category " + categoryIndex);
                    categoryIndex++;
                    break; // Exit after renaming the first empty category
                }
            }

            // Add questions to category
            CategoryFragment.addQuestionsToCategory(categoryOrder, List.of("Yes/No", "Free text"), driver);

            // Click on "Add Category" button
            WebElement addCategoryButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Add category')]")));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addCategoryButton);

            try {
                addCategoryButton.click();
            } catch (Exception e) {
                System.out.println("Normal click failed, trying JavaScript click...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addCategoryButton);
            }
        }

        System.out.println("Loop finished. Total categories processed: " + maxCategories);
    }


}

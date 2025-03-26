package EdControlsMain.EdPageObjects;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.EDFragments.CategoryFragment;
import EdControlsMain.Resources.DataReader;
import EdControlsMain.EDFragments.WaitUtilsFragment;
import EdControlsMain.ReusableFunctions.ReusableMethods;
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

    public static void createObjectTemplate(String templateGroupName, String templateName) throws Exception {
        navigateToTemplate(templateGroupName);
        // Marking this as Object Template
        WebElement checkboxLabel = driver.findElement(By.xpath("//div[@class='auditType-checkbox']//label"));
        WebElement checkboxInput = driver.findElement(By.xpath("//div[@class='auditType-checkbox']//input[@type='checkbox']"));
        // Ensure checkbox is clickable
        WaitUtilsFragment.waitForWebElementToClickable(checkboxLabel);
        // Click using JavaScript to avoid hidden element issues
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkboxLabel);
        // Verify if it's checked
        boolean isChecked = checkboxInput.isSelected();
        System.out.println("Checkbox selected: " + isChecked);
        Thread.sleep(2000);
        createAreaTemplate(templateGroupName, templateName, "object");
        Thread.sleep(2000);
    }

    public static void createAreaTemplate(String templateGroupName, String templateName, String auditType) throws Exception {
        navigateToTemplate(templateGroupName);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement tempNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input_audit_title")));
        tempNameElement.click();
        tempNameElement.sendKeys(templateName + Keys.ENTER);

        WebElement tagElement = driver.findElement(By.cssSelector(".temp-tag-container [role='combobox'] input[type='text']"));
        tagElement.click();
        tagElement.sendKeys("Tag Automation template " + Keys.ENTER);

        WebElement templateInformed = driver.findElement(By.id("template-informed"));
        templateInformed.click();
        templateInformed.sendKeys(DataReader.getValueFromJsonFile("dev.project.informed") + Keys.ENTER);

        WebElement templateBody = driver.findElement(By.xpath("//div[@class='addEditTemplate__body']"));

        // Define questions for each category
        List<List<String>> questionsPerCategory = List.of(
                List.of("Yes/No", "Multiple choice - multiple answers", "Multiple choice - single answer"),
                List.of("Yes/No", "Date", "Signature"),
                List.of("Yes/No", "Free text", "Numeric")
        );

        for (int index = 0; index < questionsPerCategory.size(); index++) {
            WebElement categoryOrder = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[@data-rbd-draggable-id='cat-" + index + "']")));

            WebElement categoryNameElement = categoryOrder.findElement(By.xpath(".//div[contains(@class,'accordian__header--left')]//span"));
            categoryNameElement.click();

            WebElement categoryInputField = wait.until(ExpectedConditions.elementToBeClickable(
                    categoryOrder.findElement(By.id("cat-name-change"))));
            categoryInputField.sendKeys("Category " + (index + 1));

            // Add questions
            CategoryFragment.addQuestionsToCategory(categoryOrder, index, questionsPerCategory.get(index));

            // Click "Add Category" only if it's not the last category
            if (index < questionsPerCategory.size() - 1) {
                WebElement addCategoryButton = wait.until(ExpectedConditions.elementToBeClickable(
                        categoryOrder.findElement(By.xpath("//button[contains(text(), 'Add category')]"))));

                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", addCategoryButton);
                addCategoryButton.click();
            }
        }

        // Scroll & Click Publish
        WebElement publishButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("publish")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", publishButton);
        publishButton.click();

        System.out.println(ReusableMethods.checkingToastMessage());
    }


    public static void navigateToTemplate(String templateGroupName) throws InterruptedException {
        WebElement templateLeftPanel = driver.findElement(By.xpath("//div[@class='template-filters']//div[contains(@class,'MuiAccordionDetails-root')]"));
        templateLeftPanel.findElement(By.xpath("//span[@class='show-more']")).click();
        List<WebElement> templateGroups = templateLeftPanel.findElements(By.xpath("//div[@class='group-item ']"));
        Thread.sleep(2000);
        System.out.println("Template Groups : " + templateGroups.size());

        for (WebElement templateGroup : templateGroups) {
            System.out.println(templateGroup.getText());
            if (templateGroup.getText().contains("TG - Auto")) {
                System.err.println("Template group: " + templateGroup.getText() + " found");
                templateGroup.click();
                Thread.sleep(2000);
                driver.findElement(By.id("ed-butn-new_template")).click();
                break;
            }
        }
    }

}

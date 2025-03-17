package EdControlsMain.EdContainers;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.EDFragments.CategoryFragment;
import EdControlsMain.Resources.DataReader;
import EdControlsMain.ReusableFunctions.WaitUtils;
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
        WaitUtils.waitForWebElementAppear(auditLeftPanel);
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
        WaitUtils.waitForWebElementAppear(auditLeftPanel);
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
        WaitUtils.waitForWebElementByAppear(createNewGroup);
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
        WebElement tempNameElement = WaitUtils.waitForElementToBeVisible(driver.findElement(By.id("input_audit_title")));
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


        // Adding categories
        int maxCategories = 0;
        for (int index = 0; index <= maxCategories; index++) {
            String categoryXpath = "//div[@class='addEditTemplate__body']//div[@data-rbd-draggable-id='cat-" + index + "']";
            WebElement categoryElement = WaitUtils.waitForElementToBeVisible(driver.findElement(By.xpath(categoryXpath)));
            System.out.println("Focusing on category: cat-" + index);

            // Enter category name
            WebElement templateBody = driver.findElement(By.xpath("//div[@class='addEditTemplate__body']"));
            WebElement categoryNameElement = templateBody.findElement(By.xpath("//div[@class='accordian__header']//div[@class='accordian__header--left']//span"));
            categoryNameElement.click();
            Thread.sleep(2000);
            WebElement categoryName = categoryElement.findElement(By.id("cat-name-change"));
            //categoryName.click();
            categoryName.sendKeys("Category " + (index + 1));

            // Add questions to category
            CategoryFragment.addQuestionsToCategory(categoryElement, List.of("Yes/No", "Free text"),driver);

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

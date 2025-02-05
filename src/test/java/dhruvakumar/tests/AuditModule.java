package dhruvakumar.tests;

import dhruvakumar.PageObjects.BaseTest;
import dhruvakumar.ReusableFunctions.ReusableMethods;
import dhruvakumar.ReusableFunctions.TemplateContainer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;


public class AuditModule extends BaseTest {

    public AuditModule() {
        super(null); // Temporary, driver will be initialized in BaseTest
    }

    public AuditModule(WebDriver driver) {
        super(driver);
    }

    @Test
    public void createAreaAudit() throws Exception {
        ReusableMethods.navigateToProject();
        Thread.sleep(2000);
        WebElement auditHeader = driver.findElement(By.id("ed-audits"));
        ReusableMethods.waitForWebElementToClickable(auditHeader);
        auditHeader.click();
        Thread.sleep(2000);
        TemplateContainer.selectAreaAuditTemplate();
        Thread.sleep(2000);

    }



}

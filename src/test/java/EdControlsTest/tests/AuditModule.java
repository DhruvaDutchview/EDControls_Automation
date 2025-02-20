package dhruvakumar.tests;

import dhruvakumar.BaseClasses.BaseTest;
import dhruvakumar.EdFragments.AuditContainer;
import dhruvakumar.EdFragments.ProjectContainer;
import dhruvakumar.ReusableFunctions.ReusableMethods;
import dhruvakumar.EdFragments.TemplateContainer;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
        ProjectContainer.navigateToProject();
        Thread.sleep(1000);
        WebElement auditHeader = driver.findElement(By.id("ed-audits"));
        ReusableMethods.waitForWebElementToClickable(auditHeader);
        auditHeader.click();
        Thread.sleep(1000);
        TemplateContainer.selectAreaAuditTemplate();
        AuditContainer.createAuditInitialization("area");

        // Add Responsible and informed
        WebElement addResponsibele =  driver.findElement(By.id("ad-responsible"));
        addResponsibele.sendKeys("responsibledev@mailinator.com");
        addResponsibele.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        driver.findElement(By.id("ad-save-edit")).click();
        Thread.sleep(1000);
    }


  @Test
    public void CreateObjectAudit() throws Exception {
      ProjectContainer.navigateToProject();
      Thread.sleep(2000);
      WebElement auditHeader = driver.findElement(By.id("ed-audits"));
      ReusableMethods.waitForWebElementToClickable(auditHeader);
      auditHeader.click();
      TemplateContainer.selectObjectAuditTemplate();
      Thread.sleep(1000);
      AuditContainer.createAuditInitialization("object");
  }


}

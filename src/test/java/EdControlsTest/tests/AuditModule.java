package EdControlsTest.tests;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.EdContainers.AuditContainer;
import EdControlsMain.EdContainers.ProjectContainer;
import EdControlsMain.ReusableFunctions.DateFragment;
import EdControlsMain.ReusableFunctions.ReusableMethods;
import EdControlsMain.EdContainers.TemplateContainer;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;


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
        Thread.sleep(2000);
        WebElement auditHeader = driver.findElement(By.id("ed-audits"));
        ReusableMethods.waitForWebElementToClickable(auditHeader);
        auditHeader.click();
        Thread.sleep(1000);
        TemplateContainer.selectAreaAuditTemplate();
        AuditContainer.createAuditInitialization("area");

        // Adding Responsible and informed
        WebElement addResponsibele =  driver.findElement(By.id("ad-responsible"));
        addResponsibele.sendKeys("responsibledev@mailinator.com");
        addResponsibele.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        driver.findElement(By.id("ad-save-edit")).click();
        Thread.sleep(2000);
        String toastMessage = ReusableMethods.checkingToastMessage();
        Assert.assertEquals("Saved successfully",toastMessage);
        System.err.println(toastMessage);
        Boolean bolean = ReusableMethods.waitForElementDisAppear(By.xpath("//div[@class='MuiAlert-message']"));
        if (bolean==true){
            Integer auditCount = ReusableMethods.getCount(auditHeader);
            System.out.println("After audit created: "+auditCount);
        }
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
      WebElement ticketSearch = driver.findElement(By.cssSelector("input[placeholder='Search']"));
      ReusableMethods.waitForWebElementAppear(ticketSearch);
      ticketSearch.sendKeys("608c3e");
      ticketSearch.sendKeys(Keys.ENTER);
      Thread.sleep(2000);
      WebElement ele = driver.findElement(By.xpath("//div[contains(@class,'auditTicket')] //div[@class='ticket-list--wrapper'] //div[@class='infinite-scroll-component ']"));
      List<WebElement> ticketList = ele.findElements(By.xpath("//div[@class='ticket-item']"));
      for (WebElement tickets : ticketList)
      {
          WebElement ticketEle = tickets.findElement(By.cssSelector("div[class='image-container'] p"));
          String ticketID = ticketEle.getText();
          System.out.println(ticketID);
          if (ticketID.equalsIgnoreCase("608c3e"))
          {
              tickets.click();
              break;
          }
          else {
              System.out.println("Ticket not selected");
          }
      }
      Thread.sleep(2000);
      WebElement dueDateElement = driver.findElement(By.id("ad-due-date"));
      dueDateElement.click();
      WebElement dateContainer = driver.findElement(By.xpath("//div[@class='react-calendar']"));
      ReusableMethods.waitForWebElementAppear(dateContainer);
      DateFragment.datePicker(dateContainer);
      Thread.sleep(2000);
      driver.findElement(By.id("ad-save-edit")).click();
      Thread.sleep(1000);
      String toastMessage = ReusableMethods.checkingToastMessage();
      Assert.assertEquals(toastMessage,"Saved successfully");
      System.err.println(toastMessage);
  }



}

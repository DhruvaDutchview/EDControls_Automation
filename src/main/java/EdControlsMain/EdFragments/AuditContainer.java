package EdControlsMain.EdFragments;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.ReusableFunctions.ReusableMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AuditContainer extends BaseTest {


    public AuditContainer(WebDriver driver) {
        super(driver);
    }

    public static void createAuditInitialization(String auditType) throws Exception {
        WebElement auditHeader = driver.findElement(By.id("ed-audits"));
        ReusableMethods.waitForWebElementToClickable(auditHeader);
        //  String auditHeaderText = auditHeader.getText();
        //li[@id='ed-audits']//span[contains(text(),'0')]
        Thread.sleep(2000);
        String auditCount = driver.findElement(By.xpath("//li[@id='ed-audits']//span")).getText();
        System.out.println(auditCount);
        Thread.sleep(2000);
        if (auditType.contains("area"))
        {
            if (auditCount.contains("0"))
            {
                createAuditFromSkeleton(auditType);
                Thread.sleep(2000);
                WebElement checkCreateAuditInitialized = driver.findElement(By.xpath("//div[@class='audit-one']"));
                ReusableMethods.waitForWebElementAppear(checkCreateAuditInitialized);
            }
            else {
                CreateAuditFromList(auditType);
                Thread.sleep(2000);
                WebElement checkCreateAuditInitialized = driver.findElement(By.xpath("//div[@class='audit-one']"));
                ReusableMethods.waitForWebElementAppear(checkCreateAuditInitialized);
            }
        }
        else if (auditType.contains("object")) {
            if (auditCount.contains("0"))
            {
                Thread.sleep(2000);
                createAuditFromSkeleton(auditType);
                Thread.sleep(2000);
             //   WebElement checkCreateAuditInitialized = driver.findElement(By.xpath("//div[@class='audit-one']"));
              //  ReusableMethods.waitForWebElementAppear(checkCreateAuditInitialized);
            }
            else {
                CreateAuditFromList(auditType);
                Thread.sleep(2000);
             //   WebElement checkCreateAuditInitialized = driver.findElement(By.xpath("//div[@class='audit-one']"));
             //   ReusableMethods.waitForWebElementAppear(checkCreateAuditInitialized);
            }
        }
        else{
            System.out.println("No Audit started");
        }

    }

    public static void createAuditFromSkeleton(String auditType) throws Exception {
            Thread.sleep(2000);
            WebElement startAuditButton = driver.findElement(By.id("start-audit"));
            ReusableMethods.waitForWebElementToClickable(startAuditButton);
            startAuditButton.click();
            Thread.sleep(1000);
            if (auditType.contains("area")){
                WebElement createAuditButton = driver.findElement(By.xpath("//span[@class='btn-label' and text()='Create Single Audit']"));
                createAuditButton.click();
                Thread.sleep(1000);
            }
            else{
                System.out.println("Object audit does not support bowquote");
            }

    }

    public static void CreateAuditFromList(String auditType) throws Exception {
        Thread.sleep(2000);
        WebElement startAuditButton2 = driver.findElement(By.id("add-audit"));
        ReusableMethods.waitForWebElementAppear(startAuditButton2);
        startAuditButton2.click();
        Thread.sleep(1000);
        if (auditType.contains("area"))
        {
            WebElement createAuditButton = driver.findElement(By.xpath("//span[@class='btn-label' and text()='Create Single Audit']"));
            createAuditButton.click();
            Thread.sleep(1000);
        }
        else{
            System.out.println("Object audit does not support bowquote");
        }
    }





}

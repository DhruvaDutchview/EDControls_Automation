package EdControlsMain.EdContainers;

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
        Thread.sleep(2000);
        WebElement element = driver.findElement(By.xpath("//li[@id='ed-audits']//span"));
        Integer auditCount  = ReusableMethods.getCount(element);
        System.out.println("Before audit created: "+auditCount);
        Thread.sleep(2000);

        if (auditType.contains("area"))
        {
            if (auditCount==0)
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
            if (auditCount==0)
            {
                Thread.sleep(2000);
                createAuditFromSkeleton(auditType);
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

    public static Integer getAuditsCount() throws Exception {
        WebElement auditElement = driver.findElement(By.xpath("//div[@class='subHeader__container'] //li[@id='ed-audits']"));
        ReusableMethods.waitForWebElementAppear(auditElement);
        Thread.sleep(3000);
        Integer currentAuditCount= ReusableMethods.getCount(auditElement);
        return currentAuditCount;
    }



}

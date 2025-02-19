package dhruvakumar.EdFragments;

import dhruvakumar.BaseClasses.BaseTest;
import dhruvakumar.ReusableFunctions.ReusableMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TemplateContainer extends BaseTest {


    public TemplateContainer(WebDriver driver) {
        super(driver);
    }

    public static void selectAreaAuditTemplate() throws Exception{
        Thread.sleep(2000);
        WebElement auditLeftPanel = driver.findElement(By.xpath("//div[@class='audits-filter']"));
        ReusableMethods.waitForWebElementAppear(auditLeftPanel);
        List<WebElement> templateGroupsList = auditLeftPanel.findElements(By.xpath("//div[contains(@class,'MuiPaper-root MuiAccordion-root filter-inner-accordian')]"));
      //  System.out.println(templateGroupsList.size());
        Thread.sleep(2000);
        for (WebElement templateGroups : templateGroupsList)
        {
            String templateGroupsText = templateGroups.getText();
          //  System.out.println(templateGroupsText);
            Thread.sleep(2000);
            if (templateGroupsText.contains("Star"))
            {
                Thread.sleep(2000);
                templateGroups.click();
                WebElement ele = templateGroups.findElement(By.xpath("//div[contains(@class,'MuiCollapse-root MuiCollapse-entered')] //div[@class='MuiAccordionDetails-root']"));
                Thread.sleep(1000);
                WebElement ele2 = ele.findElement(By.xpath("//div[@class='audit-left-template']"));
                Thread.sleep(1000);
                List<WebElement> templateList = ele2.findElements(By.xpath("//div[@id='single-template']"));
                System.out.println(templateList.size());
                for (WebElement templates: templateList)
                {
                    String templatesText = templates.getText();
                    if (templatesText.contains("Star (area audit)")){
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
      ReusableMethods.waitForWebElementAppear(auditLeftPanel);
      List<WebElement> templateGroupsList = auditLeftPanel.findElements(By.xpath("//div[contains(@class,'MuiPaper-root MuiAccordion-root filter-inner-accordian')]"));
      //  System.out.println(templateGroupsList.size());
      Thread.sleep(2000);
      for (WebElement templateGroups : templateGroupsList)
      {
          String templateGroupsText = templateGroups.getText();
          //  System.out.println(templateGroupsText);
          Thread.sleep(2000);
          if (templateGroupsText.contains("Star"))
          {
              Thread.sleep(2000);
              templateGroups.click();
              WebElement ele = templateGroups.findElement(By.xpath("//div[contains(@class,'MuiCollapse-root MuiCollapse-entered')] //div[@class='MuiAccordionDetails-root']"));
              Thread.sleep(1000);
              WebElement ele2 = ele.findElement(By.xpath("//div[@class='audit-left-template']"));
              Thread.sleep(1000);
              List<WebElement> templateList = ele2.findElements(By.xpath("//div[@id='single-template']"));
              System.out.println(templateList.size());
              for (WebElement templates: templateList)
              {
                  String templatesText = templates.getText();
                  if (templatesText.contains("Star (Object audit)")){
                      System.err.println(templatesText);
                      templates.click();
                      return;
                  }
              }

          }

      }

  }


}

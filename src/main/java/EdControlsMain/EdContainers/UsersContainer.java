package EdControlsMain.EdContainers;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.Resources.DataReader;
import EdControlsMain.ReusableFunctions.ReusableMethods;
import EdControlsMain.ReusableFunctions.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UsersContainer extends BaseTest {
    public UsersContainer(WebDriver driver) {
        super(driver);
    }

    public static void addUsersOnNewProject() throws Exception {
        WebElement addUsersElement = driver.findElement(By.xpath("//div[@class='new-form-width__screen2']"));
        WaitUtils.waitForWebElementAppear(addUsersElement);
        Thread.sleep(2000);
        WebElement projectAccountable = addUsersElement.findElement(By.id("pj-accountable"));
        projectAccountable.sendKeys(DataReader.getValueFromJsonFile("projectAccountable") + Keys.ENTER);
        Thread.sleep(2000);
        WebElement projectSupport = addUsersElement.findElement(By.id("pj-support"));
        projectSupport.sendKeys(DataReader.getValueFromJsonFile("projectSupport")+ Keys.ENTER);
        Thread.sleep(2000);
        WebElement projectInformed = addUsersElement.findElement(By.id("pj-informed"));
        projectInformed.sendKeys(DataReader.getValueFromJsonFile("projectInformed")+ Keys.ENTER);
        Thread.sleep(2000);
        WebElement projectConsulted = addUsersElement.findElement(By.id("pj-consulted"));
        projectConsulted.sendKeys(DataReader.getValueFromJsonFile("projectConsulted")+ Keys.ENTER);
    }

    public static void addProjectAccountable() throws Exception {
        WebElement addUsersElement = driver.findElement(By.xpath("//div[@class='new-form-width__screen2']"));
        WaitUtils.waitForWebElementAppear(addUsersElement);
        Thread.sleep(2000);

        // Execute only until projectAccountable
        WebElement projectAccountable = addUsersElement.findElement(By.id("pj-accountable"));
        projectAccountable.sendKeys(DataReader.getValueFromJsonFile("projectAccountable") + Keys.ENTER);
        Thread.sleep(2000);
    }




}

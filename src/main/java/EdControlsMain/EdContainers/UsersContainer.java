package EdControlsMain.EdContainers;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.Resources.DataReader;
import EdControlsMain.ReusableFunctions.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class UsersContainer extends BaseTest {
    public UsersContainer(WebDriver driver) {
        super(driver);
    }

    private static String userName;
    private static String userEmail;

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

    public static List<WebElement> getUsersFromUsersManagement(WebElement userBody){
        List<WebElement> usersList = userBody.findElements(By.xpath(".//div[contains(@class,'user-data ')]"));  // Updated to use .//
        System.err.println("Users List........................");
        // Iterate through each user row
        for (WebElement users : usersList) {
            WebElement userEmailElement = users.findElement(By.xpath(".//span[@class='email']"));  // Updated to use .//
            WebElement userNameElement = users.findElement(By.xpath(".//span"));  // Updated to use .//

            String userName = userNameElement.getText();
            String userEmail = userEmailElement.getText();
            System.out.println("User Name: " + userName + " | User Email: " + userEmail);
        }
        return usersList;
    }



}

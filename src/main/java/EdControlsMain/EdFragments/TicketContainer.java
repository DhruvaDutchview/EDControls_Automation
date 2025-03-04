package EdControlsMain.EdFragments;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.Resources.DataReader;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TicketContainer extends BaseTest {


    public TicketContainer(WebDriver driver) {
        super(driver);
    }

    public static void addUsersInTicket() throws Exception {
        // Adding RCI Users
        WebElement addResponsible = driver.findElement(By.id("tn-responsible"));
        addResponsible.sendKeys(DataReader.getValueFromJsonFile("TicketResponsible"));
        WebElement addConsulted = driver.findElement(By.id("tn-consulted"));
        addConsulted.sendKeys(DataReader.getValueFromJsonFile("TicketConsulted"));
        WebElement addInformed = driver.findElement(By.id("tn-informed"));
        addInformed.sendKeys(DataReader.getValueFromJsonFile("TicketInformed"));
        addInformed.sendKeys(Keys.ENTER);
    }


}

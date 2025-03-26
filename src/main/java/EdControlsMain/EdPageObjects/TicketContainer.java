package EdControlsMain.EdPageObjects;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.Resources.DataReader;
import EdControlsMain.ReusableFunctions.ReusableMethods;
import EdControlsMain.EDFragments.WaitUtilsFragment;
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
        addResponsible.sendKeys(DataReader.getValueFromJsonFile("dev.project.responsible"));
        WebElement addConsulted = driver.findElement(By.id("tn-consulted"));
        addConsulted.sendKeys(DataReader.getValueFromJsonFile("dev.project.consulted"));
        WebElement addInformed = driver.findElement(By.id("tn-informed"));
        addInformed.sendKeys(DataReader.getValueFromJsonFile("dev.project.informed"));
        addInformed.sendKeys(Keys.ENTER);
    }

    public static Integer getTicketsCount() throws Exception {
        WebElement ticketElement = driver.findElement(By.xpath("//div[@class='subHeader__container'] //li[@id='ed-tickts']"));
        WaitUtilsFragment.waitForWebElementAppear(ticketElement);
        Thread.sleep(3000);
        Integer currentTicketCount= ReusableMethods.getCount(ticketElement);
        return currentTicketCount;
    }

}

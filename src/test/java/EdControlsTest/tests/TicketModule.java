package EdControlsTest.tests;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.EdFragments.MapContainer;
import EdControlsMain.EdFragments.ProjectContainer;
import EdControlsMain.ReusableFunctions.DateFragment;
import EdControlsMain.ReusableFunctions.ReusableMethods;
import org.openqa.selenium.*;
import org.testng.annotations.Test;

import java.util.List;

public class TicketModule extends BaseTest {
    public TicketModule() {
        super(null); // Temporary, driver will be initialized in BaseTest
    }

    public TicketModule(WebDriver driver) {
        super(driver);
    }

    @Test
    public void createTicket() throws Exception {
        ProjectContainer.navigateToProject();
        MapContainer.navigateToMap();
        WebElement mapContainer = driver.findElement(By.xpath("//div[@id='map-container']"));
        WebElement map = ReusableMethods.waitForWebElementAppear(mapContainer);
        if (map.isDisplayed()) {
            WebElement clickTicket = driver.findElement(By.xpath("//div[contains(@class,'leaflet-container')]"));
            clickTicket.click();
        } else {

            System.err.println("map is not clicked");
        }
        Thread.sleep(1000);

        WebElement newTicketContainerDialog = ReusableMethods.waitForElementToBeVisible(By.xpath("//div[@class='ticket-new']"));
        WebElement newTicketContainer = ReusableMethods.waitForWebElementAppear(newTicketContainerDialog);
        if (newTicketContainer.isDisplayed()) {
            System.out.println("New Ticket container is displayed");
        } else {
            System.err.println("New Ticket container is not displayed");
        }
        Thread.sleep(1000);
        driver.findElement(By.id("tn-title")).sendKeys("Automation Ticket");
        WebElement desContainer = driver.findElement(By.xpath("//section[@id='log']"));
        desContainer.click();
        driver.findElement(By.xpath("//div[@contenteditable='true'] //p")).sendKeys("The Description added by automation script");
        Thread.sleep(1000);
        WebElement tagContainer = driver.findElement(By.xpath("//div[@class='tag-container']"));
        tagContainer.click();
        WebElement tag = driver.findElement(By.id("tn-tags"));
        tag.sendKeys("Tag Automation");
        tag.sendKeys(Keys.ENTER);
        Thread.sleep(1000);

        // due date selection
        WebElement dueDateElement = driver.findElement(By.id("tn-due-date"));
        dueDateElement.click();
        WebElement dateContainer = driver.findElement(By.xpath("//div[@class='react-calendar']"));
        ReusableMethods.waitForWebElementAppear(dateContainer);
        DateFragment.datePicker(dateContainer);
        Thread.sleep(2000);

        // Enable/Disable the Mandatory proof toggle
        WebElement proofMandatoryElement = driver.findElement(By.xpath("//label[contains(@class,'ticket_proof')]"));
        WebElement proofMandatory = proofMandatoryElement.findElement(By.xpath("//span[contains(@class,'MuiSwitch-switchBase')]"));
        String toggleText = proofMandatory.getDomAttribute("class");
        if (toggleText.contains("jss14 Mui-checked")) {
            System.out.println("isMandatoryProof Toggle enabled");
        } else {
            System.out.println("isMandatoryProof Toggle not enabled");
            proofMandatoryElement.findElement(By.xpath("//input[@id='mandatoryTicketProofSwitch']")).click();
        }
        Thread.sleep(2000);

        // Adding RCI Users
        WebElement addResponsible = driver.findElement(By.id("tn-responsible"));
        addResponsible.sendKeys("responsibledev@mailinator.com");
        Thread.sleep(1000);
        WebElement addConsulted = driver.findElement(By.id("tn-consulted"));
        addConsulted.sendKeys("consuldev@mailinator.com");
        Thread.sleep(1000);
        WebElement addInformed = driver.findElement(By.id("tn-informed"));
        addInformed.sendKeys("infodev@mailinator.com");
        addInformed.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        driver.findElement(By.id("tn-save-ticket")).click();

        // Checking the New ticket creation confirmation message is displayed or not
        String toastMessage = ReusableMethods.checkingToastMessage();
        System.err.println(toastMessage);
        Thread.sleep(2000);

    }

    @Test
    public void editingTicket() throws Exception {
        ProjectContainer.navigateToProject();
        WebElement ticketElement = driver.findElement(By.xpath("//div[@class='subHeader__container'] //li[@id='ed-tickts']"));
        ReusableMethods.waitForWebElementAppear(ticketElement);
        Thread.sleep(3000);
        String[] array = ReusableMethods.trimmingText(ticketElement);
        String currentTicketCount = array[1];
        System.out.println(currentTicketCount);
        Thread.sleep(2000);
        List<WebElement> ticketsList = driver.findElements(By.xpath("//div[@id='ticket-list'] //div[@class='ticket-item'] //h4"));
        for (WebElement tickets : ticketsList) {
            String ticketName = tickets.getText();
            if (ticketName.contains("Automation Ticket")) {
                System.out.println("Ticket Name: " + ticketName);
                tickets.click();
                break;
            }
        }
        WebElement ticketDescription = driver.findElement(By.xpath("//div[@id='td-description']"));
        ReusableMethods.waitForWebElementAppear(ticketDescription);
        ticketDescription.click();
        ticketDescription.findElement(By.xpath("//div[@dir='ltr']//p")).sendKeys("Automation description");

        // due date selection
        WebElement dueDateElement = driver.findElement(By.id("td-date-picker"));
        dueDateElement.click();
        WebElement dateContainer = driver.findElement(By.xpath("//div[@class='react-calendar']"));
        ReusableMethods.waitForWebElementAppear(dateContainer);
        Thread.sleep(2000);
        DateFragment.datePicker(dateContainer);
        Thread.sleep(2000);
        driver.findElement(By.id("td-save")).click();
    }

}



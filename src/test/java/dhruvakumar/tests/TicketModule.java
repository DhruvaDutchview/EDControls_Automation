package dhruvakumar.tests;

import dhruvakumar.Resources.DataReader;
import dhruvakumar.pageobjects.BaseTest;
import dhruvakumar.pageobjects.Maps;
import dhruvakumar.reusableFunctions.ReusableFunction;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
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
		ReusableFunction.navigateToProject();
		Thread.sleep(2000);
		Maps.navigateToMap();
		WebElement mapContainer = driver.findElement(By.xpath("//div[@id='map-container']"));
		WebElement map = ReusableFunction.waitForWebElementAppear(mapContainer);
		if (map.isDisplayed() == true) {
			WebElement clickTicket = driver.findElement(By.xpath("//div[contains(@class,'leaflet-container')]"));
			clickTicket.click();
		} else {
			System.out.println("map is not clicked");
		}

		WebElement newTicketContainerDialog = driver.findElement(By.xpath("//div[@class='ticket-new']"));
		WebElement newTicketContainer = ReusableFunction.waitForWebElementAppear(newTicketContainerDialog);
		if (newTicketContainer.isDisplayed() == true) {
			System.out.println("New Ticket container is displayed");
		} else {
			System.out.println("New Ticket container is not displayed");
		}
		Thread.sleep(2000);
		driver.findElement(By.id("tn-title")).sendKeys("Automation Ticket");
		WebElement desContainer = driver.findElement(By.xpath("//section[@id='log']"));
		desContainer.click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@contenteditable='true'] //p")).sendKeys("The Description added by automation script");
		WebElement tagContainer = driver.findElement(By.xpath("//div[@class='tag-container']"));
		tagContainer.click();
		Thread.sleep(2000);
		WebElement tag = driver.findElement(By.id("tn-tags"));
		tag.sendKeys("Tag Automation");
		tag.sendKeys(Keys.ENTER);
		Thread.sleep(2000);
		WebElement dueDateContainer = driver.findElement(By.id("tn-due-date"));
		dueDateContainer.click();
		Thread.sleep(2000);
		WebElement calenderContainer = driver.findElement(By.xpath("//div[@class='react-calendar']"));
		WebElement calender = ReusableFunction.waitForWebElementAppear(calenderContainer);
		if (calender.isDisplayed() == true) {
			WebElement daysContainer = driver.findElement(By.xpath("//div[@class='react-calendar'] //div[@class='react-calendar__month-view__days']"));
			List<WebElement> days = daysContainer.findElements(By.xpath("//button[contains(@class, 'react-calendar__month-view__days__day')]"));
			for (WebElement day : days) {
				if (day.getDomAttribute("disabled") == null) {
					String dayName = day.getText();
					if (dayName.contains("12")) {
						day.click();
						break;
					}
				}
			}
		}
		Thread.sleep(2000);
		driver.findElement(By.id("tn-save-ticket")).click();

		// Checking the New ticket creation confirmation message is displayed or not
		String toastMessage = ReusableFunction.checkingToastMessage();
		System.err.println(toastMessage);
		Thread.sleep(2000);
		DataReader.newTicketJSON();

	}
	/*@Test
	public void editingTicket() throws Exception {
		ReusableFunction.navigateToProject();
		WebElement ticketElement = driver.findElement(By.xpath("//div[@class='subHeader__container'] //li[@id='ed-tickts']"));
		ReusableFunction.waitForWebElementAppear(ticketElement);
		Thread.sleep(3000);
        String array[] = ReusableFunction.trimmingText(ticketElement);
		String currentTicketCount = array[1];
		System.out.println(currentTicketCount);
		Thread.sleep(2000);
		List<WebElement> ticketsList = driver.findElements(By.xpath("//div[@id='ticket-list'] //div[@class='ticket-item'] //h4"));
		for (WebElement tickets : ticketsList)
		{
			String ticketName = tickets.getText();
		    if (ticketName.contains("Test him"))
			{
				System.out.println("Ticket Name: " +ticketName);
				tickets.click();
				break;
			}
		}
	   WebElement ticketDescription = driver.findElement(By.xpath("//div[@id='td-description']"));
	   ReusableFunction.waitForWebElementAppear(ticketDescription);
	   ticketDescription.click();
       ticketDescription.findElement(By.xpath("//div[@dir='ltr']//p")).sendKeys("Automation description");
	}*/

}



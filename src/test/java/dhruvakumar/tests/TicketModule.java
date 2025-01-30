package dhruvakumar.tests;

import dhruvakumar.pageobjects.BaseTest;
import dhruvakumar.reusableFunctions.ReusableFunction;
import org.openqa.selenium.*;
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
	}
}


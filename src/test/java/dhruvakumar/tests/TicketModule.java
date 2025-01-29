package dhruvakumar.tests;

import dhruvakumar.pageobjects.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class TicketModule extends BaseTest {
	public TicketModule() {
		super(null); // Temporary, driver will be initialized in BaseTest
	}

	public TicketModule(WebDriver driver) {
		super(driver);
	}
	@Test
	public void createTicket() {
		System.out.println("Login is Successfully");

	}
}


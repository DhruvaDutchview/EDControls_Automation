package EdControlsTest.tests;

import EdControlsMain.BaseClasses.BaseTest;
import EdControlsMain.EdPageObjects.ProjectContainer;
import EdControlsMain.Resources.DataReader;
import org.openqa.selenium.WebDriver;

public class TicketFilter extends BaseTest {

    public TicketFilter() {
        super(null); // Temporary, driver will be initialized in BaseTest
    }
    public TicketFilter(WebDriver driver) {
        super(driver);
    }

    public void ticketPersonFilter() throws Exception {
        ProjectContainer.navigateToProject(DataReader.getValueFromJsonFile("dev.project.name"));



    }






}

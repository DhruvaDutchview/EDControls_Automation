package dhruvakumar.BaseClasses;


import dhruvakumar.Resources.DataReader;
import dhruvakumar.ReusableFunctions.ReusableMethods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends ReusableMethods {

	static WebDriver driver;
	static DataReader dataReader = new DataReader(driver);

	public LoginPage(WebDriver driver) {
		super(driver);
		LoginPage.driver =driver;
		PageFactory.initElements(driver, this);
	}

	public void goTo()
	{
		//calling json file and getting values
	    //JsonNode jsonNode = dataReader.readJsonFile();
	    //String url = jsonNode.get(1).get("url").asText();
		String url = DataReader.readJsonFile("url");

		/*//calling Properties file and getting values
		Map<String, String> data= dataReader.readPropertiesFile();
		String url = data.get("url");*/
		driver.get(url);
	}

	//pageFactory
	@FindBy(id="user-name")
	WebElement userEmail;
	@FindBy(id="passwd")
	WebElement userPassword;
	@FindBy(id="butn-login")
	WebElement login;

	public void loginApplication()
	{
		goTo();

		String userName = DataReader.readJsonFile("email");
		String password = DataReader.readJsonFile("password");

		/*Map<String, String> data= dataReader.readPropertiesFile();
		String userName = data.get("email");
		String password = data.get("password");*/

		userEmail.sendKeys(userName);
		userPassword.sendKeys(password);
		login.click();
		System.out.println("user logged in");
	}

	
}

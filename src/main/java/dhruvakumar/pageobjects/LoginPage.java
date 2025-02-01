package dhruvakumar.pageobjects;


import dhruvakumar.Resources.DataReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Map;

import dhruvakumar.reusableFunctions.ReusableFunction;

public class LoginPage extends ReusableFunction  {

	static WebDriver driver;
	static DataReader dataReader = new DataReader(driver);

	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	public void goTo()
	{
		//calling json file and getting values
	    //JsonNode jsonNode = dataReader.readJsonFile();
	    //String url = jsonNode.get(1).get("url").asText();

		//calling Properties file and getting values
		Map<String, String> data= dataReader.readPropertiesFile();
		String url = data.get("url");
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

		/*JsonNode jsonNode = dataReader.readJsonFile();
		String userName = jsonNode.get(0).get("email").asText();
		String password = jsonNode.get(0).get("password").asText();*/

		Map<String, String> data= dataReader.readPropertiesFile();
		String userName = data.get("email");
		String password = data.get("password");

		userEmail.sendKeys(userName);
		userPassword.sendKeys(password);
		login.click();
		System.out.println("user logged in");
	}

	
}

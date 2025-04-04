package EdControlsMain.BaseClasses;


import EdControlsMain.Resources.DataReader;
import EdControlsMain.ReusableFunctions.ReusableMethods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends ReusableMethods {

	static WebDriver driver;
	static DataReader dataReader = new DataReader(driver);
	String userName;
	String password;
	String url;

	public LoginPage(WebDriver driver) {
		super(driver);
		LoginPage.driver =driver;
		PageFactory.initElements(driver, this);
	}

	public void goTo(String URL)
	{
		//url = DataReader.getValueFromJsonFile("dev.url");
		/*//calling Properties file and getting values
		Map<String, String> data= dataReader.readPropertiesFile();
		String url = data.get("url");*/
		driver.get(URL);
	}

	//pageFactory
	@FindBy(id="user-name")
	WebElement userEmail;
	@FindBy(id="passwd")
	WebElement userPassword;
	@FindBy(id="butn-login")
	WebElement login;

	public void loginApplication(String env)
	{
		if (env.equalsIgnoreCase("dev")){
			goTo(DataReader.getValueFromJsonFile("dev.url"));
			userName = DataReader.getValueFromJsonFile("dev.admin.email");
			password = DataReader.getValueFromJsonFile("dev.admin.password");
		}
		else if (env.equalsIgnoreCase("prod")) {
			goTo(DataReader.getValueFromJsonFile("prod.url"));
			userName = DataReader.getValueFromJsonFile("prod.admin.email");
			password = DataReader.getValueFromJsonFile("prod.admin.password");
		}
		else {
			System.err.println("Environment not specified");
		}

		/*Map<String, String> data= dataReader.readPropertiesFile();
		String userName = data.get("email");
		String password = data.get("password");*/
		userEmail.sendKeys(userName);
		userPassword.sendKeys(password);
		login.click();
		System.out.println("user logged in");
	}

	
}

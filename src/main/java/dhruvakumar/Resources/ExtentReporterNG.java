package dhruvakumar.Resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporterNG {

	
	public static ExtentReports getReportObject()
	{
		 String Path=System.getProperty("user.dir")+"//TestReports//index.html";
		
		 ExtentSparkReporter reporter = new ExtentSparkReporter(Path);
		 reporter.config().setDocumentTitle("Ed Controls Automation Test Results");
		 reporter.config().enableOfflineMode(true);
		 reporter.config().setReportName("Tickets Module Results");
		 reporter.config().setTheme(Theme.STANDARD);
		 
		 ExtentReports extent = new ExtentReports();
		 extent.attachReporter(reporter);
		 extent.setSystemInfo("Tester", "Dhruva gowda");
		 return extent;
	}
	
	
}

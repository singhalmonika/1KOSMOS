package testscripts;

import org.testng.annotations.Test;

import com.opencsv.exceptions.CsvException;

import pages.MyPediaLogin;
import utility.Util;
import pages.MyPediaCreateAccount;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.apache.commons.io.FileUtils;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;

public class TestMyPedia {
	String driverPath = "chromedriver";
	WebDriver driver;

	String continueLabelText = "Continue";
	MyPediaLogin objLogin;
	MyPediaCreateAccount objCreateAccount;
	
	@DataProvider(name = "createAccount")
	public Object[][] createAccountDataProvider() throws IOException, CsvException {
		Object[][] csvData= Util.readCSVFile("/src/testdata/UserData.csv");
		return csvData;
		
		
	}

	@BeforeTest
	public void setup() {
		// Disabling chrome notifications
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("https://www.mypedia.pearson.com/");
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		objLogin = new MyPediaLogin(driver);
		objCreateAccount = new MyPediaCreateAccount(driver);
	}
	
	

	/**
	 * This method closes the iframe which appears on launching the web page
	 */

	@Test()
	public void closeTheIframe() {
		objLogin.clickContinueButton();

	}

	/**
	 * This method will validate that language drop-down has three languages
	 * Text of continue button is changing on changing the language
	 * Set default language to English after text verification
	 * Click on Setup Parent Support
	 * @throws IOException
	 */

	@Test(dependsOnMethods = { "closeTheIframe" })
	public void verifyLanguageDropdown() throws IOException {
		objLogin.verifyUniqueLanguageCount();
		objLogin.verifyTextChangeOnLanguageChange(continueLabelText);
		objLogin.setDefaultLanguage();
		objLogin.setupParentSupport();
		
    }
	
	@Test(dependsOnMethods = {"closeTheIframe","verifyLanguageDropdown"}, dataProvider = "createAccount")
	public void createNewAccount(String firstName,String lastName, String emailAddress, String parentUserName,String password,String confirmPassword) {
		objCreateAccount.clickCreateAccountButton();
		//objCreateAccount.waitForLoaderToDisappear();
		objCreateAccount.createNewUserAccount(firstName,lastName,emailAddress,parentUserName,password,confirmPassword);
		
	}
	
	

	@AfterTest
	public void teardown(ITestResult result) {
		
		try{
			
			// To create reference of TakesScreenshot
			TakesScreenshot screenshot=(TakesScreenshot)driver;
			// Call method to capture screenshot
			File srcFile=screenshot.getScreenshotAs(OutputType.FILE);
			
			// Copy files to specific location 
			// result.getName() will return name of test case so that screenshot name will be same as test case name
			FileUtils.copyFile(srcFile, new File(Util.cwd+"/src/screenshots/"+result.getName()+".png"));
			System.out.println("Successfully captured a screenshot");
			
		}catch (Exception e){
			
			System.out.println("Exception while taking screenshot "+e.getMessage());
		} 
}
		// driver.close();
		// driver.quit();
	}



package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyPediaCreateAccount {
	WebDriver driver;

	@FindBy(xpath = "//div[@class='acrCheckCodeButton'][1]/button")
	WebElement createAccountButton;
	
	@FindBy(id = "loader")
	WebElement loaderIcon;
	
	@FindBy(xpath = "//input[contains(@id,'Firstname')]")
	WebElement firstName;
	
	@FindBy(xpath = "//input[contains(@id,'Lastname')]")
	WebElement lastName;
	
	@FindBy(xpath = "//input[contains(@id,'Emailaddress')]")
	WebElement emailAddress;
	
	@FindBy(xpath ="//label[text()='Create parent username']/../input")
	WebElement parentUserName;
	
	@FindBy(xpath ="//label[text()='Create parent password']/../input")
	WebElement password;
	
	@FindBy(xpath ="//input[contains(@id,'Confirmpassword')]")
	WebElement confirmPassword;
	
	


	public MyPediaCreateAccount(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
    /**
     * Click on create account button
     */
	public void clickCreateAccountButton() {
		createAccountButton.click();
	}

	public void waitForLoaderToDisappear() {
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.invisibilityOf(loaderIcon));
		
	}

	public void createNewUserAccount(String firstName, String lastName, String emailAddress, String parentUserName,
			String password, String confirmPassword) {
		driver.navigate().refresh();
		this.firstName.sendKeys(firstName);
		this.lastName.sendKeys(lastName);
		this.emailAddress.sendKeys(emailAddress);
		this.parentUserName.sendKeys(parentUserName);
		this.password.sendKeys(password);
		this.confirmPassword.sendKeys(confirmPassword);
		
	}
	

}

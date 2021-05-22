package testscripts;

import java.io.IOException;
import java.util.Iterator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencsv.exceptions.CsvException;

import utility.Util;

public class TestDataProvider {
	
	@DataProvider(name = "createAccount")
	public Object[][] createAccountDataProvider() throws IOException, CsvException {
		Object[][] csvData= Util.readCSVFile("/src/testdata/UserData.csv");
		return csvData;	
	}
	
	@Test(dataProvider = "createAccount")
	public void createNewAccount(String firstName,String lastName, String emailAddress, String parentUserName,String password,String confirmPassword) {
		
		System.out.println(firstName);
		System.out.println(lastName);
		System.out.println(emailAddress);
		System.out.println(parentUserName);
		System.out.println(password);
		System.out.println(confirmPassword);
		
	}

}

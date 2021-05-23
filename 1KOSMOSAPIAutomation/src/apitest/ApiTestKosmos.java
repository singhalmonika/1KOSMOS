package apitest;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.opencsv.exceptions.CsvException;
import io.restassured.RestAssured;
import utility.Util;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.io.IOException;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class ApiTestKosmos {

	/**
	 * This method will read the csv file and return the object array.
	 * 
	 * @return
	 * @throws IOException
	 * @throws CsvException
	 */
	@DataProvider(name = "SetupAPI")
	public Object[][] createAPIDataProvider() throws IOException, CsvException {
		Object[][] csvData = Util.readCSVFile("/src/testdata/ApiData.csv");
		return csvData;

	}

	/**
	 * This method will set a base URI and make a get request
	 * https://1k-dev.1kosmos.net/healthz and verify the health response code
	 */
	@BeforeTest
	public void setUp() {
		RestAssured.baseURI = "https://1k-dev.1kosmos.net";
		
		// Check if the host is available
		given().when().
			get("/healthz").
		then().
			assertThat().statusCode(200).
			body("code", equalTo("200"));
	}

	/**
	 * This test would make the API calls and validate their
	 * response status code and JSON schema.  
	 * 
	 * @param tenantID
	 * @param resource
	 * @param statusCode
	 * @param schemaFile
	 */
	@Test(dataProvider = "SetupAPI")
	public void healthStatusTest(String tenantID, String resource, 
			String statusCode, String schemaFile) {

		// Get the EULA from another endpoint
		given().
			param("tenant", tenantID).
		when().
			get(resource).
		then().
			assertThat().statusCode(Integer.parseInt(statusCode)).
			body(not(emptyOrNullString())).
			body(matchesJsonSchemaInClasspath("schemas/" + schemaFile));
	}
}

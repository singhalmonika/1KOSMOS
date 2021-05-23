package utility;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

public class Util {
	public static String cwd = Path.of("").toAbsolutePath().toString();
	public static Object[][] csvData = null;
	

	/**
	 * This method will parse the CSV file and return the object array of CSV elements
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws CsvException
	 */
	public static Object[][] readCSVFile(String filePath) throws IOException, CsvException {
        String csvFilePath = FilenameUtils.separatorsToSystem(cwd+filePath);
		FileReader filereader = new FileReader(csvFilePath);
		CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
		List<String[]> csvFullFileData = csvReader.readAll();
		int rowsCSV = csvFullFileData.size();
		int colCSV = csvFullFileData.get(0).length;
		csvData = new Object[rowsCSV][colCSV];
		for (int i = 0; i < rowsCSV; i++) {
			for (int j = 0; j < colCSV; j++) {
				csvData[i][j] = csvFullFileData.get(i)[j];
			}
		}

		return csvData;
	}

}

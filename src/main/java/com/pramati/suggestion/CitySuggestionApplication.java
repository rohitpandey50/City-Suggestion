package com.pramati.suggestion;

import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@SpringBootApplication
public class CitySuggestionApplication {
	static Logger logger = LoggerFactory.getLogger(CitySuggestionApplication.class);
	public static List<String> cityList=new ArrayList<>();
	public static void main(String[] args) {
		SpringApplication.run(CitySuggestionApplication.class, args);
		redRecordFromCSV();
	}

	/**Open CSV used to read csv file row wise and match prefix if found then add in separate set.
	 * csv file also contain duplicate city name and case insensitive name.It will return unique suggestion name for city.
	 * Used TreeSet to contain unique city name and ignore case insensitive .
	 */
	public static void redRecordFromCSV()
	{
		try {
			logger.info("caching city List.....");
			ClassPathResource cpr = new ClassPathResource("static/city_data.csv");
			CSVReader csvReader = new CSVReader(new InputStreamReader(cpr.getInputStream()));
			String[] nextRecord;
			Set<String> cityList=new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
               /*Ignore first row and loop the csv row records and find each row record in nextRecord array.
                nextRecords contain array of column value in single row.*/
			csvReader.readNext();
			while ((nextRecord = csvReader.readNext()) != null) {
				cityList.add(nextRecord[7]);
			}
			//Convert TreeSet to List so we can search backward and forward for performance.
			CitySuggestionApplication.cityList.addAll(cityList);
			logger.info("City cached");

		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}

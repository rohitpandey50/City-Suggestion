package com.pramati.suggestion.services;


import com.opencsv.CSVReader;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.util.*;

@Service
public class CitySuggestionImpl implements CitySuggestion {

    @Override
    public String getSuggestion(String prefix, int atmost) {
       StringBuilder stringBuilder=new StringBuilder();
       Set<String> records=redRecordFromCSV(prefix,atmost);
       //If no records found return message;
       if(records.isEmpty())
           return "No city found.";
       for(String record:records) {
           stringBuilder.append(record+System.lineSeparator());
       }
       return stringBuilder.toString();
    }


    /**Open CSV used to read csv file row wise and match prefix if found then add in separate set.
     * csv file also contain duplicate city name and case insensitive name.It will return unique suggestion name for city.
     * Used TreeSet to contain unique city name and ignore case insensitive .
     * @param prefix
     * @param atmost
     * @return
     */
    public Set<String> redRecordFromCSV(String prefix,  int atmost)
    {
        try {

            ClassPathResource cpr = new ClassPathResource("static/city_data.csv");
            CSVReader csvReader = new CSVReader(new InputStreamReader(cpr.getInputStream()));
            Set<String> suggestion=new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            String[] nextRecord;
           /*Ignore first row and loop the csv row records and find each row record in nextRecord array.
           nextRecords contain array of column value in single row.*/
            csvReader.readNext();
            while ((nextRecord = csvReader.readNext()) != null) {

                //In each row city is at 7 column, change nextRecord index in case csv column ordering change.
                if(nextRecord[7].toUpperCase().startsWith(prefix.toUpperCase()))
                    suggestion.add(nextRecord[7]);

                // check if atmost records have been completed, break the loop and return that list;
                if(suggestion.size()==atmost)
                    return suggestion;
            }
            return suggestion;
        }
        catch (Exception e) {
            return Collections.emptySet();
        }
    }


}

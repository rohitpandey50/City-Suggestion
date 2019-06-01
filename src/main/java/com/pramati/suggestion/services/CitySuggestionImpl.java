package com.pramati.suggestion.services;


import com.opencsv.CSVReader;


import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.util.*;

@Service
public class CitySuggestionImpl implements CitySuggestion {

    @Override
    public String getSuggestion(String prefix, int numberOfRecords) {
       StringBuilder stringBuilder=new StringBuilder();
       Set<String> records=redRecordFromCSV(prefix,numberOfRecords);
       //If no records found return message;
       if(records.isEmpty())
           return "No city start with this char";
       for(String record:records) {
           stringBuilder.append(record+System.lineSeparator());
       }
       return stringBuilder.toString();
    }


    /** Read csv file line by line and and match prefix if found add in separate list.
     * csv file also contain duplicate data and case insensitive name that why TreeSet used.
     *
     * @param prefix
     * @param numberOfRecords
     * @return
     */
    public Set<String> redRecordFromCSV(String prefix,  int numberOfRecords)
    {
        try {
            File file = new ClassPathResource("city_data.csv").getFile();
            CSVReader csvReader = new CSVReader(new FileReader(file));
            Set<String> suggestion=new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            String[] nextRecord;
            // search all the row for given prefix
            while ((nextRecord = csvReader.readNext()) != null) {
                //In CSV file column 7 is city name
                if(nextRecord[7].toUpperCase().startsWith(prefix.toUpperCase()))
                    suggestion.add(nextRecord[7]);

                // check if atmost records have been completed, break the loop and return that list;
                if(suggestion.size()==numberOfRecords)
                    return suggestion;
            }
            return suggestion;
        }
        catch (Exception e) {
            return Collections.emptySet();
        }
    }


}

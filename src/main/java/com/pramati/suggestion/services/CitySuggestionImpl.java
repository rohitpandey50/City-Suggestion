package com.pramati.suggestion.services;


import com.opencsv.CSVReader;

import static com.pramati.suggestion.CitySuggestionApplication.cityList;
import  com.pramati.suggestion.CitySuggestionApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.util.*;


@Service
public class CitySuggestionImpl implements CitySuggestion {

    Logger logger = LoggerFactory.getLogger(CitySuggestionImpl.class);

    @Override
    public String getSuggestion(String prefix, int atmost) {
       StringBuilder stringBuilder=new StringBuilder();

       //Cache the cities
       if(CitySuggestionApplication.cityList.size()==0)
           redRecordFromCSV();

       List<String> records=getSuggestedCity(prefix,atmost);
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
     */
    public void redRecordFromCSV()
    {
            try {
                logger.info("Reading city List.....");
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

            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
    }

    /**
     * Search city from cache, searching will happen forward and backward.
     * @param prefix
     * @param atmost
     * @return
     */
    private List<String> getSuggestedCity(String prefix,int atmost)
    {
        logger.info("Getting suggested city");
        List<String> suggestionList=new ArrayList<>();
        int start=0, end=cityList.size()-1;
        //convert prefix to upperCase to deal with case insensitive.
        prefix=prefix.toUpperCase();
        while (start<end&&suggestionList.size()<atmost)
        {
            if(cityList.get(start).toUpperCase().startsWith(prefix))
            {
                suggestionList.add(cityList.get(start));
            }

            if(cityList.get(end).toUpperCase().startsWith(prefix))
            {
                suggestionList.add(cityList.get(end));
            }
           start++;
           end--;

        }
       return  suggestionList;

    }


}

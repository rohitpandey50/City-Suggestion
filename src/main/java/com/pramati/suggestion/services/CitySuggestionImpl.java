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

       List<String> records=getSuggestedCity(prefix,atmost);
       //If no records found return message;
       if(records.isEmpty())
           return "No city found.";
       for(String record:records) {
           stringBuilder.append(record+System.lineSeparator());
       }
       return stringBuilder.toString();
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

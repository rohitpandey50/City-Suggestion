package com.pramati.suggestion.controller;

import com.pramati.suggestion.services.CitySuggestionImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
public class CitySuggestion {

    @Autowired
    CitySuggestionImpl citySuggestion;

    @RequestMapping("/suggested_city")
    public String getCityList(@NotNull @NotEmpty @RequestParam("start") String start, @RequestParam("atmost") int atmost)
    {
        return  citySuggestion.getSuggestion(start,atmost);
    }
}

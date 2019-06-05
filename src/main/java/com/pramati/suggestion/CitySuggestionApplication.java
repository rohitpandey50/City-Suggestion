package com.pramati.suggestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@SpringBootApplication
public class CitySuggestionApplication {
	public static List<String> cityList=new ArrayList<>();
	public static void main(String[] args) {
		SpringApplication.run(CitySuggestionApplication.class, args);
	}

}

package com.selfproject.coronavirus.covid19tracker.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.selfproject.coronavirus.covid19tracker.models.LocationStats;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CoronavirusDataService {
    private static final String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStats> allStats = new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    /*
     * How to fetch data from a URL (Basic)
     */
    @PostConstruct
    /**
     * Schedule a Task Using Cron Expressions,This means saying spring to run this
     * method every day
     */
    @Scheduled(cron = "* * 1 * * *")
    public void fetchData() throws IOException, InterruptedException {

        List<LocationStats> newStats = new ArrayList<>();

        /*
         * 1. HTTP Client -> An HttpClient can be used to send requests and retrieve
         * their responses
         * 2. newHttpClient() -> Returns a new HttpClient with default settings.
         * Equivalent to newBuilder().build().
         */
        HttpClient client = HttpClient.newHttpClient();

        /*
         * 3. HttpRequest -> An HttpRequest instance is built through an HttpRequest
         * builder. An HttpRequest builder is obtained from one of the newBuilder
         * methods
         */
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();

        /*
         * 3. HttpResponse -> An HttpResponse is not created directly, but rather
         * returned as a result of sending an HttpRequest.
         * 4. send() -> Params:
         * request – the request
         * responseBodyHandler – the response body handler
         * Returns:
         * the response
         * 
         * 5. BodyHandlers -> The class BodyHandlers provides implementations of many
         * common response handlers(In this case of String)
         */
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        /*
         * How to do CSV parsing in Java using org.apache.commons.csv
         */
        StringReader csvStringReader = new StringReader(response.body()); // Convert String to StringReader as parse()
                                                                          // accept Reader as arg.
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvStringReader);
        for (CSVRecord record : records) {
            LocationStats locationStat = new LocationStats();
            locationStat.setCountry(record.get("Country/Region"));
            locationStat.setState(record.get("Province/State"));
            locationStat.setLatestTotalCases(Integer.parseInt(record.get(record.size() - 1)));
            newStats.add(locationStat);
        }
        this.allStats = newStats;
    }
}

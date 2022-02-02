package com.selfproject.coronavirus.covid19tracker.services;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CoronavirusDataService {
    private static final String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    /*
        How to fetch data from a URL (Basic)
     */
    @PostConstruct
    public void fetchData() throws IOException, InterruptedException {
        /*
            1. HTTP Client -> An HttpClient can be used to send requests and retrieve their responses
            2. newHttpClient() -> Returns a new HttpClient with default settings.
            Equivalent to newBuilder().build().
         */
        HttpClient client = HttpClient.newHttpClient();

        /*
            3. HttpRequest -> An HttpRequest instance is built through an HttpRequest builder. An HttpRequest builder is obtained from one of the newBuilder methods
         */
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();

        /*
        3. HttpResponse -> An HttpResponse is not created directly, but rather returned as a result of sending an HttpRequest.
        4. send() -> Params:
                        request – the request
                        responseBodyHandler – the response body handler
                        Returns:
                        the response

            5. BodyHandlers -> The class BodyHandlers provides implementations of many common response handlers(In this case of String)
         */
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());//printing the response's body
    }
}
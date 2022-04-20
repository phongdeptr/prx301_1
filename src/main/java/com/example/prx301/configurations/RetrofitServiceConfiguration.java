package com.example.prx301.configurations;

import com.example.prx301.services.CrawlerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitServiceConfiguration {
    @Bean
    public CrawlerService retrofit(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        CrawlerService service = new Retrofit.Builder()
                .baseUrl("https://6254ffbe89f28cf72b6379b0.mockapi.io/students/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build().create(CrawlerService.class);
        return service;
    }
}

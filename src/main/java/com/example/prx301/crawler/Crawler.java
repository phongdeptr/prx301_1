package com.example.prx301.crawler;

import com.example.prx301.dto.MajorDTO;
import com.example.prx301.dto.StudentDTO;
import com.example.prx301.services.CrawlerService;
import com.example.prx301.utils.XMLHelpers;
import lombok.Data;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class Crawler {
    private CrawlerService crawlerService;

    public Crawler(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }
    public String saveCrawlingResultToXml(){
        String filePath="";
        Call<List<StudentDTO>> studentCall = crawlerService.getStudents();
        Call<List<MajorDTO>> majorCall = crawlerService.getMajors();
        List<StudentDTO> students = new ArrayList<>();
        List<MajorDTO> majors = new ArrayList<>();
        try {
            Response<List<StudentDTO>> studentResponse = studentCall.execute();
            Response<List<MajorDTO>> majorsResponse = majorCall.execute();
            students = studentResponse.body();
            majors = majorsResponse.body();
            filePath = XMLHelpers.generateXMLDataFromApi(students,majors);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }
}

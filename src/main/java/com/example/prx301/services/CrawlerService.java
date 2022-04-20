package com.example.prx301.services;

import com.example.prx301.dto.MajorDTO;
import com.example.prx301.dto.StudentDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface CrawlerService {
    @GET("/students")
    public Call<List<StudentDTO>> getStudents();

    @GET("/students/{studentId}")
    public Call<StudentDTO> findStudentById(@Path("id") String id);

    @GET("/majors")
    public Call<List<MajorDTO>> getMajors();
}

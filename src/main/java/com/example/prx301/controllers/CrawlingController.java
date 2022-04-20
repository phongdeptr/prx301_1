package com.example.prx301.controllers;

import com.example.prx301.crawler.Crawler;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;

@RestController
@RequestMapping("api/v1/crawler")
public class CrawlingController {
    Crawler crawler;

    public CrawlingController(Crawler crawler) {
        this.crawler = crawler;
    }

    @GetMapping
    public ResponseEntity<?> crawling() throws MalformedURLException {
        String s = crawler.saveCrawlingResultToXml();
        Path filePath = Path.of(s);
        System.out.println("file path to api: "+s);
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists()) {
            String contentType = "application/xml";
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
        } else {

        }
        return ResponseEntity.ok(s);
    }
}

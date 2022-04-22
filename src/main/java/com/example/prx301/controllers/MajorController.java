package com.example.prx301.controllers;

import com.example.prx301.dto.MajorDTO;
import com.example.prx301.exceptions.MajorException;
import com.example.prx301.services.MajorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/majors")
public class MajorController {
    private MajorService majorService;

    public MajorController(MajorService majorService) {
        this.majorService = majorService;
    }

    @GetMapping("/{id}")
    public MajorDTO findMajorById(@PathVariable("id") String majorId){
        Optional<MajorDTO> result = majorService.loadMajor().stream().filter(major -> major.getId().equals(majorId))
                .findFirst();
        return result.orElse(new MajorDTO());
    }

    @GetMapping
    public ResponseEntity<?> loadMajor(){
        return ResponseEntity.ok(majorService.loadMajor());
    }


    @PostMapping
    public ResponseEntity<?> createMajor(@RequestBody MajorDTO dto) throws MajorException {
        return ResponseEntity.ok(majorService.createMajor(dto));
    }

    @PutMapping
    public ResponseEntity<?> updateMajor(@RequestBody MajorDTO dto) throws MajorException {
        return ResponseEntity.ok(majorService.updateMajor(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMajor(@RequestBody MajorDTO dto) throws MajorException {
        majorService.deleteMajor(dto);
        return ResponseEntity.ok("SUCCESS");
    }
}

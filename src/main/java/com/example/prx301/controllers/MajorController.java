package com.example.prx301.controllers;

import com.example.prx301.dto.MajorDTO;
import com.example.prx301.exceptions.MajorException;
import com.example.prx301.repositories.MajorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/majors")
public class MajorController {
    private MajorRepository<MajorDTO> majorRepository;

    public MajorController(MajorRepository<MajorDTO> majorRepository) {
        this.majorRepository = majorRepository;
    }

    @GetMapping("/{id}")
    public MajorDTO findMajorById(@PathVariable("id") String majorId){
        Optional<MajorDTO> result = majorRepository.loadMajor().stream().filter(major -> major.getId().equals(majorId))
                .findFirst();
        return result.orElse(new MajorDTO());
    }

    @GetMapping
    public List<MajorDTO> loadMajor(String majorId){
        return majorRepository.loadMajor();
    }


    @PostMapping
    public MajorDTO createMajor(@RequestBody MajorDTO dto) throws MajorException {
        return majorRepository.createMajor(dto);
    }

    @PutMapping
    public MajorDTO updateMajor(@RequestBody MajorDTO dto) throws MajorException {
        return majorRepository.updateMajor(dto);
    }

    @DeleteMapping
    public MajorDTO deleteMajor(@RequestBody MajorDTO dto) throws MajorException {
        return majorRepository.removeMajor(dto.getId());
    }
}

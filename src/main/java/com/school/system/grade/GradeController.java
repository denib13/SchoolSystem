package com.school.system.grade;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/grades/")
public class GradeController {
    private final GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping
    public ResponseEntity<GradeResponseDTO> createGrade(@RequestBody @Valid GradeRequestDTO gradeDTO) {
        return new ResponseEntity<>(gradeService.createGrade(gradeDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<GradeResponseDTO>> getGrades(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(gradeService.getGrades(pageNo, pageSize), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<GradeResponseDTO> updateGrade(@PathVariable("id") UUID id,
                                             @RequestBody @Valid GradeRequestDTO gradeDTO) {
        return new ResponseEntity<>(gradeService.updateGrade(id, gradeDTO), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable("id") UUID id) {
        gradeService.deleteGrade(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

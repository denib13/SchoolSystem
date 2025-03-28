package com.school.system.subject;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/subjects/")
public class SubjectController {
    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public ResponseEntity<SubjectResponseDTO> createSubject(@RequestBody @Valid SubjectRequestDTO subjectDTO) {
        return new ResponseEntity<>(subjectService.createSubject(subjectDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SubjectResponseDTO>> getSubjects() {
        return new ResponseEntity<>(subjectService.getSubjects(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<SubjectResponseDTO> updateSubject(@PathVariable("id") UUID id,
                                                 @RequestBody @Valid SubjectRequestDTO subjectDTO) {
        return new ResponseEntity<>(subjectService.updateSubject(id, subjectDTO), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable("id") UUID id) {
        subjectService.deleteSubject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

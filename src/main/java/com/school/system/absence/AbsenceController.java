package com.school.system.absence;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/absences/")
public class AbsenceController {
    private final AbsenceService absenceService;

    @Autowired
    public AbsenceController(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    @PostMapping
    public ResponseEntity<AbsenceResponseDTO> createAbsence(@RequestBody @Valid AbsenceRequestDTO absenceDTO) {
        return new ResponseEntity<>(absenceService.createAbsence(absenceDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AbsenceResponseDTO>> getAbsences() {
        return new ResponseEntity<>(absenceService.getAbsences(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<AbsenceResponseDTO> updateAbsence(@PathVariable("id") UUID id,
                                                            @RequestBody @Valid AbsenceRequestDTO absenceDTO) {
        return new ResponseEntity<>(absenceService.updateAbsence(id, absenceDTO), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAbsence(@PathVariable("id") UUID id) {
        absenceService.deleteAbsence(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

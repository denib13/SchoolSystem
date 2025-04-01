package com.school.system.absence;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<AbsenceResponseDTO>> getAbsences(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(absenceService.getAbsences(pageNo, pageSize), HttpStatus.OK);
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

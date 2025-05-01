package com.school.system.subject;

import com.school.system.absence.Absence;
import com.school.system.absence.AbsenceResponseDTO;
import com.school.system.mark.MarkResponseDTO;
import com.school.system.remark.RemarkResponseDTO;
import com.school.system.users.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<SubjectResponseDTO> createSubject(@RequestBody @Valid SubjectRequestDTO subjectDTO,
                                                            @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(subjectService.createSubject(subjectDTO, user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<SubjectResponseDTO>> getSubjects(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(subjectService.getSubjects(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<SubjectResponseDTO> getSubject(@PathVariable("id") UUID id,
                                                         @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(subjectService.getSubject(id, user), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<SubjectResponseDTO> updateSubject(@PathVariable("id") UUID id,
                                                 @RequestBody @Valid SubjectRequestDTO subjectDTO,
                                                            @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(subjectService.updateSubject(id, subjectDTO, user), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable("id") UUID id,
                                              @AuthenticationPrincipal User user) {
        subjectService.deleteSubject(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}/marks")
    public ResponseEntity<Page<MarkResponseDTO>> findMarksBySubject(
            @PathVariable("id") UUID id,
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
            @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(subjectService.getMarksBySubject(id, pageNo, pageSize, user), HttpStatus.OK);
    }

    @GetMapping("{id}/remarks")
    public ResponseEntity<Page<RemarkResponseDTO>> findRemarksBySubject(
            @PathVariable("id") UUID id,
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
            @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(subjectService.getRemarksBySubject(id, pageNo, pageSize, user), HttpStatus.OK);
    }

    @GetMapping("{id}/absences")
    public ResponseEntity<Page<AbsenceResponseDTO>> findAbsencesBySubject(
            @PathVariable("id") UUID id,
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
            @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(subjectService.getAbsencesBySubject(id, pageNo, pageSize, user), HttpStatus.OK);
    }
}

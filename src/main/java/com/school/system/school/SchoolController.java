package com.school.system.school;

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
@RequestMapping(path = "api/schools/")
public class SchoolController {
    private final SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping
    public ResponseEntity<SchoolResponseDTO> createSchool(@RequestBody @Valid SchoolRequestDTO schoolDTO,
                                                          @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(schoolService.createSchool(schoolDTO, user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<SchoolResponseDTO>> getSchools(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(schoolService.getSchools(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<SchoolResponseDTO> getSchool(@PathVariable("id") UUID id,
                                                       @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(schoolService.getSchool(id, user), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<SchoolResponseDTO> updateSchool(@PathVariable("id") UUID id,
                                               @RequestBody @Valid SchoolRequestDTO schoolDTO,
                                                          @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(schoolService.updateSchool(id, schoolDTO, user), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSchool(@PathVariable("id") UUID id,
                                             @AuthenticationPrincipal User user) {
        schoolService.deleteSchool(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

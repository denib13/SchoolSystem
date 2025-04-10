package com.school.system.users.parents;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/parents/")
public class ParentController {
    private final ParentService parentService;

    @Autowired
    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @PostMapping
    public ResponseEntity<ParentResponseDTO> createParent(@RequestBody @Valid ParentRequestDTO parentDTO) {
        return new ResponseEntity<>(parentService.createParent(parentDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ParentResponseDTO>> getParents(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(parentService.getParents(pageNo, pageSize), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ParentResponseDTO> updateParent(@PathVariable("id") UUID id,
                                               @RequestBody @Valid ParentRequestDTO parentDTO) {
        return new ResponseEntity<>(parentService.updateParent(id, parentDTO), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteParent(@PathVariable("id") UUID id) {
        parentService.deleteParent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

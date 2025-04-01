package com.school.system.mark;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/marks/")
public class MarkController {
    private final MarkService markService;

    @Autowired
    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    @PostMapping
    public ResponseEntity<MarkResponseDTO> createMark(@RequestBody @Valid MarkRequestDTO markDTO) {
        return new ResponseEntity<>(markService.createMark(markDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<MarkResponseDTO>> getMarks(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(markService.getMarks(pageNo, pageSize), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<MarkResponseDTO> updateMark(@PathVariable("id") UUID id,
                                           @RequestBody @Valid MarkRequestDTO markDTO) {
        return new ResponseEntity<>(markService.updateMark(id, markDTO), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteMark(@PathVariable("id") UUID id) {
        markService.deleteMark(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

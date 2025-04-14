package com.school.system.mark;

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
@RequestMapping(path = "api/marks/")
public class MarkController {
    private final MarkService markService;

    @Autowired
    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    @PostMapping
    public ResponseEntity<MarkResponseDTO> createMark(@RequestBody @Valid MarkRequestDTO markDTO,
                                                      @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(markService.createMark(markDTO, user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<MarkResponseDTO>> getMarks(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(markService.getMarks(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<MarkResponseDTO> getMark(@PathVariable("id") UUID id,
                                                   @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(markService.getMark(id, user), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<MarkResponseDTO> updateMark(@PathVariable("id") UUID id,
                                           @RequestBody @Valid MarkRequestDTO markDTO,
                                           @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(markService.updateMark(id, markDTO, user), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteMark(@PathVariable("id") UUID id,
                                           @AuthenticationPrincipal User user) {
        markService.deleteMark(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

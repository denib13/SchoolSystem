package com.school.system.users.parents;

import com.school.system.users.student.StudentResponseDTO;
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
@RequestMapping(path = "api/parents/")
public class ParentController {
    private final ParentService parentService;

    @Autowired
    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @PostMapping
    public ResponseEntity<ParentResponseDTO> createParent(@RequestBody @Valid ParentRequestDTO parentDTO) {
        return new ResponseEntity<>(ParentMapper.parentToParentResponseDTO(parentService.createParent(parentDTO)),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ParentResponseDTO>> getParents(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(parentService.getParents(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ParentResponseDTO> getParent(@PathVariable("id") UUID id,
                                                       @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(parentService.getParent(id, user), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ParentResponseDTO> updateParent(@PathVariable("id") UUID id,
                                               @RequestBody @Valid ParentRequestDTO parentDTO,
                                               @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(parentService.updateParent(id, parentDTO, user), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteParent(@PathVariable("id") UUID id,
                                             @AuthenticationPrincipal User user) {
        parentService.deleteParent(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}/children")
    public ResponseEntity<Page<StudentResponseDTO>> getChildren(
            @PathVariable("id") UUID id,
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(parentService.getChildren(id, pageNo, pageSize, user), HttpStatus.OK);
    }
}

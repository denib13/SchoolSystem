package com.school.system.users.headmaster;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/headmasters/")
public class HeadmasterController {
    private final HeadmasterService headmasterService;

    @Autowired
    public HeadmasterController(HeadmasterService headmasterService) {
        this.headmasterService = headmasterService;
    }

    @PostMapping
    public ResponseEntity<HeadmasterResponseDTO> createHeadmaster(@RequestBody @Valid HeadmasterRequestDTO headmasterDTO) {
        return new ResponseEntity<>(headmasterService.createHeadmaster(headmasterDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<HeadmasterResponseDTO>> getHeadmasters(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(headmasterService.getHeadmasters(pageNo, pageSize), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<HeadmasterResponseDTO> updateHeadmaster(@PathVariable("id") UUID id,
                                                                  @RequestBody @Valid HeadmasterRequestDTO headmasterDTO) {
        return new ResponseEntity<>(headmasterService.updateHeadmaster(id, headmasterDTO), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteHeadmaster(@PathVariable("id") UUID id) {
        headmasterService.deleteHeadmaster(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

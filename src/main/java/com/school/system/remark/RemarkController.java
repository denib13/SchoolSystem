package com.school.system.remark;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/remarks/")
public class RemarkController {
    private final RemarkService remarkService;

    @Autowired
    public RemarkController(RemarkService remarkService) {
        this.remarkService = remarkService;
    }

    @PostMapping
    public ResponseEntity<RemarkResponseDTO> createRemark(@RequestBody @Valid RemarkRequestDTO remarkDTO) {
        return new ResponseEntity<>(remarkService.createRemark(remarkDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<RemarkResponseDTO>> getRemarks(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(remarkService.getRemarks(pageNo, pageSize), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<RemarkResponseDTO> updateRemark(@PathVariable("id") UUID id,
                                                          @RequestBody @Valid RemarkRequestDTO remarkDTO) {
        return new ResponseEntity<>(remarkService.updateRemark(id, remarkDTO), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteRemark(@PathVariable("id") UUID id) {
        remarkService.deleteRemark(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

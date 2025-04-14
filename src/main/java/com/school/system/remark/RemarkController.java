package com.school.system.remark;

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
@RequestMapping(path = "api/remarks/")
public class RemarkController {
    private final RemarkService remarkService;

    @Autowired
    public RemarkController(RemarkService remarkService) {
        this.remarkService = remarkService;
    }

    @PostMapping
    public ResponseEntity<RemarkResponseDTO> createRemark(@RequestBody @Valid RemarkRequestDTO remarkDTO,
                                                          @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(remarkService.createRemark(remarkDTO, user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<RemarkResponseDTO>> getRemarks(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(remarkService.getRemarks(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<RemarkResponseDTO> getMark(@PathVariable("id") UUID id,
                                                     @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(remarkService.getRemark(id, user), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<RemarkResponseDTO> updateRemark(@PathVariable("id") UUID id,
                                                          @RequestBody @Valid RemarkRequestDTO remarkDTO,
                                                          @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(remarkService.updateRemark(id, remarkDTO, user), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteRemark(@PathVariable("id") UUID id,
                                             @AuthenticationPrincipal User user) {
        remarkService.deleteRemark(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

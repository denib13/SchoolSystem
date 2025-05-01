package com.school.system.users.headmaster;

import com.school.system.users.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
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
        return new ResponseEntity<>(
                HeadmasterMapper.headmasterToHeadmasterResponseDTO(headmasterService.createHeadmaster(headmasterDTO)),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<Page<HeadmasterResponseDTO>> getHeadmasters(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(headmasterService.getHeadmasters(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<HeadmasterResponseDTO> getHeadmaster(@PathVariable("id") UUID id,
                                                               @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(headmasterService.getHeadmaster(id, user), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<HeadmasterResponseDTO> updateHeadmaster(@PathVariable("id") UUID id,
                                                                  @RequestBody @Valid HeadmasterRequestDTO headmasterDTO,
                                                                  @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(headmasterService.updateHeadmaster(id, headmasterDTO, user), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteHeadmaster(@PathVariable("id") UUID id,
                                                 @AuthenticationPrincipal User user) {
        headmasterService.deleteHeadmaster(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("unassigned")
    public ResponseEntity<Page<HeadmasterResponseDTO>> getHeadmastersWithNoSchoolAssigned(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(headmasterService.getHeadmastersWithNoSchoolAssigned(pageNo, pageSize), HttpStatus.OK);
    }
}

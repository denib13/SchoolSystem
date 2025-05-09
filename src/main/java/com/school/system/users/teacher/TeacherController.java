package com.school.system.users.teacher;

import com.school.system.absence.AbsenceResponseDTO;
import com.school.system.mark.MarkResponseDTO;
import com.school.system.remark.RemarkResponseDTO;
import com.school.system.school.SchoolResponseDTO;
import com.school.system.subject.SubjectResponseDTO;
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
@RequestMapping(path = "api/teachers/")
public class TeacherController {
    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    public ResponseEntity<TeacherResponseDTO> createTeacher(@RequestBody @Valid TeacherRequestDTO teacherDTO) {
        return new ResponseEntity<>(TeacherMapper.teacherToTeacherResponseDTO(teacherService.createTeacher(teacherDTO)),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<TeacherResponseDTO>> getTeachers(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize
    ) {
        return new ResponseEntity<>(teacherService.getTeachers(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<TeacherResponseDTO> getTeacher(@PathVariable("id") UUID id,
                                                         @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(teacherService.getTeacher(id, user), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<TeacherResponseDTO> updateTeacher(@PathVariable("id") UUID id,
                                                 @RequestBody @Valid TeacherRequestDTO teacherDTO,
                                                 @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(teacherService.updateTeacher(id, teacherDTO, user), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable("id") UUID id,
                                              @AuthenticationPrincipal User user) {
        teacherService.deleteTeacher(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}/schools")
    public ResponseEntity<Page<SchoolResponseDTO>> getTeacherSchools(
            @PathVariable("id") UUID id,
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(teacherService.getTeacherSchools(id, pageNo, pageSize, user), HttpStatus.OK);
    }

    @GetMapping("{id}/subjects")
    public ResponseEntity<Page<SubjectResponseDTO>> getTeacherSubjects(
            @PathVariable("id") UUID id,
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(teacherService.getTeacherSubjects(id, pageNo, pageSize, user), HttpStatus.OK);
    }

    @GetMapping("{id}/marks")
    public ResponseEntity<Page<MarkResponseDTO>> getTeacherMarks(
            @PathVariable("id") UUID id,
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(teacherService.getTeacherMarks(id, pageNo, pageSize, user), HttpStatus.OK);
    }

    @GetMapping("{id}/remarks")
    public ResponseEntity<Page<RemarkResponseDTO>> getTeacherRemarks(
            @PathVariable("id") UUID id,
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(teacherService.getTeacherRemarks(id, pageNo, pageSize, user), HttpStatus.OK);
    }

    @GetMapping("{id}/absences")
    public ResponseEntity<Page<AbsenceResponseDTO>> getTeacherAbsences(
            @PathVariable("id") UUID id,
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(teacherService.getTeacherAbsences(id, pageNo, pageSize, user), HttpStatus.OK);
    }
}

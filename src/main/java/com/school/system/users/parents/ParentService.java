package com.school.system.users.parents;

import com.school.system.exception.NotFoundException;
import com.school.system.policy.DeletePolicy;
import com.school.system.policy.ReadPolicy;
import com.school.system.policy.UpdatePolicy;
import com.school.system.policy.exception.CannotDeleteException;
import com.school.system.policy.exception.CannotReadException;
import com.school.system.policy.exception.CannotUpdateException;
import com.school.system.users.student.Student;
import com.school.system.users.student.StudentMapper;
import com.school.system.users.student.StudentRepository;
import com.school.system.users.student.StudentResponseDTO;
import com.school.system.users.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ParentService {
    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public ParentService(ParentRepository parentRepository,
                         StudentRepository studentRepository) {
        this.parentRepository = parentRepository;
        this.studentRepository = studentRepository;
    }

    public Parent createParent(ParentRequestDTO parentDTO) {
        List<Student> children = parentDTO.getChildren() != null
                ? studentRepository.findAllById(parentDTO.getChildren())
                : new ArrayList<>();

        Parent toCreate = new Parent();
        toCreate.setName(parentDTO.getName());
        toCreate.setMiddleName(parentDTO.getMiddleName());
        toCreate.setSurname(parentDTO.getSurname());
        toCreate.setNationalIdNumber(parentDTO.getNationalIdNumber());
        toCreate.setUsername(parentDTO.getUsername());
        toCreate.setPassword(parentDTO.getPassword());
        toCreate.setEmail(parentDTO.getEmail());
        toCreate.setChildren(children);

        return parentRepository.save(toCreate);
    }

    public Page<ParentResponseDTO> getParents(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Parent> parents = parentRepository.findAll(pageable);
        return parents.map(parent -> ParentMapper.parentToParentResponseDTO(parent));
    }

    public ParentResponseDTO getParent(UUID id, User user) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Parent not found"));

        if(!ReadPolicy.canReadParent(user, parent)) {
            throw new CannotReadException();
        }

        return ParentMapper.parentToParentResponseDTO(parent);
    }

    public ParentResponseDTO updateParent(UUID id, ParentRequestDTO parentDTO, User user) {
        Parent toUpdate = parentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Parent not found"));

        if(!UpdatePolicy.canUpdateParent(user, toUpdate)) {
            throw new CannotUpdateException();
        }

        List<Student> children = parentDTO.getChildren() != null
                ? studentRepository.findAllById(parentDTO.getChildren())
                : new ArrayList<>();

        toUpdate.setName(parentDTO.getName());
        toUpdate.setMiddleName(parentDTO.getMiddleName());
        toUpdate.setSurname(parentDTO.getSurname());
        toUpdate.setNationalIdNumber(parentDTO.getNationalIdNumber());
        toUpdate.setUsername(parentDTO.getUsername());
        toUpdate.setChildren(children);

        return ParentMapper.parentToParentResponseDTO(parentRepository.save(toUpdate));
    }

    public void deleteParent(UUID id, User user) {
        Parent toDelete = parentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Parent not found"));

        if(!DeletePolicy.canDeleteParent(user)) {
            throw new CannotDeleteException();
        }

        parentRepository.delete(toDelete);
    }

    public Page<StudentResponseDTO> getChildren(UUID id, int pageNo, int pageSize, User user) {
        Parent parent = parentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Parent not found"));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<StudentResponseDTO> children = StudentMapper.studentListToStudentResponseDTOList(parent.getChildren());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), children.size());
        List<StudentResponseDTO> pageContent = children.subList(start, end);
        return new PageImpl<>(pageContent, pageable, children.size());
    }
}

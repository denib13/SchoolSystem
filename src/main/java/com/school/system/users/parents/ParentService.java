package com.school.system.users.parents;

import com.school.system.exception.NotFoundException;
import com.school.system.users.student.Student;
import com.school.system.users.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    public ParentResponseDTO createParent(ParentRequestDTO parentDTO) {
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

        return ParentMapper.parentToParentResponseDTO(parentRepository.save(toCreate));
    }

    public Page<ParentResponseDTO> getParents(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Parent> parents = parentRepository.findAll(pageable);
        return parents.map(parent -> ParentMapper.parentToParentResponseDTO(parent));
    }

    public ParentResponseDTO updateParent(UUID id, ParentRequestDTO parentDTO) {
        Parent toUpdate = parentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Parent not found"));

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

    public void deleteParent(UUID id) {
        Parent toDelete = parentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Parent not found"));
        parentRepository.delete(toDelete);
    }
}

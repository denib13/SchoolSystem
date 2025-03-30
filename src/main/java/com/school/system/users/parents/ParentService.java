package com.school.system.users.parents;

import com.school.system.users.student.Student;
import com.school.system.users.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<ParentResponseDTO> getParents() {
        return ParentMapper.parentListToParentResponseDTOList(parentRepository.findAll());
    }

    public ParentResponseDTO updateParent(UUID id, ParentRequestDTO parentDTO) {
        Parent toUpdate = parentRepository.findById(id).orElse(null);
        if(toUpdate == null) {
            return null;
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

    public void deleteParent(UUID id) {
        Parent toDelete = parentRepository.findById(id).orElse(null);
        if(toDelete == null) {
            return;
        }
        parentRepository.delete(toDelete);
    }
}

package com.school.system.users.headmaster;

import com.school.system.exception.NotFoundException;
import com.school.system.policy.DeletePolicy;
import com.school.system.policy.ReadPolicy;
import com.school.system.policy.UpdatePolicy;
import com.school.system.policy.exception.CannotDeleteException;
import com.school.system.policy.exception.CannotReadException;
import com.school.system.policy.exception.CannotUpdateException;
import com.school.system.school.School;
import com.school.system.school.SchoolRepository;
import com.school.system.users.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HeadmasterService {
    private final HeadmasterRepository headmasterRepository;
    private final SchoolRepository schoolRepository;

    @Autowired
    public HeadmasterService(HeadmasterRepository headmasterRepository,
                             SchoolRepository schoolRepository) {
        this.headmasterRepository = headmasterRepository;
        this.schoolRepository = schoolRepository;
    }

    public Headmaster createHeadmaster(HeadmasterRequestDTO headmasterDTO) {
        Headmaster toCreate = new Headmaster();

        School school = headmasterDTO.getSchool() != null
                ? schoolRepository.findById(headmasterDTO.getSchool())
                .orElseThrow(() -> new NotFoundException("School not found"))
                : null;

        toCreate.setName(headmasterDTO.getName());
        toCreate.setMiddleName(headmasterDTO.getMiddleName());
        toCreate.setSurname(headmasterDTO.getSurname());
        toCreate.setNationalIdNumber(headmasterDTO.getNationalIdNumber());
        toCreate.setUsername(headmasterDTO.getUsername());
        toCreate.setPassword(headmasterDTO.getPassword());
        toCreate.setEmail(headmasterDTO.getEmail());

        if(school != null) {
            toCreate.setSchool(school);
        }

        Headmaster newHeadmaster = headmasterRepository.save(toCreate);
        if(school != null) {
            school.setHeadmaster(newHeadmaster);
            schoolRepository.save(school);
        }

        return newHeadmaster;
    }

    public Page<HeadmasterResponseDTO> getHeadmasters(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Headmaster> headmasters = headmasterRepository.findAll(pageable);
        return headmasters.map(headmaster -> HeadmasterMapper.headmasterToHeadmasterResponseDTO(headmaster));
    }

    public HeadmasterResponseDTO getHeadmaster(UUID id, User user) {
        Headmaster headmaster = headmasterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Headmaster not found"));

        if(!ReadPolicy.canReadHeadmaster(user, headmaster)) {
            throw new CannotReadException();
        }

        return HeadmasterMapper.headmasterToHeadmasterResponseDTO(headmaster);
    }

    public HeadmasterResponseDTO updateHeadmaster(UUID id, HeadmasterRequestDTO headmasterDTO, User user) {
        Headmaster toUpdate = headmasterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Headmaster not found"));

        if(!UpdatePolicy.canUpdateHeadmaster(user, toUpdate)) {
            throw new CannotUpdateException();
        }

        School school = schoolRepository.findById(headmasterDTO.getSchool())
                .orElseThrow(() -> new NotFoundException("School not found"));

        if(toUpdate.getSchool() != null) {
            School oldSchool = toUpdate.getSchool();
            oldSchool.setHeadmaster(null);
        }
        toUpdate.setSchool(school);
        school.setHeadmaster(toUpdate);

        toUpdate.setName(headmasterDTO.getName());
        toUpdate.setMiddleName(headmasterDTO.getMiddleName());
        toUpdate.setSurname(headmasterDTO.getSurname());
        toUpdate.setNationalIdNumber(headmasterDTO.getNationalIdNumber());
        toUpdate.setUsername(headmasterDTO.getUsername());

        Headmaster updatedHeadmaster = headmasterRepository.save(toUpdate);

        return HeadmasterMapper.headmasterToHeadmasterResponseDTO(updatedHeadmaster);
    }

    public void deleteHeadmaster(UUID id, User user) {
        Headmaster toDelete = headmasterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Headmaster not found"));

        if(!DeletePolicy.canDeleteHeadmaster(user)) {
            throw new CannotDeleteException();
        }

        if(toDelete.getSchool() != null) {
            School school = toDelete.getSchool();
            school.setHeadmaster(null);
            schoolRepository.save(school);
        }

        headmasterRepository.delete(toDelete);
    }
}

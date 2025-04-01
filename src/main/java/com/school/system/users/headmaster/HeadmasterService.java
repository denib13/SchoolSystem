package com.school.system.users.headmaster;

import com.school.system.exception.NotFoundException;
import com.school.system.school.School;
import com.school.system.school.SchoolRepository;
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

    public HeadmasterResponseDTO createHeadmaster(HeadmasterRequestDTO headmasterDTO) {
        Headmaster toCreate = new Headmaster();

        School school = schoolRepository.findById(headmasterDTO.getSchool())
                .orElseThrow(() -> new NotFoundException("School not found"));

        toCreate.setName(headmasterDTO.getName());
        toCreate.setMiddleName(headmasterDTO.getMiddleName());
        toCreate.setSurname(headmasterDTO.getSurname());
        toCreate.setNationalIdNumber(headmasterDTO.getNationalIdNumber());
        toCreate.setUsername(headmasterDTO.getUsername());
        toCreate.setPassword(headmasterDTO.getPassword());
        toCreate.setEmail(headmasterDTO.getEmail());
        toCreate.setSchool(school);

        Headmaster newHeadmaster = headmasterRepository.save(toCreate);
        school.setHeadmaster(newHeadmaster);
        schoolRepository.save(school);

        return HeadmasterMapper.headmasterToHeadmasterResponseDTO(newHeadmaster);
    }

    public Page<HeadmasterResponseDTO> getHeadmasters(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Headmaster> headmasters = headmasterRepository.findAll(pageable);
        return headmasters.map(headmaster -> HeadmasterMapper.headmasterToHeadmasterResponseDTO(headmaster));
    }

    public HeadmasterResponseDTO updateHeadmaster(UUID id, HeadmasterRequestDTO headmasterDTO) {
        Headmaster toUpdate = headmasterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Headmaster not found"));

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

    public void deleteHeadmaster(UUID id) {
        Headmaster toDelete = headmasterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Headmaster not found"));
        headmasterRepository.delete(toDelete);
    }
}

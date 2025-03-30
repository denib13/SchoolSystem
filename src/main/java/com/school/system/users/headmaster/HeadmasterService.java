package com.school.system.users.headmaster;

import com.school.system.school.School;
import com.school.system.school.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        School school = schoolRepository.findById(headmasterDTO.getSchool()).orElse(null);
        if(school == null) {
            return null;
        }

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

        return HeadmasterMapper.INSTANCE.headmasterToHeadmasterResponseDTO(newHeadmaster);
    }

    public List<HeadmasterResponseDTO> getHeadmasters() {
        return HeadmasterMapper.INSTANCE.headmasterListToHeadmasterResponseDTOList(headmasterRepository.findAll());
    }

    public HeadmasterResponseDTO updateHeadmaster(UUID id, HeadmasterRequestDTO headmasterDTO) {
        Headmaster toUpdate = headmasterRepository.findById(id).orElse(null);

        if(toUpdate == null) {
            return null;
        }

        School school = schoolRepository.findById(headmasterDTO.getSchool()).orElse(null);

        if(school != null) {
            if(toUpdate.getSchool() != null) {
                School oldSchool = toUpdate.getSchool();
                oldSchool.setHeadmaster(null);
            }
            toUpdate.setSchool(school);
            school.setHeadmaster(toUpdate);
        }

        toUpdate.setName(headmasterDTO.getName());
        toUpdate.setMiddleName(headmasterDTO.getMiddleName());
        toUpdate.setSurname(headmasterDTO.getSurname());
        toUpdate.setNationalIdNumber(headmasterDTO.getNationalIdNumber());
        toUpdate.setUsername(headmasterDTO.getUsername());

        Headmaster updatedHeadmaster = headmasterRepository.save(toUpdate);
        // updatedHeadmaster.getSchool().setHeadmaster(updatedHeadmaster);
        // schoolRepository.save(updatedHeadmaster.getSchool());

        return HeadmasterMapper.INSTANCE.headmasterToHeadmasterResponseDTO(updatedHeadmaster);
    }

    public void deleteHeadmaster(UUID id) {
        Headmaster toDelete = headmasterRepository.findById(id).orElse(null);
        if(toDelete == null) {
            return;
        }
        headmasterRepository.delete(toDelete);
    }
}

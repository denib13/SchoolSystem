package com.school.system.users.headmaster;

import com.school.system.school.School;
import com.school.system.school.SchoolMapper;
import com.school.system.school.SchoolResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class HeadmasterMapper {
    public static HeadmasterResponseDTO headmasterToHeadmasterResponseDTO(Headmaster headmaster) {
        if(headmaster == null) {
            return null;
        }

        School school = headmaster.getSchool();
        if(school != null) {
            school.setHeadmaster(null);
        }
        SchoolResponseDTO schoolDTO = SchoolMapper.schoolToSchoolResponseDTO(school);

        return HeadmasterResponseDTO
                .builder()
                .id(headmaster.getId())
                .name(headmaster.getName())
                .middleName(headmaster.getMiddleName())
                .surname(headmaster.getSurname())
                .nationalIdNumber(headmaster.getNationalIdNumber())
                .username(headmaster.getUsername())
                .email(headmaster.getEmail())
                .role(headmaster.getRole() != null ? headmaster.getRole().getRoleAsString() : "undefined")
                .school(schoolDTO)
                .build();
    }

    public static List<HeadmasterResponseDTO> headmasterListToHeadmasterResponseDTOList(List<Headmaster> headmasters) {
        return headmasters
                .stream()
                .map(HeadmasterMapper::headmasterToHeadmasterResponseDTO)
                .collect(Collectors.toList());
    }
}

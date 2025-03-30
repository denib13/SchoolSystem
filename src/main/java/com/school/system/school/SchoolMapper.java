package com.school.system.school;

import com.school.system.users.headmaster.Headmaster;
import com.school.system.users.headmaster.HeadmasterMapper;
import com.school.system.users.headmaster.HeadmasterResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class SchoolMapper {
    public static SchoolResponseDTO schoolToSchoolResponseDTO(School school) {
        if(school == null) {
            return null;
        }

        Headmaster headmaster = school.getHeadmaster();
        if(headmaster != null) {
            headmaster.setSchool(null);
        }
        HeadmasterResponseDTO headmasterDTO = HeadmasterMapper.headmasterToHeadmasterResponseDTO(headmaster);

        return SchoolResponseDTO
                .builder()
                .name(school.getName())
                .city(school.getCity())
                .headmaster(headmasterDTO)
                .build();
    }

    public static List<SchoolResponseDTO> schoolListToSchoolResponseDTOList(List<School> schools) {
        return schools
                .stream()
                .map(SchoolMapper::schoolToSchoolResponseDTO)
                .collect(Collectors.toList());
    }
}

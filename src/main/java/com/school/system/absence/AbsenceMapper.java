package com.school.system.absence;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AbsenceMapper {
    AbsenceMapper INSTANCE = Mappers.getMapper(AbsenceMapper.class);
    AbsenceResponseDTO absenceToAbsenceResponseDTO(Absence absence);
    List<AbsenceResponseDTO> absenceListToAbsenceResponseDTOList(List<Absence> absences);
}

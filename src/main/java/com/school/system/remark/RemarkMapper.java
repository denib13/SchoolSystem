package com.school.system.remark;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RemarkMapper {
    RemarkMapper INSTANCE = Mappers.getMapper(RemarkMapper.class);

    RemarkResponseDTO remarkToRemarkResponseDTO(Remark remark);
    List<RemarkResponseDTO> remarkListToRemarkResponseDTOList(List<Remark> remarks);
}

package com.omar.hmis.service.mapper;

import com.omar.hmis.domain.*;
import com.omar.hmis.service.dto.InPatientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InPatient} and its DTO {@link InPatientDTO}.
 */
@Mapper(componentModel = "spring", uses = {RoomMapper.class, StaffMapper.class, PatientMapper.class})
public interface InPatientMapper extends EntityMapper<InPatientDTO, InPatient> {

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "suggestedBy.id", target = "suggestedById")
    @Mapping(source = "patient.id", target = "patientId")
    InPatientDTO toDto(InPatient inPatient);

    @Mapping(source = "roomId", target = "room")
    @Mapping(source = "suggestedById", target = "suggestedBy")
    @Mapping(source = "patientId", target = "patient")
    InPatient toEntity(InPatientDTO inPatientDTO);

    default InPatient fromId(Long id) {
        if (id == null) {
            return null;
        }
        InPatient inPatient = new InPatient();
        inPatient.setId(id);
        return inPatient;
    }
}

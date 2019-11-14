package com.omar.hmis.service.mapper;

import com.omar.hmis.domain.*;
import com.omar.hmis.service.dto.AppointmentScheduleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppointmentSchedule} and its DTO {@link AppointmentScheduleDTO}.
 */
@Mapper(componentModel = "spring", uses = {PatientMapper.class, StaffMapper.class})
public interface AppointmentScheduleMapper extends EntityMapper<AppointmentScheduleDTO, AppointmentSchedule> {

    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "staff.id", target = "staffId")
    @Mapping(source = "scheduledBy.id", target = "scheduledById")
    AppointmentScheduleDTO toDto(AppointmentSchedule appointmentSchedule);

    @Mapping(source = "patientId", target = "patient")
    @Mapping(source = "staffId", target = "staff")
    @Mapping(source = "scheduledById", target = "scheduledBy")
    AppointmentSchedule toEntity(AppointmentScheduleDTO appointmentScheduleDTO);

    default AppointmentSchedule fromId(Long id) {
        if (id == null) {
            return null;
        }
        AppointmentSchedule appointmentSchedule = new AppointmentSchedule();
        appointmentSchedule.setId(id);
        return appointmentSchedule;
    }
}

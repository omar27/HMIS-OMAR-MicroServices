package com.omar.hmis.service.mapper;

import com.omar.hmis.domain.*;
import com.omar.hmis.service.dto.AppointmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Appointment} and its DTO {@link AppointmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {AppointmentScheduleMapper.class, BillMapper.class})
public interface AppointmentMapper extends EntityMapper<AppointmentDTO, Appointment> {

    @Mapping(source = "appointmentSchedule.id", target = "appointmentScheduleId")
    @Mapping(source = "bill.id", target = "billId")
    AppointmentDTO toDto(Appointment appointment);

    @Mapping(source = "appointmentScheduleId", target = "appointmentSchedule")
    @Mapping(source = "billId", target = "bill")
    Appointment toEntity(AppointmentDTO appointmentDTO);

    default Appointment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Appointment appointment = new Appointment();
        appointment.setId(id);
        return appointment;
    }
}

package com.omar.hmis.service.mapper;

import com.omar.hmis.domain.AppointmentSchedule;
import com.omar.hmis.domain.Patient;
import com.omar.hmis.domain.Staff;
import com.omar.hmis.service.dto.AppointmentScheduleDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-11-14T05:15:39+0500",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_231 (Oracle Corporation)"
)
@Component
public class AppointmentScheduleMapperImpl implements AppointmentScheduleMapper {

    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private StaffMapper staffMapper;

    @Override
    public List<AppointmentSchedule> toEntity(List<AppointmentScheduleDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<AppointmentSchedule> list = new ArrayList<AppointmentSchedule>( dtoList.size() );
        for ( AppointmentScheduleDTO appointmentScheduleDTO : dtoList ) {
            list.add( toEntity( appointmentScheduleDTO ) );
        }

        return list;
    }

    @Override
    public List<AppointmentScheduleDTO> toDto(List<AppointmentSchedule> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AppointmentScheduleDTO> list = new ArrayList<AppointmentScheduleDTO>( entityList.size() );
        for ( AppointmentSchedule appointmentSchedule : entityList ) {
            list.add( toDto( appointmentSchedule ) );
        }

        return list;
    }

    @Override
    public AppointmentScheduleDTO toDto(AppointmentSchedule appointmentSchedule) {
        if ( appointmentSchedule == null ) {
            return null;
        }

        AppointmentScheduleDTO appointmentScheduleDTO = new AppointmentScheduleDTO();

        appointmentScheduleDTO.setPatientId( appointmentSchedulePatientId( appointmentSchedule ) );
        appointmentScheduleDTO.setStaffId( appointmentScheduleStaffId( appointmentSchedule ) );
        appointmentScheduleDTO.setScheduledById( appointmentScheduleScheduledById( appointmentSchedule ) );
        appointmentScheduleDTO.setId( appointmentSchedule.getId() );
        appointmentScheduleDTO.setStatus( appointmentSchedule.getStatus() );
        appointmentScheduleDTO.setScheduledDate( appointmentSchedule.getScheduledDate() );

        return appointmentScheduleDTO;
    }

    @Override
    public AppointmentSchedule toEntity(AppointmentScheduleDTO appointmentScheduleDTO) {
        if ( appointmentScheduleDTO == null ) {
            return null;
        }

        AppointmentSchedule appointmentSchedule = new AppointmentSchedule();

        appointmentSchedule.setStaff( staffMapper.fromId( appointmentScheduleDTO.getStaffId() ) );
        appointmentSchedule.setScheduledBy( staffMapper.fromId( appointmentScheduleDTO.getScheduledById() ) );
        appointmentSchedule.setPatient( patientMapper.fromId( appointmentScheduleDTO.getPatientId() ) );
        appointmentSchedule.setId( appointmentScheduleDTO.getId() );
        appointmentSchedule.setStatus( appointmentScheduleDTO.getStatus() );
        appointmentSchedule.setScheduledDate( appointmentScheduleDTO.getScheduledDate() );

        return appointmentSchedule;
    }

    private Long appointmentSchedulePatientId(AppointmentSchedule appointmentSchedule) {
        if ( appointmentSchedule == null ) {
            return null;
        }
        Patient patient = appointmentSchedule.getPatient();
        if ( patient == null ) {
            return null;
        }
        Long id = patient.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long appointmentScheduleStaffId(AppointmentSchedule appointmentSchedule) {
        if ( appointmentSchedule == null ) {
            return null;
        }
        Staff staff = appointmentSchedule.getStaff();
        if ( staff == null ) {
            return null;
        }
        Long id = staff.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long appointmentScheduleScheduledById(AppointmentSchedule appointmentSchedule) {
        if ( appointmentSchedule == null ) {
            return null;
        }
        Staff scheduledBy = appointmentSchedule.getScheduledBy();
        if ( scheduledBy == null ) {
            return null;
        }
        Long id = scheduledBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}

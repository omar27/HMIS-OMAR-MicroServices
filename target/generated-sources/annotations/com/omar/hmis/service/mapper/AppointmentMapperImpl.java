package com.omar.hmis.service.mapper;

import com.omar.hmis.domain.Appointment;
import com.omar.hmis.domain.AppointmentSchedule;
import com.omar.hmis.domain.Bill;
import com.omar.hmis.service.dto.AppointmentDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-11-14T05:15:40+0500",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_231 (Oracle Corporation)"
)
@Component
public class AppointmentMapperImpl implements AppointmentMapper {

    @Autowired
    private AppointmentScheduleMapper appointmentScheduleMapper;
    @Autowired
    private BillMapper billMapper;

    @Override
    public List<Appointment> toEntity(List<AppointmentDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Appointment> list = new ArrayList<Appointment>( dtoList.size() );
        for ( AppointmentDTO appointmentDTO : dtoList ) {
            list.add( toEntity( appointmentDTO ) );
        }

        return list;
    }

    @Override
    public List<AppointmentDTO> toDto(List<Appointment> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AppointmentDTO> list = new ArrayList<AppointmentDTO>( entityList.size() );
        for ( Appointment appointment : entityList ) {
            list.add( toDto( appointment ) );
        }

        return list;
    }

    @Override
    public AppointmentDTO toDto(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }

        AppointmentDTO appointmentDTO = new AppointmentDTO();

        appointmentDTO.setAppointmentScheduleId( appointmentAppointmentScheduleId( appointment ) );
        appointmentDTO.setBillId( appointmentBillId( appointment ) );
        appointmentDTO.setId( appointment.getId() );
        appointmentDTO.setPatientStatus( appointment.getPatientStatus() );
        appointmentDTO.setDiseaseIdentified( appointment.getDiseaseIdentified() );
        appointmentDTO.setCureSuggested( appointment.getCureSuggested() );
        appointmentDTO.setTestsSuggested( appointment.getTestsSuggested() );

        return appointmentDTO;
    }

    @Override
    public Appointment toEntity(AppointmentDTO appointmentDTO) {
        if ( appointmentDTO == null ) {
            return null;
        }

        Appointment appointment = new Appointment();

        appointment.setBill( billMapper.fromId( appointmentDTO.getBillId() ) );
        appointment.setAppointmentSchedule( appointmentScheduleMapper.fromId( appointmentDTO.getAppointmentScheduleId() ) );
        appointment.setId( appointmentDTO.getId() );
        appointment.setPatientStatus( appointmentDTO.getPatientStatus() );
        appointment.setDiseaseIdentified( appointmentDTO.getDiseaseIdentified() );
        appointment.setCureSuggested( appointmentDTO.getCureSuggested() );
        appointment.setTestsSuggested( appointmentDTO.getTestsSuggested() );

        return appointment;
    }

    private Long appointmentAppointmentScheduleId(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        AppointmentSchedule appointmentSchedule = appointment.getAppointmentSchedule();
        if ( appointmentSchedule == null ) {
            return null;
        }
        Long id = appointmentSchedule.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long appointmentBillId(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        Bill bill = appointment.getBill();
        if ( bill == null ) {
            return null;
        }
        Long id = bill.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}

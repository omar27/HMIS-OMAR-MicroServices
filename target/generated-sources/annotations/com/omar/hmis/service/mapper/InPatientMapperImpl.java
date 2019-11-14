package com.omar.hmis.service.mapper;

import com.omar.hmis.domain.InPatient;
import com.omar.hmis.domain.Patient;
import com.omar.hmis.domain.Room;
import com.omar.hmis.domain.Staff;
import com.omar.hmis.service.dto.InPatientDTO;
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
public class InPatientMapperImpl implements InPatientMapper {

    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private PatientMapper patientMapper;

    @Override
    public List<InPatient> toEntity(List<InPatientDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<InPatient> list = new ArrayList<InPatient>( dtoList.size() );
        for ( InPatientDTO inPatientDTO : dtoList ) {
            list.add( toEntity( inPatientDTO ) );
        }

        return list;
    }

    @Override
    public List<InPatientDTO> toDto(List<InPatient> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<InPatientDTO> list = new ArrayList<InPatientDTO>( entityList.size() );
        for ( InPatient inPatient : entityList ) {
            list.add( toDto( inPatient ) );
        }

        return list;
    }

    @Override
    public InPatientDTO toDto(InPatient inPatient) {
        if ( inPatient == null ) {
            return null;
        }

        InPatientDTO inPatientDTO = new InPatientDTO();

        inPatientDTO.setSuggestedById( inPatientSuggestedById( inPatient ) );
        inPatientDTO.setPatientId( inPatientPatientId( inPatient ) );
        inPatientDTO.setRoomId( inPatientRoomId( inPatient ) );
        inPatientDTO.setId( inPatient.getId() );
        inPatientDTO.setAdmitDate( inPatient.getAdmitDate() );
        inPatientDTO.setDischargeDate( inPatient.getDischargeDate() );

        return inPatientDTO;
    }

    @Override
    public InPatient toEntity(InPatientDTO inPatientDTO) {
        if ( inPatientDTO == null ) {
            return null;
        }

        InPatient inPatient = new InPatient();

        inPatient.setSuggestedBy( staffMapper.fromId( inPatientDTO.getSuggestedById() ) );
        inPatient.setPatient( patientMapper.fromId( inPatientDTO.getPatientId() ) );
        inPatient.setRoom( roomMapper.fromId( inPatientDTO.getRoomId() ) );
        inPatient.setId( inPatientDTO.getId() );
        inPatient.setAdmitDate( inPatientDTO.getAdmitDate() );
        inPatient.setDischargeDate( inPatientDTO.getDischargeDate() );

        return inPatient;
    }

    private Long inPatientSuggestedById(InPatient inPatient) {
        if ( inPatient == null ) {
            return null;
        }
        Staff suggestedBy = inPatient.getSuggestedBy();
        if ( suggestedBy == null ) {
            return null;
        }
        Long id = suggestedBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long inPatientPatientId(InPatient inPatient) {
        if ( inPatient == null ) {
            return null;
        }
        Patient patient = inPatient.getPatient();
        if ( patient == null ) {
            return null;
        }
        Long id = patient.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long inPatientRoomId(InPatient inPatient) {
        if ( inPatient == null ) {
            return null;
        }
        Room room = inPatient.getRoom();
        if ( room == null ) {
            return null;
        }
        Long id = room.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}

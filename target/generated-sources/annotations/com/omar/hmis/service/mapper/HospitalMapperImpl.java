package com.omar.hmis.service.mapper;

import com.omar.hmis.domain.Hospital;
import com.omar.hmis.service.dto.HospitalDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-11-14T05:15:39+0500",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_231 (Oracle Corporation)"
)
@Component
public class HospitalMapperImpl implements HospitalMapper {

    @Override
    public Hospital toEntity(HospitalDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Hospital hospital = new Hospital();

        hospital.setId( dto.getId() );
        hospital.setName( dto.getName() );
        hospital.setEmail( dto.getEmail() );
        hospital.setAddress( dto.getAddress() );
        hospital.setPhoneNumber( dto.getPhoneNumber() );
        hospital.setRegistrationNumber( dto.getRegistrationNumber() );

        return hospital;
    }

    @Override
    public HospitalDTO toDto(Hospital entity) {
        if ( entity == null ) {
            return null;
        }

        HospitalDTO hospitalDTO = new HospitalDTO();

        hospitalDTO.setId( entity.getId() );
        hospitalDTO.setName( entity.getName() );
        hospitalDTO.setEmail( entity.getEmail() );
        hospitalDTO.setAddress( entity.getAddress() );
        hospitalDTO.setPhoneNumber( entity.getPhoneNumber() );
        hospitalDTO.setRegistrationNumber( entity.getRegistrationNumber() );

        return hospitalDTO;
    }

    @Override
    public List<Hospital> toEntity(List<HospitalDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Hospital> list = new ArrayList<Hospital>( dtoList.size() );
        for ( HospitalDTO hospitalDTO : dtoList ) {
            list.add( toEntity( hospitalDTO ) );
        }

        return list;
    }

    @Override
    public List<HospitalDTO> toDto(List<Hospital> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<HospitalDTO> list = new ArrayList<HospitalDTO>( entityList.size() );
        for ( Hospital hospital : entityList ) {
            list.add( toDto( hospital ) );
        }

        return list;
    }
}

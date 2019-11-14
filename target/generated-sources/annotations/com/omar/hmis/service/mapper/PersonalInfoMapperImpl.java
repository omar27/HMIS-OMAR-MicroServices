package com.omar.hmis.service.mapper;

import com.omar.hmis.domain.PersonalInfo;
import com.omar.hmis.service.dto.PersonalInfoDTO;
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
public class PersonalInfoMapperImpl implements PersonalInfoMapper {

    @Override
    public PersonalInfo toEntity(PersonalInfoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PersonalInfo personalInfo = new PersonalInfo();

        personalInfo.setId( dto.getId() );
        personalInfo.setCnic( dto.getCnic() );
        personalInfo.setFirstName( dto.getFirstName() );
        personalInfo.setLastName( dto.getLastName() );
        personalInfo.setPhoneNumber( dto.getPhoneNumber() );
        personalInfo.setEmail( dto.getEmail() );
        personalInfo.setGender( dto.getGender() );
        personalInfo.setAge( dto.getAge() );
        personalInfo.setAddress( dto.getAddress() );
        personalInfo.setCity( dto.getCity() );
        personalInfo.setEntityType( dto.getEntityType() );
        personalInfo.setEntityId( dto.getEntityId() );

        return personalInfo;
    }

    @Override
    public PersonalInfoDTO toDto(PersonalInfo entity) {
        if ( entity == null ) {
            return null;
        }

        PersonalInfoDTO personalInfoDTO = new PersonalInfoDTO();

        personalInfoDTO.setId( entity.getId() );
        personalInfoDTO.setCnic( entity.getCnic() );
        personalInfoDTO.setFirstName( entity.getFirstName() );
        personalInfoDTO.setLastName( entity.getLastName() );
        personalInfoDTO.setPhoneNumber( entity.getPhoneNumber() );
        personalInfoDTO.setEmail( entity.getEmail() );
        personalInfoDTO.setGender( entity.getGender() );
        personalInfoDTO.setAge( entity.getAge() );
        personalInfoDTO.setAddress( entity.getAddress() );
        personalInfoDTO.setCity( entity.getCity() );
        personalInfoDTO.setEntityType( entity.getEntityType() );
        personalInfoDTO.setEntityId( entity.getEntityId() );

        return personalInfoDTO;
    }

    @Override
    public List<PersonalInfo> toEntity(List<PersonalInfoDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<PersonalInfo> list = new ArrayList<PersonalInfo>( dtoList.size() );
        for ( PersonalInfoDTO personalInfoDTO : dtoList ) {
            list.add( toEntity( personalInfoDTO ) );
        }

        return list;
    }

    @Override
    public List<PersonalInfoDTO> toDto(List<PersonalInfo> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PersonalInfoDTO> list = new ArrayList<PersonalInfoDTO>( entityList.size() );
        for ( PersonalInfo personalInfo : entityList ) {
            list.add( toDto( personalInfo ) );
        }

        return list;
    }
}

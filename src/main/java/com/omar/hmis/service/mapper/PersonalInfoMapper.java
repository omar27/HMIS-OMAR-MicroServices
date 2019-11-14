package com.omar.hmis.service.mapper;

import com.omar.hmis.domain.*;
import com.omar.hmis.service.dto.PersonalInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonalInfo} and its DTO {@link PersonalInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PersonalInfoMapper extends EntityMapper<PersonalInfoDTO, PersonalInfo> {



    default PersonalInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setId(id);
        return personalInfo;
    }
}

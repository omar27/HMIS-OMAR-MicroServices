package com.omar.hmis.service.mapper;

import com.omar.hmis.domain.*;
import com.omar.hmis.service.dto.StaffWorkingScheduleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StaffWorkingSchedule} and its DTO {@link StaffWorkingScheduleDTO}.
 */
@Mapper(componentModel = "spring", uses = {StaffMapper.class, WorkingScheduleMapper.class})
public interface StaffWorkingScheduleMapper extends EntityMapper<StaffWorkingScheduleDTO, StaffWorkingSchedule> {

    @Mapping(source = "staff.id", target = "staffId")
    @Mapping(source = "workingSchedule.id", target = "workingScheduleId")
    @Mapping(source = "workingSchedule.day", target = "workingScheduleDay")
    StaffWorkingScheduleDTO toDto(StaffWorkingSchedule staffWorkingSchedule);

    @Mapping(source = "staffId", target = "staff")
    @Mapping(source = "workingScheduleId", target = "workingSchedule")
    StaffWorkingSchedule toEntity(StaffWorkingScheduleDTO staffWorkingScheduleDTO);

    default StaffWorkingSchedule fromId(Long id) {
        if (id == null) {
            return null;
        }
        StaffWorkingSchedule staffWorkingSchedule = new StaffWorkingSchedule();
        staffWorkingSchedule.setId(id);
        return staffWorkingSchedule;
    }
}

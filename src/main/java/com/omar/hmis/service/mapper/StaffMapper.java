package com.omar.hmis.service.mapper;

import com.omar.hmis.domain.*;
import com.omar.hmis.service.dto.StaffDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Staff} and its DTO {@link StaffDTO}.
 */
@Mapper(componentModel = "spring", uses = {DepartmentMapper.class, JobDetailsMapper.class, StaffWorkingScheduleMapper.class})
public interface StaffMapper extends EntityMapper<StaffDTO, Staff> {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    @Mapping(source = "jobDetails.id", target = "jobDetailsId")
    @Mapping(source = "staffWorkingSchedule.id", target = "staffWorkingScheduleId")
    StaffDTO toDto(Staff staff);

    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "jobDetailsId", target = "jobDetails")
    @Mapping(source = "staffWorkingScheduleId", target = "staffWorkingSchedule")
    Staff toEntity(StaffDTO staffDTO);

    default Staff fromId(Long id) {
        if (id == null) {
            return null;
        }
        Staff staff = new Staff();
        staff.setId(id);
        return staff;
    }
}

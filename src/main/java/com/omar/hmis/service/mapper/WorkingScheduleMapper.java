package com.omar.hmis.service.mapper;

import com.omar.hmis.domain.*;
import com.omar.hmis.service.dto.WorkingScheduleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkingSchedule} and its DTO {@link WorkingScheduleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkingScheduleMapper extends EntityMapper<WorkingScheduleDTO, WorkingSchedule> {



    default WorkingSchedule fromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkingSchedule workingSchedule = new WorkingSchedule();
        workingSchedule.setId(id);
        return workingSchedule;
    }
}

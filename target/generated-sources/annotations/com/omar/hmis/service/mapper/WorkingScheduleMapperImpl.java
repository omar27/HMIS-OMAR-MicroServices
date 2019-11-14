package com.omar.hmis.service.mapper;

import com.omar.hmis.domain.WorkingSchedule;
import com.omar.hmis.service.dto.WorkingScheduleDTO;
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
public class WorkingScheduleMapperImpl implements WorkingScheduleMapper {

    @Override
    public WorkingSchedule toEntity(WorkingScheduleDTO dto) {
        if ( dto == null ) {
            return null;
        }

        WorkingSchedule workingSchedule = new WorkingSchedule();

        workingSchedule.setId( dto.getId() );
        workingSchedule.setDay( dto.getDay() );
        workingSchedule.setIsOff( dto.isIsOff() );
        workingSchedule.setStartTime( dto.getStartTime() );
        workingSchedule.setEnd( dto.getEnd() );

        return workingSchedule;
    }

    @Override
    public WorkingScheduleDTO toDto(WorkingSchedule entity) {
        if ( entity == null ) {
            return null;
        }

        WorkingScheduleDTO workingScheduleDTO = new WorkingScheduleDTO();

        workingScheduleDTO.setId( entity.getId() );
        workingScheduleDTO.setDay( entity.getDay() );
        workingScheduleDTO.setIsOff( entity.isIsOff() );
        workingScheduleDTO.setStartTime( entity.getStartTime() );
        workingScheduleDTO.setEnd( entity.getEnd() );

        return workingScheduleDTO;
    }

    @Override
    public List<WorkingSchedule> toEntity(List<WorkingScheduleDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<WorkingSchedule> list = new ArrayList<WorkingSchedule>( dtoList.size() );
        for ( WorkingScheduleDTO workingScheduleDTO : dtoList ) {
            list.add( toEntity( workingScheduleDTO ) );
        }

        return list;
    }

    @Override
    public List<WorkingScheduleDTO> toDto(List<WorkingSchedule> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<WorkingScheduleDTO> list = new ArrayList<WorkingScheduleDTO>( entityList.size() );
        for ( WorkingSchedule workingSchedule : entityList ) {
            list.add( toDto( workingSchedule ) );
        }

        return list;
    }
}

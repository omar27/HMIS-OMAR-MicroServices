package com.omar.hmis.service.mapper;

import com.omar.hmis.domain.Staff;
import com.omar.hmis.domain.StaffWorkingSchedule;
import com.omar.hmis.domain.WorkingSchedule;
import com.omar.hmis.domain.enumeration.Days;
import com.omar.hmis.service.dto.StaffWorkingScheduleDTO;
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
public class StaffWorkingScheduleMapperImpl implements StaffWorkingScheduleMapper {

    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private WorkingScheduleMapper workingScheduleMapper;

    @Override
    public List<StaffWorkingSchedule> toEntity(List<StaffWorkingScheduleDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StaffWorkingSchedule> list = new ArrayList<StaffWorkingSchedule>( dtoList.size() );
        for ( StaffWorkingScheduleDTO staffWorkingScheduleDTO : dtoList ) {
            list.add( toEntity( staffWorkingScheduleDTO ) );
        }

        return list;
    }

    @Override
    public List<StaffWorkingScheduleDTO> toDto(List<StaffWorkingSchedule> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StaffWorkingScheduleDTO> list = new ArrayList<StaffWorkingScheduleDTO>( entityList.size() );
        for ( StaffWorkingSchedule staffWorkingSchedule : entityList ) {
            list.add( toDto( staffWorkingSchedule ) );
        }

        return list;
    }

    @Override
    public StaffWorkingScheduleDTO toDto(StaffWorkingSchedule staffWorkingSchedule) {
        if ( staffWorkingSchedule == null ) {
            return null;
        }

        StaffWorkingScheduleDTO staffWorkingScheduleDTO = new StaffWorkingScheduleDTO();

        staffWorkingScheduleDTO.setWorkingScheduleId( staffWorkingScheduleWorkingScheduleId( staffWorkingSchedule ) );
        Days day = staffWorkingScheduleWorkingScheduleDay( staffWorkingSchedule );
        if ( day != null ) {
            staffWorkingScheduleDTO.setWorkingScheduleDay( day.name() );
        }
        staffWorkingScheduleDTO.setStaffId( staffWorkingScheduleStaffId( staffWorkingSchedule ) );
        staffWorkingScheduleDTO.setId( staffWorkingSchedule.getId() );
        staffWorkingScheduleDTO.setDescription( staffWorkingSchedule.getDescription() );

        return staffWorkingScheduleDTO;
    }

    @Override
    public StaffWorkingSchedule toEntity(StaffWorkingScheduleDTO staffWorkingScheduleDTO) {
        if ( staffWorkingScheduleDTO == null ) {
            return null;
        }

        StaffWorkingSchedule staffWorkingSchedule = new StaffWorkingSchedule();

        staffWorkingSchedule.setStaff( staffMapper.fromId( staffWorkingScheduleDTO.getStaffId() ) );
        staffWorkingSchedule.setWorkingSchedule( workingScheduleMapper.fromId( staffWorkingScheduleDTO.getWorkingScheduleId() ) );
        staffWorkingSchedule.setId( staffWorkingScheduleDTO.getId() );
        staffWorkingSchedule.setDescription( staffWorkingScheduleDTO.getDescription() );

        return staffWorkingSchedule;
    }

    private Long staffWorkingScheduleWorkingScheduleId(StaffWorkingSchedule staffWorkingSchedule) {
        if ( staffWorkingSchedule == null ) {
            return null;
        }
        WorkingSchedule workingSchedule = staffWorkingSchedule.getWorkingSchedule();
        if ( workingSchedule == null ) {
            return null;
        }
        Long id = workingSchedule.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Days staffWorkingScheduleWorkingScheduleDay(StaffWorkingSchedule staffWorkingSchedule) {
        if ( staffWorkingSchedule == null ) {
            return null;
        }
        WorkingSchedule workingSchedule = staffWorkingSchedule.getWorkingSchedule();
        if ( workingSchedule == null ) {
            return null;
        }
        Days day = workingSchedule.getDay();
        if ( day == null ) {
            return null;
        }
        return day;
    }

    private Long staffWorkingScheduleStaffId(StaffWorkingSchedule staffWorkingSchedule) {
        if ( staffWorkingSchedule == null ) {
            return null;
        }
        Staff staff = staffWorkingSchedule.getStaff();
        if ( staff == null ) {
            return null;
        }
        Long id = staff.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}

package com.omar.hmis.service.mapper;

import com.omar.hmis.domain.Department;
import com.omar.hmis.domain.JobDetails;
import com.omar.hmis.domain.Staff;
import com.omar.hmis.domain.StaffWorkingSchedule;
import com.omar.hmis.service.dto.StaffDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-11-14T05:15:39+0500",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_231 (Oracle Corporation)"
)
@Component
public class StaffMapperImpl implements StaffMapper {

    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private JobDetailsMapper jobDetailsMapper;
    @Autowired
    private StaffWorkingScheduleMapper staffWorkingScheduleMapper;

    @Override
    public List<Staff> toEntity(List<StaffDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Staff> list = new ArrayList<Staff>( dtoList.size() );
        for ( StaffDTO staffDTO : dtoList ) {
            list.add( toEntity( staffDTO ) );
        }

        return list;
    }

    @Override
    public List<StaffDTO> toDto(List<Staff> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StaffDTO> list = new ArrayList<StaffDTO>( entityList.size() );
        for ( Staff staff : entityList ) {
            list.add( toDto( staff ) );
        }

        return list;
    }

    @Override
    public StaffDTO toDto(Staff staff) {
        if ( staff == null ) {
            return null;
        }

        StaffDTO staffDTO = new StaffDTO();

        staffDTO.setDepartmentName( staffDepartmentName( staff ) );
        staffDTO.setJobDetailsId( staffJobDetailsId( staff ) );
        staffDTO.setStaffWorkingScheduleId( staffStaffWorkingScheduleId( staff ) );
        staffDTO.setDepartmentId( staffDepartmentId( staff ) );
        staffDTO.setId( staff.getId() );
        staffDTO.setStaffType( staff.getStaffType() );
        staffDTO.setQualification( staff.getQualification() );
        staffDTO.setJoiningDate( staff.getJoiningDate() );
        staffDTO.setExperience( staff.getExperience() );
        staffDTO.setIsRegular( staff.isIsRegular() );

        return staffDTO;
    }

    @Override
    public Staff toEntity(StaffDTO staffDTO) {
        if ( staffDTO == null ) {
            return null;
        }

        Staff staff = new Staff();

        staff.setDepartment( departmentMapper.fromId( staffDTO.getDepartmentId() ) );
        staff.setJobDetails( jobDetailsMapper.fromId( staffDTO.getJobDetailsId() ) );
        staff.setStaffWorkingSchedule( staffWorkingScheduleMapper.fromId( staffDTO.getStaffWorkingScheduleId() ) );
        staff.setId( staffDTO.getId() );
        staff.setStaffType( staffDTO.getStaffType() );
        staff.setQualification( staffDTO.getQualification() );
        staff.setJoiningDate( staffDTO.getJoiningDate() );
        staff.setExperience( staffDTO.getExperience() );
        staff.setIsRegular( staffDTO.isIsRegular() );

        return staff;
    }

    private String staffDepartmentName(Staff staff) {
        if ( staff == null ) {
            return null;
        }
        Department department = staff.getDepartment();
        if ( department == null ) {
            return null;
        }
        String name = department.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long staffJobDetailsId(Staff staff) {
        if ( staff == null ) {
            return null;
        }
        JobDetails jobDetails = staff.getJobDetails();
        if ( jobDetails == null ) {
            return null;
        }
        Long id = jobDetails.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long staffStaffWorkingScheduleId(Staff staff) {
        if ( staff == null ) {
            return null;
        }
        StaffWorkingSchedule staffWorkingSchedule = staff.getStaffWorkingSchedule();
        if ( staffWorkingSchedule == null ) {
            return null;
        }
        Long id = staffWorkingSchedule.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long staffDepartmentId(Staff staff) {
        if ( staff == null ) {
            return null;
        }
        Department department = staff.getDepartment();
        if ( department == null ) {
            return null;
        }
        Long id = department.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}

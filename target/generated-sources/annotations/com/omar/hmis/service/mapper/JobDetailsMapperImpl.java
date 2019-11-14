package com.omar.hmis.service.mapper;

import com.omar.hmis.domain.JobDetails;
import com.omar.hmis.service.dto.JobDetailsDTO;
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
public class JobDetailsMapperImpl implements JobDetailsMapper {

    @Override
    public JobDetails toEntity(JobDetailsDTO dto) {
        if ( dto == null ) {
            return null;
        }

        JobDetails jobDetails = new JobDetails();

        jobDetails.setId( dto.getId() );
        jobDetails.setDesignation( dto.getDesignation() );
        jobDetails.setGrade( dto.getGrade() );
        jobDetails.setType( dto.getType() );
        jobDetails.setSalary( dto.getSalary() );
        jobDetails.setDetails( dto.getDetails() );

        return jobDetails;
    }

    @Override
    public JobDetailsDTO toDto(JobDetails entity) {
        if ( entity == null ) {
            return null;
        }

        JobDetailsDTO jobDetailsDTO = new JobDetailsDTO();

        jobDetailsDTO.setId( entity.getId() );
        jobDetailsDTO.setDesignation( entity.getDesignation() );
        jobDetailsDTO.setGrade( entity.getGrade() );
        jobDetailsDTO.setType( entity.getType() );
        jobDetailsDTO.setSalary( entity.getSalary() );
        jobDetailsDTO.setDetails( entity.getDetails() );

        return jobDetailsDTO;
    }

    @Override
    public List<JobDetails> toEntity(List<JobDetailsDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<JobDetails> list = new ArrayList<JobDetails>( dtoList.size() );
        for ( JobDetailsDTO jobDetailsDTO : dtoList ) {
            list.add( toEntity( jobDetailsDTO ) );
        }

        return list;
    }

    @Override
    public List<JobDetailsDTO> toDto(List<JobDetails> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<JobDetailsDTO> list = new ArrayList<JobDetailsDTO>( entityList.size() );
        for ( JobDetails jobDetails : entityList ) {
            list.add( toDto( jobDetails ) );
        }

        return list;
    }
}

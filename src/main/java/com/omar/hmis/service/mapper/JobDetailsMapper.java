package com.omar.hmis.service.mapper;

import com.omar.hmis.domain.*;
import com.omar.hmis.service.dto.JobDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link JobDetails} and its DTO {@link JobDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JobDetailsMapper extends EntityMapper<JobDetailsDTO, JobDetails> {



    default JobDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        JobDetails jobDetails = new JobDetails();
        jobDetails.setId(id);
        return jobDetails;
    }
}

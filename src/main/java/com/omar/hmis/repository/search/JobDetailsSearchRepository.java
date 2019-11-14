package com.omar.hmis.repository.search;
import com.omar.hmis.domain.JobDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link JobDetails} entity.
 */
public interface JobDetailsSearchRepository extends ElasticsearchRepository<JobDetails, Long> {
}

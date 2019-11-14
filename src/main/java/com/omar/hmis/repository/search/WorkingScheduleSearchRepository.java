package com.omar.hmis.repository.search;
import com.omar.hmis.domain.WorkingSchedule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link WorkingSchedule} entity.
 */
public interface WorkingScheduleSearchRepository extends ElasticsearchRepository<WorkingSchedule, Long> {
}

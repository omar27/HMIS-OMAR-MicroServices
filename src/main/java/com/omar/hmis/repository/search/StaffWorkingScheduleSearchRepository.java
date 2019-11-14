package com.omar.hmis.repository.search;
import com.omar.hmis.domain.StaffWorkingSchedule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link StaffWorkingSchedule} entity.
 */
public interface StaffWorkingScheduleSearchRepository extends ElasticsearchRepository<StaffWorkingSchedule, Long> {
}

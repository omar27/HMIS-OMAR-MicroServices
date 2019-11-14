package com.omar.hmis.repository.search;
import com.omar.hmis.domain.AppointmentSchedule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AppointmentSchedule} entity.
 */
public interface AppointmentScheduleSearchRepository extends ElasticsearchRepository<AppointmentSchedule, Long> {
}

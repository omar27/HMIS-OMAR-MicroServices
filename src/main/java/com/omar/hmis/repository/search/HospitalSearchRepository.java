package com.omar.hmis.repository.search;
import com.omar.hmis.domain.Hospital;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Hospital} entity.
 */
public interface HospitalSearchRepository extends ElasticsearchRepository<Hospital, Long> {
}

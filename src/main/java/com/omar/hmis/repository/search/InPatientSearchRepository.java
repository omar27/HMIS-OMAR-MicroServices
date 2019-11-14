package com.omar.hmis.repository.search;
import com.omar.hmis.domain.InPatient;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link InPatient} entity.
 */
public interface InPatientSearchRepository extends ElasticsearchRepository<InPatient, Long> {
}

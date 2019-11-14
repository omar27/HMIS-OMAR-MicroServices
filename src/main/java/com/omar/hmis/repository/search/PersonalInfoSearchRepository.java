package com.omar.hmis.repository.search;
import com.omar.hmis.domain.PersonalInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PersonalInfo} entity.
 */
public interface PersonalInfoSearchRepository extends ElasticsearchRepository<PersonalInfo, Long> {
}

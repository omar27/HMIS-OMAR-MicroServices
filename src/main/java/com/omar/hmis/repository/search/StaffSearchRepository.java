package com.omar.hmis.repository.search;
import com.omar.hmis.domain.Staff;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Staff} entity.
 */
public interface StaffSearchRepository extends ElasticsearchRepository<Staff, Long> {
}

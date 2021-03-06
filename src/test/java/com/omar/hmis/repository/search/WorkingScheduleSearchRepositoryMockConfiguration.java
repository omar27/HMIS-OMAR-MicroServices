package com.omar.hmis.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link WorkingScheduleSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class WorkingScheduleSearchRepositoryMockConfiguration {

    @MockBean
    private WorkingScheduleSearchRepository mockWorkingScheduleSearchRepository;

}

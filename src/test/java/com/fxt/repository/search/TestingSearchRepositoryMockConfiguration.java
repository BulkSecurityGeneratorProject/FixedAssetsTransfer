package com.fxt.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of TestingSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TestingSearchRepositoryMockConfiguration {

    @MockBean
    private TestingSearchRepository mockTestingSearchRepository;

}

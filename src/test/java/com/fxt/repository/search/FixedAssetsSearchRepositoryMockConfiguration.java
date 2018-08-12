package com.fxt.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of FixedAssetsSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class FixedAssetsSearchRepositoryMockConfiguration {

    @MockBean
    private FixedAssetsSearchRepository mockFixedAssetsSearchRepository;

}

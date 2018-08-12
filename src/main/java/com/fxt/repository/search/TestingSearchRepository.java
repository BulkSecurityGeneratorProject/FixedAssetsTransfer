package com.fxt.repository.search;

import com.fxt.domain.Testing;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Testing entity.
 */
public interface TestingSearchRepository extends ElasticsearchRepository<Testing, Long> {
}

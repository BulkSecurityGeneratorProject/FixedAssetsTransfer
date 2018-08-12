package com.fxt.repository.search;

import com.fxt.domain.FixedAssets;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FixedAssets entity.
 */
public interface FixedAssetsSearchRepository extends ElasticsearchRepository<FixedAssets, Long> {
}

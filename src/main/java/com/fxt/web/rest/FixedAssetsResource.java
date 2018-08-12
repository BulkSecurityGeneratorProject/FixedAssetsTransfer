package com.fxt.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fxt.domain.FixedAssets;
import com.fxt.repository.FixedAssetsRepository;
import com.fxt.repository.search.FixedAssetsSearchRepository;
import com.fxt.web.rest.errors.BadRequestAlertException;
import com.fxt.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing FixedAssets.
 */
@RestController
@RequestMapping("/api")
public class FixedAssetsResource {

    private final Logger log = LoggerFactory.getLogger(FixedAssetsResource.class);

    private static final String ENTITY_NAME = "fixedAssets";

    private final FixedAssetsRepository fixedAssetsRepository;

    private final FixedAssetsSearchRepository fixedAssetsSearchRepository;

    public FixedAssetsResource(FixedAssetsRepository fixedAssetsRepository, FixedAssetsSearchRepository fixedAssetsSearchRepository) {
        this.fixedAssetsRepository = fixedAssetsRepository;
        this.fixedAssetsSearchRepository = fixedAssetsSearchRepository;
    }

    /**
     * POST  /fixed-assets : Create a new fixedAssets.
     *
     * @param fixedAssets the fixedAssets to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fixedAssets, or with status 400 (Bad Request) if the fixedAssets has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fixed-assets")
    @Timed
    public ResponseEntity<FixedAssets> createFixedAssets(@Valid @RequestBody FixedAssets fixedAssets) throws URISyntaxException {
        log.debug("REST request to save FixedAssets : {}", fixedAssets);
        if (fixedAssets.getId() != null) {
            throw new BadRequestAlertException("A new fixedAssets cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FixedAssets result = fixedAssetsRepository.save(fixedAssets);
        fixedAssetsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/fixed-assets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fixed-assets : Updates an existing fixedAssets.
     *
     * @param fixedAssets the fixedAssets to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fixedAssets,
     * or with status 400 (Bad Request) if the fixedAssets is not valid,
     * or with status 500 (Internal Server Error) if the fixedAssets couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fixed-assets")
    @Timed
    public ResponseEntity<FixedAssets> updateFixedAssets(@Valid @RequestBody FixedAssets fixedAssets) throws URISyntaxException {
        log.debug("REST request to update FixedAssets : {}", fixedAssets);
        if (fixedAssets.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FixedAssets result = fixedAssetsRepository.save(fixedAssets);
        fixedAssetsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fixedAssets.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fixed-assets : get all the fixedAssets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fixedAssets in body
     */
    @GetMapping("/fixed-assets")
    @Timed
    public List<FixedAssets> getAllFixedAssets() {
        log.debug("REST request to get all FixedAssets");
        return fixedAssetsRepository.findAll();
    }

    /**
     * GET  /fixed-assets/:id : get the "id" fixedAssets.
     *
     * @param id the id of the fixedAssets to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fixedAssets, or with status 404 (Not Found)
     */
    @GetMapping("/fixed-assets/{id}")
    @Timed
    public ResponseEntity<FixedAssets> getFixedAssets(@PathVariable Long id) {
        log.debug("REST request to get FixedAssets : {}", id);
        Optional<FixedAssets> fixedAssets = fixedAssetsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fixedAssets);
    }

    /**
     * DELETE  /fixed-assets/:id : delete the "id" fixedAssets.
     *
     * @param id the id of the fixedAssets to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fixed-assets/{id}")
    @Timed
    public ResponseEntity<Void> deleteFixedAssets(@PathVariable Long id) {
        log.debug("REST request to delete FixedAssets : {}", id);

        fixedAssetsRepository.deleteById(id);
        fixedAssetsSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/fixed-assets?query=:query : search for the fixedAssets corresponding
     * to the query.
     *
     * @param query the query of the fixedAssets search
     * @return the result of the search
     */
    @GetMapping("/_search/fixed-assets")
    @Timed
    public List<FixedAssets> searchFixedAssets(@RequestParam String query) {
        log.debug("REST request to search FixedAssets for query {}", query);
        return StreamSupport
            .stream(fixedAssetsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}

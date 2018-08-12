package com.fxt.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fxt.domain.Testing;
import com.fxt.repository.TestingRepository;
import com.fxt.repository.search.TestingSearchRepository;
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
 * REST controller for managing Testing.
 */
@RestController
@RequestMapping("/api")
public class TestingResource {

    private final Logger log = LoggerFactory.getLogger(TestingResource.class);

    private static final String ENTITY_NAME = "testing";

    private final TestingRepository testingRepository;

    private final TestingSearchRepository testingSearchRepository;

    public TestingResource(TestingRepository testingRepository, TestingSearchRepository testingSearchRepository) {
        this.testingRepository = testingRepository;
        this.testingSearchRepository = testingSearchRepository;
    }

    /**
     * POST  /testings : Create a new testing.
     *
     * @param testing the testing to create
     * @return the ResponseEntity with status 201 (Created) and with body the new testing, or with status 400 (Bad Request) if the testing has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/testings")
    @Timed
    public ResponseEntity<Testing> createTesting(@Valid @RequestBody Testing testing) throws URISyntaxException {
        log.debug("REST request to save Testing : {}", testing);
        if (testing.getId() != null) {
            throw new BadRequestAlertException("A new testing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Testing result = testingRepository.save(testing);
        testingSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/testings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /testings : Updates an existing testing.
     *
     * @param testing the testing to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated testing,
     * or with status 400 (Bad Request) if the testing is not valid,
     * or with status 500 (Internal Server Error) if the testing couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/testings")
    @Timed
    public ResponseEntity<Testing> updateTesting(@Valid @RequestBody Testing testing) throws URISyntaxException {
        log.debug("REST request to update Testing : {}", testing);
        if (testing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Testing result = testingRepository.save(testing);
        testingSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, testing.getId().toString()))
            .body(result);
    }

    /**
     * GET  /testings : get all the testings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of testings in body
     */
    @GetMapping("/testings")
    @Timed
    public List<Testing> getAllTestings() {
        log.debug("REST request to get all Testings");
        return testingRepository.findAll();
    }

    /**
     * GET  /testings/:id : get the "id" testing.
     *
     * @param id the id of the testing to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the testing, or with status 404 (Not Found)
     */
    @GetMapping("/testings/{id}")
    @Timed
    public ResponseEntity<Testing> getTesting(@PathVariable Long id) {
        log.debug("REST request to get Testing : {}", id);
        Optional<Testing> testing = testingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(testing);
    }

    /**
     * DELETE  /testings/:id : delete the "id" testing.
     *
     * @param id the id of the testing to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/testings/{id}")
    @Timed
    public ResponseEntity<Void> deleteTesting(@PathVariable Long id) {
        log.debug("REST request to delete Testing : {}", id);

        testingRepository.deleteById(id);
        testingSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/testings?query=:query : search for the testing corresponding
     * to the query.
     *
     * @param query the query of the testing search
     * @return the result of the search
     */
    @GetMapping("/_search/testings")
    @Timed
    public List<Testing> searchTestings(@RequestParam String query) {
        log.debug("REST request to search Testings for query {}", query);
        return StreamSupport
            .stream(testingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}

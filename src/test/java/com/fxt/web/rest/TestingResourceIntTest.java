package com.fxt.web.rest;

import com.fxt.FixedAssetsTransferApp;

import com.fxt.domain.Testing;
import com.fxt.repository.TestingRepository;
import com.fxt.repository.search.TestingSearchRepository;
import com.fxt.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.fxt.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TestingResource REST controller.
 *
 * @see TestingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FixedAssetsTransferApp.class)
public class TestingResourceIntTest {

    private static final String DEFAULT_TEST = "AAAAAAAAAA";
    private static final String UPDATED_TEST = "BBBBBBBBBB";

    @Autowired
    private TestingRepository testingRepository;


    /**
     * This repository is mocked in the com.fxt.repository.search test package.
     *
     * @see com.fxt.repository.search.TestingSearchRepositoryMockConfiguration
     */
    @Autowired
    private TestingSearchRepository mockTestingSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTestingMockMvc;

    private Testing testing;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TestingResource testingResource = new TestingResource(testingRepository, mockTestingSearchRepository);
        this.restTestingMockMvc = MockMvcBuilders.standaloneSetup(testingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Testing createEntity(EntityManager em) {
        Testing testing = new Testing()
            .test(DEFAULT_TEST);
        return testing;
    }

    @Before
    public void initTest() {
        testing = createEntity(em);
    }

    @Test
    @Transactional
    public void createTesting() throws Exception {
        int databaseSizeBeforeCreate = testingRepository.findAll().size();

        // Create the Testing
        restTestingMockMvc.perform(post("/api/testings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testing)))
            .andExpect(status().isCreated());

        // Validate the Testing in the database
        List<Testing> testingList = testingRepository.findAll();
        assertThat(testingList).hasSize(databaseSizeBeforeCreate + 1);
        Testing testTesting = testingList.get(testingList.size() - 1);
        assertThat(testTesting.getTest()).isEqualTo(DEFAULT_TEST);

        // Validate the Testing in Elasticsearch
        verify(mockTestingSearchRepository, times(1)).save(testTesting);
    }

    @Test
    @Transactional
    public void createTestingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = testingRepository.findAll().size();

        // Create the Testing with an existing ID
        testing.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTestingMockMvc.perform(post("/api/testings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testing)))
            .andExpect(status().isBadRequest());

        // Validate the Testing in the database
        List<Testing> testingList = testingRepository.findAll();
        assertThat(testingList).hasSize(databaseSizeBeforeCreate);

        // Validate the Testing in Elasticsearch
        verify(mockTestingSearchRepository, times(0)).save(testing);
    }

    @Test
    @Transactional
    public void checkTestIsRequired() throws Exception {
        int databaseSizeBeforeTest = testingRepository.findAll().size();
        // set the field null
        testing.setTest(null);

        // Create the Testing, which fails.

        restTestingMockMvc.perform(post("/api/testings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testing)))
            .andExpect(status().isBadRequest());

        List<Testing> testingList = testingRepository.findAll();
        assertThat(testingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTestings() throws Exception {
        // Initialize the database
        testingRepository.saveAndFlush(testing);

        // Get all the testingList
        restTestingMockMvc.perform(get("/api/testings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testing.getId().intValue())))
            .andExpect(jsonPath("$.[*].test").value(hasItem(DEFAULT_TEST.toString())));
    }
    

    @Test
    @Transactional
    public void getTesting() throws Exception {
        // Initialize the database
        testingRepository.saveAndFlush(testing);

        // Get the testing
        restTestingMockMvc.perform(get("/api/testings/{id}", testing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(testing.getId().intValue()))
            .andExpect(jsonPath("$.test").value(DEFAULT_TEST.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTesting() throws Exception {
        // Get the testing
        restTestingMockMvc.perform(get("/api/testings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTesting() throws Exception {
        // Initialize the database
        testingRepository.saveAndFlush(testing);

        int databaseSizeBeforeUpdate = testingRepository.findAll().size();

        // Update the testing
        Testing updatedTesting = testingRepository.findById(testing.getId()).get();
        // Disconnect from session so that the updates on updatedTesting are not directly saved in db
        em.detach(updatedTesting);
        updatedTesting
            .test(UPDATED_TEST);

        restTestingMockMvc.perform(put("/api/testings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTesting)))
            .andExpect(status().isOk());

        // Validate the Testing in the database
        List<Testing> testingList = testingRepository.findAll();
        assertThat(testingList).hasSize(databaseSizeBeforeUpdate);
        Testing testTesting = testingList.get(testingList.size() - 1);
        assertThat(testTesting.getTest()).isEqualTo(UPDATED_TEST);

        // Validate the Testing in Elasticsearch
        verify(mockTestingSearchRepository, times(1)).save(testTesting);
    }

    @Test
    @Transactional
    public void updateNonExistingTesting() throws Exception {
        int databaseSizeBeforeUpdate = testingRepository.findAll().size();

        // Create the Testing

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTestingMockMvc.perform(put("/api/testings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testing)))
            .andExpect(status().isBadRequest());

        // Validate the Testing in the database
        List<Testing> testingList = testingRepository.findAll();
        assertThat(testingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Testing in Elasticsearch
        verify(mockTestingSearchRepository, times(0)).save(testing);
    }

    @Test
    @Transactional
    public void deleteTesting() throws Exception {
        // Initialize the database
        testingRepository.saveAndFlush(testing);

        int databaseSizeBeforeDelete = testingRepository.findAll().size();

        // Get the testing
        restTestingMockMvc.perform(delete("/api/testings/{id}", testing.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Testing> testingList = testingRepository.findAll();
        assertThat(testingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Testing in Elasticsearch
        verify(mockTestingSearchRepository, times(1)).deleteById(testing.getId());
    }

    @Test
    @Transactional
    public void searchTesting() throws Exception {
        // Initialize the database
        testingRepository.saveAndFlush(testing);
        when(mockTestingSearchRepository.search(queryStringQuery("id:" + testing.getId())))
            .thenReturn(Collections.singletonList(testing));
        // Search the testing
        restTestingMockMvc.perform(get("/api/_search/testings?query=id:" + testing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testing.getId().intValue())))
            .andExpect(jsonPath("$.[*].test").value(hasItem(DEFAULT_TEST.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Testing.class);
        Testing testing1 = new Testing();
        testing1.setId(1L);
        Testing testing2 = new Testing();
        testing2.setId(testing1.getId());
        assertThat(testing1).isEqualTo(testing2);
        testing2.setId(2L);
        assertThat(testing1).isNotEqualTo(testing2);
        testing1.setId(null);
        assertThat(testing1).isNotEqualTo(testing2);
    }
}

package com.fxt.web.rest;

import com.fxt.FixedAssetsTransferApp;

import com.fxt.domain.FixedAssets;
import com.fxt.repository.FixedAssetsRepository;
import com.fxt.repository.search.FixedAssetsSearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static com.fxt.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fxt.domain.enumeration.AssetActionType;
import com.fxt.domain.enumeration.TechnicalReview;
/**
 * Test class for the FixedAssetsResource REST controller.
 *
 * @see FixedAssetsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FixedAssetsTransferApp.class)
public class FixedAssetsResourceIntTest {

    private static final Long DEFAULT_ASSETS_NO = 1L;
    private static final Long UPDATED_ASSETS_NO = 2L;

    private static final Long DEFAULT_SERIAL_NO = 1L;
    private static final Long UPDATED_SERIAL_NO = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final AssetActionType DEFAULT_ASSET_ACTION_TYPE = AssetActionType.DISPOSAL;
    private static final AssetActionType UPDATED_ASSET_ACTION_TYPE = AssetActionType.RETURN;

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MANAGER_APPROVAL = false;
    private static final Boolean UPDATED_MANAGER_APPROVAL = true;

    private static final Boolean DEFAULT_FIXED_ASSETS_MANAGER_APPROVAL = false;
    private static final Boolean UPDATED_FIXED_ASSETS_MANAGER_APPROVAL = true;

    private static final TechnicalReview DEFAULT_TECHNICAL_REVIEW = TechnicalReview.IT;
    private static final TechnicalReview UPDATED_TECHNICAL_REVIEW = TechnicalReview.ADMINSTRAITIVE_AFFAIRS;

    private static final String DEFAULT_TECHNICAL_COMMENTARY = "AAAAAAAAAA";
    private static final String UPDATED_TECHNICAL_COMMENTARY = "BBBBBBBBBB";

    private static final String DEFAULT_IT_ASSET_PREVIEWER = "AAAAAAAAAA";
    private static final String UPDATED_IT_ASSET_PREVIEWER = "BBBBBBBBBB";

    private static final String DEFAULT_IT_ASSET_PREVIEWER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_IT_ASSET_PREVIEWER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADMINISTRAITVE_AFFAIRS_PREVIEWER = "AAAAAAAAAA";
    private static final String UPDATED_ADMINISTRAITVE_AFFAIRS_PREVIEWER = "BBBBBBBBBB";

    private static final String DEFAULT_ADMINISTRAITIVE_AFFAIRS_JOB_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ADMINISTRAITIVE_AFFAIRS_JOB_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNICAL_MAINTENANCE_COMMENTRY = "AAAAAAAAAA";
    private static final String UPDATED_TECHNICAL_MAINTENANCE_COMMENTRY = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNICAL_MAINTENANCE_PREVIEWER = "AAAAAAAAAA";
    private static final String UPDATED_TECHNICAL_MAINTENANCE_PREVIEWER = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNICAL_MAINTENANCE_JOB_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TECHNICAL_MAINTENANCE_JOB_NAME = "BBBBBBBBBB";

    @Autowired
    private FixedAssetsRepository fixedAssetsRepository;


    /**
     * This repository is mocked in the com.fxt.repository.search test package.
     *
     * @see com.fxt.repository.search.FixedAssetsSearchRepositoryMockConfiguration
     */
    @Autowired
    private FixedAssetsSearchRepository mockFixedAssetsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFixedAssetsMockMvc;

    private FixedAssets fixedAssets;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FixedAssetsResource fixedAssetsResource = new FixedAssetsResource(fixedAssetsRepository, mockFixedAssetsSearchRepository);
        this.restFixedAssetsMockMvc = MockMvcBuilders.standaloneSetup(fixedAssetsResource)
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
    public static FixedAssets createEntity(EntityManager em) {
        FixedAssets fixedAssets = new FixedAssets()
            .assets_no(DEFAULT_ASSETS_NO)
            .serial_no(DEFAULT_SERIAL_NO)
            .description(DEFAULT_DESCRIPTION)
            .date(DEFAULT_DATE)
            .asset_action_type(DEFAULT_ASSET_ACTION_TYPE)
            .source(DEFAULT_SOURCE)
            .destination(DEFAULT_DESTINATION)
            .manager_approval(DEFAULT_MANAGER_APPROVAL)
            .fixed_assets_manager_approval(DEFAULT_FIXED_ASSETS_MANAGER_APPROVAL)
            .technical_review(DEFAULT_TECHNICAL_REVIEW)
            .technical_commentary(DEFAULT_TECHNICAL_COMMENTARY)
            .it_asset_previewer(DEFAULT_IT_ASSET_PREVIEWER)
            .it_asset_previewer_name(DEFAULT_IT_ASSET_PREVIEWER_NAME)
            .administraitve_affairs_previewer(DEFAULT_ADMINISTRAITVE_AFFAIRS_PREVIEWER)
            .administraitive_affairs_job_name(DEFAULT_ADMINISTRAITIVE_AFFAIRS_JOB_NAME)
            .technical_maintenance_commentry(DEFAULT_TECHNICAL_MAINTENANCE_COMMENTRY)
            .technical_maintenance_previewer(DEFAULT_TECHNICAL_MAINTENANCE_PREVIEWER)
            .technical_maintenance_job_name(DEFAULT_TECHNICAL_MAINTENANCE_JOB_NAME);
        return fixedAssets;
    }

    @Before
    public void initTest() {
        fixedAssets = createEntity(em);
    }

    @Test
    @Transactional
    public void createFixedAssets() throws Exception {
        int databaseSizeBeforeCreate = fixedAssetsRepository.findAll().size();

        // Create the FixedAssets
        restFixedAssetsMockMvc.perform(post("/api/fixed-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssets)))
            .andExpect(status().isCreated());

        // Validate the FixedAssets in the database
        List<FixedAssets> fixedAssetsList = fixedAssetsRepository.findAll();
        assertThat(fixedAssetsList).hasSize(databaseSizeBeforeCreate + 1);
        FixedAssets testFixedAssets = fixedAssetsList.get(fixedAssetsList.size() - 1);
        assertThat(testFixedAssets.getAssets_no()).isEqualTo(DEFAULT_ASSETS_NO);
        assertThat(testFixedAssets.getSerial_no()).isEqualTo(DEFAULT_SERIAL_NO);
        assertThat(testFixedAssets.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFixedAssets.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testFixedAssets.getAsset_action_type()).isEqualTo(DEFAULT_ASSET_ACTION_TYPE);
        assertThat(testFixedAssets.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testFixedAssets.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testFixedAssets.isManager_approval()).isEqualTo(DEFAULT_MANAGER_APPROVAL);
        assertThat(testFixedAssets.isFixed_assets_manager_approval()).isEqualTo(DEFAULT_FIXED_ASSETS_MANAGER_APPROVAL);
        assertThat(testFixedAssets.getTechnical_review()).isEqualTo(DEFAULT_TECHNICAL_REVIEW);
        assertThat(testFixedAssets.getTechnical_commentary()).isEqualTo(DEFAULT_TECHNICAL_COMMENTARY);
        assertThat(testFixedAssets.getIt_asset_previewer()).isEqualTo(DEFAULT_IT_ASSET_PREVIEWER);
        assertThat(testFixedAssets.getIt_asset_previewer_name()).isEqualTo(DEFAULT_IT_ASSET_PREVIEWER_NAME);
        assertThat(testFixedAssets.getAdministraitve_affairs_previewer()).isEqualTo(DEFAULT_ADMINISTRAITVE_AFFAIRS_PREVIEWER);
        assertThat(testFixedAssets.getAdministraitive_affairs_job_name()).isEqualTo(DEFAULT_ADMINISTRAITIVE_AFFAIRS_JOB_NAME);
        assertThat(testFixedAssets.getTechnical_maintenance_commentry()).isEqualTo(DEFAULT_TECHNICAL_MAINTENANCE_COMMENTRY);
        assertThat(testFixedAssets.getTechnical_maintenance_previewer()).isEqualTo(DEFAULT_TECHNICAL_MAINTENANCE_PREVIEWER);
        assertThat(testFixedAssets.getTechnical_maintenance_job_name()).isEqualTo(DEFAULT_TECHNICAL_MAINTENANCE_JOB_NAME);

        // Validate the FixedAssets in Elasticsearch
        verify(mockFixedAssetsSearchRepository, times(1)).save(testFixedAssets);
    }

    @Test
    @Transactional
    public void createFixedAssetsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fixedAssetsRepository.findAll().size();

        // Create the FixedAssets with an existing ID
        fixedAssets.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFixedAssetsMockMvc.perform(post("/api/fixed-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssets)))
            .andExpect(status().isBadRequest());

        // Validate the FixedAssets in the database
        List<FixedAssets> fixedAssetsList = fixedAssetsRepository.findAll();
        assertThat(fixedAssetsList).hasSize(databaseSizeBeforeCreate);

        // Validate the FixedAssets in Elasticsearch
        verify(mockFixedAssetsSearchRepository, times(0)).save(fixedAssets);
    }

    @Test
    @Transactional
    public void checkAssets_noIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedAssetsRepository.findAll().size();
        // set the field null
        fixedAssets.setAssets_no(null);

        // Create the FixedAssets, which fails.

        restFixedAssetsMockMvc.perform(post("/api/fixed-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssets)))
            .andExpect(status().isBadRequest());

        List<FixedAssets> fixedAssetsList = fixedAssetsRepository.findAll();
        assertThat(fixedAssetsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSerial_noIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedAssetsRepository.findAll().size();
        // set the field null
        fixedAssets.setSerial_no(null);

        // Create the FixedAssets, which fails.

        restFixedAssetsMockMvc.perform(post("/api/fixed-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssets)))
            .andExpect(status().isBadRequest());

        List<FixedAssets> fixedAssetsList = fixedAssetsRepository.findAll();
        assertThat(fixedAssetsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedAssetsRepository.findAll().size();
        // set the field null
        fixedAssets.setDescription(null);

        // Create the FixedAssets, which fails.

        restFixedAssetsMockMvc.perform(post("/api/fixed-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssets)))
            .andExpect(status().isBadRequest());

        List<FixedAssets> fixedAssetsList = fixedAssetsRepository.findAll();
        assertThat(fixedAssetsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedAssetsRepository.findAll().size();
        // set the field null
        fixedAssets.setDate(null);

        // Create the FixedAssets, which fails.

        restFixedAssetsMockMvc.perform(post("/api/fixed-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssets)))
            .andExpect(status().isBadRequest());

        List<FixedAssets> fixedAssetsList = fixedAssetsRepository.findAll();
        assertThat(fixedAssetsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAsset_action_typeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedAssetsRepository.findAll().size();
        // set the field null
        fixedAssets.setAsset_action_type(null);

        // Create the FixedAssets, which fails.

        restFixedAssetsMockMvc.perform(post("/api/fixed-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssets)))
            .andExpect(status().isBadRequest());

        List<FixedAssets> fixedAssetsList = fixedAssetsRepository.findAll();
        assertThat(fixedAssetsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSourceIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedAssetsRepository.findAll().size();
        // set the field null
        fixedAssets.setSource(null);

        // Create the FixedAssets, which fails.

        restFixedAssetsMockMvc.perform(post("/api/fixed-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssets)))
            .andExpect(status().isBadRequest());

        List<FixedAssets> fixedAssetsList = fixedAssetsRepository.findAll();
        assertThat(fixedAssetsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDestinationIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedAssetsRepository.findAll().size();
        // set the field null
        fixedAssets.setDestination(null);

        // Create the FixedAssets, which fails.

        restFixedAssetsMockMvc.perform(post("/api/fixed-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssets)))
            .andExpect(status().isBadRequest());

        List<FixedAssets> fixedAssetsList = fixedAssetsRepository.findAll();
        assertThat(fixedAssetsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFixedAssets() throws Exception {
        // Initialize the database
        fixedAssetsRepository.saveAndFlush(fixedAssets);

        // Get all the fixedAssetsList
        restFixedAssetsMockMvc.perform(get("/api/fixed-assets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fixedAssets.getId().intValue())))
            .andExpect(jsonPath("$.[*].assets_no").value(hasItem(DEFAULT_ASSETS_NO.intValue())))
            .andExpect(jsonPath("$.[*].serial_no").value(hasItem(DEFAULT_SERIAL_NO.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].asset_action_type").value(hasItem(DEFAULT_ASSET_ACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION.toString())))
            .andExpect(jsonPath("$.[*].manager_approval").value(hasItem(DEFAULT_MANAGER_APPROVAL.booleanValue())))
            .andExpect(jsonPath("$.[*].fixed_assets_manager_approval").value(hasItem(DEFAULT_FIXED_ASSETS_MANAGER_APPROVAL.booleanValue())))
            .andExpect(jsonPath("$.[*].technical_review").value(hasItem(DEFAULT_TECHNICAL_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].technical_commentary").value(hasItem(DEFAULT_TECHNICAL_COMMENTARY.toString())))
            .andExpect(jsonPath("$.[*].it_asset_previewer").value(hasItem(DEFAULT_IT_ASSET_PREVIEWER.toString())))
            .andExpect(jsonPath("$.[*].it_asset_previewer_name").value(hasItem(DEFAULT_IT_ASSET_PREVIEWER_NAME.toString())))
            .andExpect(jsonPath("$.[*].administraitve_affairs_previewer").value(hasItem(DEFAULT_ADMINISTRAITVE_AFFAIRS_PREVIEWER.toString())))
            .andExpect(jsonPath("$.[*].administraitive_affairs_job_name").value(hasItem(DEFAULT_ADMINISTRAITIVE_AFFAIRS_JOB_NAME.toString())))
            .andExpect(jsonPath("$.[*].technical_maintenance_commentry").value(hasItem(DEFAULT_TECHNICAL_MAINTENANCE_COMMENTRY.toString())))
            .andExpect(jsonPath("$.[*].technical_maintenance_previewer").value(hasItem(DEFAULT_TECHNICAL_MAINTENANCE_PREVIEWER.toString())))
            .andExpect(jsonPath("$.[*].technical_maintenance_job_name").value(hasItem(DEFAULT_TECHNICAL_MAINTENANCE_JOB_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getFixedAssets() throws Exception {
        // Initialize the database
        fixedAssetsRepository.saveAndFlush(fixedAssets);

        // Get the fixedAssets
        restFixedAssetsMockMvc.perform(get("/api/fixed-assets/{id}", fixedAssets.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fixedAssets.getId().intValue()))
            .andExpect(jsonPath("$.assets_no").value(DEFAULT_ASSETS_NO.intValue()))
            .andExpect(jsonPath("$.serial_no").value(DEFAULT_SERIAL_NO.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.asset_action_type").value(DEFAULT_ASSET_ACTION_TYPE.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.destination").value(DEFAULT_DESTINATION.toString()))
            .andExpect(jsonPath("$.manager_approval").value(DEFAULT_MANAGER_APPROVAL.booleanValue()))
            .andExpect(jsonPath("$.fixed_assets_manager_approval").value(DEFAULT_FIXED_ASSETS_MANAGER_APPROVAL.booleanValue()))
            .andExpect(jsonPath("$.technical_review").value(DEFAULT_TECHNICAL_REVIEW.toString()))
            .andExpect(jsonPath("$.technical_commentary").value(DEFAULT_TECHNICAL_COMMENTARY.toString()))
            .andExpect(jsonPath("$.it_asset_previewer").value(DEFAULT_IT_ASSET_PREVIEWER.toString()))
            .andExpect(jsonPath("$.it_asset_previewer_name").value(DEFAULT_IT_ASSET_PREVIEWER_NAME.toString()))
            .andExpect(jsonPath("$.administraitve_affairs_previewer").value(DEFAULT_ADMINISTRAITVE_AFFAIRS_PREVIEWER.toString()))
            .andExpect(jsonPath("$.administraitive_affairs_job_name").value(DEFAULT_ADMINISTRAITIVE_AFFAIRS_JOB_NAME.toString()))
            .andExpect(jsonPath("$.technical_maintenance_commentry").value(DEFAULT_TECHNICAL_MAINTENANCE_COMMENTRY.toString()))
            .andExpect(jsonPath("$.technical_maintenance_previewer").value(DEFAULT_TECHNICAL_MAINTENANCE_PREVIEWER.toString()))
            .andExpect(jsonPath("$.technical_maintenance_job_name").value(DEFAULT_TECHNICAL_MAINTENANCE_JOB_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingFixedAssets() throws Exception {
        // Get the fixedAssets
        restFixedAssetsMockMvc.perform(get("/api/fixed-assets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFixedAssets() throws Exception {
        // Initialize the database
        fixedAssetsRepository.saveAndFlush(fixedAssets);

        int databaseSizeBeforeUpdate = fixedAssetsRepository.findAll().size();

        // Update the fixedAssets
        FixedAssets updatedFixedAssets = fixedAssetsRepository.findById(fixedAssets.getId()).get();
        // Disconnect from session so that the updates on updatedFixedAssets are not directly saved in db
        em.detach(updatedFixedAssets);
        updatedFixedAssets
            .assets_no(UPDATED_ASSETS_NO)
            .serial_no(UPDATED_SERIAL_NO)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .asset_action_type(UPDATED_ASSET_ACTION_TYPE)
            .source(UPDATED_SOURCE)
            .destination(UPDATED_DESTINATION)
            .manager_approval(UPDATED_MANAGER_APPROVAL)
            .fixed_assets_manager_approval(UPDATED_FIXED_ASSETS_MANAGER_APPROVAL)
            .technical_review(UPDATED_TECHNICAL_REVIEW)
            .technical_commentary(UPDATED_TECHNICAL_COMMENTARY)
            .it_asset_previewer(UPDATED_IT_ASSET_PREVIEWER)
            .it_asset_previewer_name(UPDATED_IT_ASSET_PREVIEWER_NAME)
            .administraitve_affairs_previewer(UPDATED_ADMINISTRAITVE_AFFAIRS_PREVIEWER)
            .administraitive_affairs_job_name(UPDATED_ADMINISTRAITIVE_AFFAIRS_JOB_NAME)
            .technical_maintenance_commentry(UPDATED_TECHNICAL_MAINTENANCE_COMMENTRY)
            .technical_maintenance_previewer(UPDATED_TECHNICAL_MAINTENANCE_PREVIEWER)
            .technical_maintenance_job_name(UPDATED_TECHNICAL_MAINTENANCE_JOB_NAME);

        restFixedAssetsMockMvc.perform(put("/api/fixed-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFixedAssets)))
            .andExpect(status().isOk());

        // Validate the FixedAssets in the database
        List<FixedAssets> fixedAssetsList = fixedAssetsRepository.findAll();
        assertThat(fixedAssetsList).hasSize(databaseSizeBeforeUpdate);
        FixedAssets testFixedAssets = fixedAssetsList.get(fixedAssetsList.size() - 1);
        assertThat(testFixedAssets.getAssets_no()).isEqualTo(UPDATED_ASSETS_NO);
        assertThat(testFixedAssets.getSerial_no()).isEqualTo(UPDATED_SERIAL_NO);
        assertThat(testFixedAssets.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFixedAssets.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testFixedAssets.getAsset_action_type()).isEqualTo(UPDATED_ASSET_ACTION_TYPE);
        assertThat(testFixedAssets.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testFixedAssets.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testFixedAssets.isManager_approval()).isEqualTo(UPDATED_MANAGER_APPROVAL);
        assertThat(testFixedAssets.isFixed_assets_manager_approval()).isEqualTo(UPDATED_FIXED_ASSETS_MANAGER_APPROVAL);
        assertThat(testFixedAssets.getTechnical_review()).isEqualTo(UPDATED_TECHNICAL_REVIEW);
        assertThat(testFixedAssets.getTechnical_commentary()).isEqualTo(UPDATED_TECHNICAL_COMMENTARY);
        assertThat(testFixedAssets.getIt_asset_previewer()).isEqualTo(UPDATED_IT_ASSET_PREVIEWER);
        assertThat(testFixedAssets.getIt_asset_previewer_name()).isEqualTo(UPDATED_IT_ASSET_PREVIEWER_NAME);
        assertThat(testFixedAssets.getAdministraitve_affairs_previewer()).isEqualTo(UPDATED_ADMINISTRAITVE_AFFAIRS_PREVIEWER);
        assertThat(testFixedAssets.getAdministraitive_affairs_job_name()).isEqualTo(UPDATED_ADMINISTRAITIVE_AFFAIRS_JOB_NAME);
        assertThat(testFixedAssets.getTechnical_maintenance_commentry()).isEqualTo(UPDATED_TECHNICAL_MAINTENANCE_COMMENTRY);
        assertThat(testFixedAssets.getTechnical_maintenance_previewer()).isEqualTo(UPDATED_TECHNICAL_MAINTENANCE_PREVIEWER);
        assertThat(testFixedAssets.getTechnical_maintenance_job_name()).isEqualTo(UPDATED_TECHNICAL_MAINTENANCE_JOB_NAME);

        // Validate the FixedAssets in Elasticsearch
        verify(mockFixedAssetsSearchRepository, times(1)).save(testFixedAssets);
    }

    @Test
    @Transactional
    public void updateNonExistingFixedAssets() throws Exception {
        int databaseSizeBeforeUpdate = fixedAssetsRepository.findAll().size();

        // Create the FixedAssets

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFixedAssetsMockMvc.perform(put("/api/fixed-assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssets)))
            .andExpect(status().isBadRequest());

        // Validate the FixedAssets in the database
        List<FixedAssets> fixedAssetsList = fixedAssetsRepository.findAll();
        assertThat(fixedAssetsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FixedAssets in Elasticsearch
        verify(mockFixedAssetsSearchRepository, times(0)).save(fixedAssets);
    }

    @Test
    @Transactional
    public void deleteFixedAssets() throws Exception {
        // Initialize the database
        fixedAssetsRepository.saveAndFlush(fixedAssets);

        int databaseSizeBeforeDelete = fixedAssetsRepository.findAll().size();

        // Get the fixedAssets
        restFixedAssetsMockMvc.perform(delete("/api/fixed-assets/{id}", fixedAssets.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FixedAssets> fixedAssetsList = fixedAssetsRepository.findAll();
        assertThat(fixedAssetsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FixedAssets in Elasticsearch
        verify(mockFixedAssetsSearchRepository, times(1)).deleteById(fixedAssets.getId());
    }

    @Test
    @Transactional
    public void searchFixedAssets() throws Exception {
        // Initialize the database
        fixedAssetsRepository.saveAndFlush(fixedAssets);
        when(mockFixedAssetsSearchRepository.search(queryStringQuery("id:" + fixedAssets.getId())))
            .thenReturn(Collections.singletonList(fixedAssets));
        // Search the fixedAssets
        restFixedAssetsMockMvc.perform(get("/api/_search/fixed-assets?query=id:" + fixedAssets.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fixedAssets.getId().intValue())))
            .andExpect(jsonPath("$.[*].assets_no").value(hasItem(DEFAULT_ASSETS_NO.intValue())))
            .andExpect(jsonPath("$.[*].serial_no").value(hasItem(DEFAULT_SERIAL_NO.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].asset_action_type").value(hasItem(DEFAULT_ASSET_ACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION.toString())))
            .andExpect(jsonPath("$.[*].manager_approval").value(hasItem(DEFAULT_MANAGER_APPROVAL.booleanValue())))
            .andExpect(jsonPath("$.[*].fixed_assets_manager_approval").value(hasItem(DEFAULT_FIXED_ASSETS_MANAGER_APPROVAL.booleanValue())))
            .andExpect(jsonPath("$.[*].technical_review").value(hasItem(DEFAULT_TECHNICAL_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].technical_commentary").value(hasItem(DEFAULT_TECHNICAL_COMMENTARY.toString())))
            .andExpect(jsonPath("$.[*].it_asset_previewer").value(hasItem(DEFAULT_IT_ASSET_PREVIEWER.toString())))
            .andExpect(jsonPath("$.[*].it_asset_previewer_name").value(hasItem(DEFAULT_IT_ASSET_PREVIEWER_NAME.toString())))
            .andExpect(jsonPath("$.[*].administraitve_affairs_previewer").value(hasItem(DEFAULT_ADMINISTRAITVE_AFFAIRS_PREVIEWER.toString())))
            .andExpect(jsonPath("$.[*].administraitive_affairs_job_name").value(hasItem(DEFAULT_ADMINISTRAITIVE_AFFAIRS_JOB_NAME.toString())))
            .andExpect(jsonPath("$.[*].technical_maintenance_commentry").value(hasItem(DEFAULT_TECHNICAL_MAINTENANCE_COMMENTRY.toString())))
            .andExpect(jsonPath("$.[*].technical_maintenance_previewer").value(hasItem(DEFAULT_TECHNICAL_MAINTENANCE_PREVIEWER.toString())))
            .andExpect(jsonPath("$.[*].technical_maintenance_job_name").value(hasItem(DEFAULT_TECHNICAL_MAINTENANCE_JOB_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FixedAssets.class);
        FixedAssets fixedAssets1 = new FixedAssets();
        fixedAssets1.setId(1L);
        FixedAssets fixedAssets2 = new FixedAssets();
        fixedAssets2.setId(fixedAssets1.getId());
        assertThat(fixedAssets1).isEqualTo(fixedAssets2);
        fixedAssets2.setId(2L);
        assertThat(fixedAssets1).isNotEqualTo(fixedAssets2);
        fixedAssets1.setId(null);
        assertThat(fixedAssets1).isNotEqualTo(fixedAssets2);
    }
}

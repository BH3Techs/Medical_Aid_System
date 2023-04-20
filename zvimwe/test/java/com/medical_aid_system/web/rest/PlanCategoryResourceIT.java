package com.medical_aid_system.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.medical_aid_system.IntegrationTest;
import com.medical_aid_system.domain.PlanCategory;
import com.medical_aid_system.repository.PlanCategoryRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PlanCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlanCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/plan-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlanCategoryRepository planCategoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanCategoryMockMvc;

    private PlanCategory planCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanCategory createEntity(EntityManager em) {
        PlanCategory planCategory = new PlanCategory()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .dateCreated(DEFAULT_DATE_CREATED)
            .active(DEFAULT_ACTIVE);
        return planCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanCategory createUpdatedEntity(EntityManager em) {
        PlanCategory planCategory = new PlanCategory()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .dateCreated(UPDATED_DATE_CREATED)
            .active(UPDATED_ACTIVE);
        return planCategory;
    }

    @BeforeEach
    public void initTest() {
        planCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createPlanCategory() throws Exception {
        int databaseSizeBeforeCreate = planCategoryRepository.findAll().size();
        // Create the PlanCategory
        restPlanCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planCategory)))
            .andExpect(status().isCreated());

        // Validate the PlanCategory in the database
        List<PlanCategory> planCategoryList = planCategoryRepository.findAll();
        assertThat(planCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        PlanCategory testPlanCategory = planCategoryList.get(planCategoryList.size() - 1);
        assertThat(testPlanCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlanCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPlanCategory.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testPlanCategory.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    void createPlanCategoryWithExistingId() throws Exception {
        // Create the PlanCategory with an existing ID
        planCategory.setId(1L);

        int databaseSizeBeforeCreate = planCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planCategory)))
            .andExpect(status().isBadRequest());

        // Validate the PlanCategory in the database
        List<PlanCategory> planCategoryList = planCategoryRepository.findAll();
        assertThat(planCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = planCategoryRepository.findAll().size();
        // set the field null
        planCategory.setName(null);

        // Create the PlanCategory, which fails.

        restPlanCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planCategory)))
            .andExpect(status().isBadRequest());

        List<PlanCategory> planCategoryList = planCategoryRepository.findAll();
        assertThat(planCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = planCategoryRepository.findAll().size();
        // set the field null
        planCategory.setActive(null);

        // Create the PlanCategory, which fails.

        restPlanCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planCategory)))
            .andExpect(status().isBadRequest());

        List<PlanCategory> planCategoryList = planCategoryRepository.findAll();
        assertThat(planCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlanCategories() throws Exception {
        // Initialize the database
        planCategoryRepository.saveAndFlush(planCategory);

        // Get all the planCategoryList
        restPlanCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getPlanCategory() throws Exception {
        // Initialize the database
        planCategoryRepository.saveAndFlush(planCategory);

        // Get the planCategory
        restPlanCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, planCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingPlanCategory() throws Exception {
        // Get the planCategory
        restPlanCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlanCategory() throws Exception {
        // Initialize the database
        planCategoryRepository.saveAndFlush(planCategory);

        int databaseSizeBeforeUpdate = planCategoryRepository.findAll().size();

        // Update the planCategory
        PlanCategory updatedPlanCategory = planCategoryRepository.findById(planCategory.getId()).get();
        // Disconnect from session so that the updates on updatedPlanCategory are not directly saved in db
        em.detach(updatedPlanCategory);
        updatedPlanCategory.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).dateCreated(UPDATED_DATE_CREATED).active(UPDATED_ACTIVE);

        restPlanCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlanCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlanCategory))
            )
            .andExpect(status().isOk());

        // Validate the PlanCategory in the database
        List<PlanCategory> planCategoryList = planCategoryRepository.findAll();
        assertThat(planCategoryList).hasSize(databaseSizeBeforeUpdate);
        PlanCategory testPlanCategory = planCategoryList.get(planCategoryList.size() - 1);
        assertThat(testPlanCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlanCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPlanCategory.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testPlanCategory.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingPlanCategory() throws Exception {
        int databaseSizeBeforeUpdate = planCategoryRepository.findAll().size();
        planCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanCategory in the database
        List<PlanCategory> planCategoryList = planCategoryRepository.findAll();
        assertThat(planCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlanCategory() throws Exception {
        int databaseSizeBeforeUpdate = planCategoryRepository.findAll().size();
        planCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanCategory in the database
        List<PlanCategory> planCategoryList = planCategoryRepository.findAll();
        assertThat(planCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlanCategory() throws Exception {
        int databaseSizeBeforeUpdate = planCategoryRepository.findAll().size();
        planCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanCategoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planCategory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanCategory in the database
        List<PlanCategory> planCategoryList = planCategoryRepository.findAll();
        assertThat(planCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanCategoryWithPatch() throws Exception {
        // Initialize the database
        planCategoryRepository.saveAndFlush(planCategory);

        int databaseSizeBeforeUpdate = planCategoryRepository.findAll().size();

        // Update the planCategory using partial update
        PlanCategory partialUpdatedPlanCategory = new PlanCategory();
        partialUpdatedPlanCategory.setId(planCategory.getId());

        partialUpdatedPlanCategory.name(UPDATED_NAME).dateCreated(UPDATED_DATE_CREATED).active(UPDATED_ACTIVE);

        restPlanCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanCategory))
            )
            .andExpect(status().isOk());

        // Validate the PlanCategory in the database
        List<PlanCategory> planCategoryList = planCategoryRepository.findAll();
        assertThat(planCategoryList).hasSize(databaseSizeBeforeUpdate);
        PlanCategory testPlanCategory = planCategoryList.get(planCategoryList.size() - 1);
        assertThat(testPlanCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlanCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPlanCategory.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testPlanCategory.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdatePlanCategoryWithPatch() throws Exception {
        // Initialize the database
        planCategoryRepository.saveAndFlush(planCategory);

        int databaseSizeBeforeUpdate = planCategoryRepository.findAll().size();

        // Update the planCategory using partial update
        PlanCategory partialUpdatedPlanCategory = new PlanCategory();
        partialUpdatedPlanCategory.setId(planCategory.getId());

        partialUpdatedPlanCategory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .dateCreated(UPDATED_DATE_CREATED)
            .active(UPDATED_ACTIVE);

        restPlanCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanCategory))
            )
            .andExpect(status().isOk());

        // Validate the PlanCategory in the database
        List<PlanCategory> planCategoryList = planCategoryRepository.findAll();
        assertThat(planCategoryList).hasSize(databaseSizeBeforeUpdate);
        PlanCategory testPlanCategory = planCategoryList.get(planCategoryList.size() - 1);
        assertThat(testPlanCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlanCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPlanCategory.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testPlanCategory.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingPlanCategory() throws Exception {
        int databaseSizeBeforeUpdate = planCategoryRepository.findAll().size();
        planCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, planCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanCategory in the database
        List<PlanCategory> planCategoryList = planCategoryRepository.findAll();
        assertThat(planCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlanCategory() throws Exception {
        int databaseSizeBeforeUpdate = planCategoryRepository.findAll().size();
        planCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanCategory in the database
        List<PlanCategory> planCategoryList = planCategoryRepository.findAll();
        assertThat(planCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlanCategory() throws Exception {
        int databaseSizeBeforeUpdate = planCategoryRepository.findAll().size();
        planCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(planCategory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanCategory in the database
        List<PlanCategory> planCategoryList = planCategoryRepository.findAll();
        assertThat(planCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlanCategory() throws Exception {
        // Initialize the database
        planCategoryRepository.saveAndFlush(planCategory);

        int databaseSizeBeforeDelete = planCategoryRepository.findAll().size();

        // Delete the planCategory
        restPlanCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, planCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanCategory> planCategoryList = planCategoryRepository.findAll();
        assertThat(planCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

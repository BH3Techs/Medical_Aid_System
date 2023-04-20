package com.xplug.medical_aid_system.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.xplug.medical_aid_system.IntegrationTest;
import com.xplug.medical_aid_system.domain.Plans;
import com.xplug.medical_aid_system.domain.enumeration.PeriodUnit;
import com.xplug.medical_aid_system.repository.PlansRepository;
import com.xplug.medical_aid_system.service.PlansService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PlansResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PlansResourceIT {

    private static final String DEFAULT_PLAN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PLAN_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BASE_PREMIUM = "AAAAAAAAAA";
    private static final String UPDATED_BASE_PREMIUM = "BBBBBBBBBB";

    private static final String DEFAULT_COVER_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_COVER_AMOUNT = "BBBBBBBBBB";

    private static final PeriodUnit DEFAULT_COVER_PERIOD_UNIT = PeriodUnit.DAY;
    private static final PeriodUnit UPDATED_COVER_PERIOD_UNIT = PeriodUnit.WEEK;

    private static final Integer DEFAULT_COVER_PERIOD_VALUE = 1;
    private static final Integer UPDATED_COVER_PERIOD_VALUE = 2;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/plans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlansRepository plansRepository;

    @Mock
    private PlansRepository plansRepositoryMock;

    @Mock
    private PlansService plansServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlansMockMvc;

    private Plans plans;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plans createEntity(EntityManager em) {
        Plans plans = new Plans()
            .planCode(DEFAULT_PLAN_CODE)
            .name(DEFAULT_NAME)
            .basePremium(DEFAULT_BASE_PREMIUM)
            .coverAmount(DEFAULT_COVER_AMOUNT)
            .coverPeriodUnit(DEFAULT_COVER_PERIOD_UNIT)
            .coverPeriodValue(DEFAULT_COVER_PERIOD_VALUE)
            .active(DEFAULT_ACTIVE);
        return plans;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plans createUpdatedEntity(EntityManager em) {
        Plans plans = new Plans()
            .planCode(UPDATED_PLAN_CODE)
            .name(UPDATED_NAME)
            .basePremium(UPDATED_BASE_PREMIUM)
            .coverAmount(UPDATED_COVER_AMOUNT)
            .coverPeriodUnit(UPDATED_COVER_PERIOD_UNIT)
            .coverPeriodValue(UPDATED_COVER_PERIOD_VALUE)
            .active(UPDATED_ACTIVE);
        return plans;
    }

    @BeforeEach
    public void initTest() {
        plans = createEntity(em);
    }

    @Test
    @Transactional
    void createPlans() throws Exception {
        int databaseSizeBeforeCreate = plansRepository.findAll().size();
        // Create the Plans
        restPlansMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plans)))
            .andExpect(status().isCreated());

        // Validate the Plans in the database
        List<Plans> plansList = plansRepository.findAll();
        assertThat(plansList).hasSize(databaseSizeBeforeCreate + 1);
        Plans testPlans = plansList.get(plansList.size() - 1);
        assertThat(testPlans.getPlanCode()).isEqualTo(DEFAULT_PLAN_CODE);
        assertThat(testPlans.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlans.getBasePremium()).isEqualTo(DEFAULT_BASE_PREMIUM);
        assertThat(testPlans.getCoverAmount()).isEqualTo(DEFAULT_COVER_AMOUNT);
        assertThat(testPlans.getCoverPeriodUnit()).isEqualTo(DEFAULT_COVER_PERIOD_UNIT);
        assertThat(testPlans.getCoverPeriodValue()).isEqualTo(DEFAULT_COVER_PERIOD_VALUE);
        assertThat(testPlans.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    void createPlansWithExistingId() throws Exception {
        // Create the Plans with an existing ID
        plans.setId(1L);

        int databaseSizeBeforeCreate = plansRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlansMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plans)))
            .andExpect(status().isBadRequest());

        // Validate the Plans in the database
        List<Plans> plansList = plansRepository.findAll();
        assertThat(plansList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPlanCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = plansRepository.findAll().size();
        // set the field null
        plans.setPlanCode(null);

        // Create the Plans, which fails.

        restPlansMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plans)))
            .andExpect(status().isBadRequest());

        List<Plans> plansList = plansRepository.findAll();
        assertThat(plansList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = plansRepository.findAll().size();
        // set the field null
        plans.setName(null);

        // Create the Plans, which fails.

        restPlansMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plans)))
            .andExpect(status().isBadRequest());

        List<Plans> plansList = plansRepository.findAll();
        assertThat(plansList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCoverAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = plansRepository.findAll().size();
        // set the field null
        plans.setCoverAmount(null);

        // Create the Plans, which fails.

        restPlansMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plans)))
            .andExpect(status().isBadRequest());

        List<Plans> plansList = plansRepository.findAll();
        assertThat(plansList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCoverPeriodUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = plansRepository.findAll().size();
        // set the field null
        plans.setCoverPeriodUnit(null);

        // Create the Plans, which fails.

        restPlansMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plans)))
            .andExpect(status().isBadRequest());

        List<Plans> plansList = plansRepository.findAll();
        assertThat(plansList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCoverPeriodValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = plansRepository.findAll().size();
        // set the field null
        plans.setCoverPeriodValue(null);

        // Create the Plans, which fails.

        restPlansMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plans)))
            .andExpect(status().isBadRequest());

        List<Plans> plansList = plansRepository.findAll();
        assertThat(plansList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = plansRepository.findAll().size();
        // set the field null
        plans.setActive(null);

        // Create the Plans, which fails.

        restPlansMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plans)))
            .andExpect(status().isBadRequest());

        List<Plans> plansList = plansRepository.findAll();
        assertThat(plansList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlans() throws Exception {
        // Initialize the database
        plansRepository.saveAndFlush(plans);

        // Get all the plansList
        restPlansMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plans.getId().intValue())))
            .andExpect(jsonPath("$.[*].planCode").value(hasItem(DEFAULT_PLAN_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].basePremium").value(hasItem(DEFAULT_BASE_PREMIUM)))
            .andExpect(jsonPath("$.[*].coverAmount").value(hasItem(DEFAULT_COVER_AMOUNT)))
            .andExpect(jsonPath("$.[*].coverPeriodUnit").value(hasItem(DEFAULT_COVER_PERIOD_UNIT.toString())))
            .andExpect(jsonPath("$.[*].coverPeriodValue").value(hasItem(DEFAULT_COVER_PERIOD_VALUE)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlansWithEagerRelationshipsIsEnabled() throws Exception {
        when(plansServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlansMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(plansServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlansWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(plansServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlansMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(plansRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPlans() throws Exception {
        // Initialize the database
        plansRepository.saveAndFlush(plans);

        // Get the plans
        restPlansMockMvc
            .perform(get(ENTITY_API_URL_ID, plans.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plans.getId().intValue()))
            .andExpect(jsonPath("$.planCode").value(DEFAULT_PLAN_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.basePremium").value(DEFAULT_BASE_PREMIUM))
            .andExpect(jsonPath("$.coverAmount").value(DEFAULT_COVER_AMOUNT))
            .andExpect(jsonPath("$.coverPeriodUnit").value(DEFAULT_COVER_PERIOD_UNIT.toString()))
            .andExpect(jsonPath("$.coverPeriodValue").value(DEFAULT_COVER_PERIOD_VALUE))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingPlans() throws Exception {
        // Get the plans
        restPlansMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlans() throws Exception {
        // Initialize the database
        plansRepository.saveAndFlush(plans);

        int databaseSizeBeforeUpdate = plansRepository.findAll().size();

        // Update the plans
        Plans updatedPlans = plansRepository.findById(plans.getId()).get();
        // Disconnect from session so that the updates on updatedPlans are not directly saved in db
        em.detach(updatedPlans);
        updatedPlans
            .planCode(UPDATED_PLAN_CODE)
            .name(UPDATED_NAME)
            .basePremium(UPDATED_BASE_PREMIUM)
            .coverAmount(UPDATED_COVER_AMOUNT)
            .coverPeriodUnit(UPDATED_COVER_PERIOD_UNIT)
            .coverPeriodValue(UPDATED_COVER_PERIOD_VALUE)
            .active(UPDATED_ACTIVE);

        restPlansMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlans.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlans))
            )
            .andExpect(status().isOk());

        // Validate the Plans in the database
        List<Plans> plansList = plansRepository.findAll();
        assertThat(plansList).hasSize(databaseSizeBeforeUpdate);
        Plans testPlans = plansList.get(plansList.size() - 1);
        assertThat(testPlans.getPlanCode()).isEqualTo(UPDATED_PLAN_CODE);
        assertThat(testPlans.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlans.getBasePremium()).isEqualTo(UPDATED_BASE_PREMIUM);
        assertThat(testPlans.getCoverAmount()).isEqualTo(UPDATED_COVER_AMOUNT);
        assertThat(testPlans.getCoverPeriodUnit()).isEqualTo(UPDATED_COVER_PERIOD_UNIT);
        assertThat(testPlans.getCoverPeriodValue()).isEqualTo(UPDATED_COVER_PERIOD_VALUE);
        assertThat(testPlans.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingPlans() throws Exception {
        int databaseSizeBeforeUpdate = plansRepository.findAll().size();
        plans.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlansMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plans.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plans))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plans in the database
        List<Plans> plansList = plansRepository.findAll();
        assertThat(plansList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlans() throws Exception {
        int databaseSizeBeforeUpdate = plansRepository.findAll().size();
        plans.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlansMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plans))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plans in the database
        List<Plans> plansList = plansRepository.findAll();
        assertThat(plansList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlans() throws Exception {
        int databaseSizeBeforeUpdate = plansRepository.findAll().size();
        plans.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlansMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plans)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plans in the database
        List<Plans> plansList = plansRepository.findAll();
        assertThat(plansList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlansWithPatch() throws Exception {
        // Initialize the database
        plansRepository.saveAndFlush(plans);

        int databaseSizeBeforeUpdate = plansRepository.findAll().size();

        // Update the plans using partial update
        Plans partialUpdatedPlans = new Plans();
        partialUpdatedPlans.setId(plans.getId());

        partialUpdatedPlans.coverPeriodValue(UPDATED_COVER_PERIOD_VALUE).active(UPDATED_ACTIVE);

        restPlansMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlans.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlans))
            )
            .andExpect(status().isOk());

        // Validate the Plans in the database
        List<Plans> plansList = plansRepository.findAll();
        assertThat(plansList).hasSize(databaseSizeBeforeUpdate);
        Plans testPlans = plansList.get(plansList.size() - 1);
        assertThat(testPlans.getPlanCode()).isEqualTo(DEFAULT_PLAN_CODE);
        assertThat(testPlans.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlans.getBasePremium()).isEqualTo(DEFAULT_BASE_PREMIUM);
        assertThat(testPlans.getCoverAmount()).isEqualTo(DEFAULT_COVER_AMOUNT);
        assertThat(testPlans.getCoverPeriodUnit()).isEqualTo(DEFAULT_COVER_PERIOD_UNIT);
        assertThat(testPlans.getCoverPeriodValue()).isEqualTo(UPDATED_COVER_PERIOD_VALUE);
        assertThat(testPlans.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdatePlansWithPatch() throws Exception {
        // Initialize the database
        plansRepository.saveAndFlush(plans);

        int databaseSizeBeforeUpdate = plansRepository.findAll().size();

        // Update the plans using partial update
        Plans partialUpdatedPlans = new Plans();
        partialUpdatedPlans.setId(plans.getId());

        partialUpdatedPlans
            .planCode(UPDATED_PLAN_CODE)
            .name(UPDATED_NAME)
            .basePremium(UPDATED_BASE_PREMIUM)
            .coverAmount(UPDATED_COVER_AMOUNT)
            .coverPeriodUnit(UPDATED_COVER_PERIOD_UNIT)
            .coverPeriodValue(UPDATED_COVER_PERIOD_VALUE)
            .active(UPDATED_ACTIVE);

        restPlansMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlans.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlans))
            )
            .andExpect(status().isOk());

        // Validate the Plans in the database
        List<Plans> plansList = plansRepository.findAll();
        assertThat(plansList).hasSize(databaseSizeBeforeUpdate);
        Plans testPlans = plansList.get(plansList.size() - 1);
        assertThat(testPlans.getPlanCode()).isEqualTo(UPDATED_PLAN_CODE);
        assertThat(testPlans.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlans.getBasePremium()).isEqualTo(UPDATED_BASE_PREMIUM);
        assertThat(testPlans.getCoverAmount()).isEqualTo(UPDATED_COVER_AMOUNT);
        assertThat(testPlans.getCoverPeriodUnit()).isEqualTo(UPDATED_COVER_PERIOD_UNIT);
        assertThat(testPlans.getCoverPeriodValue()).isEqualTo(UPDATED_COVER_PERIOD_VALUE);
        assertThat(testPlans.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingPlans() throws Exception {
        int databaseSizeBeforeUpdate = plansRepository.findAll().size();
        plans.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlansMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plans.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plans))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plans in the database
        List<Plans> plansList = plansRepository.findAll();
        assertThat(plansList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlans() throws Exception {
        int databaseSizeBeforeUpdate = plansRepository.findAll().size();
        plans.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlansMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plans))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plans in the database
        List<Plans> plansList = plansRepository.findAll();
        assertThat(plansList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlans() throws Exception {
        int databaseSizeBeforeUpdate = plansRepository.findAll().size();
        plans.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlansMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(plans)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plans in the database
        List<Plans> plansList = plansRepository.findAll();
        assertThat(plansList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlans() throws Exception {
        // Initialize the database
        plansRepository.saveAndFlush(plans);

        int databaseSizeBeforeDelete = plansRepository.findAll().size();

        // Delete the plans
        restPlansMockMvc
            .perform(delete(ENTITY_API_URL_ID, plans.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Plans> plansList = plansRepository.findAll();
        assertThat(plansList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

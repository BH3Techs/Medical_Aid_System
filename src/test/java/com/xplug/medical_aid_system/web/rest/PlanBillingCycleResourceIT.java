package com.xplug.medical_aid_system.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.xplug.medical_aid_system.IntegrationTest;
import com.xplug.medical_aid_system.domain.PlanBillingCycle;
import com.xplug.medical_aid_system.domain.enumeration.DateConfiguration;
import com.xplug.medical_aid_system.domain.enumeration.PeriodUnit;
import com.xplug.medical_aid_system.repository.PlanBillingCycleRepository;
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
 * Integration tests for the {@link PlanBillingCycleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlanBillingCycleResourceIT {

    private static final PeriodUnit DEFAULT_PERIOD_UNIT = PeriodUnit.DAY;
    private static final PeriodUnit UPDATED_PERIOD_UNIT = PeriodUnit.WEEK;

    private static final Integer DEFAULT_PERIOD_VALUE = 1;
    private static final Integer UPDATED_PERIOD_VALUE = 2;

    private static final DateConfiguration DEFAULT_DATE_CONFIGURATION = DateConfiguration.DYNAMIC;
    private static final DateConfiguration UPDATED_DATE_CONFIGURATION = DateConfiguration.STATIC;

    private static final String DEFAULT_BILLING_DATE = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_DATE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/plan-billing-cycles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlanBillingCycleRepository planBillingCycleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanBillingCycleMockMvc;

    private PlanBillingCycle planBillingCycle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanBillingCycle createEntity(EntityManager em) {
        PlanBillingCycle planBillingCycle = new PlanBillingCycle()
            .periodUnit(DEFAULT_PERIOD_UNIT)
            .periodValue(DEFAULT_PERIOD_VALUE)
            .dateConfiguration(DEFAULT_DATE_CONFIGURATION)
            .billingDate(DEFAULT_BILLING_DATE);
        return planBillingCycle;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanBillingCycle createUpdatedEntity(EntityManager em) {
        PlanBillingCycle planBillingCycle = new PlanBillingCycle()
            .periodUnit(UPDATED_PERIOD_UNIT)
            .periodValue(UPDATED_PERIOD_VALUE)
            .dateConfiguration(UPDATED_DATE_CONFIGURATION)
            .billingDate(UPDATED_BILLING_DATE);
        return planBillingCycle;
    }

    @BeforeEach
    public void initTest() {
        planBillingCycle = createEntity(em);
    }

    @Test
    @Transactional
    void createPlanBillingCycle() throws Exception {
        int databaseSizeBeforeCreate = planBillingCycleRepository.findAll().size();
        // Create the PlanBillingCycle
        restPlanBillingCycleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planBillingCycle))
            )
            .andExpect(status().isCreated());

        // Validate the PlanBillingCycle in the database
        List<PlanBillingCycle> planBillingCycleList = planBillingCycleRepository.findAll();
        assertThat(planBillingCycleList).hasSize(databaseSizeBeforeCreate + 1);
        PlanBillingCycle testPlanBillingCycle = planBillingCycleList.get(planBillingCycleList.size() - 1);
        assertThat(testPlanBillingCycle.getPeriodUnit()).isEqualTo(DEFAULT_PERIOD_UNIT);
        assertThat(testPlanBillingCycle.getPeriodValue()).isEqualTo(DEFAULT_PERIOD_VALUE);
        assertThat(testPlanBillingCycle.getDateConfiguration()).isEqualTo(DEFAULT_DATE_CONFIGURATION);
        assertThat(testPlanBillingCycle.getBillingDate()).isEqualTo(DEFAULT_BILLING_DATE);
    }

    @Test
    @Transactional
    void createPlanBillingCycleWithExistingId() throws Exception {
        // Create the PlanBillingCycle with an existing ID
        planBillingCycle.setId(1L);

        int databaseSizeBeforeCreate = planBillingCycleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanBillingCycleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planBillingCycle))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanBillingCycle in the database
        List<PlanBillingCycle> planBillingCycleList = planBillingCycleRepository.findAll();
        assertThat(planBillingCycleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPeriodUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = planBillingCycleRepository.findAll().size();
        // set the field null
        planBillingCycle.setPeriodUnit(null);

        // Create the PlanBillingCycle, which fails.

        restPlanBillingCycleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planBillingCycle))
            )
            .andExpect(status().isBadRequest());

        List<PlanBillingCycle> planBillingCycleList = planBillingCycleRepository.findAll();
        assertThat(planBillingCycleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPeriodValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = planBillingCycleRepository.findAll().size();
        // set the field null
        planBillingCycle.setPeriodValue(null);

        // Create the PlanBillingCycle, which fails.

        restPlanBillingCycleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planBillingCycle))
            )
            .andExpect(status().isBadRequest());

        List<PlanBillingCycle> planBillingCycleList = planBillingCycleRepository.findAll();
        assertThat(planBillingCycleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlanBillingCycles() throws Exception {
        // Initialize the database
        planBillingCycleRepository.saveAndFlush(planBillingCycle);

        // Get all the planBillingCycleList
        restPlanBillingCycleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planBillingCycle.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodUnit").value(hasItem(DEFAULT_PERIOD_UNIT.toString())))
            .andExpect(jsonPath("$.[*].periodValue").value(hasItem(DEFAULT_PERIOD_VALUE)))
            .andExpect(jsonPath("$.[*].dateConfiguration").value(hasItem(DEFAULT_DATE_CONFIGURATION.toString())))
            .andExpect(jsonPath("$.[*].billingDate").value(hasItem(DEFAULT_BILLING_DATE)));
    }

    @Test
    @Transactional
    void getPlanBillingCycle() throws Exception {
        // Initialize the database
        planBillingCycleRepository.saveAndFlush(planBillingCycle);

        // Get the planBillingCycle
        restPlanBillingCycleMockMvc
            .perform(get(ENTITY_API_URL_ID, planBillingCycle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planBillingCycle.getId().intValue()))
            .andExpect(jsonPath("$.periodUnit").value(DEFAULT_PERIOD_UNIT.toString()))
            .andExpect(jsonPath("$.periodValue").value(DEFAULT_PERIOD_VALUE))
            .andExpect(jsonPath("$.dateConfiguration").value(DEFAULT_DATE_CONFIGURATION.toString()))
            .andExpect(jsonPath("$.billingDate").value(DEFAULT_BILLING_DATE));
    }

    @Test
    @Transactional
    void getNonExistingPlanBillingCycle() throws Exception {
        // Get the planBillingCycle
        restPlanBillingCycleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlanBillingCycle() throws Exception {
        // Initialize the database
        planBillingCycleRepository.saveAndFlush(planBillingCycle);

        int databaseSizeBeforeUpdate = planBillingCycleRepository.findAll().size();

        // Update the planBillingCycle
        PlanBillingCycle updatedPlanBillingCycle = planBillingCycleRepository.findById(planBillingCycle.getId()).get();
        // Disconnect from session so that the updates on updatedPlanBillingCycle are not directly saved in db
        em.detach(updatedPlanBillingCycle);
        updatedPlanBillingCycle
            .periodUnit(UPDATED_PERIOD_UNIT)
            .periodValue(UPDATED_PERIOD_VALUE)
            .dateConfiguration(UPDATED_DATE_CONFIGURATION)
            .billingDate(UPDATED_BILLING_DATE);

        restPlanBillingCycleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlanBillingCycle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlanBillingCycle))
            )
            .andExpect(status().isOk());

        // Validate the PlanBillingCycle in the database
        List<PlanBillingCycle> planBillingCycleList = planBillingCycleRepository.findAll();
        assertThat(planBillingCycleList).hasSize(databaseSizeBeforeUpdate);
        PlanBillingCycle testPlanBillingCycle = planBillingCycleList.get(planBillingCycleList.size() - 1);
        assertThat(testPlanBillingCycle.getPeriodUnit()).isEqualTo(UPDATED_PERIOD_UNIT);
        assertThat(testPlanBillingCycle.getPeriodValue()).isEqualTo(UPDATED_PERIOD_VALUE);
        assertThat(testPlanBillingCycle.getDateConfiguration()).isEqualTo(UPDATED_DATE_CONFIGURATION);
        assertThat(testPlanBillingCycle.getBillingDate()).isEqualTo(UPDATED_BILLING_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPlanBillingCycle() throws Exception {
        int databaseSizeBeforeUpdate = planBillingCycleRepository.findAll().size();
        planBillingCycle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanBillingCycleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planBillingCycle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planBillingCycle))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanBillingCycle in the database
        List<PlanBillingCycle> planBillingCycleList = planBillingCycleRepository.findAll();
        assertThat(planBillingCycleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlanBillingCycle() throws Exception {
        int databaseSizeBeforeUpdate = planBillingCycleRepository.findAll().size();
        planBillingCycle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanBillingCycleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planBillingCycle))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanBillingCycle in the database
        List<PlanBillingCycle> planBillingCycleList = planBillingCycleRepository.findAll();
        assertThat(planBillingCycleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlanBillingCycle() throws Exception {
        int databaseSizeBeforeUpdate = planBillingCycleRepository.findAll().size();
        planBillingCycle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanBillingCycleMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planBillingCycle))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanBillingCycle in the database
        List<PlanBillingCycle> planBillingCycleList = planBillingCycleRepository.findAll();
        assertThat(planBillingCycleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanBillingCycleWithPatch() throws Exception {
        // Initialize the database
        planBillingCycleRepository.saveAndFlush(planBillingCycle);

        int databaseSizeBeforeUpdate = planBillingCycleRepository.findAll().size();

        // Update the planBillingCycle using partial update
        PlanBillingCycle partialUpdatedPlanBillingCycle = new PlanBillingCycle();
        partialUpdatedPlanBillingCycle.setId(planBillingCycle.getId());

        partialUpdatedPlanBillingCycle.periodValue(UPDATED_PERIOD_VALUE).dateConfiguration(UPDATED_DATE_CONFIGURATION);

        restPlanBillingCycleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanBillingCycle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanBillingCycle))
            )
            .andExpect(status().isOk());

        // Validate the PlanBillingCycle in the database
        List<PlanBillingCycle> planBillingCycleList = planBillingCycleRepository.findAll();
        assertThat(planBillingCycleList).hasSize(databaseSizeBeforeUpdate);
        PlanBillingCycle testPlanBillingCycle = planBillingCycleList.get(planBillingCycleList.size() - 1);
        assertThat(testPlanBillingCycle.getPeriodUnit()).isEqualTo(DEFAULT_PERIOD_UNIT);
        assertThat(testPlanBillingCycle.getPeriodValue()).isEqualTo(UPDATED_PERIOD_VALUE);
        assertThat(testPlanBillingCycle.getDateConfiguration()).isEqualTo(UPDATED_DATE_CONFIGURATION);
        assertThat(testPlanBillingCycle.getBillingDate()).isEqualTo(DEFAULT_BILLING_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePlanBillingCycleWithPatch() throws Exception {
        // Initialize the database
        planBillingCycleRepository.saveAndFlush(planBillingCycle);

        int databaseSizeBeforeUpdate = planBillingCycleRepository.findAll().size();

        // Update the planBillingCycle using partial update
        PlanBillingCycle partialUpdatedPlanBillingCycle = new PlanBillingCycle();
        partialUpdatedPlanBillingCycle.setId(planBillingCycle.getId());

        partialUpdatedPlanBillingCycle
            .periodUnit(UPDATED_PERIOD_UNIT)
            .periodValue(UPDATED_PERIOD_VALUE)
            .dateConfiguration(UPDATED_DATE_CONFIGURATION)
            .billingDate(UPDATED_BILLING_DATE);

        restPlanBillingCycleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanBillingCycle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanBillingCycle))
            )
            .andExpect(status().isOk());

        // Validate the PlanBillingCycle in the database
        List<PlanBillingCycle> planBillingCycleList = planBillingCycleRepository.findAll();
        assertThat(planBillingCycleList).hasSize(databaseSizeBeforeUpdate);
        PlanBillingCycle testPlanBillingCycle = planBillingCycleList.get(planBillingCycleList.size() - 1);
        assertThat(testPlanBillingCycle.getPeriodUnit()).isEqualTo(UPDATED_PERIOD_UNIT);
        assertThat(testPlanBillingCycle.getPeriodValue()).isEqualTo(UPDATED_PERIOD_VALUE);
        assertThat(testPlanBillingCycle.getDateConfiguration()).isEqualTo(UPDATED_DATE_CONFIGURATION);
        assertThat(testPlanBillingCycle.getBillingDate()).isEqualTo(UPDATED_BILLING_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPlanBillingCycle() throws Exception {
        int databaseSizeBeforeUpdate = planBillingCycleRepository.findAll().size();
        planBillingCycle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanBillingCycleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, planBillingCycle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planBillingCycle))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanBillingCycle in the database
        List<PlanBillingCycle> planBillingCycleList = planBillingCycleRepository.findAll();
        assertThat(planBillingCycleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlanBillingCycle() throws Exception {
        int databaseSizeBeforeUpdate = planBillingCycleRepository.findAll().size();
        planBillingCycle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanBillingCycleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planBillingCycle))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanBillingCycle in the database
        List<PlanBillingCycle> planBillingCycleList = planBillingCycleRepository.findAll();
        assertThat(planBillingCycleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlanBillingCycle() throws Exception {
        int databaseSizeBeforeUpdate = planBillingCycleRepository.findAll().size();
        planBillingCycle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanBillingCycleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planBillingCycle))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanBillingCycle in the database
        List<PlanBillingCycle> planBillingCycleList = planBillingCycleRepository.findAll();
        assertThat(planBillingCycleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlanBillingCycle() throws Exception {
        // Initialize the database
        planBillingCycleRepository.saveAndFlush(planBillingCycle);

        int databaseSizeBeforeDelete = planBillingCycleRepository.findAll().size();

        // Delete the planBillingCycle
        restPlanBillingCycleMockMvc
            .perform(delete(ENTITY_API_URL_ID, planBillingCycle.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanBillingCycle> planBillingCycleList = planBillingCycleRepository.findAll();
        assertThat(planBillingCycleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

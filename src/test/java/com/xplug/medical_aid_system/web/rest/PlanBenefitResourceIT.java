package com.xplug.medical_aid_system.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.xplug.medical_aid_system.IntegrationTest;
import com.xplug.medical_aid_system.domain.PlanBenefit;
import com.xplug.medical_aid_system.domain.enumeration.PeriodUnit;
import com.xplug.medical_aid_system.repository.PlanBenefitRepository;
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
 * Integration tests for the {@link PlanBenefitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlanBenefitResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final PeriodUnit DEFAULT_WAITING_PERIOD_UNIT = PeriodUnit.DAY;
    private static final PeriodUnit UPDATED_WAITING_PERIOD_UNIT = PeriodUnit.WEEK;

    private static final Integer DEFAULT_WAITING_PERIOD_VALUE = 1;
    private static final Integer UPDATED_WAITING_PERIOD_VALUE = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/plan-benefits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlanBenefitRepository planBenefitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanBenefitMockMvc;

    private PlanBenefit planBenefit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanBenefit createEntity(EntityManager em) {
        PlanBenefit planBenefit = new PlanBenefit()
            .name(DEFAULT_NAME)
            .waitingPeriodUnit(DEFAULT_WAITING_PERIOD_UNIT)
            .waitingPeriodValue(DEFAULT_WAITING_PERIOD_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .active(DEFAULT_ACTIVE);
        return planBenefit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanBenefit createUpdatedEntity(EntityManager em) {
        PlanBenefit planBenefit = new PlanBenefit()
            .name(UPDATED_NAME)
            .waitingPeriodUnit(UPDATED_WAITING_PERIOD_UNIT)
            .waitingPeriodValue(UPDATED_WAITING_PERIOD_VALUE)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        return planBenefit;
    }

    @BeforeEach
    public void initTest() {
        planBenefit = createEntity(em);
    }

    @Test
    @Transactional
    void createPlanBenefit() throws Exception {
        int databaseSizeBeforeCreate = planBenefitRepository.findAll().size();
        // Create the PlanBenefit
        restPlanBenefitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planBenefit)))
            .andExpect(status().isCreated());

        // Validate the PlanBenefit in the database
        List<PlanBenefit> planBenefitList = planBenefitRepository.findAll();
        assertThat(planBenefitList).hasSize(databaseSizeBeforeCreate + 1);
        PlanBenefit testPlanBenefit = planBenefitList.get(planBenefitList.size() - 1);
        assertThat(testPlanBenefit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlanBenefit.getWaitingPeriodUnit()).isEqualTo(DEFAULT_WAITING_PERIOD_UNIT);
        assertThat(testPlanBenefit.getWaitingPeriodValue()).isEqualTo(DEFAULT_WAITING_PERIOD_VALUE);
        assertThat(testPlanBenefit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPlanBenefit.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    void createPlanBenefitWithExistingId() throws Exception {
        // Create the PlanBenefit with an existing ID
        planBenefit.setId(1L);

        int databaseSizeBeforeCreate = planBenefitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanBenefitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planBenefit)))
            .andExpect(status().isBadRequest());

        // Validate the PlanBenefit in the database
        List<PlanBenefit> planBenefitList = planBenefitRepository.findAll();
        assertThat(planBenefitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = planBenefitRepository.findAll().size();
        // set the field null
        planBenefit.setName(null);

        // Create the PlanBenefit, which fails.

        restPlanBenefitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planBenefit)))
            .andExpect(status().isBadRequest());

        List<PlanBenefit> planBenefitList = planBenefitRepository.findAll();
        assertThat(planBenefitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWaitingPeriodUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = planBenefitRepository.findAll().size();
        // set the field null
        planBenefit.setWaitingPeriodUnit(null);

        // Create the PlanBenefit, which fails.

        restPlanBenefitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planBenefit)))
            .andExpect(status().isBadRequest());

        List<PlanBenefit> planBenefitList = planBenefitRepository.findAll();
        assertThat(planBenefitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWaitingPeriodValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = planBenefitRepository.findAll().size();
        // set the field null
        planBenefit.setWaitingPeriodValue(null);

        // Create the PlanBenefit, which fails.

        restPlanBenefitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planBenefit)))
            .andExpect(status().isBadRequest());

        List<PlanBenefit> planBenefitList = planBenefitRepository.findAll();
        assertThat(planBenefitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = planBenefitRepository.findAll().size();
        // set the field null
        planBenefit.setDescription(null);

        // Create the PlanBenefit, which fails.

        restPlanBenefitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planBenefit)))
            .andExpect(status().isBadRequest());

        List<PlanBenefit> planBenefitList = planBenefitRepository.findAll();
        assertThat(planBenefitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = planBenefitRepository.findAll().size();
        // set the field null
        planBenefit.setActive(null);

        // Create the PlanBenefit, which fails.

        restPlanBenefitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planBenefit)))
            .andExpect(status().isBadRequest());

        List<PlanBenefit> planBenefitList = planBenefitRepository.findAll();
        assertThat(planBenefitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlanBenefits() throws Exception {
        // Initialize the database
        planBenefitRepository.saveAndFlush(planBenefit);

        // Get all the planBenefitList
        restPlanBenefitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planBenefit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].waitingPeriodUnit").value(hasItem(DEFAULT_WAITING_PERIOD_UNIT.toString())))
            .andExpect(jsonPath("$.[*].waitingPeriodValue").value(hasItem(DEFAULT_WAITING_PERIOD_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getPlanBenefit() throws Exception {
        // Initialize the database
        planBenefitRepository.saveAndFlush(planBenefit);

        // Get the planBenefit
        restPlanBenefitMockMvc
            .perform(get(ENTITY_API_URL_ID, planBenefit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planBenefit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.waitingPeriodUnit").value(DEFAULT_WAITING_PERIOD_UNIT.toString()))
            .andExpect(jsonPath("$.waitingPeriodValue").value(DEFAULT_WAITING_PERIOD_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingPlanBenefit() throws Exception {
        // Get the planBenefit
        restPlanBenefitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlanBenefit() throws Exception {
        // Initialize the database
        planBenefitRepository.saveAndFlush(planBenefit);

        int databaseSizeBeforeUpdate = planBenefitRepository.findAll().size();

        // Update the planBenefit
        PlanBenefit updatedPlanBenefit = planBenefitRepository.findById(planBenefit.getId()).get();
        // Disconnect from session so that the updates on updatedPlanBenefit are not directly saved in db
        em.detach(updatedPlanBenefit);
        updatedPlanBenefit
            .name(UPDATED_NAME)
            .waitingPeriodUnit(UPDATED_WAITING_PERIOD_UNIT)
            .waitingPeriodValue(UPDATED_WAITING_PERIOD_VALUE)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);

        restPlanBenefitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlanBenefit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlanBenefit))
            )
            .andExpect(status().isOk());

        // Validate the PlanBenefit in the database
        List<PlanBenefit> planBenefitList = planBenefitRepository.findAll();
        assertThat(planBenefitList).hasSize(databaseSizeBeforeUpdate);
        PlanBenefit testPlanBenefit = planBenefitList.get(planBenefitList.size() - 1);
        assertThat(testPlanBenefit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlanBenefit.getWaitingPeriodUnit()).isEqualTo(UPDATED_WAITING_PERIOD_UNIT);
        assertThat(testPlanBenefit.getWaitingPeriodValue()).isEqualTo(UPDATED_WAITING_PERIOD_VALUE);
        assertThat(testPlanBenefit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPlanBenefit.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingPlanBenefit() throws Exception {
        int databaseSizeBeforeUpdate = planBenefitRepository.findAll().size();
        planBenefit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanBenefitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planBenefit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planBenefit))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanBenefit in the database
        List<PlanBenefit> planBenefitList = planBenefitRepository.findAll();
        assertThat(planBenefitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlanBenefit() throws Exception {
        int databaseSizeBeforeUpdate = planBenefitRepository.findAll().size();
        planBenefit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanBenefitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planBenefit))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanBenefit in the database
        List<PlanBenefit> planBenefitList = planBenefitRepository.findAll();
        assertThat(planBenefitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlanBenefit() throws Exception {
        int databaseSizeBeforeUpdate = planBenefitRepository.findAll().size();
        planBenefit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanBenefitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planBenefit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanBenefit in the database
        List<PlanBenefit> planBenefitList = planBenefitRepository.findAll();
        assertThat(planBenefitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanBenefitWithPatch() throws Exception {
        // Initialize the database
        planBenefitRepository.saveAndFlush(planBenefit);

        int databaseSizeBeforeUpdate = planBenefitRepository.findAll().size();

        // Update the planBenefit using partial update
        PlanBenefit partialUpdatedPlanBenefit = new PlanBenefit();
        partialUpdatedPlanBenefit.setId(planBenefit.getId());

        partialUpdatedPlanBenefit.name(UPDATED_NAME).active(UPDATED_ACTIVE);

        restPlanBenefitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanBenefit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanBenefit))
            )
            .andExpect(status().isOk());

        // Validate the PlanBenefit in the database
        List<PlanBenefit> planBenefitList = planBenefitRepository.findAll();
        assertThat(planBenefitList).hasSize(databaseSizeBeforeUpdate);
        PlanBenefit testPlanBenefit = planBenefitList.get(planBenefitList.size() - 1);
        assertThat(testPlanBenefit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlanBenefit.getWaitingPeriodUnit()).isEqualTo(DEFAULT_WAITING_PERIOD_UNIT);
        assertThat(testPlanBenefit.getWaitingPeriodValue()).isEqualTo(DEFAULT_WAITING_PERIOD_VALUE);
        assertThat(testPlanBenefit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPlanBenefit.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdatePlanBenefitWithPatch() throws Exception {
        // Initialize the database
        planBenefitRepository.saveAndFlush(planBenefit);

        int databaseSizeBeforeUpdate = planBenefitRepository.findAll().size();

        // Update the planBenefit using partial update
        PlanBenefit partialUpdatedPlanBenefit = new PlanBenefit();
        partialUpdatedPlanBenefit.setId(planBenefit.getId());

        partialUpdatedPlanBenefit
            .name(UPDATED_NAME)
            .waitingPeriodUnit(UPDATED_WAITING_PERIOD_UNIT)
            .waitingPeriodValue(UPDATED_WAITING_PERIOD_VALUE)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);

        restPlanBenefitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanBenefit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanBenefit))
            )
            .andExpect(status().isOk());

        // Validate the PlanBenefit in the database
        List<PlanBenefit> planBenefitList = planBenefitRepository.findAll();
        assertThat(planBenefitList).hasSize(databaseSizeBeforeUpdate);
        PlanBenefit testPlanBenefit = planBenefitList.get(planBenefitList.size() - 1);
        assertThat(testPlanBenefit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlanBenefit.getWaitingPeriodUnit()).isEqualTo(UPDATED_WAITING_PERIOD_UNIT);
        assertThat(testPlanBenefit.getWaitingPeriodValue()).isEqualTo(UPDATED_WAITING_PERIOD_VALUE);
        assertThat(testPlanBenefit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPlanBenefit.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingPlanBenefit() throws Exception {
        int databaseSizeBeforeUpdate = planBenefitRepository.findAll().size();
        planBenefit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanBenefitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, planBenefit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planBenefit))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanBenefit in the database
        List<PlanBenefit> planBenefitList = planBenefitRepository.findAll();
        assertThat(planBenefitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlanBenefit() throws Exception {
        int databaseSizeBeforeUpdate = planBenefitRepository.findAll().size();
        planBenefit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanBenefitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planBenefit))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanBenefit in the database
        List<PlanBenefit> planBenefitList = planBenefitRepository.findAll();
        assertThat(planBenefitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlanBenefit() throws Exception {
        int databaseSizeBeforeUpdate = planBenefitRepository.findAll().size();
        planBenefit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanBenefitMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(planBenefit))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanBenefit in the database
        List<PlanBenefit> planBenefitList = planBenefitRepository.findAll();
        assertThat(planBenefitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlanBenefit() throws Exception {
        // Initialize the database
        planBenefitRepository.saveAndFlush(planBenefit);

        int databaseSizeBeforeDelete = planBenefitRepository.findAll().size();

        // Delete the planBenefit
        restPlanBenefitMockMvc
            .perform(delete(ENTITY_API_URL_ID, planBenefit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanBenefit> planBenefitList = planBenefitRepository.findAll();
        assertThat(planBenefitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

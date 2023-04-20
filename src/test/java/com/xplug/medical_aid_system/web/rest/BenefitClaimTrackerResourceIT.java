package com.xplug.medical_aid_system.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.xplug.medical_aid_system.IntegrationTest;
import com.xplug.medical_aid_system.domain.BenefitClaimTracker;
import com.xplug.medical_aid_system.repository.BenefitClaimTrackerRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link BenefitClaimTrackerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BenefitClaimTrackerResourceIT {

    private static final LocalDate DEFAULT_RESET_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RESET_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_NEXT_POSSIBLE_CLAIM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NEXT_POSSIBLE_CLAIM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CURRENT_LIMIT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_LIMIT_VALUE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CURRENT_LIMIT_PERIOD = 1;
    private static final Integer UPDATED_CURRENT_LIMIT_PERIOD = 2;

    private static final String ENTITY_API_URL = "/api/benefit-claim-trackers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BenefitClaimTrackerRepository benefitClaimTrackerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBenefitClaimTrackerMockMvc;

    private BenefitClaimTracker benefitClaimTracker;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BenefitClaimTracker createEntity(EntityManager em) {
        BenefitClaimTracker benefitClaimTracker = new BenefitClaimTracker()
            .resetDate(DEFAULT_RESET_DATE)
            .nextPossibleClaimDate(DEFAULT_NEXT_POSSIBLE_CLAIM_DATE)
            .currentLimitValue(DEFAULT_CURRENT_LIMIT_VALUE)
            .currentLimitPeriod(DEFAULT_CURRENT_LIMIT_PERIOD);
        return benefitClaimTracker;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BenefitClaimTracker createUpdatedEntity(EntityManager em) {
        BenefitClaimTracker benefitClaimTracker = new BenefitClaimTracker()
            .resetDate(UPDATED_RESET_DATE)
            .nextPossibleClaimDate(UPDATED_NEXT_POSSIBLE_CLAIM_DATE)
            .currentLimitValue(UPDATED_CURRENT_LIMIT_VALUE)
            .currentLimitPeriod(UPDATED_CURRENT_LIMIT_PERIOD);
        return benefitClaimTracker;
    }

    @BeforeEach
    public void initTest() {
        benefitClaimTracker = createEntity(em);
    }

    @Test
    @Transactional
    void createBenefitClaimTracker() throws Exception {
        int databaseSizeBeforeCreate = benefitClaimTrackerRepository.findAll().size();
        // Create the BenefitClaimTracker
        restBenefitClaimTrackerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefitClaimTracker))
            )
            .andExpect(status().isCreated());

        // Validate the BenefitClaimTracker in the database
        List<BenefitClaimTracker> benefitClaimTrackerList = benefitClaimTrackerRepository.findAll();
        assertThat(benefitClaimTrackerList).hasSize(databaseSizeBeforeCreate + 1);
        BenefitClaimTracker testBenefitClaimTracker = benefitClaimTrackerList.get(benefitClaimTrackerList.size() - 1);
        assertThat(testBenefitClaimTracker.getResetDate()).isEqualTo(DEFAULT_RESET_DATE);
        assertThat(testBenefitClaimTracker.getNextPossibleClaimDate()).isEqualTo(DEFAULT_NEXT_POSSIBLE_CLAIM_DATE);
        assertThat(testBenefitClaimTracker.getCurrentLimitValue()).isEqualTo(DEFAULT_CURRENT_LIMIT_VALUE);
        assertThat(testBenefitClaimTracker.getCurrentLimitPeriod()).isEqualTo(DEFAULT_CURRENT_LIMIT_PERIOD);
    }

    @Test
    @Transactional
    void createBenefitClaimTrackerWithExistingId() throws Exception {
        // Create the BenefitClaimTracker with an existing ID
        benefitClaimTracker.setId(1L);

        int databaseSizeBeforeCreate = benefitClaimTrackerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBenefitClaimTrackerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefitClaimTracker))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenefitClaimTracker in the database
        List<BenefitClaimTracker> benefitClaimTrackerList = benefitClaimTrackerRepository.findAll();
        assertThat(benefitClaimTrackerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBenefitClaimTrackers() throws Exception {
        // Initialize the database
        benefitClaimTrackerRepository.saveAndFlush(benefitClaimTracker);

        // Get all the benefitClaimTrackerList
        restBenefitClaimTrackerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(benefitClaimTracker.getId().intValue())))
            .andExpect(jsonPath("$.[*].resetDate").value(hasItem(DEFAULT_RESET_DATE.toString())))
            .andExpect(jsonPath("$.[*].nextPossibleClaimDate").value(hasItem(DEFAULT_NEXT_POSSIBLE_CLAIM_DATE.toString())))
            .andExpect(jsonPath("$.[*].currentLimitValue").value(hasItem(DEFAULT_CURRENT_LIMIT_VALUE)))
            .andExpect(jsonPath("$.[*].currentLimitPeriod").value(hasItem(DEFAULT_CURRENT_LIMIT_PERIOD)));
    }

    @Test
    @Transactional
    void getBenefitClaimTracker() throws Exception {
        // Initialize the database
        benefitClaimTrackerRepository.saveAndFlush(benefitClaimTracker);

        // Get the benefitClaimTracker
        restBenefitClaimTrackerMockMvc
            .perform(get(ENTITY_API_URL_ID, benefitClaimTracker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(benefitClaimTracker.getId().intValue()))
            .andExpect(jsonPath("$.resetDate").value(DEFAULT_RESET_DATE.toString()))
            .andExpect(jsonPath("$.nextPossibleClaimDate").value(DEFAULT_NEXT_POSSIBLE_CLAIM_DATE.toString()))
            .andExpect(jsonPath("$.currentLimitValue").value(DEFAULT_CURRENT_LIMIT_VALUE))
            .andExpect(jsonPath("$.currentLimitPeriod").value(DEFAULT_CURRENT_LIMIT_PERIOD));
    }

    @Test
    @Transactional
    void getNonExistingBenefitClaimTracker() throws Exception {
        // Get the benefitClaimTracker
        restBenefitClaimTrackerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBenefitClaimTracker() throws Exception {
        // Initialize the database
        benefitClaimTrackerRepository.saveAndFlush(benefitClaimTracker);

        int databaseSizeBeforeUpdate = benefitClaimTrackerRepository.findAll().size();

        // Update the benefitClaimTracker
        BenefitClaimTracker updatedBenefitClaimTracker = benefitClaimTrackerRepository.findById(benefitClaimTracker.getId()).get();
        // Disconnect from session so that the updates on updatedBenefitClaimTracker are not directly saved in db
        em.detach(updatedBenefitClaimTracker);
        updatedBenefitClaimTracker
            .resetDate(UPDATED_RESET_DATE)
            .nextPossibleClaimDate(UPDATED_NEXT_POSSIBLE_CLAIM_DATE)
            .currentLimitValue(UPDATED_CURRENT_LIMIT_VALUE)
            .currentLimitPeriod(UPDATED_CURRENT_LIMIT_PERIOD);

        restBenefitClaimTrackerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBenefitClaimTracker.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBenefitClaimTracker))
            )
            .andExpect(status().isOk());

        // Validate the BenefitClaimTracker in the database
        List<BenefitClaimTracker> benefitClaimTrackerList = benefitClaimTrackerRepository.findAll();
        assertThat(benefitClaimTrackerList).hasSize(databaseSizeBeforeUpdate);
        BenefitClaimTracker testBenefitClaimTracker = benefitClaimTrackerList.get(benefitClaimTrackerList.size() - 1);
        assertThat(testBenefitClaimTracker.getResetDate()).isEqualTo(UPDATED_RESET_DATE);
        assertThat(testBenefitClaimTracker.getNextPossibleClaimDate()).isEqualTo(UPDATED_NEXT_POSSIBLE_CLAIM_DATE);
        assertThat(testBenefitClaimTracker.getCurrentLimitValue()).isEqualTo(UPDATED_CURRENT_LIMIT_VALUE);
        assertThat(testBenefitClaimTracker.getCurrentLimitPeriod()).isEqualTo(UPDATED_CURRENT_LIMIT_PERIOD);
    }

    @Test
    @Transactional
    void putNonExistingBenefitClaimTracker() throws Exception {
        int databaseSizeBeforeUpdate = benefitClaimTrackerRepository.findAll().size();
        benefitClaimTracker.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenefitClaimTrackerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, benefitClaimTracker.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benefitClaimTracker))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenefitClaimTracker in the database
        List<BenefitClaimTracker> benefitClaimTrackerList = benefitClaimTrackerRepository.findAll();
        assertThat(benefitClaimTrackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBenefitClaimTracker() throws Exception {
        int databaseSizeBeforeUpdate = benefitClaimTrackerRepository.findAll().size();
        benefitClaimTracker.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitClaimTrackerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benefitClaimTracker))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenefitClaimTracker in the database
        List<BenefitClaimTracker> benefitClaimTrackerList = benefitClaimTrackerRepository.findAll();
        assertThat(benefitClaimTrackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBenefitClaimTracker() throws Exception {
        int databaseSizeBeforeUpdate = benefitClaimTrackerRepository.findAll().size();
        benefitClaimTracker.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitClaimTrackerMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefitClaimTracker))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BenefitClaimTracker in the database
        List<BenefitClaimTracker> benefitClaimTrackerList = benefitClaimTrackerRepository.findAll();
        assertThat(benefitClaimTrackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBenefitClaimTrackerWithPatch() throws Exception {
        // Initialize the database
        benefitClaimTrackerRepository.saveAndFlush(benefitClaimTracker);

        int databaseSizeBeforeUpdate = benefitClaimTrackerRepository.findAll().size();

        // Update the benefitClaimTracker using partial update
        BenefitClaimTracker partialUpdatedBenefitClaimTracker = new BenefitClaimTracker();
        partialUpdatedBenefitClaimTracker.setId(benefitClaimTracker.getId());

        partialUpdatedBenefitClaimTracker
            .resetDate(UPDATED_RESET_DATE)
            .nextPossibleClaimDate(UPDATED_NEXT_POSSIBLE_CLAIM_DATE)
            .currentLimitValue(UPDATED_CURRENT_LIMIT_VALUE);

        restBenefitClaimTrackerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBenefitClaimTracker.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBenefitClaimTracker))
            )
            .andExpect(status().isOk());

        // Validate the BenefitClaimTracker in the database
        List<BenefitClaimTracker> benefitClaimTrackerList = benefitClaimTrackerRepository.findAll();
        assertThat(benefitClaimTrackerList).hasSize(databaseSizeBeforeUpdate);
        BenefitClaimTracker testBenefitClaimTracker = benefitClaimTrackerList.get(benefitClaimTrackerList.size() - 1);
        assertThat(testBenefitClaimTracker.getResetDate()).isEqualTo(UPDATED_RESET_DATE);
        assertThat(testBenefitClaimTracker.getNextPossibleClaimDate()).isEqualTo(UPDATED_NEXT_POSSIBLE_CLAIM_DATE);
        assertThat(testBenefitClaimTracker.getCurrentLimitValue()).isEqualTo(UPDATED_CURRENT_LIMIT_VALUE);
        assertThat(testBenefitClaimTracker.getCurrentLimitPeriod()).isEqualTo(DEFAULT_CURRENT_LIMIT_PERIOD);
    }

    @Test
    @Transactional
    void fullUpdateBenefitClaimTrackerWithPatch() throws Exception {
        // Initialize the database
        benefitClaimTrackerRepository.saveAndFlush(benefitClaimTracker);

        int databaseSizeBeforeUpdate = benefitClaimTrackerRepository.findAll().size();

        // Update the benefitClaimTracker using partial update
        BenefitClaimTracker partialUpdatedBenefitClaimTracker = new BenefitClaimTracker();
        partialUpdatedBenefitClaimTracker.setId(benefitClaimTracker.getId());

        partialUpdatedBenefitClaimTracker
            .resetDate(UPDATED_RESET_DATE)
            .nextPossibleClaimDate(UPDATED_NEXT_POSSIBLE_CLAIM_DATE)
            .currentLimitValue(UPDATED_CURRENT_LIMIT_VALUE)
            .currentLimitPeriod(UPDATED_CURRENT_LIMIT_PERIOD);

        restBenefitClaimTrackerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBenefitClaimTracker.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBenefitClaimTracker))
            )
            .andExpect(status().isOk());

        // Validate the BenefitClaimTracker in the database
        List<BenefitClaimTracker> benefitClaimTrackerList = benefitClaimTrackerRepository.findAll();
        assertThat(benefitClaimTrackerList).hasSize(databaseSizeBeforeUpdate);
        BenefitClaimTracker testBenefitClaimTracker = benefitClaimTrackerList.get(benefitClaimTrackerList.size() - 1);
        assertThat(testBenefitClaimTracker.getResetDate()).isEqualTo(UPDATED_RESET_DATE);
        assertThat(testBenefitClaimTracker.getNextPossibleClaimDate()).isEqualTo(UPDATED_NEXT_POSSIBLE_CLAIM_DATE);
        assertThat(testBenefitClaimTracker.getCurrentLimitValue()).isEqualTo(UPDATED_CURRENT_LIMIT_VALUE);
        assertThat(testBenefitClaimTracker.getCurrentLimitPeriod()).isEqualTo(UPDATED_CURRENT_LIMIT_PERIOD);
    }

    @Test
    @Transactional
    void patchNonExistingBenefitClaimTracker() throws Exception {
        int databaseSizeBeforeUpdate = benefitClaimTrackerRepository.findAll().size();
        benefitClaimTracker.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenefitClaimTrackerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, benefitClaimTracker.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(benefitClaimTracker))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenefitClaimTracker in the database
        List<BenefitClaimTracker> benefitClaimTrackerList = benefitClaimTrackerRepository.findAll();
        assertThat(benefitClaimTrackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBenefitClaimTracker() throws Exception {
        int databaseSizeBeforeUpdate = benefitClaimTrackerRepository.findAll().size();
        benefitClaimTracker.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitClaimTrackerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(benefitClaimTracker))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenefitClaimTracker in the database
        List<BenefitClaimTracker> benefitClaimTrackerList = benefitClaimTrackerRepository.findAll();
        assertThat(benefitClaimTrackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBenefitClaimTracker() throws Exception {
        int databaseSizeBeforeUpdate = benefitClaimTrackerRepository.findAll().size();
        benefitClaimTracker.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitClaimTrackerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(benefitClaimTracker))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BenefitClaimTracker in the database
        List<BenefitClaimTracker> benefitClaimTrackerList = benefitClaimTrackerRepository.findAll();
        assertThat(benefitClaimTrackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBenefitClaimTracker() throws Exception {
        // Initialize the database
        benefitClaimTrackerRepository.saveAndFlush(benefitClaimTracker);

        int databaseSizeBeforeDelete = benefitClaimTrackerRepository.findAll().size();

        // Delete the benefitClaimTracker
        restBenefitClaimTrackerMockMvc
            .perform(delete(ENTITY_API_URL_ID, benefitClaimTracker.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BenefitClaimTracker> benefitClaimTrackerList = benefitClaimTrackerRepository.findAll();
        assertThat(benefitClaimTrackerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

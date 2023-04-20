package com.medical_aid_system.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.medical_aid_system.IntegrationTest;
import com.medical_aid_system.domain.BenefitLimit;
import com.medical_aid_system.domain.enumeration.PeriodUnit;
import com.medical_aid_system.repository.BenefitLimitRepository;
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
 * Integration tests for the {@link BenefitLimitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BenefitLimitResourceIT {

    private static final String DEFAULT_LIMIT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_LIMIT_VALUE = "BBBBBBBBBB";

    private static final PeriodUnit DEFAULT_LIMIT_PERIOD_UNIT = PeriodUnit.DAY;
    private static final PeriodUnit UPDATED_LIMIT_PERIOD_UNIT = PeriodUnit.WEEK;

    private static final Integer DEFAULT_LIMIT_PERIOD_VALUE = 1;
    private static final Integer UPDATED_LIMIT_PERIOD_VALUE = 2;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/benefit-limits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BenefitLimitRepository benefitLimitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBenefitLimitMockMvc;

    private BenefitLimit benefitLimit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BenefitLimit createEntity(EntityManager em) {
        BenefitLimit benefitLimit = new BenefitLimit()
            .limitValue(DEFAULT_LIMIT_VALUE)
            .limitPeriodUnit(DEFAULT_LIMIT_PERIOD_UNIT)
            .limitPeriodValue(DEFAULT_LIMIT_PERIOD_VALUE)
            .active(DEFAULT_ACTIVE);
        return benefitLimit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BenefitLimit createUpdatedEntity(EntityManager em) {
        BenefitLimit benefitLimit = new BenefitLimit()
            .limitValue(UPDATED_LIMIT_VALUE)
            .limitPeriodUnit(UPDATED_LIMIT_PERIOD_UNIT)
            .limitPeriodValue(UPDATED_LIMIT_PERIOD_VALUE)
            .active(UPDATED_ACTIVE);
        return benefitLimit;
    }

    @BeforeEach
    public void initTest() {
        benefitLimit = createEntity(em);
    }

    @Test
    @Transactional
    void createBenefitLimit() throws Exception {
        int databaseSizeBeforeCreate = benefitLimitRepository.findAll().size();
        // Create the BenefitLimit
        restBenefitLimitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefitLimit)))
            .andExpect(status().isCreated());

        // Validate the BenefitLimit in the database
        List<BenefitLimit> benefitLimitList = benefitLimitRepository.findAll();
        assertThat(benefitLimitList).hasSize(databaseSizeBeforeCreate + 1);
        BenefitLimit testBenefitLimit = benefitLimitList.get(benefitLimitList.size() - 1);
        assertThat(testBenefitLimit.getLimitValue()).isEqualTo(DEFAULT_LIMIT_VALUE);
        assertThat(testBenefitLimit.getLimitPeriodUnit()).isEqualTo(DEFAULT_LIMIT_PERIOD_UNIT);
        assertThat(testBenefitLimit.getLimitPeriodValue()).isEqualTo(DEFAULT_LIMIT_PERIOD_VALUE);
        assertThat(testBenefitLimit.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    void createBenefitLimitWithExistingId() throws Exception {
        // Create the BenefitLimit with an existing ID
        benefitLimit.setId(1L);

        int databaseSizeBeforeCreate = benefitLimitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBenefitLimitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefitLimit)))
            .andExpect(status().isBadRequest());

        // Validate the BenefitLimit in the database
        List<BenefitLimit> benefitLimitList = benefitLimitRepository.findAll();
        assertThat(benefitLimitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLimitValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefitLimitRepository.findAll().size();
        // set the field null
        benefitLimit.setLimitValue(null);

        // Create the BenefitLimit, which fails.

        restBenefitLimitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefitLimit)))
            .andExpect(status().isBadRequest());

        List<BenefitLimit> benefitLimitList = benefitLimitRepository.findAll();
        assertThat(benefitLimitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLimitPeriodUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefitLimitRepository.findAll().size();
        // set the field null
        benefitLimit.setLimitPeriodUnit(null);

        // Create the BenefitLimit, which fails.

        restBenefitLimitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefitLimit)))
            .andExpect(status().isBadRequest());

        List<BenefitLimit> benefitLimitList = benefitLimitRepository.findAll();
        assertThat(benefitLimitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLimitPeriodValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefitLimitRepository.findAll().size();
        // set the field null
        benefitLimit.setLimitPeriodValue(null);

        // Create the BenefitLimit, which fails.

        restBenefitLimitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefitLimit)))
            .andExpect(status().isBadRequest());

        List<BenefitLimit> benefitLimitList = benefitLimitRepository.findAll();
        assertThat(benefitLimitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefitLimitRepository.findAll().size();
        // set the field null
        benefitLimit.setActive(null);

        // Create the BenefitLimit, which fails.

        restBenefitLimitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefitLimit)))
            .andExpect(status().isBadRequest());

        List<BenefitLimit> benefitLimitList = benefitLimitRepository.findAll();
        assertThat(benefitLimitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBenefitLimits() throws Exception {
        // Initialize the database
        benefitLimitRepository.saveAndFlush(benefitLimit);

        // Get all the benefitLimitList
        restBenefitLimitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(benefitLimit.getId().intValue())))
            .andExpect(jsonPath("$.[*].limitValue").value(hasItem(DEFAULT_LIMIT_VALUE)))
            .andExpect(jsonPath("$.[*].limitPeriodUnit").value(hasItem(DEFAULT_LIMIT_PERIOD_UNIT.toString())))
            .andExpect(jsonPath("$.[*].limitPeriodValue").value(hasItem(DEFAULT_LIMIT_PERIOD_VALUE)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getBenefitLimit() throws Exception {
        // Initialize the database
        benefitLimitRepository.saveAndFlush(benefitLimit);

        // Get the benefitLimit
        restBenefitLimitMockMvc
            .perform(get(ENTITY_API_URL_ID, benefitLimit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(benefitLimit.getId().intValue()))
            .andExpect(jsonPath("$.limitValue").value(DEFAULT_LIMIT_VALUE))
            .andExpect(jsonPath("$.limitPeriodUnit").value(DEFAULT_LIMIT_PERIOD_UNIT.toString()))
            .andExpect(jsonPath("$.limitPeriodValue").value(DEFAULT_LIMIT_PERIOD_VALUE))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingBenefitLimit() throws Exception {
        // Get the benefitLimit
        restBenefitLimitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBenefitLimit() throws Exception {
        // Initialize the database
        benefitLimitRepository.saveAndFlush(benefitLimit);

        int databaseSizeBeforeUpdate = benefitLimitRepository.findAll().size();

        // Update the benefitLimit
        BenefitLimit updatedBenefitLimit = benefitLimitRepository.findById(benefitLimit.getId()).get();
        // Disconnect from session so that the updates on updatedBenefitLimit are not directly saved in db
        em.detach(updatedBenefitLimit);
        updatedBenefitLimit
            .limitValue(UPDATED_LIMIT_VALUE)
            .limitPeriodUnit(UPDATED_LIMIT_PERIOD_UNIT)
            .limitPeriodValue(UPDATED_LIMIT_PERIOD_VALUE)
            .active(UPDATED_ACTIVE);

        restBenefitLimitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBenefitLimit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBenefitLimit))
            )
            .andExpect(status().isOk());

        // Validate the BenefitLimit in the database
        List<BenefitLimit> benefitLimitList = benefitLimitRepository.findAll();
        assertThat(benefitLimitList).hasSize(databaseSizeBeforeUpdate);
        BenefitLimit testBenefitLimit = benefitLimitList.get(benefitLimitList.size() - 1);
        assertThat(testBenefitLimit.getLimitValue()).isEqualTo(UPDATED_LIMIT_VALUE);
        assertThat(testBenefitLimit.getLimitPeriodUnit()).isEqualTo(UPDATED_LIMIT_PERIOD_UNIT);
        assertThat(testBenefitLimit.getLimitPeriodValue()).isEqualTo(UPDATED_LIMIT_PERIOD_VALUE);
        assertThat(testBenefitLimit.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingBenefitLimit() throws Exception {
        int databaseSizeBeforeUpdate = benefitLimitRepository.findAll().size();
        benefitLimit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenefitLimitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, benefitLimit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benefitLimit))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenefitLimit in the database
        List<BenefitLimit> benefitLimitList = benefitLimitRepository.findAll();
        assertThat(benefitLimitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBenefitLimit() throws Exception {
        int databaseSizeBeforeUpdate = benefitLimitRepository.findAll().size();
        benefitLimit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitLimitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benefitLimit))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenefitLimit in the database
        List<BenefitLimit> benefitLimitList = benefitLimitRepository.findAll();
        assertThat(benefitLimitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBenefitLimit() throws Exception {
        int databaseSizeBeforeUpdate = benefitLimitRepository.findAll().size();
        benefitLimit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitLimitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefitLimit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BenefitLimit in the database
        List<BenefitLimit> benefitLimitList = benefitLimitRepository.findAll();
        assertThat(benefitLimitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBenefitLimitWithPatch() throws Exception {
        // Initialize the database
        benefitLimitRepository.saveAndFlush(benefitLimit);

        int databaseSizeBeforeUpdate = benefitLimitRepository.findAll().size();

        // Update the benefitLimit using partial update
        BenefitLimit partialUpdatedBenefitLimit = new BenefitLimit();
        partialUpdatedBenefitLimit.setId(benefitLimit.getId());

        partialUpdatedBenefitLimit
            .limitValue(UPDATED_LIMIT_VALUE)
            .limitPeriodUnit(UPDATED_LIMIT_PERIOD_UNIT)
            .limitPeriodValue(UPDATED_LIMIT_PERIOD_VALUE);

        restBenefitLimitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBenefitLimit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBenefitLimit))
            )
            .andExpect(status().isOk());

        // Validate the BenefitLimit in the database
        List<BenefitLimit> benefitLimitList = benefitLimitRepository.findAll();
        assertThat(benefitLimitList).hasSize(databaseSizeBeforeUpdate);
        BenefitLimit testBenefitLimit = benefitLimitList.get(benefitLimitList.size() - 1);
        assertThat(testBenefitLimit.getLimitValue()).isEqualTo(UPDATED_LIMIT_VALUE);
        assertThat(testBenefitLimit.getLimitPeriodUnit()).isEqualTo(UPDATED_LIMIT_PERIOD_UNIT);
        assertThat(testBenefitLimit.getLimitPeriodValue()).isEqualTo(UPDATED_LIMIT_PERIOD_VALUE);
        assertThat(testBenefitLimit.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateBenefitLimitWithPatch() throws Exception {
        // Initialize the database
        benefitLimitRepository.saveAndFlush(benefitLimit);

        int databaseSizeBeforeUpdate = benefitLimitRepository.findAll().size();

        // Update the benefitLimit using partial update
        BenefitLimit partialUpdatedBenefitLimit = new BenefitLimit();
        partialUpdatedBenefitLimit.setId(benefitLimit.getId());

        partialUpdatedBenefitLimit
            .limitValue(UPDATED_LIMIT_VALUE)
            .limitPeriodUnit(UPDATED_LIMIT_PERIOD_UNIT)
            .limitPeriodValue(UPDATED_LIMIT_PERIOD_VALUE)
            .active(UPDATED_ACTIVE);

        restBenefitLimitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBenefitLimit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBenefitLimit))
            )
            .andExpect(status().isOk());

        // Validate the BenefitLimit in the database
        List<BenefitLimit> benefitLimitList = benefitLimitRepository.findAll();
        assertThat(benefitLimitList).hasSize(databaseSizeBeforeUpdate);
        BenefitLimit testBenefitLimit = benefitLimitList.get(benefitLimitList.size() - 1);
        assertThat(testBenefitLimit.getLimitValue()).isEqualTo(UPDATED_LIMIT_VALUE);
        assertThat(testBenefitLimit.getLimitPeriodUnit()).isEqualTo(UPDATED_LIMIT_PERIOD_UNIT);
        assertThat(testBenefitLimit.getLimitPeriodValue()).isEqualTo(UPDATED_LIMIT_PERIOD_VALUE);
        assertThat(testBenefitLimit.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingBenefitLimit() throws Exception {
        int databaseSizeBeforeUpdate = benefitLimitRepository.findAll().size();
        benefitLimit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenefitLimitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, benefitLimit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(benefitLimit))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenefitLimit in the database
        List<BenefitLimit> benefitLimitList = benefitLimitRepository.findAll();
        assertThat(benefitLimitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBenefitLimit() throws Exception {
        int databaseSizeBeforeUpdate = benefitLimitRepository.findAll().size();
        benefitLimit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitLimitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(benefitLimit))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenefitLimit in the database
        List<BenefitLimit> benefitLimitList = benefitLimitRepository.findAll();
        assertThat(benefitLimitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBenefitLimit() throws Exception {
        int databaseSizeBeforeUpdate = benefitLimitRepository.findAll().size();
        benefitLimit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitLimitMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(benefitLimit))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BenefitLimit in the database
        List<BenefitLimit> benefitLimitList = benefitLimitRepository.findAll();
        assertThat(benefitLimitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBenefitLimit() throws Exception {
        // Initialize the database
        benefitLimitRepository.saveAndFlush(benefitLimit);

        int databaseSizeBeforeDelete = benefitLimitRepository.findAll().size();

        // Delete the benefitLimit
        restBenefitLimitMockMvc
            .perform(delete(ENTITY_API_URL_ID, benefitLimit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BenefitLimit> benefitLimitList = benefitLimitRepository.findAll();
        assertThat(benefitLimitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

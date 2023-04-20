package com.medical_aid_system.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.medical_aid_system.IntegrationTest;
import com.medical_aid_system.domain.RiskProfile;
import com.medical_aid_system.repository.RiskProfileRepository;
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
 * Integration tests for the {@link RiskProfileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RiskProfileResourceIT {

    private static final Double DEFAULT_TOTAL_RISK_SCORE = 1D;
    private static final Double UPDATED_TOTAL_RISK_SCORE = 2D;

    private static final String DEFAULT_LIFE_STYLE = "AAAAAAAAAA";
    private static final String UPDATED_LIFE_STYLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/risk-profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RiskProfileRepository riskProfileRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRiskProfileMockMvc;

    private RiskProfile riskProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RiskProfile createEntity(EntityManager em) {
        RiskProfile riskProfile = new RiskProfile().totalRiskScore(DEFAULT_TOTAL_RISK_SCORE).lifeStyle(DEFAULT_LIFE_STYLE);
        return riskProfile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RiskProfile createUpdatedEntity(EntityManager em) {
        RiskProfile riskProfile = new RiskProfile().totalRiskScore(UPDATED_TOTAL_RISK_SCORE).lifeStyle(UPDATED_LIFE_STYLE);
        return riskProfile;
    }

    @BeforeEach
    public void initTest() {
        riskProfile = createEntity(em);
    }

    @Test
    @Transactional
    void createRiskProfile() throws Exception {
        int databaseSizeBeforeCreate = riskProfileRepository.findAll().size();
        // Create the RiskProfile
        restRiskProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(riskProfile)))
            .andExpect(status().isCreated());

        // Validate the RiskProfile in the database
        List<RiskProfile> riskProfileList = riskProfileRepository.findAll();
        assertThat(riskProfileList).hasSize(databaseSizeBeforeCreate + 1);
        RiskProfile testRiskProfile = riskProfileList.get(riskProfileList.size() - 1);
        assertThat(testRiskProfile.getTotalRiskScore()).isEqualTo(DEFAULT_TOTAL_RISK_SCORE);
        assertThat(testRiskProfile.getLifeStyle()).isEqualTo(DEFAULT_LIFE_STYLE);
    }

    @Test
    @Transactional
    void createRiskProfileWithExistingId() throws Exception {
        // Create the RiskProfile with an existing ID
        riskProfile.setId(1L);

        int databaseSizeBeforeCreate = riskProfileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRiskProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(riskProfile)))
            .andExpect(status().isBadRequest());

        // Validate the RiskProfile in the database
        List<RiskProfile> riskProfileList = riskProfileRepository.findAll();
        assertThat(riskProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLifeStyleIsRequired() throws Exception {
        int databaseSizeBeforeTest = riskProfileRepository.findAll().size();
        // set the field null
        riskProfile.setLifeStyle(null);

        // Create the RiskProfile, which fails.

        restRiskProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(riskProfile)))
            .andExpect(status().isBadRequest());

        List<RiskProfile> riskProfileList = riskProfileRepository.findAll();
        assertThat(riskProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRiskProfiles() throws Exception {
        // Initialize the database
        riskProfileRepository.saveAndFlush(riskProfile);

        // Get all the riskProfileList
        restRiskProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(riskProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalRiskScore").value(hasItem(DEFAULT_TOTAL_RISK_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].lifeStyle").value(hasItem(DEFAULT_LIFE_STYLE)));
    }

    @Test
    @Transactional
    void getRiskProfile() throws Exception {
        // Initialize the database
        riskProfileRepository.saveAndFlush(riskProfile);

        // Get the riskProfile
        restRiskProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, riskProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(riskProfile.getId().intValue()))
            .andExpect(jsonPath("$.totalRiskScore").value(DEFAULT_TOTAL_RISK_SCORE.doubleValue()))
            .andExpect(jsonPath("$.lifeStyle").value(DEFAULT_LIFE_STYLE));
    }

    @Test
    @Transactional
    void getNonExistingRiskProfile() throws Exception {
        // Get the riskProfile
        restRiskProfileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRiskProfile() throws Exception {
        // Initialize the database
        riskProfileRepository.saveAndFlush(riskProfile);

        int databaseSizeBeforeUpdate = riskProfileRepository.findAll().size();

        // Update the riskProfile
        RiskProfile updatedRiskProfile = riskProfileRepository.findById(riskProfile.getId()).get();
        // Disconnect from session so that the updates on updatedRiskProfile are not directly saved in db
        em.detach(updatedRiskProfile);
        updatedRiskProfile.totalRiskScore(UPDATED_TOTAL_RISK_SCORE).lifeStyle(UPDATED_LIFE_STYLE);

        restRiskProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRiskProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRiskProfile))
            )
            .andExpect(status().isOk());

        // Validate the RiskProfile in the database
        List<RiskProfile> riskProfileList = riskProfileRepository.findAll();
        assertThat(riskProfileList).hasSize(databaseSizeBeforeUpdate);
        RiskProfile testRiskProfile = riskProfileList.get(riskProfileList.size() - 1);
        assertThat(testRiskProfile.getTotalRiskScore()).isEqualTo(UPDATED_TOTAL_RISK_SCORE);
        assertThat(testRiskProfile.getLifeStyle()).isEqualTo(UPDATED_LIFE_STYLE);
    }

    @Test
    @Transactional
    void putNonExistingRiskProfile() throws Exception {
        int databaseSizeBeforeUpdate = riskProfileRepository.findAll().size();
        riskProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRiskProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, riskProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(riskProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the RiskProfile in the database
        List<RiskProfile> riskProfileList = riskProfileRepository.findAll();
        assertThat(riskProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRiskProfile() throws Exception {
        int databaseSizeBeforeUpdate = riskProfileRepository.findAll().size();
        riskProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiskProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(riskProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the RiskProfile in the database
        List<RiskProfile> riskProfileList = riskProfileRepository.findAll();
        assertThat(riskProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRiskProfile() throws Exception {
        int databaseSizeBeforeUpdate = riskProfileRepository.findAll().size();
        riskProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiskProfileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(riskProfile)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RiskProfile in the database
        List<RiskProfile> riskProfileList = riskProfileRepository.findAll();
        assertThat(riskProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRiskProfileWithPatch() throws Exception {
        // Initialize the database
        riskProfileRepository.saveAndFlush(riskProfile);

        int databaseSizeBeforeUpdate = riskProfileRepository.findAll().size();

        // Update the riskProfile using partial update
        RiskProfile partialUpdatedRiskProfile = new RiskProfile();
        partialUpdatedRiskProfile.setId(riskProfile.getId());

        partialUpdatedRiskProfile.lifeStyle(UPDATED_LIFE_STYLE);

        restRiskProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRiskProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRiskProfile))
            )
            .andExpect(status().isOk());

        // Validate the RiskProfile in the database
        List<RiskProfile> riskProfileList = riskProfileRepository.findAll();
        assertThat(riskProfileList).hasSize(databaseSizeBeforeUpdate);
        RiskProfile testRiskProfile = riskProfileList.get(riskProfileList.size() - 1);
        assertThat(testRiskProfile.getTotalRiskScore()).isEqualTo(DEFAULT_TOTAL_RISK_SCORE);
        assertThat(testRiskProfile.getLifeStyle()).isEqualTo(UPDATED_LIFE_STYLE);
    }

    @Test
    @Transactional
    void fullUpdateRiskProfileWithPatch() throws Exception {
        // Initialize the database
        riskProfileRepository.saveAndFlush(riskProfile);

        int databaseSizeBeforeUpdate = riskProfileRepository.findAll().size();

        // Update the riskProfile using partial update
        RiskProfile partialUpdatedRiskProfile = new RiskProfile();
        partialUpdatedRiskProfile.setId(riskProfile.getId());

        partialUpdatedRiskProfile.totalRiskScore(UPDATED_TOTAL_RISK_SCORE).lifeStyle(UPDATED_LIFE_STYLE);

        restRiskProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRiskProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRiskProfile))
            )
            .andExpect(status().isOk());

        // Validate the RiskProfile in the database
        List<RiskProfile> riskProfileList = riskProfileRepository.findAll();
        assertThat(riskProfileList).hasSize(databaseSizeBeforeUpdate);
        RiskProfile testRiskProfile = riskProfileList.get(riskProfileList.size() - 1);
        assertThat(testRiskProfile.getTotalRiskScore()).isEqualTo(UPDATED_TOTAL_RISK_SCORE);
        assertThat(testRiskProfile.getLifeStyle()).isEqualTo(UPDATED_LIFE_STYLE);
    }

    @Test
    @Transactional
    void patchNonExistingRiskProfile() throws Exception {
        int databaseSizeBeforeUpdate = riskProfileRepository.findAll().size();
        riskProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRiskProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, riskProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(riskProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the RiskProfile in the database
        List<RiskProfile> riskProfileList = riskProfileRepository.findAll();
        assertThat(riskProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRiskProfile() throws Exception {
        int databaseSizeBeforeUpdate = riskProfileRepository.findAll().size();
        riskProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiskProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(riskProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the RiskProfile in the database
        List<RiskProfile> riskProfileList = riskProfileRepository.findAll();
        assertThat(riskProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRiskProfile() throws Exception {
        int databaseSizeBeforeUpdate = riskProfileRepository.findAll().size();
        riskProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiskProfileMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(riskProfile))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RiskProfile in the database
        List<RiskProfile> riskProfileList = riskProfileRepository.findAll();
        assertThat(riskProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRiskProfile() throws Exception {
        // Initialize the database
        riskProfileRepository.saveAndFlush(riskProfile);

        int databaseSizeBeforeDelete = riskProfileRepository.findAll().size();

        // Delete the riskProfile
        restRiskProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, riskProfile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RiskProfile> riskProfileList = riskProfileRepository.findAll();
        assertThat(riskProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

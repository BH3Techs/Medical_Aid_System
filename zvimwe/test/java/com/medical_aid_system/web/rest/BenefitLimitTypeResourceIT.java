package com.medical_aid_system.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.medical_aid_system.IntegrationTest;
import com.medical_aid_system.domain.BenefitLimitType;
import com.medical_aid_system.repository.BenefitLimitTypeRepository;
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
 * Integration tests for the {@link BenefitLimitTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BenefitLimitTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/benefit-limit-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BenefitLimitTypeRepository benefitLimitTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBenefitLimitTypeMockMvc;

    private BenefitLimitType benefitLimitType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BenefitLimitType createEntity(EntityManager em) {
        BenefitLimitType benefitLimitType = new BenefitLimitType().name(DEFAULT_NAME).active(DEFAULT_ACTIVE);
        return benefitLimitType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BenefitLimitType createUpdatedEntity(EntityManager em) {
        BenefitLimitType benefitLimitType = new BenefitLimitType().name(UPDATED_NAME).active(UPDATED_ACTIVE);
        return benefitLimitType;
    }

    @BeforeEach
    public void initTest() {
        benefitLimitType = createEntity(em);
    }

    @Test
    @Transactional
    void createBenefitLimitType() throws Exception {
        int databaseSizeBeforeCreate = benefitLimitTypeRepository.findAll().size();
        // Create the BenefitLimitType
        restBenefitLimitTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefitLimitType))
            )
            .andExpect(status().isCreated());

        // Validate the BenefitLimitType in the database
        List<BenefitLimitType> benefitLimitTypeList = benefitLimitTypeRepository.findAll();
        assertThat(benefitLimitTypeList).hasSize(databaseSizeBeforeCreate + 1);
        BenefitLimitType testBenefitLimitType = benefitLimitTypeList.get(benefitLimitTypeList.size() - 1);
        assertThat(testBenefitLimitType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBenefitLimitType.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    void createBenefitLimitTypeWithExistingId() throws Exception {
        // Create the BenefitLimitType with an existing ID
        benefitLimitType.setId(1L);

        int databaseSizeBeforeCreate = benefitLimitTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBenefitLimitTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefitLimitType))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenefitLimitType in the database
        List<BenefitLimitType> benefitLimitTypeList = benefitLimitTypeRepository.findAll();
        assertThat(benefitLimitTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefitLimitTypeRepository.findAll().size();
        // set the field null
        benefitLimitType.setName(null);

        // Create the BenefitLimitType, which fails.

        restBenefitLimitTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefitLimitType))
            )
            .andExpect(status().isBadRequest());

        List<BenefitLimitType> benefitLimitTypeList = benefitLimitTypeRepository.findAll();
        assertThat(benefitLimitTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefitLimitTypeRepository.findAll().size();
        // set the field null
        benefitLimitType.setActive(null);

        // Create the BenefitLimitType, which fails.

        restBenefitLimitTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefitLimitType))
            )
            .andExpect(status().isBadRequest());

        List<BenefitLimitType> benefitLimitTypeList = benefitLimitTypeRepository.findAll();
        assertThat(benefitLimitTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBenefitLimitTypes() throws Exception {
        // Initialize the database
        benefitLimitTypeRepository.saveAndFlush(benefitLimitType);

        // Get all the benefitLimitTypeList
        restBenefitLimitTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(benefitLimitType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getBenefitLimitType() throws Exception {
        // Initialize the database
        benefitLimitTypeRepository.saveAndFlush(benefitLimitType);

        // Get the benefitLimitType
        restBenefitLimitTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, benefitLimitType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(benefitLimitType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingBenefitLimitType() throws Exception {
        // Get the benefitLimitType
        restBenefitLimitTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBenefitLimitType() throws Exception {
        // Initialize the database
        benefitLimitTypeRepository.saveAndFlush(benefitLimitType);

        int databaseSizeBeforeUpdate = benefitLimitTypeRepository.findAll().size();

        // Update the benefitLimitType
        BenefitLimitType updatedBenefitLimitType = benefitLimitTypeRepository.findById(benefitLimitType.getId()).get();
        // Disconnect from session so that the updates on updatedBenefitLimitType are not directly saved in db
        em.detach(updatedBenefitLimitType);
        updatedBenefitLimitType.name(UPDATED_NAME).active(UPDATED_ACTIVE);

        restBenefitLimitTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBenefitLimitType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBenefitLimitType))
            )
            .andExpect(status().isOk());

        // Validate the BenefitLimitType in the database
        List<BenefitLimitType> benefitLimitTypeList = benefitLimitTypeRepository.findAll();
        assertThat(benefitLimitTypeList).hasSize(databaseSizeBeforeUpdate);
        BenefitLimitType testBenefitLimitType = benefitLimitTypeList.get(benefitLimitTypeList.size() - 1);
        assertThat(testBenefitLimitType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBenefitLimitType.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingBenefitLimitType() throws Exception {
        int databaseSizeBeforeUpdate = benefitLimitTypeRepository.findAll().size();
        benefitLimitType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenefitLimitTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, benefitLimitType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benefitLimitType))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenefitLimitType in the database
        List<BenefitLimitType> benefitLimitTypeList = benefitLimitTypeRepository.findAll();
        assertThat(benefitLimitTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBenefitLimitType() throws Exception {
        int databaseSizeBeforeUpdate = benefitLimitTypeRepository.findAll().size();
        benefitLimitType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitLimitTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benefitLimitType))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenefitLimitType in the database
        List<BenefitLimitType> benefitLimitTypeList = benefitLimitTypeRepository.findAll();
        assertThat(benefitLimitTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBenefitLimitType() throws Exception {
        int databaseSizeBeforeUpdate = benefitLimitTypeRepository.findAll().size();
        benefitLimitType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitLimitTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefitLimitType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BenefitLimitType in the database
        List<BenefitLimitType> benefitLimitTypeList = benefitLimitTypeRepository.findAll();
        assertThat(benefitLimitTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBenefitLimitTypeWithPatch() throws Exception {
        // Initialize the database
        benefitLimitTypeRepository.saveAndFlush(benefitLimitType);

        int databaseSizeBeforeUpdate = benefitLimitTypeRepository.findAll().size();

        // Update the benefitLimitType using partial update
        BenefitLimitType partialUpdatedBenefitLimitType = new BenefitLimitType();
        partialUpdatedBenefitLimitType.setId(benefitLimitType.getId());

        partialUpdatedBenefitLimitType.active(UPDATED_ACTIVE);

        restBenefitLimitTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBenefitLimitType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBenefitLimitType))
            )
            .andExpect(status().isOk());

        // Validate the BenefitLimitType in the database
        List<BenefitLimitType> benefitLimitTypeList = benefitLimitTypeRepository.findAll();
        assertThat(benefitLimitTypeList).hasSize(databaseSizeBeforeUpdate);
        BenefitLimitType testBenefitLimitType = benefitLimitTypeList.get(benefitLimitTypeList.size() - 1);
        assertThat(testBenefitLimitType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBenefitLimitType.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateBenefitLimitTypeWithPatch() throws Exception {
        // Initialize the database
        benefitLimitTypeRepository.saveAndFlush(benefitLimitType);

        int databaseSizeBeforeUpdate = benefitLimitTypeRepository.findAll().size();

        // Update the benefitLimitType using partial update
        BenefitLimitType partialUpdatedBenefitLimitType = new BenefitLimitType();
        partialUpdatedBenefitLimitType.setId(benefitLimitType.getId());

        partialUpdatedBenefitLimitType.name(UPDATED_NAME).active(UPDATED_ACTIVE);

        restBenefitLimitTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBenefitLimitType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBenefitLimitType))
            )
            .andExpect(status().isOk());

        // Validate the BenefitLimitType in the database
        List<BenefitLimitType> benefitLimitTypeList = benefitLimitTypeRepository.findAll();
        assertThat(benefitLimitTypeList).hasSize(databaseSizeBeforeUpdate);
        BenefitLimitType testBenefitLimitType = benefitLimitTypeList.get(benefitLimitTypeList.size() - 1);
        assertThat(testBenefitLimitType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBenefitLimitType.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingBenefitLimitType() throws Exception {
        int databaseSizeBeforeUpdate = benefitLimitTypeRepository.findAll().size();
        benefitLimitType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenefitLimitTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, benefitLimitType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(benefitLimitType))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenefitLimitType in the database
        List<BenefitLimitType> benefitLimitTypeList = benefitLimitTypeRepository.findAll();
        assertThat(benefitLimitTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBenefitLimitType() throws Exception {
        int databaseSizeBeforeUpdate = benefitLimitTypeRepository.findAll().size();
        benefitLimitType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitLimitTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(benefitLimitType))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenefitLimitType in the database
        List<BenefitLimitType> benefitLimitTypeList = benefitLimitTypeRepository.findAll();
        assertThat(benefitLimitTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBenefitLimitType() throws Exception {
        int databaseSizeBeforeUpdate = benefitLimitTypeRepository.findAll().size();
        benefitLimitType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitLimitTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(benefitLimitType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BenefitLimitType in the database
        List<BenefitLimitType> benefitLimitTypeList = benefitLimitTypeRepository.findAll();
        assertThat(benefitLimitTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBenefitLimitType() throws Exception {
        // Initialize the database
        benefitLimitTypeRepository.saveAndFlush(benefitLimitType);

        int databaseSizeBeforeDelete = benefitLimitTypeRepository.findAll().size();

        // Delete the benefitLimitType
        restBenefitLimitTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, benefitLimitType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BenefitLimitType> benefitLimitTypeList = benefitLimitTypeRepository.findAll();
        assertThat(benefitLimitTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

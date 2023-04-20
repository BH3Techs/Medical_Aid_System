package com.medical_aid_system.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.medical_aid_system.IntegrationTest;
import com.medical_aid_system.domain.BenefitType;
import com.medical_aid_system.repository.BenefitTypeRepository;
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
 * Integration tests for the {@link BenefitTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BenefitTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/benefit-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BenefitTypeRepository benefitTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBenefitTypeMockMvc;

    private BenefitType benefitType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BenefitType createEntity(EntityManager em) {
        BenefitType benefitType = new BenefitType().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION).active(DEFAULT_ACTIVE);
        return benefitType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BenefitType createUpdatedEntity(EntityManager em) {
        BenefitType benefitType = new BenefitType().name(UPDATED_NAME).description(UPDATED_DESCRIPTION).active(UPDATED_ACTIVE);
        return benefitType;
    }

    @BeforeEach
    public void initTest() {
        benefitType = createEntity(em);
    }

    @Test
    @Transactional
    void createBenefitType() throws Exception {
        int databaseSizeBeforeCreate = benefitTypeRepository.findAll().size();
        // Create the BenefitType
        restBenefitTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefitType)))
            .andExpect(status().isCreated());

        // Validate the BenefitType in the database
        List<BenefitType> benefitTypeList = benefitTypeRepository.findAll();
        assertThat(benefitTypeList).hasSize(databaseSizeBeforeCreate + 1);
        BenefitType testBenefitType = benefitTypeList.get(benefitTypeList.size() - 1);
        assertThat(testBenefitType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBenefitType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBenefitType.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    void createBenefitTypeWithExistingId() throws Exception {
        // Create the BenefitType with an existing ID
        benefitType.setId(1L);

        int databaseSizeBeforeCreate = benefitTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBenefitTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefitType)))
            .andExpect(status().isBadRequest());

        // Validate the BenefitType in the database
        List<BenefitType> benefitTypeList = benefitTypeRepository.findAll();
        assertThat(benefitTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefitTypeRepository.findAll().size();
        // set the field null
        benefitType.setName(null);

        // Create the BenefitType, which fails.

        restBenefitTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefitType)))
            .andExpect(status().isBadRequest());

        List<BenefitType> benefitTypeList = benefitTypeRepository.findAll();
        assertThat(benefitTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefitTypeRepository.findAll().size();
        // set the field null
        benefitType.setDescription(null);

        // Create the BenefitType, which fails.

        restBenefitTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefitType)))
            .andExpect(status().isBadRequest());

        List<BenefitType> benefitTypeList = benefitTypeRepository.findAll();
        assertThat(benefitTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefitTypeRepository.findAll().size();
        // set the field null
        benefitType.setActive(null);

        // Create the BenefitType, which fails.

        restBenefitTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefitType)))
            .andExpect(status().isBadRequest());

        List<BenefitType> benefitTypeList = benefitTypeRepository.findAll();
        assertThat(benefitTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBenefitTypes() throws Exception {
        // Initialize the database
        benefitTypeRepository.saveAndFlush(benefitType);

        // Get all the benefitTypeList
        restBenefitTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(benefitType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getBenefitType() throws Exception {
        // Initialize the database
        benefitTypeRepository.saveAndFlush(benefitType);

        // Get the benefitType
        restBenefitTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, benefitType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(benefitType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingBenefitType() throws Exception {
        // Get the benefitType
        restBenefitTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBenefitType() throws Exception {
        // Initialize the database
        benefitTypeRepository.saveAndFlush(benefitType);

        int databaseSizeBeforeUpdate = benefitTypeRepository.findAll().size();

        // Update the benefitType
        BenefitType updatedBenefitType = benefitTypeRepository.findById(benefitType.getId()).get();
        // Disconnect from session so that the updates on updatedBenefitType are not directly saved in db
        em.detach(updatedBenefitType);
        updatedBenefitType.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).active(UPDATED_ACTIVE);

        restBenefitTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBenefitType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBenefitType))
            )
            .andExpect(status().isOk());

        // Validate the BenefitType in the database
        List<BenefitType> benefitTypeList = benefitTypeRepository.findAll();
        assertThat(benefitTypeList).hasSize(databaseSizeBeforeUpdate);
        BenefitType testBenefitType = benefitTypeList.get(benefitTypeList.size() - 1);
        assertThat(testBenefitType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBenefitType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBenefitType.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingBenefitType() throws Exception {
        int databaseSizeBeforeUpdate = benefitTypeRepository.findAll().size();
        benefitType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenefitTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, benefitType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benefitType))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenefitType in the database
        List<BenefitType> benefitTypeList = benefitTypeRepository.findAll();
        assertThat(benefitTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBenefitType() throws Exception {
        int databaseSizeBeforeUpdate = benefitTypeRepository.findAll().size();
        benefitType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benefitType))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenefitType in the database
        List<BenefitType> benefitTypeList = benefitTypeRepository.findAll();
        assertThat(benefitTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBenefitType() throws Exception {
        int databaseSizeBeforeUpdate = benefitTypeRepository.findAll().size();
        benefitType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefitType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BenefitType in the database
        List<BenefitType> benefitTypeList = benefitTypeRepository.findAll();
        assertThat(benefitTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBenefitTypeWithPatch() throws Exception {
        // Initialize the database
        benefitTypeRepository.saveAndFlush(benefitType);

        int databaseSizeBeforeUpdate = benefitTypeRepository.findAll().size();

        // Update the benefitType using partial update
        BenefitType partialUpdatedBenefitType = new BenefitType();
        partialUpdatedBenefitType.setId(benefitType.getId());

        partialUpdatedBenefitType.active(UPDATED_ACTIVE);

        restBenefitTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBenefitType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBenefitType))
            )
            .andExpect(status().isOk());

        // Validate the BenefitType in the database
        List<BenefitType> benefitTypeList = benefitTypeRepository.findAll();
        assertThat(benefitTypeList).hasSize(databaseSizeBeforeUpdate);
        BenefitType testBenefitType = benefitTypeList.get(benefitTypeList.size() - 1);
        assertThat(testBenefitType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBenefitType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBenefitType.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateBenefitTypeWithPatch() throws Exception {
        // Initialize the database
        benefitTypeRepository.saveAndFlush(benefitType);

        int databaseSizeBeforeUpdate = benefitTypeRepository.findAll().size();

        // Update the benefitType using partial update
        BenefitType partialUpdatedBenefitType = new BenefitType();
        partialUpdatedBenefitType.setId(benefitType.getId());

        partialUpdatedBenefitType.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).active(UPDATED_ACTIVE);

        restBenefitTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBenefitType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBenefitType))
            )
            .andExpect(status().isOk());

        // Validate the BenefitType in the database
        List<BenefitType> benefitTypeList = benefitTypeRepository.findAll();
        assertThat(benefitTypeList).hasSize(databaseSizeBeforeUpdate);
        BenefitType testBenefitType = benefitTypeList.get(benefitTypeList.size() - 1);
        assertThat(testBenefitType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBenefitType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBenefitType.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingBenefitType() throws Exception {
        int databaseSizeBeforeUpdate = benefitTypeRepository.findAll().size();
        benefitType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenefitTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, benefitType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(benefitType))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenefitType in the database
        List<BenefitType> benefitTypeList = benefitTypeRepository.findAll();
        assertThat(benefitTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBenefitType() throws Exception {
        int databaseSizeBeforeUpdate = benefitTypeRepository.findAll().size();
        benefitType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(benefitType))
            )
            .andExpect(status().isBadRequest());

        // Validate the BenefitType in the database
        List<BenefitType> benefitTypeList = benefitTypeRepository.findAll();
        assertThat(benefitTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBenefitType() throws Exception {
        int databaseSizeBeforeUpdate = benefitTypeRepository.findAll().size();
        benefitType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(benefitType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BenefitType in the database
        List<BenefitType> benefitTypeList = benefitTypeRepository.findAll();
        assertThat(benefitTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBenefitType() throws Exception {
        // Initialize the database
        benefitTypeRepository.saveAndFlush(benefitType);

        int databaseSizeBeforeDelete = benefitTypeRepository.findAll().size();

        // Delete the benefitType
        restBenefitTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, benefitType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BenefitType> benefitTypeList = benefitTypeRepository.findAll();
        assertThat(benefitTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

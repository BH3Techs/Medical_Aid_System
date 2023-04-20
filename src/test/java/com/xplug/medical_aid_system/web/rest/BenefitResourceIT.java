package com.xplug.medical_aid_system.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.xplug.medical_aid_system.IntegrationTest;
import com.xplug.medical_aid_system.domain.Benefit;
import com.xplug.medical_aid_system.repository.BenefitRepository;
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
 * Integration tests for the {@link BenefitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BenefitResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_BENEFIT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BENEFIT_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/benefits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BenefitRepository benefitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBenefitMockMvc;

    private Benefit benefit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Benefit createEntity(EntityManager em) {
        Benefit benefit = new Benefit()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .benefitCode(DEFAULT_BENEFIT_CODE)
            .active(DEFAULT_ACTIVE);
        return benefit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Benefit createUpdatedEntity(EntityManager em) {
        Benefit benefit = new Benefit()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .benefitCode(UPDATED_BENEFIT_CODE)
            .active(UPDATED_ACTIVE);
        return benefit;
    }

    @BeforeEach
    public void initTest() {
        benefit = createEntity(em);
    }

    @Test
    @Transactional
    void createBenefit() throws Exception {
        int databaseSizeBeforeCreate = benefitRepository.findAll().size();
        // Create the Benefit
        restBenefitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefit)))
            .andExpect(status().isCreated());

        // Validate the Benefit in the database
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeCreate + 1);
        Benefit testBenefit = benefitList.get(benefitList.size() - 1);
        assertThat(testBenefit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBenefit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBenefit.getBenefitCode()).isEqualTo(DEFAULT_BENEFIT_CODE);
        assertThat(testBenefit.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    void createBenefitWithExistingId() throws Exception {
        // Create the Benefit with an existing ID
        benefit.setId(1L);

        int databaseSizeBeforeCreate = benefitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBenefitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefit)))
            .andExpect(status().isBadRequest());

        // Validate the Benefit in the database
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefitRepository.findAll().size();
        // set the field null
        benefit.setName(null);

        // Create the Benefit, which fails.

        restBenefitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefit)))
            .andExpect(status().isBadRequest());

        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefitRepository.findAll().size();
        // set the field null
        benefit.setDescription(null);

        // Create the Benefit, which fails.

        restBenefitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefit)))
            .andExpect(status().isBadRequest());

        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBenefitCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefitRepository.findAll().size();
        // set the field null
        benefit.setBenefitCode(null);

        // Create the Benefit, which fails.

        restBenefitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefit)))
            .andExpect(status().isBadRequest());

        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefitRepository.findAll().size();
        // set the field null
        benefit.setActive(null);

        // Create the Benefit, which fails.

        restBenefitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefit)))
            .andExpect(status().isBadRequest());

        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBenefits() throws Exception {
        // Initialize the database
        benefitRepository.saveAndFlush(benefit);

        // Get all the benefitList
        restBenefitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(benefit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].benefitCode").value(hasItem(DEFAULT_BENEFIT_CODE)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getBenefit() throws Exception {
        // Initialize the database
        benefitRepository.saveAndFlush(benefit);

        // Get the benefit
        restBenefitMockMvc
            .perform(get(ENTITY_API_URL_ID, benefit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(benefit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.benefitCode").value(DEFAULT_BENEFIT_CODE))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingBenefit() throws Exception {
        // Get the benefit
        restBenefitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBenefit() throws Exception {
        // Initialize the database
        benefitRepository.saveAndFlush(benefit);

        int databaseSizeBeforeUpdate = benefitRepository.findAll().size();

        // Update the benefit
        Benefit updatedBenefit = benefitRepository.findById(benefit.getId()).get();
        // Disconnect from session so that the updates on updatedBenefit are not directly saved in db
        em.detach(updatedBenefit);
        updatedBenefit.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).benefitCode(UPDATED_BENEFIT_CODE).active(UPDATED_ACTIVE);

        restBenefitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBenefit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBenefit))
            )
            .andExpect(status().isOk());

        // Validate the Benefit in the database
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeUpdate);
        Benefit testBenefit = benefitList.get(benefitList.size() - 1);
        assertThat(testBenefit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBenefit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBenefit.getBenefitCode()).isEqualTo(UPDATED_BENEFIT_CODE);
        assertThat(testBenefit.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingBenefit() throws Exception {
        int databaseSizeBeforeUpdate = benefitRepository.findAll().size();
        benefit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenefitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, benefit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benefit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Benefit in the database
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBenefit() throws Exception {
        int databaseSizeBeforeUpdate = benefitRepository.findAll().size();
        benefit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(benefit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Benefit in the database
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBenefit() throws Exception {
        int databaseSizeBeforeUpdate = benefitRepository.findAll().size();
        benefit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(benefit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Benefit in the database
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBenefitWithPatch() throws Exception {
        // Initialize the database
        benefitRepository.saveAndFlush(benefit);

        int databaseSizeBeforeUpdate = benefitRepository.findAll().size();

        // Update the benefit using partial update
        Benefit partialUpdatedBenefit = new Benefit();
        partialUpdatedBenefit.setId(benefit.getId());

        partialUpdatedBenefit.name(UPDATED_NAME).benefitCode(UPDATED_BENEFIT_CODE);

        restBenefitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBenefit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBenefit))
            )
            .andExpect(status().isOk());

        // Validate the Benefit in the database
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeUpdate);
        Benefit testBenefit = benefitList.get(benefitList.size() - 1);
        assertThat(testBenefit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBenefit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBenefit.getBenefitCode()).isEqualTo(UPDATED_BENEFIT_CODE);
        assertThat(testBenefit.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateBenefitWithPatch() throws Exception {
        // Initialize the database
        benefitRepository.saveAndFlush(benefit);

        int databaseSizeBeforeUpdate = benefitRepository.findAll().size();

        // Update the benefit using partial update
        Benefit partialUpdatedBenefit = new Benefit();
        partialUpdatedBenefit.setId(benefit.getId());

        partialUpdatedBenefit.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).benefitCode(UPDATED_BENEFIT_CODE).active(UPDATED_ACTIVE);

        restBenefitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBenefit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBenefit))
            )
            .andExpect(status().isOk());

        // Validate the Benefit in the database
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeUpdate);
        Benefit testBenefit = benefitList.get(benefitList.size() - 1);
        assertThat(testBenefit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBenefit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBenefit.getBenefitCode()).isEqualTo(UPDATED_BENEFIT_CODE);
        assertThat(testBenefit.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingBenefit() throws Exception {
        int databaseSizeBeforeUpdate = benefitRepository.findAll().size();
        benefit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenefitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, benefit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(benefit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Benefit in the database
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBenefit() throws Exception {
        int databaseSizeBeforeUpdate = benefitRepository.findAll().size();
        benefit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(benefit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Benefit in the database
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBenefit() throws Exception {
        int databaseSizeBeforeUpdate = benefitRepository.findAll().size();
        benefit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBenefitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(benefit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Benefit in the database
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBenefit() throws Exception {
        // Initialize the database
        benefitRepository.saveAndFlush(benefit);

        int databaseSizeBeforeDelete = benefitRepository.findAll().size();

        // Delete the benefit
        restBenefitMockMvc
            .perform(delete(ENTITY_API_URL_ID, benefit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

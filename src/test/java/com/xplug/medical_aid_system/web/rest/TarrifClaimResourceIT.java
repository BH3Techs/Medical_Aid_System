package com.xplug.medical_aid_system.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.xplug.medical_aid_system.IntegrationTest;
import com.xplug.medical_aid_system.domain.TarrifClaim;
import com.xplug.medical_aid_system.repository.TarrifClaimRepository;
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
 * Integration tests for the {@link TarrifClaimResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TarrifClaimResourceIT {

    private static final String DEFAULT_TARRIF_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TARRIF_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tarrif-claims";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TarrifClaimRepository tarrifClaimRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTarrifClaimMockMvc;

    private TarrifClaim tarrifClaim;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarrifClaim createEntity(EntityManager em) {
        TarrifClaim tarrifClaim = new TarrifClaim()
            .tarrifCode(DEFAULT_TARRIF_CODE)
            .quantity(DEFAULT_QUANTITY)
            .amount(DEFAULT_AMOUNT)
            .description(DEFAULT_DESCRIPTION);
        return tarrifClaim;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarrifClaim createUpdatedEntity(EntityManager em) {
        TarrifClaim tarrifClaim = new TarrifClaim()
            .tarrifCode(UPDATED_TARRIF_CODE)
            .quantity(UPDATED_QUANTITY)
            .amount(UPDATED_AMOUNT)
            .description(UPDATED_DESCRIPTION);
        return tarrifClaim;
    }

    @BeforeEach
    public void initTest() {
        tarrifClaim = createEntity(em);
    }

    @Test
    @Transactional
    void createTarrifClaim() throws Exception {
        int databaseSizeBeforeCreate = tarrifClaimRepository.findAll().size();
        // Create the TarrifClaim
        restTarrifClaimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tarrifClaim)))
            .andExpect(status().isCreated());

        // Validate the TarrifClaim in the database
        List<TarrifClaim> tarrifClaimList = tarrifClaimRepository.findAll();
        assertThat(tarrifClaimList).hasSize(databaseSizeBeforeCreate + 1);
        TarrifClaim testTarrifClaim = tarrifClaimList.get(tarrifClaimList.size() - 1);
        assertThat(testTarrifClaim.getTarrifCode()).isEqualTo(DEFAULT_TARRIF_CODE);
        assertThat(testTarrifClaim.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testTarrifClaim.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testTarrifClaim.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createTarrifClaimWithExistingId() throws Exception {
        // Create the TarrifClaim with an existing ID
        tarrifClaim.setId(1L);

        int databaseSizeBeforeCreate = tarrifClaimRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarrifClaimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tarrifClaim)))
            .andExpect(status().isBadRequest());

        // Validate the TarrifClaim in the database
        List<TarrifClaim> tarrifClaimList = tarrifClaimRepository.findAll();
        assertThat(tarrifClaimList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTarrifCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarrifClaimRepository.findAll().size();
        // set the field null
        tarrifClaim.setTarrifCode(null);

        // Create the TarrifClaim, which fails.

        restTarrifClaimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tarrifClaim)))
            .andExpect(status().isBadRequest());

        List<TarrifClaim> tarrifClaimList = tarrifClaimRepository.findAll();
        assertThat(tarrifClaimList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarrifClaimRepository.findAll().size();
        // set the field null
        tarrifClaim.setQuantity(null);

        // Create the TarrifClaim, which fails.

        restTarrifClaimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tarrifClaim)))
            .andExpect(status().isBadRequest());

        List<TarrifClaim> tarrifClaimList = tarrifClaimRepository.findAll();
        assertThat(tarrifClaimList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarrifClaimRepository.findAll().size();
        // set the field null
        tarrifClaim.setAmount(null);

        // Create the TarrifClaim, which fails.

        restTarrifClaimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tarrifClaim)))
            .andExpect(status().isBadRequest());

        List<TarrifClaim> tarrifClaimList = tarrifClaimRepository.findAll();
        assertThat(tarrifClaimList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarrifClaimRepository.findAll().size();
        // set the field null
        tarrifClaim.setDescription(null);

        // Create the TarrifClaim, which fails.

        restTarrifClaimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tarrifClaim)))
            .andExpect(status().isBadRequest());

        List<TarrifClaim> tarrifClaimList = tarrifClaimRepository.findAll();
        assertThat(tarrifClaimList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTarrifClaims() throws Exception {
        // Initialize the database
        tarrifClaimRepository.saveAndFlush(tarrifClaim);

        // Get all the tarrifClaimList
        restTarrifClaimMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarrifClaim.getId().intValue())))
            .andExpect(jsonPath("$.[*].tarrifCode").value(hasItem(DEFAULT_TARRIF_CODE)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getTarrifClaim() throws Exception {
        // Initialize the database
        tarrifClaimRepository.saveAndFlush(tarrifClaim);

        // Get the tarrifClaim
        restTarrifClaimMockMvc
            .perform(get(ENTITY_API_URL_ID, tarrifClaim.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarrifClaim.getId().intValue()))
            .andExpect(jsonPath("$.tarrifCode").value(DEFAULT_TARRIF_CODE))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingTarrifClaim() throws Exception {
        // Get the tarrifClaim
        restTarrifClaimMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTarrifClaim() throws Exception {
        // Initialize the database
        tarrifClaimRepository.saveAndFlush(tarrifClaim);

        int databaseSizeBeforeUpdate = tarrifClaimRepository.findAll().size();

        // Update the tarrifClaim
        TarrifClaim updatedTarrifClaim = tarrifClaimRepository.findById(tarrifClaim.getId()).get();
        // Disconnect from session so that the updates on updatedTarrifClaim are not directly saved in db
        em.detach(updatedTarrifClaim);
        updatedTarrifClaim
            .tarrifCode(UPDATED_TARRIF_CODE)
            .quantity(UPDATED_QUANTITY)
            .amount(UPDATED_AMOUNT)
            .description(UPDATED_DESCRIPTION);

        restTarrifClaimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTarrifClaim.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTarrifClaim))
            )
            .andExpect(status().isOk());

        // Validate the TarrifClaim in the database
        List<TarrifClaim> tarrifClaimList = tarrifClaimRepository.findAll();
        assertThat(tarrifClaimList).hasSize(databaseSizeBeforeUpdate);
        TarrifClaim testTarrifClaim = tarrifClaimList.get(tarrifClaimList.size() - 1);
        assertThat(testTarrifClaim.getTarrifCode()).isEqualTo(UPDATED_TARRIF_CODE);
        assertThat(testTarrifClaim.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testTarrifClaim.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTarrifClaim.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingTarrifClaim() throws Exception {
        int databaseSizeBeforeUpdate = tarrifClaimRepository.findAll().size();
        tarrifClaim.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarrifClaimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tarrifClaim.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tarrifClaim))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarrifClaim in the database
        List<TarrifClaim> tarrifClaimList = tarrifClaimRepository.findAll();
        assertThat(tarrifClaimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTarrifClaim() throws Exception {
        int databaseSizeBeforeUpdate = tarrifClaimRepository.findAll().size();
        tarrifClaim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarrifClaimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tarrifClaim))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarrifClaim in the database
        List<TarrifClaim> tarrifClaimList = tarrifClaimRepository.findAll();
        assertThat(tarrifClaimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTarrifClaim() throws Exception {
        int databaseSizeBeforeUpdate = tarrifClaimRepository.findAll().size();
        tarrifClaim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarrifClaimMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tarrifClaim)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TarrifClaim in the database
        List<TarrifClaim> tarrifClaimList = tarrifClaimRepository.findAll();
        assertThat(tarrifClaimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTarrifClaimWithPatch() throws Exception {
        // Initialize the database
        tarrifClaimRepository.saveAndFlush(tarrifClaim);

        int databaseSizeBeforeUpdate = tarrifClaimRepository.findAll().size();

        // Update the tarrifClaim using partial update
        TarrifClaim partialUpdatedTarrifClaim = new TarrifClaim();
        partialUpdatedTarrifClaim.setId(tarrifClaim.getId());

        partialUpdatedTarrifClaim.description(UPDATED_DESCRIPTION);

        restTarrifClaimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarrifClaim.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTarrifClaim))
            )
            .andExpect(status().isOk());

        // Validate the TarrifClaim in the database
        List<TarrifClaim> tarrifClaimList = tarrifClaimRepository.findAll();
        assertThat(tarrifClaimList).hasSize(databaseSizeBeforeUpdate);
        TarrifClaim testTarrifClaim = tarrifClaimList.get(tarrifClaimList.size() - 1);
        assertThat(testTarrifClaim.getTarrifCode()).isEqualTo(DEFAULT_TARRIF_CODE);
        assertThat(testTarrifClaim.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testTarrifClaim.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testTarrifClaim.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateTarrifClaimWithPatch() throws Exception {
        // Initialize the database
        tarrifClaimRepository.saveAndFlush(tarrifClaim);

        int databaseSizeBeforeUpdate = tarrifClaimRepository.findAll().size();

        // Update the tarrifClaim using partial update
        TarrifClaim partialUpdatedTarrifClaim = new TarrifClaim();
        partialUpdatedTarrifClaim.setId(tarrifClaim.getId());

        partialUpdatedTarrifClaim
            .tarrifCode(UPDATED_TARRIF_CODE)
            .quantity(UPDATED_QUANTITY)
            .amount(UPDATED_AMOUNT)
            .description(UPDATED_DESCRIPTION);

        restTarrifClaimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarrifClaim.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTarrifClaim))
            )
            .andExpect(status().isOk());

        // Validate the TarrifClaim in the database
        List<TarrifClaim> tarrifClaimList = tarrifClaimRepository.findAll();
        assertThat(tarrifClaimList).hasSize(databaseSizeBeforeUpdate);
        TarrifClaim testTarrifClaim = tarrifClaimList.get(tarrifClaimList.size() - 1);
        assertThat(testTarrifClaim.getTarrifCode()).isEqualTo(UPDATED_TARRIF_CODE);
        assertThat(testTarrifClaim.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testTarrifClaim.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTarrifClaim.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingTarrifClaim() throws Exception {
        int databaseSizeBeforeUpdate = tarrifClaimRepository.findAll().size();
        tarrifClaim.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarrifClaimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tarrifClaim.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tarrifClaim))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarrifClaim in the database
        List<TarrifClaim> tarrifClaimList = tarrifClaimRepository.findAll();
        assertThat(tarrifClaimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTarrifClaim() throws Exception {
        int databaseSizeBeforeUpdate = tarrifClaimRepository.findAll().size();
        tarrifClaim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarrifClaimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tarrifClaim))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarrifClaim in the database
        List<TarrifClaim> tarrifClaimList = tarrifClaimRepository.findAll();
        assertThat(tarrifClaimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTarrifClaim() throws Exception {
        int databaseSizeBeforeUpdate = tarrifClaimRepository.findAll().size();
        tarrifClaim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarrifClaimMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tarrifClaim))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TarrifClaim in the database
        List<TarrifClaim> tarrifClaimList = tarrifClaimRepository.findAll();
        assertThat(tarrifClaimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTarrifClaim() throws Exception {
        // Initialize the database
        tarrifClaimRepository.saveAndFlush(tarrifClaim);

        int databaseSizeBeforeDelete = tarrifClaimRepository.findAll().size();

        // Delete the tarrifClaim
        restTarrifClaimMockMvc
            .perform(delete(ENTITY_API_URL_ID, tarrifClaim.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TarrifClaim> tarrifClaimList = tarrifClaimRepository.findAll();
        assertThat(tarrifClaimList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

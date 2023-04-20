package com.medical_aid_system.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.medical_aid_system.IntegrationTest;
import com.medical_aid_system.domain.BankingDetails;
import com.medical_aid_system.repository.BankingDetailsRepository;
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
 * Integration tests for the {@link BankingDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankingDetailsResourceIT {

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SWIFT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SWIFT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/banking-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankingDetailsRepository bankingDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankingDetailsMockMvc;

    private BankingDetails bankingDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankingDetails createEntity(EntityManager em) {
        BankingDetails bankingDetails = new BankingDetails()
            .accountName(DEFAULT_ACCOUNT_NAME)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .swiftCode(DEFAULT_SWIFT_CODE)
            .bankName(DEFAULT_BANK_NAME);
        return bankingDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankingDetails createUpdatedEntity(EntityManager em) {
        BankingDetails bankingDetails = new BankingDetails()
            .accountName(UPDATED_ACCOUNT_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .swiftCode(UPDATED_SWIFT_CODE)
            .bankName(UPDATED_BANK_NAME);
        return bankingDetails;
    }

    @BeforeEach
    public void initTest() {
        bankingDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createBankingDetails() throws Exception {
        int databaseSizeBeforeCreate = bankingDetailsRepository.findAll().size();
        // Create the BankingDetails
        restBankingDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankingDetails))
            )
            .andExpect(status().isCreated());

        // Validate the BankingDetails in the database
        List<BankingDetails> bankingDetailsList = bankingDetailsRepository.findAll();
        assertThat(bankingDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        BankingDetails testBankingDetails = bankingDetailsList.get(bankingDetailsList.size() - 1);
        assertThat(testBankingDetails.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testBankingDetails.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testBankingDetails.getSwiftCode()).isEqualTo(DEFAULT_SWIFT_CODE);
        assertThat(testBankingDetails.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
    }

    @Test
    @Transactional
    void createBankingDetailsWithExistingId() throws Exception {
        // Create the BankingDetails with an existing ID
        bankingDetails.setId(1L);

        int databaseSizeBeforeCreate = bankingDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankingDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankingDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankingDetails in the database
        List<BankingDetails> bankingDetailsList = bankingDetailsRepository.findAll();
        assertThat(bankingDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAccountNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankingDetailsRepository.findAll().size();
        // set the field null
        bankingDetails.setAccountName(null);

        // Create the BankingDetails, which fails.

        restBankingDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankingDetails))
            )
            .andExpect(status().isBadRequest());

        List<BankingDetails> bankingDetailsList = bankingDetailsRepository.findAll();
        assertThat(bankingDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankingDetailsRepository.findAll().size();
        // set the field null
        bankingDetails.setAccountNumber(null);

        // Create the BankingDetails, which fails.

        restBankingDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankingDetails))
            )
            .andExpect(status().isBadRequest());

        List<BankingDetails> bankingDetailsList = bankingDetailsRepository.findAll();
        assertThat(bankingDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSwiftCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankingDetailsRepository.findAll().size();
        // set the field null
        bankingDetails.setSwiftCode(null);

        // Create the BankingDetails, which fails.

        restBankingDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankingDetails))
            )
            .andExpect(status().isBadRequest());

        List<BankingDetails> bankingDetailsList = bankingDetailsRepository.findAll();
        assertThat(bankingDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBankNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankingDetailsRepository.findAll().size();
        // set the field null
        bankingDetails.setBankName(null);

        // Create the BankingDetails, which fails.

        restBankingDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankingDetails))
            )
            .andExpect(status().isBadRequest());

        List<BankingDetails> bankingDetailsList = bankingDetailsRepository.findAll();
        assertThat(bankingDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBankingDetails() throws Exception {
        // Initialize the database
        bankingDetailsRepository.saveAndFlush(bankingDetails);

        // Get all the bankingDetailsList
        restBankingDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankingDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].swiftCode").value(hasItem(DEFAULT_SWIFT_CODE)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)));
    }

    @Test
    @Transactional
    void getBankingDetails() throws Exception {
        // Initialize the database
        bankingDetailsRepository.saveAndFlush(bankingDetails);

        // Get the bankingDetails
        restBankingDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, bankingDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankingDetails.getId().intValue()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.swiftCode").value(DEFAULT_SWIFT_CODE))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME));
    }

    @Test
    @Transactional
    void getNonExistingBankingDetails() throws Exception {
        // Get the bankingDetails
        restBankingDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBankingDetails() throws Exception {
        // Initialize the database
        bankingDetailsRepository.saveAndFlush(bankingDetails);

        int databaseSizeBeforeUpdate = bankingDetailsRepository.findAll().size();

        // Update the bankingDetails
        BankingDetails updatedBankingDetails = bankingDetailsRepository.findById(bankingDetails.getId()).get();
        // Disconnect from session so that the updates on updatedBankingDetails are not directly saved in db
        em.detach(updatedBankingDetails);
        updatedBankingDetails
            .accountName(UPDATED_ACCOUNT_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .swiftCode(UPDATED_SWIFT_CODE)
            .bankName(UPDATED_BANK_NAME);

        restBankingDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBankingDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBankingDetails))
            )
            .andExpect(status().isOk());

        // Validate the BankingDetails in the database
        List<BankingDetails> bankingDetailsList = bankingDetailsRepository.findAll();
        assertThat(bankingDetailsList).hasSize(databaseSizeBeforeUpdate);
        BankingDetails testBankingDetails = bankingDetailsList.get(bankingDetailsList.size() - 1);
        assertThat(testBankingDetails.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testBankingDetails.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testBankingDetails.getSwiftCode()).isEqualTo(UPDATED_SWIFT_CODE);
        assertThat(testBankingDetails.getBankName()).isEqualTo(UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void putNonExistingBankingDetails() throws Exception {
        int databaseSizeBeforeUpdate = bankingDetailsRepository.findAll().size();
        bankingDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankingDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankingDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankingDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankingDetails in the database
        List<BankingDetails> bankingDetailsList = bankingDetailsRepository.findAll();
        assertThat(bankingDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankingDetails() throws Exception {
        int databaseSizeBeforeUpdate = bankingDetailsRepository.findAll().size();
        bankingDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankingDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankingDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankingDetails in the database
        List<BankingDetails> bankingDetailsList = bankingDetailsRepository.findAll();
        assertThat(bankingDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankingDetails() throws Exception {
        int databaseSizeBeforeUpdate = bankingDetailsRepository.findAll().size();
        bankingDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankingDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankingDetails)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankingDetails in the database
        List<BankingDetails> bankingDetailsList = bankingDetailsRepository.findAll();
        assertThat(bankingDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankingDetailsWithPatch() throws Exception {
        // Initialize the database
        bankingDetailsRepository.saveAndFlush(bankingDetails);

        int databaseSizeBeforeUpdate = bankingDetailsRepository.findAll().size();

        // Update the bankingDetails using partial update
        BankingDetails partialUpdatedBankingDetails = new BankingDetails();
        partialUpdatedBankingDetails.setId(bankingDetails.getId());

        partialUpdatedBankingDetails.swiftCode(UPDATED_SWIFT_CODE).bankName(UPDATED_BANK_NAME);

        restBankingDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankingDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankingDetails))
            )
            .andExpect(status().isOk());

        // Validate the BankingDetails in the database
        List<BankingDetails> bankingDetailsList = bankingDetailsRepository.findAll();
        assertThat(bankingDetailsList).hasSize(databaseSizeBeforeUpdate);
        BankingDetails testBankingDetails = bankingDetailsList.get(bankingDetailsList.size() - 1);
        assertThat(testBankingDetails.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testBankingDetails.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testBankingDetails.getSwiftCode()).isEqualTo(UPDATED_SWIFT_CODE);
        assertThat(testBankingDetails.getBankName()).isEqualTo(UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void fullUpdateBankingDetailsWithPatch() throws Exception {
        // Initialize the database
        bankingDetailsRepository.saveAndFlush(bankingDetails);

        int databaseSizeBeforeUpdate = bankingDetailsRepository.findAll().size();

        // Update the bankingDetails using partial update
        BankingDetails partialUpdatedBankingDetails = new BankingDetails();
        partialUpdatedBankingDetails.setId(bankingDetails.getId());

        partialUpdatedBankingDetails
            .accountName(UPDATED_ACCOUNT_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .swiftCode(UPDATED_SWIFT_CODE)
            .bankName(UPDATED_BANK_NAME);

        restBankingDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankingDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankingDetails))
            )
            .andExpect(status().isOk());

        // Validate the BankingDetails in the database
        List<BankingDetails> bankingDetailsList = bankingDetailsRepository.findAll();
        assertThat(bankingDetailsList).hasSize(databaseSizeBeforeUpdate);
        BankingDetails testBankingDetails = bankingDetailsList.get(bankingDetailsList.size() - 1);
        assertThat(testBankingDetails.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testBankingDetails.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testBankingDetails.getSwiftCode()).isEqualTo(UPDATED_SWIFT_CODE);
        assertThat(testBankingDetails.getBankName()).isEqualTo(UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingBankingDetails() throws Exception {
        int databaseSizeBeforeUpdate = bankingDetailsRepository.findAll().size();
        bankingDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankingDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankingDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankingDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankingDetails in the database
        List<BankingDetails> bankingDetailsList = bankingDetailsRepository.findAll();
        assertThat(bankingDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankingDetails() throws Exception {
        int databaseSizeBeforeUpdate = bankingDetailsRepository.findAll().size();
        bankingDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankingDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankingDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankingDetails in the database
        List<BankingDetails> bankingDetailsList = bankingDetailsRepository.findAll();
        assertThat(bankingDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankingDetails() throws Exception {
        int databaseSizeBeforeUpdate = bankingDetailsRepository.findAll().size();
        bankingDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankingDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bankingDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankingDetails in the database
        List<BankingDetails> bankingDetailsList = bankingDetailsRepository.findAll();
        assertThat(bankingDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankingDetails() throws Exception {
        // Initialize the database
        bankingDetailsRepository.saveAndFlush(bankingDetails);

        int databaseSizeBeforeDelete = bankingDetailsRepository.findAll().size();

        // Delete the bankingDetails
        restBankingDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankingDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankingDetails> bankingDetailsList = bankingDetailsRepository.findAll();
        assertThat(bankingDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

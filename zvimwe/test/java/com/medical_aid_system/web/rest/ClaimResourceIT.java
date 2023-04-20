package com.medical_aid_system.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.medical_aid_system.IntegrationTest;
import com.medical_aid_system.domain.Claim;
import com.medical_aid_system.domain.enumeration.ClaimStatus;
import com.medical_aid_system.repository.ClaimRepository;
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
 * Integration tests for the {@link ClaimResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClaimResourceIT {

    private static final LocalDate DEFAULT_SUBMISSION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SUBMISSION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_APPROVAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPROVAL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PROCESSING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PROCESSING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ClaimStatus DEFAULT_CLAIM_STATUS = ClaimStatus.PENDING;
    private static final ClaimStatus UPDATED_CLAIM_STATUS = ClaimStatus.APPROVED;

    private static final String DEFAULT_DIAGNOSIS = "AAAAAAAAAA";
    private static final String UPDATED_DIAGNOSIS = "BBBBBBBBBB";

    private static final String DEFAULT_CLAIMANT = "AAAAAAAAAA";
    private static final String UPDATED_CLAIMANT = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIONSHIP_TO_MEMBER = "AAAAAAAAAA";
    private static final String UPDATED_RELATIONSHIP_TO_MEMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/claims";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClaimMockMvc;

    private Claim claim;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Claim createEntity(EntityManager em) {
        Claim claim = new Claim()
            .submissionDate(DEFAULT_SUBMISSION_DATE)
            .approvalDate(DEFAULT_APPROVAL_DATE)
            .processingDate(DEFAULT_PROCESSING_DATE)
            .claimStatus(DEFAULT_CLAIM_STATUS)
            .diagnosis(DEFAULT_DIAGNOSIS)
            .claimant(DEFAULT_CLAIMANT)
            .relationshipToMember(DEFAULT_RELATIONSHIP_TO_MEMBER);
        return claim;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Claim createUpdatedEntity(EntityManager em) {
        Claim claim = new Claim()
            .submissionDate(UPDATED_SUBMISSION_DATE)
            .approvalDate(UPDATED_APPROVAL_DATE)
            .processingDate(UPDATED_PROCESSING_DATE)
            .claimStatus(UPDATED_CLAIM_STATUS)
            .diagnosis(UPDATED_DIAGNOSIS)
            .claimant(UPDATED_CLAIMANT)
            .relationshipToMember(UPDATED_RELATIONSHIP_TO_MEMBER);
        return claim;
    }

    @BeforeEach
    public void initTest() {
        claim = createEntity(em);
    }

    @Test
    @Transactional
    void createClaim() throws Exception {
        int databaseSizeBeforeCreate = claimRepository.findAll().size();
        // Create the Claim
        restClaimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(claim)))
            .andExpect(status().isCreated());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeCreate + 1);
        Claim testClaim = claimList.get(claimList.size() - 1);
        assertThat(testClaim.getSubmissionDate()).isEqualTo(DEFAULT_SUBMISSION_DATE);
        assertThat(testClaim.getApprovalDate()).isEqualTo(DEFAULT_APPROVAL_DATE);
        assertThat(testClaim.getProcessingDate()).isEqualTo(DEFAULT_PROCESSING_DATE);
        assertThat(testClaim.getClaimStatus()).isEqualTo(DEFAULT_CLAIM_STATUS);
        assertThat(testClaim.getDiagnosis()).isEqualTo(DEFAULT_DIAGNOSIS);
        assertThat(testClaim.getClaimant()).isEqualTo(DEFAULT_CLAIMANT);
        assertThat(testClaim.getRelationshipToMember()).isEqualTo(DEFAULT_RELATIONSHIP_TO_MEMBER);
    }

    @Test
    @Transactional
    void createClaimWithExistingId() throws Exception {
        // Create the Claim with an existing ID
        claim.setId(1L);

        int databaseSizeBeforeCreate = claimRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClaimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(claim)))
            .andExpect(status().isBadRequest());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSubmissionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = claimRepository.findAll().size();
        // set the field null
        claim.setSubmissionDate(null);

        // Create the Claim, which fails.

        restClaimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(claim)))
            .andExpect(status().isBadRequest());

        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClaims() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get all the claimList
        restClaimMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claim.getId().intValue())))
            .andExpect(jsonPath("$.[*].submissionDate").value(hasItem(DEFAULT_SUBMISSION_DATE.toString())))
            .andExpect(jsonPath("$.[*].approvalDate").value(hasItem(DEFAULT_APPROVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].processingDate").value(hasItem(DEFAULT_PROCESSING_DATE.toString())))
            .andExpect(jsonPath("$.[*].claimStatus").value(hasItem(DEFAULT_CLAIM_STATUS.toString())))
            .andExpect(jsonPath("$.[*].diagnosis").value(hasItem(DEFAULT_DIAGNOSIS)))
            .andExpect(jsonPath("$.[*].claimant").value(hasItem(DEFAULT_CLAIMANT)))
            .andExpect(jsonPath("$.[*].relationshipToMember").value(hasItem(DEFAULT_RELATIONSHIP_TO_MEMBER)));
    }

    @Test
    @Transactional
    void getClaim() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get the claim
        restClaimMockMvc
            .perform(get(ENTITY_API_URL_ID, claim.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(claim.getId().intValue()))
            .andExpect(jsonPath("$.submissionDate").value(DEFAULT_SUBMISSION_DATE.toString()))
            .andExpect(jsonPath("$.approvalDate").value(DEFAULT_APPROVAL_DATE.toString()))
            .andExpect(jsonPath("$.processingDate").value(DEFAULT_PROCESSING_DATE.toString()))
            .andExpect(jsonPath("$.claimStatus").value(DEFAULT_CLAIM_STATUS.toString()))
            .andExpect(jsonPath("$.diagnosis").value(DEFAULT_DIAGNOSIS))
            .andExpect(jsonPath("$.claimant").value(DEFAULT_CLAIMANT))
            .andExpect(jsonPath("$.relationshipToMember").value(DEFAULT_RELATIONSHIP_TO_MEMBER));
    }

    @Test
    @Transactional
    void getNonExistingClaim() throws Exception {
        // Get the claim
        restClaimMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClaim() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        int databaseSizeBeforeUpdate = claimRepository.findAll().size();

        // Update the claim
        Claim updatedClaim = claimRepository.findById(claim.getId()).get();
        // Disconnect from session so that the updates on updatedClaim are not directly saved in db
        em.detach(updatedClaim);
        updatedClaim
            .submissionDate(UPDATED_SUBMISSION_DATE)
            .approvalDate(UPDATED_APPROVAL_DATE)
            .processingDate(UPDATED_PROCESSING_DATE)
            .claimStatus(UPDATED_CLAIM_STATUS)
            .diagnosis(UPDATED_DIAGNOSIS)
            .claimant(UPDATED_CLAIMANT)
            .relationshipToMember(UPDATED_RELATIONSHIP_TO_MEMBER);

        restClaimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClaim.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClaim))
            )
            .andExpect(status().isOk());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
        Claim testClaim = claimList.get(claimList.size() - 1);
        assertThat(testClaim.getSubmissionDate()).isEqualTo(UPDATED_SUBMISSION_DATE);
        assertThat(testClaim.getApprovalDate()).isEqualTo(UPDATED_APPROVAL_DATE);
        assertThat(testClaim.getProcessingDate()).isEqualTo(UPDATED_PROCESSING_DATE);
        assertThat(testClaim.getClaimStatus()).isEqualTo(UPDATED_CLAIM_STATUS);
        assertThat(testClaim.getDiagnosis()).isEqualTo(UPDATED_DIAGNOSIS);
        assertThat(testClaim.getClaimant()).isEqualTo(UPDATED_CLAIMANT);
        assertThat(testClaim.getRelationshipToMember()).isEqualTo(UPDATED_RELATIONSHIP_TO_MEMBER);
    }

    @Test
    @Transactional
    void putNonExistingClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, claim.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claim))
            )
            .andExpect(status().isBadRequest());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claim))
            )
            .andExpect(status().isBadRequest());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(claim)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClaimWithPatch() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        int databaseSizeBeforeUpdate = claimRepository.findAll().size();

        // Update the claim using partial update
        Claim partialUpdatedClaim = new Claim();
        partialUpdatedClaim.setId(claim.getId());

        partialUpdatedClaim
            .approvalDate(UPDATED_APPROVAL_DATE)
            .processingDate(UPDATED_PROCESSING_DATE)
            .relationshipToMember(UPDATED_RELATIONSHIP_TO_MEMBER);

        restClaimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaim.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaim))
            )
            .andExpect(status().isOk());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
        Claim testClaim = claimList.get(claimList.size() - 1);
        assertThat(testClaim.getSubmissionDate()).isEqualTo(DEFAULT_SUBMISSION_DATE);
        assertThat(testClaim.getApprovalDate()).isEqualTo(UPDATED_APPROVAL_DATE);
        assertThat(testClaim.getProcessingDate()).isEqualTo(UPDATED_PROCESSING_DATE);
        assertThat(testClaim.getClaimStatus()).isEqualTo(DEFAULT_CLAIM_STATUS);
        assertThat(testClaim.getDiagnosis()).isEqualTo(DEFAULT_DIAGNOSIS);
        assertThat(testClaim.getClaimant()).isEqualTo(DEFAULT_CLAIMANT);
        assertThat(testClaim.getRelationshipToMember()).isEqualTo(UPDATED_RELATIONSHIP_TO_MEMBER);
    }

    @Test
    @Transactional
    void fullUpdateClaimWithPatch() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        int databaseSizeBeforeUpdate = claimRepository.findAll().size();

        // Update the claim using partial update
        Claim partialUpdatedClaim = new Claim();
        partialUpdatedClaim.setId(claim.getId());

        partialUpdatedClaim
            .submissionDate(UPDATED_SUBMISSION_DATE)
            .approvalDate(UPDATED_APPROVAL_DATE)
            .processingDate(UPDATED_PROCESSING_DATE)
            .claimStatus(UPDATED_CLAIM_STATUS)
            .diagnosis(UPDATED_DIAGNOSIS)
            .claimant(UPDATED_CLAIMANT)
            .relationshipToMember(UPDATED_RELATIONSHIP_TO_MEMBER);

        restClaimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaim.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaim))
            )
            .andExpect(status().isOk());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
        Claim testClaim = claimList.get(claimList.size() - 1);
        assertThat(testClaim.getSubmissionDate()).isEqualTo(UPDATED_SUBMISSION_DATE);
        assertThat(testClaim.getApprovalDate()).isEqualTo(UPDATED_APPROVAL_DATE);
        assertThat(testClaim.getProcessingDate()).isEqualTo(UPDATED_PROCESSING_DATE);
        assertThat(testClaim.getClaimStatus()).isEqualTo(UPDATED_CLAIM_STATUS);
        assertThat(testClaim.getDiagnosis()).isEqualTo(UPDATED_DIAGNOSIS);
        assertThat(testClaim.getClaimant()).isEqualTo(UPDATED_CLAIMANT);
        assertThat(testClaim.getRelationshipToMember()).isEqualTo(UPDATED_RELATIONSHIP_TO_MEMBER);
    }

    @Test
    @Transactional
    void patchNonExistingClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, claim.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claim))
            )
            .andExpect(status().isBadRequest());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claim))
            )
            .andExpect(status().isBadRequest());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(claim)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClaim() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        int databaseSizeBeforeDelete = claimRepository.findAll().size();

        // Delete the claim
        restClaimMockMvc
            .perform(delete(ENTITY_API_URL_ID, claim.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

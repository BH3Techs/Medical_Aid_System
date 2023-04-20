package com.xplug.medical_aid_system.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.xplug.medical_aid_system.IntegrationTest;
import com.xplug.medical_aid_system.domain.Policy;
import com.xplug.medical_aid_system.domain.enumeration.PolicyStatus;
import com.xplug.medical_aid_system.repository.PolicyRepository;
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
 * Integration tests for the {@link PolicyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PolicyResourceIT {

    private static final String DEFAULT_POLICY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_POLICY_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SUFFIX = "AAAAAAAAAA";
    private static final String UPDATED_SUFFIX = "BBBBBBBBBB";

    private static final String DEFAULT_PRICING_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_PRICING_GROUP = "BBBBBBBBBB";

    private static final String DEFAULT_NEXT_OF_KIN = "AAAAAAAAAA";
    private static final String UPDATED_NEXT_OF_KIN = "BBBBBBBBBB";

    private static final String DEFAULT_MEMBER_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_POLICY = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_POLICY = "BBBBBBBBBB";

    private static final String DEFAULT_SPONSOR_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_SPONSOR_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_SPONSOR_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SPONSOR_TYPE = "BBBBBBBBBB";

    private static final PolicyStatus DEFAULT_STATUS = PolicyStatus.ACTIVE;
    private static final PolicyStatus UPDATED_STATUS = PolicyStatus.SUSPNDED;

    private static final Double DEFAULT_BALANCE = 1D;
    private static final Double UPDATED_BALANCE = 2D;

    private static final String ENTITY_API_URL = "/api/policies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPolicyMockMvc;

    private Policy policy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Policy createEntity(EntityManager em) {
        Policy policy = new Policy()
            .policyNumber(DEFAULT_POLICY_NUMBER)
            .suffix(DEFAULT_SUFFIX)
            .pricingGroup(DEFAULT_PRICING_GROUP)
            .nextOfKin(DEFAULT_NEXT_OF_KIN)
            .memberIdentifier(DEFAULT_MEMBER_IDENTIFIER)
            .parentPolicy(DEFAULT_PARENT_POLICY)
            .sponsorIdentifier(DEFAULT_SPONSOR_IDENTIFIER)
            .sponsorType(DEFAULT_SPONSOR_TYPE)
            .status(DEFAULT_STATUS)
            .balance(DEFAULT_BALANCE);
        return policy;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Policy createUpdatedEntity(EntityManager em) {
        Policy policy = new Policy()
            .policyNumber(UPDATED_POLICY_NUMBER)
            .suffix(UPDATED_SUFFIX)
            .pricingGroup(UPDATED_PRICING_GROUP)
            .nextOfKin(UPDATED_NEXT_OF_KIN)
            .memberIdentifier(UPDATED_MEMBER_IDENTIFIER)
            .parentPolicy(UPDATED_PARENT_POLICY)
            .sponsorIdentifier(UPDATED_SPONSOR_IDENTIFIER)
            .sponsorType(UPDATED_SPONSOR_TYPE)
            .status(UPDATED_STATUS)
            .balance(UPDATED_BALANCE);
        return policy;
    }

    @BeforeEach
    public void initTest() {
        policy = createEntity(em);
    }

    @Test
    @Transactional
    void createPolicy() throws Exception {
        int databaseSizeBeforeCreate = policyRepository.findAll().size();
        // Create the Policy
        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policy)))
            .andExpect(status().isCreated());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeCreate + 1);
        Policy testPolicy = policyList.get(policyList.size() - 1);
        assertThat(testPolicy.getPolicyNumber()).isEqualTo(DEFAULT_POLICY_NUMBER);
        assertThat(testPolicy.getSuffix()).isEqualTo(DEFAULT_SUFFIX);
        assertThat(testPolicy.getPricingGroup()).isEqualTo(DEFAULT_PRICING_GROUP);
        assertThat(testPolicy.getNextOfKin()).isEqualTo(DEFAULT_NEXT_OF_KIN);
        assertThat(testPolicy.getMemberIdentifier()).isEqualTo(DEFAULT_MEMBER_IDENTIFIER);
        assertThat(testPolicy.getParentPolicy()).isEqualTo(DEFAULT_PARENT_POLICY);
        assertThat(testPolicy.getSponsorIdentifier()).isEqualTo(DEFAULT_SPONSOR_IDENTIFIER);
        assertThat(testPolicy.getSponsorType()).isEqualTo(DEFAULT_SPONSOR_TYPE);
        assertThat(testPolicy.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPolicy.getBalance()).isEqualTo(DEFAULT_BALANCE);
    }

    @Test
    @Transactional
    void createPolicyWithExistingId() throws Exception {
        // Create the Policy with an existing ID
        policy.setId(1L);

        int databaseSizeBeforeCreate = policyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policy)))
            .andExpect(status().isBadRequest());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPolicyNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyRepository.findAll().size();
        // set the field null
        policy.setPolicyNumber(null);

        // Create the Policy, which fails.

        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policy)))
            .andExpect(status().isBadRequest());

        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSuffixIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyRepository.findAll().size();
        // set the field null
        policy.setSuffix(null);

        // Create the Policy, which fails.

        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policy)))
            .andExpect(status().isBadRequest());

        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyRepository.findAll().size();
        // set the field null
        policy.setStatus(null);

        // Create the Policy, which fails.

        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policy)))
            .andExpect(status().isBadRequest());

        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyRepository.findAll().size();
        // set the field null
        policy.setBalance(null);

        // Create the Policy, which fails.

        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policy)))
            .andExpect(status().isBadRequest());

        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPolicies() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList
        restPolicyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policy.getId().intValue())))
            .andExpect(jsonPath("$.[*].policyNumber").value(hasItem(DEFAULT_POLICY_NUMBER)))
            .andExpect(jsonPath("$.[*].suffix").value(hasItem(DEFAULT_SUFFIX)))
            .andExpect(jsonPath("$.[*].pricingGroup").value(hasItem(DEFAULT_PRICING_GROUP)))
            .andExpect(jsonPath("$.[*].nextOfKin").value(hasItem(DEFAULT_NEXT_OF_KIN)))
            .andExpect(jsonPath("$.[*].memberIdentifier").value(hasItem(DEFAULT_MEMBER_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].parentPolicy").value(hasItem(DEFAULT_PARENT_POLICY)))
            .andExpect(jsonPath("$.[*].sponsorIdentifier").value(hasItem(DEFAULT_SPONSOR_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].sponsorType").value(hasItem(DEFAULT_SPONSOR_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())));
    }

    @Test
    @Transactional
    void getPolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get the policy
        restPolicyMockMvc
            .perform(get(ENTITY_API_URL_ID, policy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(policy.getId().intValue()))
            .andExpect(jsonPath("$.policyNumber").value(DEFAULT_POLICY_NUMBER))
            .andExpect(jsonPath("$.suffix").value(DEFAULT_SUFFIX))
            .andExpect(jsonPath("$.pricingGroup").value(DEFAULT_PRICING_GROUP))
            .andExpect(jsonPath("$.nextOfKin").value(DEFAULT_NEXT_OF_KIN))
            .andExpect(jsonPath("$.memberIdentifier").value(DEFAULT_MEMBER_IDENTIFIER))
            .andExpect(jsonPath("$.parentPolicy").value(DEFAULT_PARENT_POLICY))
            .andExpect(jsonPath("$.sponsorIdentifier").value(DEFAULT_SPONSOR_IDENTIFIER))
            .andExpect(jsonPath("$.sponsorType").value(DEFAULT_SPONSOR_TYPE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingPolicy() throws Exception {
        // Get the policy
        restPolicyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        int databaseSizeBeforeUpdate = policyRepository.findAll().size();

        // Update the policy
        Policy updatedPolicy = policyRepository.findById(policy.getId()).get();
        // Disconnect from session so that the updates on updatedPolicy are not directly saved in db
        em.detach(updatedPolicy);
        updatedPolicy
            .policyNumber(UPDATED_POLICY_NUMBER)
            .suffix(UPDATED_SUFFIX)
            .pricingGroup(UPDATED_PRICING_GROUP)
            .nextOfKin(UPDATED_NEXT_OF_KIN)
            .memberIdentifier(UPDATED_MEMBER_IDENTIFIER)
            .parentPolicy(UPDATED_PARENT_POLICY)
            .sponsorIdentifier(UPDATED_SPONSOR_IDENTIFIER)
            .sponsorType(UPDATED_SPONSOR_TYPE)
            .status(UPDATED_STATUS)
            .balance(UPDATED_BALANCE);

        restPolicyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPolicy.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPolicy))
            )
            .andExpect(status().isOk());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
        Policy testPolicy = policyList.get(policyList.size() - 1);
        assertThat(testPolicy.getPolicyNumber()).isEqualTo(UPDATED_POLICY_NUMBER);
        assertThat(testPolicy.getSuffix()).isEqualTo(UPDATED_SUFFIX);
        assertThat(testPolicy.getPricingGroup()).isEqualTo(UPDATED_PRICING_GROUP);
        assertThat(testPolicy.getNextOfKin()).isEqualTo(UPDATED_NEXT_OF_KIN);
        assertThat(testPolicy.getMemberIdentifier()).isEqualTo(UPDATED_MEMBER_IDENTIFIER);
        assertThat(testPolicy.getParentPolicy()).isEqualTo(UPDATED_PARENT_POLICY);
        assertThat(testPolicy.getSponsorIdentifier()).isEqualTo(UPDATED_SPONSOR_IDENTIFIER);
        assertThat(testPolicy.getSponsorType()).isEqualTo(UPDATED_SPONSOR_TYPE);
        assertThat(testPolicy.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPolicy.getBalance()).isEqualTo(UPDATED_BALANCE);
    }

    @Test
    @Transactional
    void putNonExistingPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();
        policy.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPolicyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, policy.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(policy))
            )
            .andExpect(status().isBadRequest());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();
        policy.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolicyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(policy))
            )
            .andExpect(status().isBadRequest());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();
        policy.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolicyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policy)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePolicyWithPatch() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        int databaseSizeBeforeUpdate = policyRepository.findAll().size();

        // Update the policy using partial update
        Policy partialUpdatedPolicy = new Policy();
        partialUpdatedPolicy.setId(policy.getId());

        partialUpdatedPolicy.pricingGroup(UPDATED_PRICING_GROUP).sponsorType(UPDATED_SPONSOR_TYPE).balance(UPDATED_BALANCE);

        restPolicyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPolicy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPolicy))
            )
            .andExpect(status().isOk());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
        Policy testPolicy = policyList.get(policyList.size() - 1);
        assertThat(testPolicy.getPolicyNumber()).isEqualTo(DEFAULT_POLICY_NUMBER);
        assertThat(testPolicy.getSuffix()).isEqualTo(DEFAULT_SUFFIX);
        assertThat(testPolicy.getPricingGroup()).isEqualTo(UPDATED_PRICING_GROUP);
        assertThat(testPolicy.getNextOfKin()).isEqualTo(DEFAULT_NEXT_OF_KIN);
        assertThat(testPolicy.getMemberIdentifier()).isEqualTo(DEFAULT_MEMBER_IDENTIFIER);
        assertThat(testPolicy.getParentPolicy()).isEqualTo(DEFAULT_PARENT_POLICY);
        assertThat(testPolicy.getSponsorIdentifier()).isEqualTo(DEFAULT_SPONSOR_IDENTIFIER);
        assertThat(testPolicy.getSponsorType()).isEqualTo(UPDATED_SPONSOR_TYPE);
        assertThat(testPolicy.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPolicy.getBalance()).isEqualTo(UPDATED_BALANCE);
    }

    @Test
    @Transactional
    void fullUpdatePolicyWithPatch() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        int databaseSizeBeforeUpdate = policyRepository.findAll().size();

        // Update the policy using partial update
        Policy partialUpdatedPolicy = new Policy();
        partialUpdatedPolicy.setId(policy.getId());

        partialUpdatedPolicy
            .policyNumber(UPDATED_POLICY_NUMBER)
            .suffix(UPDATED_SUFFIX)
            .pricingGroup(UPDATED_PRICING_GROUP)
            .nextOfKin(UPDATED_NEXT_OF_KIN)
            .memberIdentifier(UPDATED_MEMBER_IDENTIFIER)
            .parentPolicy(UPDATED_PARENT_POLICY)
            .sponsorIdentifier(UPDATED_SPONSOR_IDENTIFIER)
            .sponsorType(UPDATED_SPONSOR_TYPE)
            .status(UPDATED_STATUS)
            .balance(UPDATED_BALANCE);

        restPolicyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPolicy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPolicy))
            )
            .andExpect(status().isOk());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
        Policy testPolicy = policyList.get(policyList.size() - 1);
        assertThat(testPolicy.getPolicyNumber()).isEqualTo(UPDATED_POLICY_NUMBER);
        assertThat(testPolicy.getSuffix()).isEqualTo(UPDATED_SUFFIX);
        assertThat(testPolicy.getPricingGroup()).isEqualTo(UPDATED_PRICING_GROUP);
        assertThat(testPolicy.getNextOfKin()).isEqualTo(UPDATED_NEXT_OF_KIN);
        assertThat(testPolicy.getMemberIdentifier()).isEqualTo(UPDATED_MEMBER_IDENTIFIER);
        assertThat(testPolicy.getParentPolicy()).isEqualTo(UPDATED_PARENT_POLICY);
        assertThat(testPolicy.getSponsorIdentifier()).isEqualTo(UPDATED_SPONSOR_IDENTIFIER);
        assertThat(testPolicy.getSponsorType()).isEqualTo(UPDATED_SPONSOR_TYPE);
        assertThat(testPolicy.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPolicy.getBalance()).isEqualTo(UPDATED_BALANCE);
    }

    @Test
    @Transactional
    void patchNonExistingPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();
        policy.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPolicyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, policy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(policy))
            )
            .andExpect(status().isBadRequest());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();
        policy.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolicyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(policy))
            )
            .andExpect(status().isBadRequest());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();
        policy.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolicyMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(policy)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        int databaseSizeBeforeDelete = policyRepository.findAll().size();

        // Delete the policy
        restPolicyMockMvc
            .perform(delete(ENTITY_API_URL_ID, policy.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

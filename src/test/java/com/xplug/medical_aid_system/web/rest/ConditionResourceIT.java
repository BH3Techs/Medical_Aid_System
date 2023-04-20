package com.xplug.medical_aid_system.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.xplug.medical_aid_system.IntegrationTest;
import com.xplug.medical_aid_system.domain.Condition;
import com.xplug.medical_aid_system.repository.ConditionRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ConditionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConditionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/conditions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConditionRepository conditionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConditionMockMvc;

    private Condition condition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Condition createEntity(EntityManager em) {
        Condition condition = new Condition().name(DEFAULT_NAME).details(DEFAULT_DETAILS);
        return condition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Condition createUpdatedEntity(EntityManager em) {
        Condition condition = new Condition().name(UPDATED_NAME).details(UPDATED_DETAILS);
        return condition;
    }

    @BeforeEach
    public void initTest() {
        condition = createEntity(em);
    }

    @Test
    @Transactional
    void createCondition() throws Exception {
        int databaseSizeBeforeCreate = conditionRepository.findAll().size();
        // Create the Condition
        restConditionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(condition)))
            .andExpect(status().isCreated());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeCreate + 1);
        Condition testCondition = conditionList.get(conditionList.size() - 1);
        assertThat(testCondition.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCondition.getDetails()).isEqualTo(DEFAULT_DETAILS);
    }

    @Test
    @Transactional
    void createConditionWithExistingId() throws Exception {
        // Create the Condition with an existing ID
        condition.setId(1L);

        int databaseSizeBeforeCreate = conditionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConditionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(condition)))
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = conditionRepository.findAll().size();
        // set the field null
        condition.setName(null);

        // Create the Condition, which fails.

        restConditionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(condition)))
            .andExpect(status().isBadRequest());

        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllConditions() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList
        restConditionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(condition.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())));
    }

    @Test
    @Transactional
    void getCondition() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get the condition
        restConditionMockMvc
            .perform(get(ENTITY_API_URL_ID, condition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(condition.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCondition() throws Exception {
        // Get the condition
        restConditionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCondition() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();

        // Update the condition
        Condition updatedCondition = conditionRepository.findById(condition.getId()).get();
        // Disconnect from session so that the updates on updatedCondition are not directly saved in db
        em.detach(updatedCondition);
        updatedCondition.name(UPDATED_NAME).details(UPDATED_DETAILS);

        restConditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCondition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCondition))
            )
            .andExpect(status().isOk());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
        Condition testCondition = conditionList.get(conditionList.size() - 1);
        assertThat(testCondition.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCondition.getDetails()).isEqualTo(UPDATED_DETAILS);
    }

    @Test
    @Transactional
    void putNonExistingCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, condition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(condition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(condition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(condition)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConditionWithPatch() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();

        // Update the condition using partial update
        Condition partialUpdatedCondition = new Condition();
        partialUpdatedCondition.setId(condition.getId());

        partialUpdatedCondition.name(UPDATED_NAME);

        restConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCondition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCondition))
            )
            .andExpect(status().isOk());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
        Condition testCondition = conditionList.get(conditionList.size() - 1);
        assertThat(testCondition.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCondition.getDetails()).isEqualTo(DEFAULT_DETAILS);
    }

    @Test
    @Transactional
    void fullUpdateConditionWithPatch() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();

        // Update the condition using partial update
        Condition partialUpdatedCondition = new Condition();
        partialUpdatedCondition.setId(condition.getId());

        partialUpdatedCondition.name(UPDATED_NAME).details(UPDATED_DETAILS);

        restConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCondition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCondition))
            )
            .andExpect(status().isOk());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
        Condition testCondition = conditionList.get(conditionList.size() - 1);
        assertThat(testCondition.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCondition.getDetails()).isEqualTo(UPDATED_DETAILS);
    }

    @Test
    @Transactional
    void patchNonExistingCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, condition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(condition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(condition))
            )
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();
        condition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(condition))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCondition() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        int databaseSizeBeforeDelete = conditionRepository.findAll().size();

        // Delete the condition
        restConditionMockMvc
            .perform(delete(ENTITY_API_URL_ID, condition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.medical_aid_system.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.medical_aid_system.IntegrationTest;
import com.medical_aid_system.domain.NextOfKin;
import com.medical_aid_system.repository.NextOfKinRepository;
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
 * Integration tests for the {@link NextOfKinResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NextOfKinResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/next-of-kins";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NextOfKinRepository nextOfKinRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNextOfKinMockMvc;

    private NextOfKin nextOfKin;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NextOfKin createEntity(EntityManager em) {
        NextOfKin nextOfKin = new NextOfKin().name(DEFAULT_NAME).identifier(DEFAULT_IDENTIFIER);
        return nextOfKin;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NextOfKin createUpdatedEntity(EntityManager em) {
        NextOfKin nextOfKin = new NextOfKin().name(UPDATED_NAME).identifier(UPDATED_IDENTIFIER);
        return nextOfKin;
    }

    @BeforeEach
    public void initTest() {
        nextOfKin = createEntity(em);
    }

    @Test
    @Transactional
    void createNextOfKin() throws Exception {
        int databaseSizeBeforeCreate = nextOfKinRepository.findAll().size();
        // Create the NextOfKin
        restNextOfKinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nextOfKin)))
            .andExpect(status().isCreated());

        // Validate the NextOfKin in the database
        List<NextOfKin> nextOfKinList = nextOfKinRepository.findAll();
        assertThat(nextOfKinList).hasSize(databaseSizeBeforeCreate + 1);
        NextOfKin testNextOfKin = nextOfKinList.get(nextOfKinList.size() - 1);
        assertThat(testNextOfKin.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNextOfKin.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
    }

    @Test
    @Transactional
    void createNextOfKinWithExistingId() throws Exception {
        // Create the NextOfKin with an existing ID
        nextOfKin.setId(1L);

        int databaseSizeBeforeCreate = nextOfKinRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNextOfKinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nextOfKin)))
            .andExpect(status().isBadRequest());

        // Validate the NextOfKin in the database
        List<NextOfKin> nextOfKinList = nextOfKinRepository.findAll();
        assertThat(nextOfKinList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nextOfKinRepository.findAll().size();
        // set the field null
        nextOfKin.setName(null);

        // Create the NextOfKin, which fails.

        restNextOfKinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nextOfKin)))
            .andExpect(status().isBadRequest());

        List<NextOfKin> nextOfKinList = nextOfKinRepository.findAll();
        assertThat(nextOfKinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = nextOfKinRepository.findAll().size();
        // set the field null
        nextOfKin.setIdentifier(null);

        // Create the NextOfKin, which fails.

        restNextOfKinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nextOfKin)))
            .andExpect(status().isBadRequest());

        List<NextOfKin> nextOfKinList = nextOfKinRepository.findAll();
        assertThat(nextOfKinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNextOfKins() throws Exception {
        // Initialize the database
        nextOfKinRepository.saveAndFlush(nextOfKin);

        // Get all the nextOfKinList
        restNextOfKinMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nextOfKin.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)));
    }

    @Test
    @Transactional
    void getNextOfKin() throws Exception {
        // Initialize the database
        nextOfKinRepository.saveAndFlush(nextOfKin);

        // Get the nextOfKin
        restNextOfKinMockMvc
            .perform(get(ENTITY_API_URL_ID, nextOfKin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nextOfKin.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER));
    }

    @Test
    @Transactional
    void getNonExistingNextOfKin() throws Exception {
        // Get the nextOfKin
        restNextOfKinMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNextOfKin() throws Exception {
        // Initialize the database
        nextOfKinRepository.saveAndFlush(nextOfKin);

        int databaseSizeBeforeUpdate = nextOfKinRepository.findAll().size();

        // Update the nextOfKin
        NextOfKin updatedNextOfKin = nextOfKinRepository.findById(nextOfKin.getId()).get();
        // Disconnect from session so that the updates on updatedNextOfKin are not directly saved in db
        em.detach(updatedNextOfKin);
        updatedNextOfKin.name(UPDATED_NAME).identifier(UPDATED_IDENTIFIER);

        restNextOfKinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNextOfKin.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNextOfKin))
            )
            .andExpect(status().isOk());

        // Validate the NextOfKin in the database
        List<NextOfKin> nextOfKinList = nextOfKinRepository.findAll();
        assertThat(nextOfKinList).hasSize(databaseSizeBeforeUpdate);
        NextOfKin testNextOfKin = nextOfKinList.get(nextOfKinList.size() - 1);
        assertThat(testNextOfKin.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNextOfKin.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    void putNonExistingNextOfKin() throws Exception {
        int databaseSizeBeforeUpdate = nextOfKinRepository.findAll().size();
        nextOfKin.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNextOfKinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nextOfKin.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nextOfKin))
            )
            .andExpect(status().isBadRequest());

        // Validate the NextOfKin in the database
        List<NextOfKin> nextOfKinList = nextOfKinRepository.findAll();
        assertThat(nextOfKinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNextOfKin() throws Exception {
        int databaseSizeBeforeUpdate = nextOfKinRepository.findAll().size();
        nextOfKin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNextOfKinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nextOfKin))
            )
            .andExpect(status().isBadRequest());

        // Validate the NextOfKin in the database
        List<NextOfKin> nextOfKinList = nextOfKinRepository.findAll();
        assertThat(nextOfKinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNextOfKin() throws Exception {
        int databaseSizeBeforeUpdate = nextOfKinRepository.findAll().size();
        nextOfKin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNextOfKinMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nextOfKin)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NextOfKin in the database
        List<NextOfKin> nextOfKinList = nextOfKinRepository.findAll();
        assertThat(nextOfKinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNextOfKinWithPatch() throws Exception {
        // Initialize the database
        nextOfKinRepository.saveAndFlush(nextOfKin);

        int databaseSizeBeforeUpdate = nextOfKinRepository.findAll().size();

        // Update the nextOfKin using partial update
        NextOfKin partialUpdatedNextOfKin = new NextOfKin();
        partialUpdatedNextOfKin.setId(nextOfKin.getId());

        partialUpdatedNextOfKin.name(UPDATED_NAME).identifier(UPDATED_IDENTIFIER);

        restNextOfKinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNextOfKin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNextOfKin))
            )
            .andExpect(status().isOk());

        // Validate the NextOfKin in the database
        List<NextOfKin> nextOfKinList = nextOfKinRepository.findAll();
        assertThat(nextOfKinList).hasSize(databaseSizeBeforeUpdate);
        NextOfKin testNextOfKin = nextOfKinList.get(nextOfKinList.size() - 1);
        assertThat(testNextOfKin.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNextOfKin.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    void fullUpdateNextOfKinWithPatch() throws Exception {
        // Initialize the database
        nextOfKinRepository.saveAndFlush(nextOfKin);

        int databaseSizeBeforeUpdate = nextOfKinRepository.findAll().size();

        // Update the nextOfKin using partial update
        NextOfKin partialUpdatedNextOfKin = new NextOfKin();
        partialUpdatedNextOfKin.setId(nextOfKin.getId());

        partialUpdatedNextOfKin.name(UPDATED_NAME).identifier(UPDATED_IDENTIFIER);

        restNextOfKinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNextOfKin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNextOfKin))
            )
            .andExpect(status().isOk());

        // Validate the NextOfKin in the database
        List<NextOfKin> nextOfKinList = nextOfKinRepository.findAll();
        assertThat(nextOfKinList).hasSize(databaseSizeBeforeUpdate);
        NextOfKin testNextOfKin = nextOfKinList.get(nextOfKinList.size() - 1);
        assertThat(testNextOfKin.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNextOfKin.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    void patchNonExistingNextOfKin() throws Exception {
        int databaseSizeBeforeUpdate = nextOfKinRepository.findAll().size();
        nextOfKin.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNextOfKinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nextOfKin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nextOfKin))
            )
            .andExpect(status().isBadRequest());

        // Validate the NextOfKin in the database
        List<NextOfKin> nextOfKinList = nextOfKinRepository.findAll();
        assertThat(nextOfKinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNextOfKin() throws Exception {
        int databaseSizeBeforeUpdate = nextOfKinRepository.findAll().size();
        nextOfKin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNextOfKinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nextOfKin))
            )
            .andExpect(status().isBadRequest());

        // Validate the NextOfKin in the database
        List<NextOfKin> nextOfKinList = nextOfKinRepository.findAll();
        assertThat(nextOfKinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNextOfKin() throws Exception {
        int databaseSizeBeforeUpdate = nextOfKinRepository.findAll().size();
        nextOfKin.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNextOfKinMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nextOfKin))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NextOfKin in the database
        List<NextOfKin> nextOfKinList = nextOfKinRepository.findAll();
        assertThat(nextOfKinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNextOfKin() throws Exception {
        // Initialize the database
        nextOfKinRepository.saveAndFlush(nextOfKin);

        int databaseSizeBeforeDelete = nextOfKinRepository.findAll().size();

        // Delete the nextOfKin
        restNextOfKinMockMvc
            .perform(delete(ENTITY_API_URL_ID, nextOfKin.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NextOfKin> nextOfKinList = nextOfKinRepository.findAll();
        assertThat(nextOfKinList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

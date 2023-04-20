package com.xplug.medical_aid_system.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.xplug.medical_aid_system.IntegrationTest;
import com.xplug.medical_aid_system.domain.SponsorAdministration;
import com.xplug.medical_aid_system.domain.enumeration.SponsorType;
import com.xplug.medical_aid_system.repository.SponsorAdministrationRepository;
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
 * Integration tests for the {@link SponsorAdministrationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SponsorAdministrationResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INITIAL = "AAAAAAAAAA";
    private static final String UPDATED_INITIAL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SPONSOR_ID = "AAAAAAAAAA";
    private static final String UPDATED_SPONSOR_ID = "BBBBBBBBBB";

    private static final SponsorType DEFAULT_SPONSOR_TYPE = SponsorType.INDIVIDUAL;
    private static final SponsorType UPDATED_SPONSOR_TYPE = SponsorType.CORPORATE;

    private static final String ENTITY_API_URL = "/api/sponsor-administrations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SponsorAdministrationRepository sponsorAdministrationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSponsorAdministrationMockMvc;

    private SponsorAdministration sponsorAdministration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SponsorAdministration createEntity(EntityManager em) {
        SponsorAdministration sponsorAdministration = new SponsorAdministration()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .initial(DEFAULT_INITIAL)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .sponsorId(DEFAULT_SPONSOR_ID)
            .sponsorType(DEFAULT_SPONSOR_TYPE);
        return sponsorAdministration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SponsorAdministration createUpdatedEntity(EntityManager em) {
        SponsorAdministration sponsorAdministration = new SponsorAdministration()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .initial(UPDATED_INITIAL)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .sponsorId(UPDATED_SPONSOR_ID)
            .sponsorType(UPDATED_SPONSOR_TYPE);
        return sponsorAdministration;
    }

    @BeforeEach
    public void initTest() {
        sponsorAdministration = createEntity(em);
    }

    @Test
    @Transactional
    void createSponsorAdministration() throws Exception {
        int databaseSizeBeforeCreate = sponsorAdministrationRepository.findAll().size();
        // Create the SponsorAdministration
        restSponsorAdministrationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sponsorAdministration))
            )
            .andExpect(status().isCreated());

        // Validate the SponsorAdministration in the database
        List<SponsorAdministration> sponsorAdministrationList = sponsorAdministrationRepository.findAll();
        assertThat(sponsorAdministrationList).hasSize(databaseSizeBeforeCreate + 1);
        SponsorAdministration testSponsorAdministration = sponsorAdministrationList.get(sponsorAdministrationList.size() - 1);
        assertThat(testSponsorAdministration.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testSponsorAdministration.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testSponsorAdministration.getInitial()).isEqualTo(DEFAULT_INITIAL);
        assertThat(testSponsorAdministration.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testSponsorAdministration.getSponsorId()).isEqualTo(DEFAULT_SPONSOR_ID);
        assertThat(testSponsorAdministration.getSponsorType()).isEqualTo(DEFAULT_SPONSOR_TYPE);
    }

    @Test
    @Transactional
    void createSponsorAdministrationWithExistingId() throws Exception {
        // Create the SponsorAdministration with an existing ID
        sponsorAdministration.setId(1L);

        int databaseSizeBeforeCreate = sponsorAdministrationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSponsorAdministrationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sponsorAdministration))
            )
            .andExpect(status().isBadRequest());

        // Validate the SponsorAdministration in the database
        List<SponsorAdministration> sponsorAdministrationList = sponsorAdministrationRepository.findAll();
        assertThat(sponsorAdministrationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sponsorAdministrationRepository.findAll().size();
        // set the field null
        sponsorAdministration.setFirstName(null);

        // Create the SponsorAdministration, which fails.

        restSponsorAdministrationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sponsorAdministration))
            )
            .andExpect(status().isBadRequest());

        List<SponsorAdministration> sponsorAdministrationList = sponsorAdministrationRepository.findAll();
        assertThat(sponsorAdministrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sponsorAdministrationRepository.findAll().size();
        // set the field null
        sponsorAdministration.setLastName(null);

        // Create the SponsorAdministration, which fails.

        restSponsorAdministrationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sponsorAdministration))
            )
            .andExpect(status().isBadRequest());

        List<SponsorAdministration> sponsorAdministrationList = sponsorAdministrationRepository.findAll();
        assertThat(sponsorAdministrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = sponsorAdministrationRepository.findAll().size();
        // set the field null
        sponsorAdministration.setDateOfBirth(null);

        // Create the SponsorAdministration, which fails.

        restSponsorAdministrationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sponsorAdministration))
            )
            .andExpect(status().isBadRequest());

        List<SponsorAdministration> sponsorAdministrationList = sponsorAdministrationRepository.findAll();
        assertThat(sponsorAdministrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSponsorIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = sponsorAdministrationRepository.findAll().size();
        // set the field null
        sponsorAdministration.setSponsorId(null);

        // Create the SponsorAdministration, which fails.

        restSponsorAdministrationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sponsorAdministration))
            )
            .andExpect(status().isBadRequest());

        List<SponsorAdministration> sponsorAdministrationList = sponsorAdministrationRepository.findAll();
        assertThat(sponsorAdministrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSponsorTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sponsorAdministrationRepository.findAll().size();
        // set the field null
        sponsorAdministration.setSponsorType(null);

        // Create the SponsorAdministration, which fails.

        restSponsorAdministrationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sponsorAdministration))
            )
            .andExpect(status().isBadRequest());

        List<SponsorAdministration> sponsorAdministrationList = sponsorAdministrationRepository.findAll();
        assertThat(sponsorAdministrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSponsorAdministrations() throws Exception {
        // Initialize the database
        sponsorAdministrationRepository.saveAndFlush(sponsorAdministration);

        // Get all the sponsorAdministrationList
        restSponsorAdministrationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sponsorAdministration.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].initial").value(hasItem(DEFAULT_INITIAL)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].sponsorId").value(hasItem(DEFAULT_SPONSOR_ID)))
            .andExpect(jsonPath("$.[*].sponsorType").value(hasItem(DEFAULT_SPONSOR_TYPE.toString())));
    }

    @Test
    @Transactional
    void getSponsorAdministration() throws Exception {
        // Initialize the database
        sponsorAdministrationRepository.saveAndFlush(sponsorAdministration);

        // Get the sponsorAdministration
        restSponsorAdministrationMockMvc
            .perform(get(ENTITY_API_URL_ID, sponsorAdministration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sponsorAdministration.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.initial").value(DEFAULT_INITIAL))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.sponsorId").value(DEFAULT_SPONSOR_ID))
            .andExpect(jsonPath("$.sponsorType").value(DEFAULT_SPONSOR_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSponsorAdministration() throws Exception {
        // Get the sponsorAdministration
        restSponsorAdministrationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSponsorAdministration() throws Exception {
        // Initialize the database
        sponsorAdministrationRepository.saveAndFlush(sponsorAdministration);

        int databaseSizeBeforeUpdate = sponsorAdministrationRepository.findAll().size();

        // Update the sponsorAdministration
        SponsorAdministration updatedSponsorAdministration = sponsorAdministrationRepository.findById(sponsorAdministration.getId()).get();
        // Disconnect from session so that the updates on updatedSponsorAdministration are not directly saved in db
        em.detach(updatedSponsorAdministration);
        updatedSponsorAdministration
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .initial(UPDATED_INITIAL)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .sponsorId(UPDATED_SPONSOR_ID)
            .sponsorType(UPDATED_SPONSOR_TYPE);

        restSponsorAdministrationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSponsorAdministration.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSponsorAdministration))
            )
            .andExpect(status().isOk());

        // Validate the SponsorAdministration in the database
        List<SponsorAdministration> sponsorAdministrationList = sponsorAdministrationRepository.findAll();
        assertThat(sponsorAdministrationList).hasSize(databaseSizeBeforeUpdate);
        SponsorAdministration testSponsorAdministration = sponsorAdministrationList.get(sponsorAdministrationList.size() - 1);
        assertThat(testSponsorAdministration.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testSponsorAdministration.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testSponsorAdministration.getInitial()).isEqualTo(UPDATED_INITIAL);
        assertThat(testSponsorAdministration.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testSponsorAdministration.getSponsorId()).isEqualTo(UPDATED_SPONSOR_ID);
        assertThat(testSponsorAdministration.getSponsorType()).isEqualTo(UPDATED_SPONSOR_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingSponsorAdministration() throws Exception {
        int databaseSizeBeforeUpdate = sponsorAdministrationRepository.findAll().size();
        sponsorAdministration.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSponsorAdministrationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sponsorAdministration.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sponsorAdministration))
            )
            .andExpect(status().isBadRequest());

        // Validate the SponsorAdministration in the database
        List<SponsorAdministration> sponsorAdministrationList = sponsorAdministrationRepository.findAll();
        assertThat(sponsorAdministrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSponsorAdministration() throws Exception {
        int databaseSizeBeforeUpdate = sponsorAdministrationRepository.findAll().size();
        sponsorAdministration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSponsorAdministrationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sponsorAdministration))
            )
            .andExpect(status().isBadRequest());

        // Validate the SponsorAdministration in the database
        List<SponsorAdministration> sponsorAdministrationList = sponsorAdministrationRepository.findAll();
        assertThat(sponsorAdministrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSponsorAdministration() throws Exception {
        int databaseSizeBeforeUpdate = sponsorAdministrationRepository.findAll().size();
        sponsorAdministration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSponsorAdministrationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sponsorAdministration))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SponsorAdministration in the database
        List<SponsorAdministration> sponsorAdministrationList = sponsorAdministrationRepository.findAll();
        assertThat(sponsorAdministrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSponsorAdministrationWithPatch() throws Exception {
        // Initialize the database
        sponsorAdministrationRepository.saveAndFlush(sponsorAdministration);

        int databaseSizeBeforeUpdate = sponsorAdministrationRepository.findAll().size();

        // Update the sponsorAdministration using partial update
        SponsorAdministration partialUpdatedSponsorAdministration = new SponsorAdministration();
        partialUpdatedSponsorAdministration.setId(sponsorAdministration.getId());

        partialUpdatedSponsorAdministration.firstName(UPDATED_FIRST_NAME).dateOfBirth(UPDATED_DATE_OF_BIRTH);

        restSponsorAdministrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSponsorAdministration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSponsorAdministration))
            )
            .andExpect(status().isOk());

        // Validate the SponsorAdministration in the database
        List<SponsorAdministration> sponsorAdministrationList = sponsorAdministrationRepository.findAll();
        assertThat(sponsorAdministrationList).hasSize(databaseSizeBeforeUpdate);
        SponsorAdministration testSponsorAdministration = sponsorAdministrationList.get(sponsorAdministrationList.size() - 1);
        assertThat(testSponsorAdministration.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testSponsorAdministration.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testSponsorAdministration.getInitial()).isEqualTo(DEFAULT_INITIAL);
        assertThat(testSponsorAdministration.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testSponsorAdministration.getSponsorId()).isEqualTo(DEFAULT_SPONSOR_ID);
        assertThat(testSponsorAdministration.getSponsorType()).isEqualTo(DEFAULT_SPONSOR_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateSponsorAdministrationWithPatch() throws Exception {
        // Initialize the database
        sponsorAdministrationRepository.saveAndFlush(sponsorAdministration);

        int databaseSizeBeforeUpdate = sponsorAdministrationRepository.findAll().size();

        // Update the sponsorAdministration using partial update
        SponsorAdministration partialUpdatedSponsorAdministration = new SponsorAdministration();
        partialUpdatedSponsorAdministration.setId(sponsorAdministration.getId());

        partialUpdatedSponsorAdministration
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .initial(UPDATED_INITIAL)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .sponsorId(UPDATED_SPONSOR_ID)
            .sponsorType(UPDATED_SPONSOR_TYPE);

        restSponsorAdministrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSponsorAdministration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSponsorAdministration))
            )
            .andExpect(status().isOk());

        // Validate the SponsorAdministration in the database
        List<SponsorAdministration> sponsorAdministrationList = sponsorAdministrationRepository.findAll();
        assertThat(sponsorAdministrationList).hasSize(databaseSizeBeforeUpdate);
        SponsorAdministration testSponsorAdministration = sponsorAdministrationList.get(sponsorAdministrationList.size() - 1);
        assertThat(testSponsorAdministration.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testSponsorAdministration.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testSponsorAdministration.getInitial()).isEqualTo(UPDATED_INITIAL);
        assertThat(testSponsorAdministration.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testSponsorAdministration.getSponsorId()).isEqualTo(UPDATED_SPONSOR_ID);
        assertThat(testSponsorAdministration.getSponsorType()).isEqualTo(UPDATED_SPONSOR_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingSponsorAdministration() throws Exception {
        int databaseSizeBeforeUpdate = sponsorAdministrationRepository.findAll().size();
        sponsorAdministration.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSponsorAdministrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sponsorAdministration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sponsorAdministration))
            )
            .andExpect(status().isBadRequest());

        // Validate the SponsorAdministration in the database
        List<SponsorAdministration> sponsorAdministrationList = sponsorAdministrationRepository.findAll();
        assertThat(sponsorAdministrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSponsorAdministration() throws Exception {
        int databaseSizeBeforeUpdate = sponsorAdministrationRepository.findAll().size();
        sponsorAdministration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSponsorAdministrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sponsorAdministration))
            )
            .andExpect(status().isBadRequest());

        // Validate the SponsorAdministration in the database
        List<SponsorAdministration> sponsorAdministrationList = sponsorAdministrationRepository.findAll();
        assertThat(sponsorAdministrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSponsorAdministration() throws Exception {
        int databaseSizeBeforeUpdate = sponsorAdministrationRepository.findAll().size();
        sponsorAdministration.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSponsorAdministrationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sponsorAdministration))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SponsorAdministration in the database
        List<SponsorAdministration> sponsorAdministrationList = sponsorAdministrationRepository.findAll();
        assertThat(sponsorAdministrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSponsorAdministration() throws Exception {
        // Initialize the database
        sponsorAdministrationRepository.saveAndFlush(sponsorAdministration);

        int databaseSizeBeforeDelete = sponsorAdministrationRepository.findAll().size();

        // Delete the sponsorAdministration
        restSponsorAdministrationMockMvc
            .perform(delete(ENTITY_API_URL_ID, sponsorAdministration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SponsorAdministration> sponsorAdministrationList = sponsorAdministrationRepository.findAll();
        assertThat(sponsorAdministrationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

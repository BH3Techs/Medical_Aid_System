package com.xplug.medical_aid_system.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.xplug.medical_aid_system.IntegrationTest;
import com.xplug.medical_aid_system.domain.Individual;
import com.xplug.medical_aid_system.repository.IndividualRepository;
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
 * Integration tests for the {@link IndividualResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndividualResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INITIAL = "AAAAAAAAAA";
    private static final String UPDATED_INITIAL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONAL_ID = "81-77237K64";
    private static final String UPDATED_NATIONAL_ID = "71-21754v08";

    private static final String ENTITY_API_URL = "/api/individuals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IndividualRepository individualRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndividualMockMvc;

    private Individual individual;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Individual createEntity(EntityManager em) {
        Individual individual = new Individual()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .initial(DEFAULT_INITIAL)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .gender(DEFAULT_GENDER)
            .nationalId(DEFAULT_NATIONAL_ID);
        return individual;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Individual createUpdatedEntity(EntityManager em) {
        Individual individual = new Individual()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .initial(UPDATED_INITIAL)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER)
            .nationalId(UPDATED_NATIONAL_ID);
        return individual;
    }

    @BeforeEach
    public void initTest() {
        individual = createEntity(em);
    }

    @Test
    @Transactional
    void createIndividual() throws Exception {
        int databaseSizeBeforeCreate = individualRepository.findAll().size();
        // Create the Individual
        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individual)))
            .andExpect(status().isCreated());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeCreate + 1);
        Individual testIndividual = individualList.get(individualList.size() - 1);
        assertThat(testIndividual.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testIndividual.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testIndividual.getInitial()).isEqualTo(DEFAULT_INITIAL);
        assertThat(testIndividual.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testIndividual.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testIndividual.getNationalId()).isEqualTo(DEFAULT_NATIONAL_ID);
    }

    @Test
    @Transactional
    void createIndividualWithExistingId() throws Exception {
        // Create the Individual with an existing ID
        individual.setId(1L);

        int databaseSizeBeforeCreate = individualRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individual)))
            .andExpect(status().isBadRequest());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = individualRepository.findAll().size();
        // set the field null
        individual.setFirstName(null);

        // Create the Individual, which fails.

        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individual)))
            .andExpect(status().isBadRequest());

        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = individualRepository.findAll().size();
        // set the field null
        individual.setLastName(null);

        // Create the Individual, which fails.

        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individual)))
            .andExpect(status().isBadRequest());

        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = individualRepository.findAll().size();
        // set the field null
        individual.setDateOfBirth(null);

        // Create the Individual, which fails.

        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individual)))
            .andExpect(status().isBadRequest());

        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIndividuals() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList
        restIndividualMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(individual.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].initial").value(hasItem(DEFAULT_INITIAL)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].nationalId").value(hasItem(DEFAULT_NATIONAL_ID)));
    }

    @Test
    @Transactional
    void getIndividual() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get the individual
        restIndividualMockMvc
            .perform(get(ENTITY_API_URL_ID, individual.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(individual.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.initial").value(DEFAULT_INITIAL))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.nationalId").value(DEFAULT_NATIONAL_ID));
    }

    @Test
    @Transactional
    void getNonExistingIndividual() throws Exception {
        // Get the individual
        restIndividualMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIndividual() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        int databaseSizeBeforeUpdate = individualRepository.findAll().size();

        // Update the individual
        Individual updatedIndividual = individualRepository.findById(individual.getId()).get();
        // Disconnect from session so that the updates on updatedIndividual are not directly saved in db
        em.detach(updatedIndividual);
        updatedIndividual
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .initial(UPDATED_INITIAL)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER)
            .nationalId(UPDATED_NATIONAL_ID);

        restIndividualMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIndividual.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIndividual))
            )
            .andExpect(status().isOk());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
        Individual testIndividual = individualList.get(individualList.size() - 1);
        assertThat(testIndividual.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testIndividual.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testIndividual.getInitial()).isEqualTo(UPDATED_INITIAL);
        assertThat(testIndividual.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testIndividual.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testIndividual.getNationalId()).isEqualTo(UPDATED_NATIONAL_ID);
    }

    @Test
    @Transactional
    void putNonExistingIndividual() throws Exception {
        int databaseSizeBeforeUpdate = individualRepository.findAll().size();
        individual.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndividualMockMvc
            .perform(
                put(ENTITY_API_URL_ID, individual.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(individual))
            )
            .andExpect(status().isBadRequest());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndividual() throws Exception {
        int databaseSizeBeforeUpdate = individualRepository.findAll().size();
        individual.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndividualMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(individual))
            )
            .andExpect(status().isBadRequest());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndividual() throws Exception {
        int databaseSizeBeforeUpdate = individualRepository.findAll().size();
        individual.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndividualMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individual)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndividualWithPatch() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        int databaseSizeBeforeUpdate = individualRepository.findAll().size();

        // Update the individual using partial update
        Individual partialUpdatedIndividual = new Individual();
        partialUpdatedIndividual.setId(individual.getId());

        restIndividualMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndividual.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndividual))
            )
            .andExpect(status().isOk());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
        Individual testIndividual = individualList.get(individualList.size() - 1);
        assertThat(testIndividual.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testIndividual.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testIndividual.getInitial()).isEqualTo(DEFAULT_INITIAL);
        assertThat(testIndividual.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testIndividual.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testIndividual.getNationalId()).isEqualTo(DEFAULT_NATIONAL_ID);
    }

    @Test
    @Transactional
    void fullUpdateIndividualWithPatch() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        int databaseSizeBeforeUpdate = individualRepository.findAll().size();

        // Update the individual using partial update
        Individual partialUpdatedIndividual = new Individual();
        partialUpdatedIndividual.setId(individual.getId());

        partialUpdatedIndividual
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .initial(UPDATED_INITIAL)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER)
            .nationalId(UPDATED_NATIONAL_ID);

        restIndividualMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndividual.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndividual))
            )
            .andExpect(status().isOk());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
        Individual testIndividual = individualList.get(individualList.size() - 1);
        assertThat(testIndividual.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testIndividual.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testIndividual.getInitial()).isEqualTo(UPDATED_INITIAL);
        assertThat(testIndividual.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testIndividual.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testIndividual.getNationalId()).isEqualTo(UPDATED_NATIONAL_ID);
    }

    @Test
    @Transactional
    void patchNonExistingIndividual() throws Exception {
        int databaseSizeBeforeUpdate = individualRepository.findAll().size();
        individual.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndividualMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, individual.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(individual))
            )
            .andExpect(status().isBadRequest());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndividual() throws Exception {
        int databaseSizeBeforeUpdate = individualRepository.findAll().size();
        individual.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndividualMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(individual))
            )
            .andExpect(status().isBadRequest());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndividual() throws Exception {
        int databaseSizeBeforeUpdate = individualRepository.findAll().size();
        individual.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndividualMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(individual))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndividual() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        int databaseSizeBeforeDelete = individualRepository.findAll().size();

        // Delete the individual
        restIndividualMockMvc
            .perform(delete(ENTITY_API_URL_ID, individual.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

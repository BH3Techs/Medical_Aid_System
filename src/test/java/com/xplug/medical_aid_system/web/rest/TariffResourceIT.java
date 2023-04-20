package com.xplug.medical_aid_system.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.xplug.medical_aid_system.IntegrationTest;
import com.xplug.medical_aid_system.domain.Tariff;
import com.xplug.medical_aid_system.repository.TariffRepository;
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
 * Integration tests for the {@link TariffResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TariffResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/tariffs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TariffRepository tariffRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTariffMockMvc;

    private Tariff tariff;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tariff createEntity(EntityManager em) {
        Tariff tariff = new Tariff().name(DEFAULT_NAME).price(DEFAULT_PRICE).active(DEFAULT_ACTIVE);
        return tariff;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tariff createUpdatedEntity(EntityManager em) {
        Tariff tariff = new Tariff().name(UPDATED_NAME).price(UPDATED_PRICE).active(UPDATED_ACTIVE);
        return tariff;
    }

    @BeforeEach
    public void initTest() {
        tariff = createEntity(em);
    }

    @Test
    @Transactional
    void createTariff() throws Exception {
        int databaseSizeBeforeCreate = tariffRepository.findAll().size();
        // Create the Tariff
        restTariffMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tariff)))
            .andExpect(status().isCreated());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeCreate + 1);
        Tariff testTariff = tariffList.get(tariffList.size() - 1);
        assertThat(testTariff.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTariff.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTariff.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    void createTariffWithExistingId() throws Exception {
        // Create the Tariff with an existing ID
        tariff.setId(1L);

        int databaseSizeBeforeCreate = tariffRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTariffMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tariff)))
            .andExpect(status().isBadRequest());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tariffRepository.findAll().size();
        // set the field null
        tariff.setName(null);

        // Create the Tariff, which fails.

        restTariffMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tariff)))
            .andExpect(status().isBadRequest());

        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = tariffRepository.findAll().size();
        // set the field null
        tariff.setActive(null);

        // Create the Tariff, which fails.

        restTariffMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tariff)))
            .andExpect(status().isBadRequest());

        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTariffs() throws Exception {
        // Initialize the database
        tariffRepository.saveAndFlush(tariff);

        // Get all the tariffList
        restTariffMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tariff.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getTariff() throws Exception {
        // Initialize the database
        tariffRepository.saveAndFlush(tariff);

        // Get the tariff
        restTariffMockMvc
            .perform(get(ENTITY_API_URL_ID, tariff.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tariff.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingTariff() throws Exception {
        // Get the tariff
        restTariffMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTariff() throws Exception {
        // Initialize the database
        tariffRepository.saveAndFlush(tariff);

        int databaseSizeBeforeUpdate = tariffRepository.findAll().size();

        // Update the tariff
        Tariff updatedTariff = tariffRepository.findById(tariff.getId()).get();
        // Disconnect from session so that the updates on updatedTariff are not directly saved in db
        em.detach(updatedTariff);
        updatedTariff.name(UPDATED_NAME).price(UPDATED_PRICE).active(UPDATED_ACTIVE);

        restTariffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTariff.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTariff))
            )
            .andExpect(status().isOk());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeUpdate);
        Tariff testTariff = tariffList.get(tariffList.size() - 1);
        assertThat(testTariff.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTariff.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTariff.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingTariff() throws Exception {
        int databaseSizeBeforeUpdate = tariffRepository.findAll().size();
        tariff.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTariffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tariff.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tariff))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTariff() throws Exception {
        int databaseSizeBeforeUpdate = tariffRepository.findAll().size();
        tariff.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTariffMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tariff))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTariff() throws Exception {
        int databaseSizeBeforeUpdate = tariffRepository.findAll().size();
        tariff.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTariffMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tariff)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTariffWithPatch() throws Exception {
        // Initialize the database
        tariffRepository.saveAndFlush(tariff);

        int databaseSizeBeforeUpdate = tariffRepository.findAll().size();

        // Update the tariff using partial update
        Tariff partialUpdatedTariff = new Tariff();
        partialUpdatedTariff.setId(tariff.getId());

        partialUpdatedTariff.active(UPDATED_ACTIVE);

        restTariffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTariff.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTariff))
            )
            .andExpect(status().isOk());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeUpdate);
        Tariff testTariff = tariffList.get(tariffList.size() - 1);
        assertThat(testTariff.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTariff.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTariff.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateTariffWithPatch() throws Exception {
        // Initialize the database
        tariffRepository.saveAndFlush(tariff);

        int databaseSizeBeforeUpdate = tariffRepository.findAll().size();

        // Update the tariff using partial update
        Tariff partialUpdatedTariff = new Tariff();
        partialUpdatedTariff.setId(tariff.getId());

        partialUpdatedTariff.name(UPDATED_NAME).price(UPDATED_PRICE).active(UPDATED_ACTIVE);

        restTariffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTariff.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTariff))
            )
            .andExpect(status().isOk());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeUpdate);
        Tariff testTariff = tariffList.get(tariffList.size() - 1);
        assertThat(testTariff.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTariff.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTariff.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingTariff() throws Exception {
        int databaseSizeBeforeUpdate = tariffRepository.findAll().size();
        tariff.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTariffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tariff.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tariff))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTariff() throws Exception {
        int databaseSizeBeforeUpdate = tariffRepository.findAll().size();
        tariff.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTariffMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tariff))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTariff() throws Exception {
        int databaseSizeBeforeUpdate = tariffRepository.findAll().size();
        tariff.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTariffMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tariff)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTariff() throws Exception {
        // Initialize the database
        tariffRepository.saveAndFlush(tariff);

        int databaseSizeBeforeDelete = tariffRepository.findAll().size();

        // Delete the tariff
        restTariffMockMvc
            .perform(delete(ENTITY_API_URL_ID, tariff.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

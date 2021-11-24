package com.telvendas.discadora.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.telvendas.discadora.IntegrationTest;
import com.telvendas.discadora.domain.Filial;
import com.telvendas.discadora.repository.FilialRepository;
import com.telvendas.discadora.service.dto.FilialDTO;
import com.telvendas.discadora.service.mapper.FilialMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link FilialResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FilialResourceIT {

    private static final String DEFAULT_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBB";

    private static final String DEFAULT_RAZAO_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZAO_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_FANTASIA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_FANTASIA = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/filials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FilialRepository filialRepository;

    @Autowired
    private FilialMapper filialMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFilialMockMvc;

    private Filial filial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Filial createEntity(EntityManager em) {
        Filial filial = new Filial()
            .cnpj(DEFAULT_CNPJ)
            .razaoSocial(DEFAULT_RAZAO_SOCIAL)
            .nomeFantasia(DEFAULT_NOME_FANTASIA)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return filial;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Filial createUpdatedEntity(EntityManager em) {
        Filial filial = new Filial()
            .cnpj(UPDATED_CNPJ)
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .nomeFantasia(UPDATED_NOME_FANTASIA)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return filial;
    }

    @BeforeEach
    public void initTest() {
        filial = createEntity(em);
    }

    @Test
    @Transactional
    void createFilial() throws Exception {
        int databaseSizeBeforeCreate = filialRepository.findAll().size();
        // Create the Filial
        FilialDTO filialDTO = filialMapper.toDto(filial);
        restFilialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(filialDTO)))
            .andExpect(status().isCreated());

        // Validate the Filial in the database
        List<Filial> filialList = filialRepository.findAll();
        assertThat(filialList).hasSize(databaseSizeBeforeCreate + 1);
        Filial testFilial = filialList.get(filialList.size() - 1);
        assertThat(testFilial.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testFilial.getRazaoSocial()).isEqualTo(DEFAULT_RAZAO_SOCIAL);
        assertThat(testFilial.getNomeFantasia()).isEqualTo(DEFAULT_NOME_FANTASIA);
        assertThat(testFilial.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFilial.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFilial.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testFilial.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createFilialWithExistingId() throws Exception {
        // Create the Filial with an existing ID
        filial.setId(1L);
        FilialDTO filialDTO = filialMapper.toDto(filial);

        int databaseSizeBeforeCreate = filialRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(filialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Filial in the database
        List<Filial> filialList = filialRepository.findAll();
        assertThat(filialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFilials() throws Exception {
        // Initialize the database
        filialRepository.saveAndFlush(filial);

        // Get all the filialList
        restFilialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filial.getId().intValue())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].razaoSocial").value(hasItem(DEFAULT_RAZAO_SOCIAL)))
            .andExpect(jsonPath("$.[*].nomeFantasia").value(hasItem(DEFAULT_NOME_FANTASIA)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getFilial() throws Exception {
        // Initialize the database
        filialRepository.saveAndFlush(filial);

        // Get the filial
        restFilialMockMvc
            .perform(get(ENTITY_API_URL_ID, filial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(filial.getId().intValue()))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ))
            .andExpect(jsonPath("$.razaoSocial").value(DEFAULT_RAZAO_SOCIAL))
            .andExpect(jsonPath("$.nomeFantasia").value(DEFAULT_NOME_FANTASIA))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFilial() throws Exception {
        // Get the filial
        restFilialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFilial() throws Exception {
        // Initialize the database
        filialRepository.saveAndFlush(filial);

        int databaseSizeBeforeUpdate = filialRepository.findAll().size();

        // Update the filial
        Filial updatedFilial = filialRepository.findById(filial.getId()).get();
        // Disconnect from session so that the updates on updatedFilial are not directly saved in db
        em.detach(updatedFilial);
        updatedFilial
            .cnpj(UPDATED_CNPJ)
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .nomeFantasia(UPDATED_NOME_FANTASIA)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        FilialDTO filialDTO = filialMapper.toDto(updatedFilial);

        restFilialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, filialDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filialDTO))
            )
            .andExpect(status().isOk());

        // Validate the Filial in the database
        List<Filial> filialList = filialRepository.findAll();
        assertThat(filialList).hasSize(databaseSizeBeforeUpdate);
        Filial testFilial = filialList.get(filialList.size() - 1);
        assertThat(testFilial.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testFilial.getRazaoSocial()).isEqualTo(UPDATED_RAZAO_SOCIAL);
        assertThat(testFilial.getNomeFantasia()).isEqualTo(UPDATED_NOME_FANTASIA);
        assertThat(testFilial.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFilial.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFilial.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testFilial.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingFilial() throws Exception {
        int databaseSizeBeforeUpdate = filialRepository.findAll().size();
        filial.setId(count.incrementAndGet());

        // Create the Filial
        FilialDTO filialDTO = filialMapper.toDto(filial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFilialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, filialDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Filial in the database
        List<Filial> filialList = filialRepository.findAll();
        assertThat(filialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFilial() throws Exception {
        int databaseSizeBeforeUpdate = filialRepository.findAll().size();
        filial.setId(count.incrementAndGet());

        // Create the Filial
        FilialDTO filialDTO = filialMapper.toDto(filial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(filialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Filial in the database
        List<Filial> filialList = filialRepository.findAll();
        assertThat(filialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFilial() throws Exception {
        int databaseSizeBeforeUpdate = filialRepository.findAll().size();
        filial.setId(count.incrementAndGet());

        // Create the Filial
        FilialDTO filialDTO = filialMapper.toDto(filial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilialMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(filialDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Filial in the database
        List<Filial> filialList = filialRepository.findAll();
        assertThat(filialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFilialWithPatch() throws Exception {
        // Initialize the database
        filialRepository.saveAndFlush(filial);

        int databaseSizeBeforeUpdate = filialRepository.findAll().size();

        // Update the filial using partial update
        Filial partialUpdatedFilial = new Filial();
        partialUpdatedFilial.setId(filial.getId());

        partialUpdatedFilial
            .cnpj(UPDATED_CNPJ)
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restFilialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFilial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFilial))
            )
            .andExpect(status().isOk());

        // Validate the Filial in the database
        List<Filial> filialList = filialRepository.findAll();
        assertThat(filialList).hasSize(databaseSizeBeforeUpdate);
        Filial testFilial = filialList.get(filialList.size() - 1);
        assertThat(testFilial.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testFilial.getRazaoSocial()).isEqualTo(UPDATED_RAZAO_SOCIAL);
        assertThat(testFilial.getNomeFantasia()).isEqualTo(DEFAULT_NOME_FANTASIA);
        assertThat(testFilial.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFilial.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFilial.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testFilial.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateFilialWithPatch() throws Exception {
        // Initialize the database
        filialRepository.saveAndFlush(filial);

        int databaseSizeBeforeUpdate = filialRepository.findAll().size();

        // Update the filial using partial update
        Filial partialUpdatedFilial = new Filial();
        partialUpdatedFilial.setId(filial.getId());

        partialUpdatedFilial
            .cnpj(UPDATED_CNPJ)
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .nomeFantasia(UPDATED_NOME_FANTASIA)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restFilialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFilial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFilial))
            )
            .andExpect(status().isOk());

        // Validate the Filial in the database
        List<Filial> filialList = filialRepository.findAll();
        assertThat(filialList).hasSize(databaseSizeBeforeUpdate);
        Filial testFilial = filialList.get(filialList.size() - 1);
        assertThat(testFilial.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testFilial.getRazaoSocial()).isEqualTo(UPDATED_RAZAO_SOCIAL);
        assertThat(testFilial.getNomeFantasia()).isEqualTo(UPDATED_NOME_FANTASIA);
        assertThat(testFilial.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFilial.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFilial.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testFilial.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingFilial() throws Exception {
        int databaseSizeBeforeUpdate = filialRepository.findAll().size();
        filial.setId(count.incrementAndGet());

        // Create the Filial
        FilialDTO filialDTO = filialMapper.toDto(filial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFilialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, filialDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(filialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Filial in the database
        List<Filial> filialList = filialRepository.findAll();
        assertThat(filialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFilial() throws Exception {
        int databaseSizeBeforeUpdate = filialRepository.findAll().size();
        filial.setId(count.incrementAndGet());

        // Create the Filial
        FilialDTO filialDTO = filialMapper.toDto(filial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(filialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Filial in the database
        List<Filial> filialList = filialRepository.findAll();
        assertThat(filialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFilial() throws Exception {
        int databaseSizeBeforeUpdate = filialRepository.findAll().size();
        filial.setId(count.incrementAndGet());

        // Create the Filial
        FilialDTO filialDTO = filialMapper.toDto(filial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFilialMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(filialDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Filial in the database
        List<Filial> filialList = filialRepository.findAll();
        assertThat(filialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFilial() throws Exception {
        // Initialize the database
        filialRepository.saveAndFlush(filial);

        int databaseSizeBeforeDelete = filialRepository.findAll().size();

        // Delete the filial
        restFilialMockMvc
            .perform(delete(ENTITY_API_URL_ID, filial.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Filial> filialList = filialRepository.findAll();
        assertThat(filialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

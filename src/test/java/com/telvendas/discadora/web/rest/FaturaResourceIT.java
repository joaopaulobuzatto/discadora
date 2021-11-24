package com.telvendas.discadora.web.rest;

import static com.telvendas.discadora.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.telvendas.discadora.IntegrationTest;
import com.telvendas.discadora.domain.Fatura;
import com.telvendas.discadora.repository.FaturaRepository;
import com.telvendas.discadora.service.dto.FaturaDTO;
import com.telvendas.discadora.service.mapper.FaturaMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link FaturaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FaturaResourceIT {

    private static final String DEFAULT_NUMERO_FATURA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_FATURA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_VENCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_VENCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_VALOR_FATURADO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_FATURADO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALOR_ABERTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_ABERTO = new BigDecimal(2);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/faturas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FaturaRepository faturaRepository;

    @Autowired
    private FaturaMapper faturaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFaturaMockMvc;

    private Fatura fatura;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fatura createEntity(EntityManager em) {
        Fatura fatura = new Fatura()
            .numeroFatura(DEFAULT_NUMERO_FATURA)
            .dataVencimento(DEFAULT_DATA_VENCIMENTO)
            .valorFaturado(DEFAULT_VALOR_FATURADO)
            .valorAberto(DEFAULT_VALOR_ABERTO)
            .status(DEFAULT_STATUS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return fatura;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fatura createUpdatedEntity(EntityManager em) {
        Fatura fatura = new Fatura()
            .numeroFatura(UPDATED_NUMERO_FATURA)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .valorFaturado(UPDATED_VALOR_FATURADO)
            .valorAberto(UPDATED_VALOR_ABERTO)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return fatura;
    }

    @BeforeEach
    public void initTest() {
        fatura = createEntity(em);
    }

    @Test
    @Transactional
    void createFatura() throws Exception {
        int databaseSizeBeforeCreate = faturaRepository.findAll().size();
        // Create the Fatura
        FaturaDTO faturaDTO = faturaMapper.toDto(fatura);
        restFaturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(faturaDTO)))
            .andExpect(status().isCreated());

        // Validate the Fatura in the database
        List<Fatura> faturaList = faturaRepository.findAll();
        assertThat(faturaList).hasSize(databaseSizeBeforeCreate + 1);
        Fatura testFatura = faturaList.get(faturaList.size() - 1);
        assertThat(testFatura.getNumeroFatura()).isEqualTo(DEFAULT_NUMERO_FATURA);
        assertThat(testFatura.getDataVencimento()).isEqualTo(DEFAULT_DATA_VENCIMENTO);
        assertThat(testFatura.getValorFaturado()).isEqualByComparingTo(DEFAULT_VALOR_FATURADO);
        assertThat(testFatura.getValorAberto()).isEqualByComparingTo(DEFAULT_VALOR_ABERTO);
        assertThat(testFatura.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFatura.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFatura.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFatura.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testFatura.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createFaturaWithExistingId() throws Exception {
        // Create the Fatura with an existing ID
        fatura.setId(1L);
        FaturaDTO faturaDTO = faturaMapper.toDto(fatura);

        int databaseSizeBeforeCreate = faturaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFaturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(faturaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fatura in the database
        List<Fatura> faturaList = faturaRepository.findAll();
        assertThat(faturaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFaturas() throws Exception {
        // Initialize the database
        faturaRepository.saveAndFlush(fatura);

        // Get all the faturaList
        restFaturaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fatura.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroFatura").value(hasItem(DEFAULT_NUMERO_FATURA)))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].valorFaturado").value(hasItem(sameNumber(DEFAULT_VALOR_FATURADO))))
            .andExpect(jsonPath("$.[*].valorAberto").value(hasItem(sameNumber(DEFAULT_VALOR_ABERTO))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getFatura() throws Exception {
        // Initialize the database
        faturaRepository.saveAndFlush(fatura);

        // Get the fatura
        restFaturaMockMvc
            .perform(get(ENTITY_API_URL_ID, fatura.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fatura.getId().intValue()))
            .andExpect(jsonPath("$.numeroFatura").value(DEFAULT_NUMERO_FATURA))
            .andExpect(jsonPath("$.dataVencimento").value(DEFAULT_DATA_VENCIMENTO.toString()))
            .andExpect(jsonPath("$.valorFaturado").value(sameNumber(DEFAULT_VALOR_FATURADO)))
            .andExpect(jsonPath("$.valorAberto").value(sameNumber(DEFAULT_VALOR_ABERTO)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFatura() throws Exception {
        // Get the fatura
        restFaturaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFatura() throws Exception {
        // Initialize the database
        faturaRepository.saveAndFlush(fatura);

        int databaseSizeBeforeUpdate = faturaRepository.findAll().size();

        // Update the fatura
        Fatura updatedFatura = faturaRepository.findById(fatura.getId()).get();
        // Disconnect from session so that the updates on updatedFatura are not directly saved in db
        em.detach(updatedFatura);
        updatedFatura
            .numeroFatura(UPDATED_NUMERO_FATURA)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .valorFaturado(UPDATED_VALOR_FATURADO)
            .valorAberto(UPDATED_VALOR_ABERTO)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        FaturaDTO faturaDTO = faturaMapper.toDto(updatedFatura);

        restFaturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, faturaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(faturaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Fatura in the database
        List<Fatura> faturaList = faturaRepository.findAll();
        assertThat(faturaList).hasSize(databaseSizeBeforeUpdate);
        Fatura testFatura = faturaList.get(faturaList.size() - 1);
        assertThat(testFatura.getNumeroFatura()).isEqualTo(UPDATED_NUMERO_FATURA);
        assertThat(testFatura.getDataVencimento()).isEqualTo(UPDATED_DATA_VENCIMENTO);
        assertThat(testFatura.getValorFaturado()).isEqualTo(UPDATED_VALOR_FATURADO);
        assertThat(testFatura.getValorAberto()).isEqualTo(UPDATED_VALOR_ABERTO);
        assertThat(testFatura.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFatura.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFatura.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFatura.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testFatura.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingFatura() throws Exception {
        int databaseSizeBeforeUpdate = faturaRepository.findAll().size();
        fatura.setId(count.incrementAndGet());

        // Create the Fatura
        FaturaDTO faturaDTO = faturaMapper.toDto(fatura);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFaturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, faturaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(faturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fatura in the database
        List<Fatura> faturaList = faturaRepository.findAll();
        assertThat(faturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFatura() throws Exception {
        int databaseSizeBeforeUpdate = faturaRepository.findAll().size();
        fatura.setId(count.incrementAndGet());

        // Create the Fatura
        FaturaDTO faturaDTO = faturaMapper.toDto(fatura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFaturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(faturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fatura in the database
        List<Fatura> faturaList = faturaRepository.findAll();
        assertThat(faturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFatura() throws Exception {
        int databaseSizeBeforeUpdate = faturaRepository.findAll().size();
        fatura.setId(count.incrementAndGet());

        // Create the Fatura
        FaturaDTO faturaDTO = faturaMapper.toDto(fatura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFaturaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(faturaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fatura in the database
        List<Fatura> faturaList = faturaRepository.findAll();
        assertThat(faturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFaturaWithPatch() throws Exception {
        // Initialize the database
        faturaRepository.saveAndFlush(fatura);

        int databaseSizeBeforeUpdate = faturaRepository.findAll().size();

        // Update the fatura using partial update
        Fatura partialUpdatedFatura = new Fatura();
        partialUpdatedFatura.setId(fatura.getId());

        partialUpdatedFatura
            .numeroFatura(UPDATED_NUMERO_FATURA)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .valorFaturado(UPDATED_VALOR_FATURADO)
            .valorAberto(UPDATED_VALOR_ABERTO)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restFaturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFatura.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFatura))
            )
            .andExpect(status().isOk());

        // Validate the Fatura in the database
        List<Fatura> faturaList = faturaRepository.findAll();
        assertThat(faturaList).hasSize(databaseSizeBeforeUpdate);
        Fatura testFatura = faturaList.get(faturaList.size() - 1);
        assertThat(testFatura.getNumeroFatura()).isEqualTo(UPDATED_NUMERO_FATURA);
        assertThat(testFatura.getDataVencimento()).isEqualTo(UPDATED_DATA_VENCIMENTO);
        assertThat(testFatura.getValorFaturado()).isEqualByComparingTo(UPDATED_VALOR_FATURADO);
        assertThat(testFatura.getValorAberto()).isEqualByComparingTo(UPDATED_VALOR_ABERTO);
        assertThat(testFatura.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFatura.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFatura.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFatura.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testFatura.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateFaturaWithPatch() throws Exception {
        // Initialize the database
        faturaRepository.saveAndFlush(fatura);

        int databaseSizeBeforeUpdate = faturaRepository.findAll().size();

        // Update the fatura using partial update
        Fatura partialUpdatedFatura = new Fatura();
        partialUpdatedFatura.setId(fatura.getId());

        partialUpdatedFatura
            .numeroFatura(UPDATED_NUMERO_FATURA)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .valorFaturado(UPDATED_VALOR_FATURADO)
            .valorAberto(UPDATED_VALOR_ABERTO)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restFaturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFatura.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFatura))
            )
            .andExpect(status().isOk());

        // Validate the Fatura in the database
        List<Fatura> faturaList = faturaRepository.findAll();
        assertThat(faturaList).hasSize(databaseSizeBeforeUpdate);
        Fatura testFatura = faturaList.get(faturaList.size() - 1);
        assertThat(testFatura.getNumeroFatura()).isEqualTo(UPDATED_NUMERO_FATURA);
        assertThat(testFatura.getDataVencimento()).isEqualTo(UPDATED_DATA_VENCIMENTO);
        assertThat(testFatura.getValorFaturado()).isEqualByComparingTo(UPDATED_VALOR_FATURADO);
        assertThat(testFatura.getValorAberto()).isEqualByComparingTo(UPDATED_VALOR_ABERTO);
        assertThat(testFatura.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFatura.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFatura.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFatura.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testFatura.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingFatura() throws Exception {
        int databaseSizeBeforeUpdate = faturaRepository.findAll().size();
        fatura.setId(count.incrementAndGet());

        // Create the Fatura
        FaturaDTO faturaDTO = faturaMapper.toDto(fatura);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFaturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, faturaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(faturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fatura in the database
        List<Fatura> faturaList = faturaRepository.findAll();
        assertThat(faturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFatura() throws Exception {
        int databaseSizeBeforeUpdate = faturaRepository.findAll().size();
        fatura.setId(count.incrementAndGet());

        // Create the Fatura
        FaturaDTO faturaDTO = faturaMapper.toDto(fatura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFaturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(faturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fatura in the database
        List<Fatura> faturaList = faturaRepository.findAll();
        assertThat(faturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFatura() throws Exception {
        int databaseSizeBeforeUpdate = faturaRepository.findAll().size();
        fatura.setId(count.incrementAndGet());

        // Create the Fatura
        FaturaDTO faturaDTO = faturaMapper.toDto(fatura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFaturaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(faturaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fatura in the database
        List<Fatura> faturaList = faturaRepository.findAll();
        assertThat(faturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFatura() throws Exception {
        // Initialize the database
        faturaRepository.saveAndFlush(fatura);

        int databaseSizeBeforeDelete = faturaRepository.findAll().size();

        // Delete the fatura
        restFaturaMockMvc
            .perform(delete(ENTITY_API_URL_ID, fatura.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fatura> faturaList = faturaRepository.findAll();
        assertThat(faturaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

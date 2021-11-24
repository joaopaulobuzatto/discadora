package com.telvendas.discadora.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.telvendas.discadora.IntegrationTest;
import com.telvendas.discadora.domain.LinhaMovel;
import com.telvendas.discadora.repository.LinhaMovelRepository;
import com.telvendas.discadora.service.dto.LinhaMovelDTO;
import com.telvendas.discadora.service.mapper.LinhaMovelMapper;
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
 * Integration tests for the {@link LinhaMovelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LinhaMovelResourceIT {

    private static final String DEFAULT_NUMERO_LINHA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_LINHA = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_ATIVACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ATIVACAO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PLANO = "AAAAAAAAAA";
    private static final String UPDATED_PLANO = "BBBBBBBBBB";

    private static final String DEFAULT_OPERADORA = "AAAAAAAAAA";
    private static final String UPDATED_OPERADORA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/linha-movels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LinhaMovelRepository linhaMovelRepository;

    @Autowired
    private LinhaMovelMapper linhaMovelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLinhaMovelMockMvc;

    private LinhaMovel linhaMovel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LinhaMovel createEntity(EntityManager em) {
        LinhaMovel linhaMovel = new LinhaMovel()
            .numeroLinha(DEFAULT_NUMERO_LINHA)
            .status(DEFAULT_STATUS)
            .dataAtivacao(DEFAULT_DATA_ATIVACAO)
            .plano(DEFAULT_PLANO)
            .operadora(DEFAULT_OPERADORA);
        return linhaMovel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LinhaMovel createUpdatedEntity(EntityManager em) {
        LinhaMovel linhaMovel = new LinhaMovel()
            .numeroLinha(UPDATED_NUMERO_LINHA)
            .status(UPDATED_STATUS)
            .dataAtivacao(UPDATED_DATA_ATIVACAO)
            .plano(UPDATED_PLANO)
            .operadora(UPDATED_OPERADORA);
        return linhaMovel;
    }

    @BeforeEach
    public void initTest() {
        linhaMovel = createEntity(em);
    }

    @Test
    @Transactional
    void createLinhaMovel() throws Exception {
        int databaseSizeBeforeCreate = linhaMovelRepository.findAll().size();
        // Create the LinhaMovel
        LinhaMovelDTO linhaMovelDTO = linhaMovelMapper.toDto(linhaMovel);
        restLinhaMovelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(linhaMovelDTO)))
            .andExpect(status().isCreated());

        // Validate the LinhaMovel in the database
        List<LinhaMovel> linhaMovelList = linhaMovelRepository.findAll();
        assertThat(linhaMovelList).hasSize(databaseSizeBeforeCreate + 1);
        LinhaMovel testLinhaMovel = linhaMovelList.get(linhaMovelList.size() - 1);
        assertThat(testLinhaMovel.getNumeroLinha()).isEqualTo(DEFAULT_NUMERO_LINHA);
        assertThat(testLinhaMovel.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLinhaMovel.getDataAtivacao()).isEqualTo(DEFAULT_DATA_ATIVACAO);
        assertThat(testLinhaMovel.getPlano()).isEqualTo(DEFAULT_PLANO);
        assertThat(testLinhaMovel.getOperadora()).isEqualTo(DEFAULT_OPERADORA);
    }

    @Test
    @Transactional
    void createLinhaMovelWithExistingId() throws Exception {
        // Create the LinhaMovel with an existing ID
        linhaMovel.setId(1L);
        LinhaMovelDTO linhaMovelDTO = linhaMovelMapper.toDto(linhaMovel);

        int databaseSizeBeforeCreate = linhaMovelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLinhaMovelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(linhaMovelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LinhaMovel in the database
        List<LinhaMovel> linhaMovelList = linhaMovelRepository.findAll();
        assertThat(linhaMovelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLinhaMovels() throws Exception {
        // Initialize the database
        linhaMovelRepository.saveAndFlush(linhaMovel);

        // Get all the linhaMovelList
        restLinhaMovelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(linhaMovel.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroLinha").value(hasItem(DEFAULT_NUMERO_LINHA)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].dataAtivacao").value(hasItem(DEFAULT_DATA_ATIVACAO.toString())))
            .andExpect(jsonPath("$.[*].plano").value(hasItem(DEFAULT_PLANO)))
            .andExpect(jsonPath("$.[*].operadora").value(hasItem(DEFAULT_OPERADORA)));
    }

    @Test
    @Transactional
    void getLinhaMovel() throws Exception {
        // Initialize the database
        linhaMovelRepository.saveAndFlush(linhaMovel);

        // Get the linhaMovel
        restLinhaMovelMockMvc
            .perform(get(ENTITY_API_URL_ID, linhaMovel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(linhaMovel.getId().intValue()))
            .andExpect(jsonPath("$.numeroLinha").value(DEFAULT_NUMERO_LINHA))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.dataAtivacao").value(DEFAULT_DATA_ATIVACAO.toString()))
            .andExpect(jsonPath("$.plano").value(DEFAULT_PLANO))
            .andExpect(jsonPath("$.operadora").value(DEFAULT_OPERADORA));
    }

    @Test
    @Transactional
    void getNonExistingLinhaMovel() throws Exception {
        // Get the linhaMovel
        restLinhaMovelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLinhaMovel() throws Exception {
        // Initialize the database
        linhaMovelRepository.saveAndFlush(linhaMovel);

        int databaseSizeBeforeUpdate = linhaMovelRepository.findAll().size();

        // Update the linhaMovel
        LinhaMovel updatedLinhaMovel = linhaMovelRepository.findById(linhaMovel.getId()).get();
        // Disconnect from session so that the updates on updatedLinhaMovel are not directly saved in db
        em.detach(updatedLinhaMovel);
        updatedLinhaMovel
            .numeroLinha(UPDATED_NUMERO_LINHA)
            .status(UPDATED_STATUS)
            .dataAtivacao(UPDATED_DATA_ATIVACAO)
            .plano(UPDATED_PLANO)
            .operadora(UPDATED_OPERADORA);
        LinhaMovelDTO linhaMovelDTO = linhaMovelMapper.toDto(updatedLinhaMovel);

        restLinhaMovelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, linhaMovelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(linhaMovelDTO))
            )
            .andExpect(status().isOk());

        // Validate the LinhaMovel in the database
        List<LinhaMovel> linhaMovelList = linhaMovelRepository.findAll();
        assertThat(linhaMovelList).hasSize(databaseSizeBeforeUpdate);
        LinhaMovel testLinhaMovel = linhaMovelList.get(linhaMovelList.size() - 1);
        assertThat(testLinhaMovel.getNumeroLinha()).isEqualTo(UPDATED_NUMERO_LINHA);
        assertThat(testLinhaMovel.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLinhaMovel.getDataAtivacao()).isEqualTo(UPDATED_DATA_ATIVACAO);
        assertThat(testLinhaMovel.getPlano()).isEqualTo(UPDATED_PLANO);
        assertThat(testLinhaMovel.getOperadora()).isEqualTo(UPDATED_OPERADORA);
    }

    @Test
    @Transactional
    void putNonExistingLinhaMovel() throws Exception {
        int databaseSizeBeforeUpdate = linhaMovelRepository.findAll().size();
        linhaMovel.setId(count.incrementAndGet());

        // Create the LinhaMovel
        LinhaMovelDTO linhaMovelDTO = linhaMovelMapper.toDto(linhaMovel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinhaMovelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, linhaMovelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(linhaMovelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LinhaMovel in the database
        List<LinhaMovel> linhaMovelList = linhaMovelRepository.findAll();
        assertThat(linhaMovelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLinhaMovel() throws Exception {
        int databaseSizeBeforeUpdate = linhaMovelRepository.findAll().size();
        linhaMovel.setId(count.incrementAndGet());

        // Create the LinhaMovel
        LinhaMovelDTO linhaMovelDTO = linhaMovelMapper.toDto(linhaMovel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinhaMovelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(linhaMovelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LinhaMovel in the database
        List<LinhaMovel> linhaMovelList = linhaMovelRepository.findAll();
        assertThat(linhaMovelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLinhaMovel() throws Exception {
        int databaseSizeBeforeUpdate = linhaMovelRepository.findAll().size();
        linhaMovel.setId(count.incrementAndGet());

        // Create the LinhaMovel
        LinhaMovelDTO linhaMovelDTO = linhaMovelMapper.toDto(linhaMovel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinhaMovelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(linhaMovelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LinhaMovel in the database
        List<LinhaMovel> linhaMovelList = linhaMovelRepository.findAll();
        assertThat(linhaMovelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLinhaMovelWithPatch() throws Exception {
        // Initialize the database
        linhaMovelRepository.saveAndFlush(linhaMovel);

        int databaseSizeBeforeUpdate = linhaMovelRepository.findAll().size();

        // Update the linhaMovel using partial update
        LinhaMovel partialUpdatedLinhaMovel = new LinhaMovel();
        partialUpdatedLinhaMovel.setId(linhaMovel.getId());

        partialUpdatedLinhaMovel
            .numeroLinha(UPDATED_NUMERO_LINHA)
            .dataAtivacao(UPDATED_DATA_ATIVACAO)
            .plano(UPDATED_PLANO)
            .operadora(UPDATED_OPERADORA);

        restLinhaMovelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLinhaMovel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLinhaMovel))
            )
            .andExpect(status().isOk());

        // Validate the LinhaMovel in the database
        List<LinhaMovel> linhaMovelList = linhaMovelRepository.findAll();
        assertThat(linhaMovelList).hasSize(databaseSizeBeforeUpdate);
        LinhaMovel testLinhaMovel = linhaMovelList.get(linhaMovelList.size() - 1);
        assertThat(testLinhaMovel.getNumeroLinha()).isEqualTo(UPDATED_NUMERO_LINHA);
        assertThat(testLinhaMovel.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLinhaMovel.getDataAtivacao()).isEqualTo(UPDATED_DATA_ATIVACAO);
        assertThat(testLinhaMovel.getPlano()).isEqualTo(UPDATED_PLANO);
        assertThat(testLinhaMovel.getOperadora()).isEqualTo(UPDATED_OPERADORA);
    }

    @Test
    @Transactional
    void fullUpdateLinhaMovelWithPatch() throws Exception {
        // Initialize the database
        linhaMovelRepository.saveAndFlush(linhaMovel);

        int databaseSizeBeforeUpdate = linhaMovelRepository.findAll().size();

        // Update the linhaMovel using partial update
        LinhaMovel partialUpdatedLinhaMovel = new LinhaMovel();
        partialUpdatedLinhaMovel.setId(linhaMovel.getId());

        partialUpdatedLinhaMovel
            .numeroLinha(UPDATED_NUMERO_LINHA)
            .status(UPDATED_STATUS)
            .dataAtivacao(UPDATED_DATA_ATIVACAO)
            .plano(UPDATED_PLANO)
            .operadora(UPDATED_OPERADORA);

        restLinhaMovelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLinhaMovel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLinhaMovel))
            )
            .andExpect(status().isOk());

        // Validate the LinhaMovel in the database
        List<LinhaMovel> linhaMovelList = linhaMovelRepository.findAll();
        assertThat(linhaMovelList).hasSize(databaseSizeBeforeUpdate);
        LinhaMovel testLinhaMovel = linhaMovelList.get(linhaMovelList.size() - 1);
        assertThat(testLinhaMovel.getNumeroLinha()).isEqualTo(UPDATED_NUMERO_LINHA);
        assertThat(testLinhaMovel.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLinhaMovel.getDataAtivacao()).isEqualTo(UPDATED_DATA_ATIVACAO);
        assertThat(testLinhaMovel.getPlano()).isEqualTo(UPDATED_PLANO);
        assertThat(testLinhaMovel.getOperadora()).isEqualTo(UPDATED_OPERADORA);
    }

    @Test
    @Transactional
    void patchNonExistingLinhaMovel() throws Exception {
        int databaseSizeBeforeUpdate = linhaMovelRepository.findAll().size();
        linhaMovel.setId(count.incrementAndGet());

        // Create the LinhaMovel
        LinhaMovelDTO linhaMovelDTO = linhaMovelMapper.toDto(linhaMovel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinhaMovelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, linhaMovelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(linhaMovelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LinhaMovel in the database
        List<LinhaMovel> linhaMovelList = linhaMovelRepository.findAll();
        assertThat(linhaMovelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLinhaMovel() throws Exception {
        int databaseSizeBeforeUpdate = linhaMovelRepository.findAll().size();
        linhaMovel.setId(count.incrementAndGet());

        // Create the LinhaMovel
        LinhaMovelDTO linhaMovelDTO = linhaMovelMapper.toDto(linhaMovel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinhaMovelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(linhaMovelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LinhaMovel in the database
        List<LinhaMovel> linhaMovelList = linhaMovelRepository.findAll();
        assertThat(linhaMovelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLinhaMovel() throws Exception {
        int databaseSizeBeforeUpdate = linhaMovelRepository.findAll().size();
        linhaMovel.setId(count.incrementAndGet());

        // Create the LinhaMovel
        LinhaMovelDTO linhaMovelDTO = linhaMovelMapper.toDto(linhaMovel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinhaMovelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(linhaMovelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LinhaMovel in the database
        List<LinhaMovel> linhaMovelList = linhaMovelRepository.findAll();
        assertThat(linhaMovelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLinhaMovel() throws Exception {
        // Initialize the database
        linhaMovelRepository.saveAndFlush(linhaMovel);

        int databaseSizeBeforeDelete = linhaMovelRepository.findAll().size();

        // Delete the linhaMovel
        restLinhaMovelMockMvc
            .perform(delete(ENTITY_API_URL_ID, linhaMovel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LinhaMovel> linhaMovelList = linhaMovelRepository.findAll();
        assertThat(linhaMovelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

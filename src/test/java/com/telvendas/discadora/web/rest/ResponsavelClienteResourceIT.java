package com.telvendas.discadora.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.telvendas.discadora.IntegrationTest;
import com.telvendas.discadora.domain.ResponsavelCliente;
import com.telvendas.discadora.repository.ResponsavelClienteRepository;
import com.telvendas.discadora.service.dto.ResponsavelClienteDTO;
import com.telvendas.discadora.service.mapper.ResponsavelClienteMapper;
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
 * Integration tests for the {@link ResponsavelClienteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResponsavelClienteResourceIT {

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_RG = "AAAAAAAAAA";
    private static final String UPDATED_RG = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_1 = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_2 = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_MAE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_MAE = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_PAI = "AAAAAAAAAA";
    private static final String UPDATED_NOME_PAI = "BBBBBBBBBB";

    private static final String DEFAULT_FUNCAO = "AAAAAAAAAA";
    private static final String UPDATED_FUNCAO = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/responsavel-clientes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResponsavelClienteRepository responsavelClienteRepository;

    @Autowired
    private ResponsavelClienteMapper responsavelClienteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResponsavelClienteMockMvc;

    private ResponsavelCliente responsavelCliente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponsavelCliente createEntity(EntityManager em) {
        ResponsavelCliente responsavelCliente = new ResponsavelCliente()
            .cpf(DEFAULT_CPF)
            .nome(DEFAULT_NOME)
            .dataNascimento(DEFAULT_DATA_NASCIMENTO)
            .rg(DEFAULT_RG)
            .telefone1(DEFAULT_TELEFONE_1)
            .telefone2(DEFAULT_TELEFONE_2)
            .email(DEFAULT_EMAIL)
            .nomeMae(DEFAULT_NOME_MAE)
            .nomePai(DEFAULT_NOME_PAI)
            .funcao(DEFAULT_FUNCAO)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return responsavelCliente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponsavelCliente createUpdatedEntity(EntityManager em) {
        ResponsavelCliente responsavelCliente = new ResponsavelCliente()
            .cpf(UPDATED_CPF)
            .nome(UPDATED_NOME)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .rg(UPDATED_RG)
            .telefone1(UPDATED_TELEFONE_1)
            .telefone2(UPDATED_TELEFONE_2)
            .email(UPDATED_EMAIL)
            .nomeMae(UPDATED_NOME_MAE)
            .nomePai(UPDATED_NOME_PAI)
            .funcao(UPDATED_FUNCAO)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return responsavelCliente;
    }

    @BeforeEach
    public void initTest() {
        responsavelCliente = createEntity(em);
    }

    @Test
    @Transactional
    void createResponsavelCliente() throws Exception {
        int databaseSizeBeforeCreate = responsavelClienteRepository.findAll().size();
        // Create the ResponsavelCliente
        ResponsavelClienteDTO responsavelClienteDTO = responsavelClienteMapper.toDto(responsavelCliente);
        restResponsavelClienteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelClienteDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ResponsavelCliente in the database
        List<ResponsavelCliente> responsavelClienteList = responsavelClienteRepository.findAll();
        assertThat(responsavelClienteList).hasSize(databaseSizeBeforeCreate + 1);
        ResponsavelCliente testResponsavelCliente = responsavelClienteList.get(responsavelClienteList.size() - 1);
        assertThat(testResponsavelCliente.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testResponsavelCliente.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testResponsavelCliente.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testResponsavelCliente.getRg()).isEqualTo(DEFAULT_RG);
        assertThat(testResponsavelCliente.getTelefone1()).isEqualTo(DEFAULT_TELEFONE_1);
        assertThat(testResponsavelCliente.getTelefone2()).isEqualTo(DEFAULT_TELEFONE_2);
        assertThat(testResponsavelCliente.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testResponsavelCliente.getNomeMae()).isEqualTo(DEFAULT_NOME_MAE);
        assertThat(testResponsavelCliente.getNomePai()).isEqualTo(DEFAULT_NOME_PAI);
        assertThat(testResponsavelCliente.getFuncao()).isEqualTo(DEFAULT_FUNCAO);
        assertThat(testResponsavelCliente.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testResponsavelCliente.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testResponsavelCliente.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testResponsavelCliente.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createResponsavelClienteWithExistingId() throws Exception {
        // Create the ResponsavelCliente with an existing ID
        responsavelCliente.setId(1L);
        ResponsavelClienteDTO responsavelClienteDTO = responsavelClienteMapper.toDto(responsavelCliente);

        int databaseSizeBeforeCreate = responsavelClienteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsavelClienteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelCliente in the database
        List<ResponsavelCliente> responsavelClienteList = responsavelClienteRepository.findAll();
        assertThat(responsavelClienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllResponsavelClientes() throws Exception {
        // Initialize the database
        responsavelClienteRepository.saveAndFlush(responsavelCliente);

        // Get all the responsavelClienteList
        restResponsavelClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsavelCliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].rg").value(hasItem(DEFAULT_RG)))
            .andExpect(jsonPath("$.[*].telefone1").value(hasItem(DEFAULT_TELEFONE_1)))
            .andExpect(jsonPath("$.[*].telefone2").value(hasItem(DEFAULT_TELEFONE_2)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].nomeMae").value(hasItem(DEFAULT_NOME_MAE)))
            .andExpect(jsonPath("$.[*].nomePai").value(hasItem(DEFAULT_NOME_PAI)))
            .andExpect(jsonPath("$.[*].funcao").value(hasItem(DEFAULT_FUNCAO)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getResponsavelCliente() throws Exception {
        // Initialize the database
        responsavelClienteRepository.saveAndFlush(responsavelCliente);

        // Get the responsavelCliente
        restResponsavelClienteMockMvc
            .perform(get(ENTITY_API_URL_ID, responsavelCliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(responsavelCliente.getId().intValue()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.rg").value(DEFAULT_RG))
            .andExpect(jsonPath("$.telefone1").value(DEFAULT_TELEFONE_1))
            .andExpect(jsonPath("$.telefone2").value(DEFAULT_TELEFONE_2))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.nomeMae").value(DEFAULT_NOME_MAE))
            .andExpect(jsonPath("$.nomePai").value(DEFAULT_NOME_PAI))
            .andExpect(jsonPath("$.funcao").value(DEFAULT_FUNCAO))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingResponsavelCliente() throws Exception {
        // Get the responsavelCliente
        restResponsavelClienteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewResponsavelCliente() throws Exception {
        // Initialize the database
        responsavelClienteRepository.saveAndFlush(responsavelCliente);

        int databaseSizeBeforeUpdate = responsavelClienteRepository.findAll().size();

        // Update the responsavelCliente
        ResponsavelCliente updatedResponsavelCliente = responsavelClienteRepository.findById(responsavelCliente.getId()).get();
        // Disconnect from session so that the updates on updatedResponsavelCliente are not directly saved in db
        em.detach(updatedResponsavelCliente);
        updatedResponsavelCliente
            .cpf(UPDATED_CPF)
            .nome(UPDATED_NOME)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .rg(UPDATED_RG)
            .telefone1(UPDATED_TELEFONE_1)
            .telefone2(UPDATED_TELEFONE_2)
            .email(UPDATED_EMAIL)
            .nomeMae(UPDATED_NOME_MAE)
            .nomePai(UPDATED_NOME_PAI)
            .funcao(UPDATED_FUNCAO)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ResponsavelClienteDTO responsavelClienteDTO = responsavelClienteMapper.toDto(updatedResponsavelCliente);

        restResponsavelClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responsavelClienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelClienteDTO))
            )
            .andExpect(status().isOk());

        // Validate the ResponsavelCliente in the database
        List<ResponsavelCliente> responsavelClienteList = responsavelClienteRepository.findAll();
        assertThat(responsavelClienteList).hasSize(databaseSizeBeforeUpdate);
        ResponsavelCliente testResponsavelCliente = responsavelClienteList.get(responsavelClienteList.size() - 1);
        assertThat(testResponsavelCliente.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testResponsavelCliente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testResponsavelCliente.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testResponsavelCliente.getRg()).isEqualTo(UPDATED_RG);
        assertThat(testResponsavelCliente.getTelefone1()).isEqualTo(UPDATED_TELEFONE_1);
        assertThat(testResponsavelCliente.getTelefone2()).isEqualTo(UPDATED_TELEFONE_2);
        assertThat(testResponsavelCliente.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testResponsavelCliente.getNomeMae()).isEqualTo(UPDATED_NOME_MAE);
        assertThat(testResponsavelCliente.getNomePai()).isEqualTo(UPDATED_NOME_PAI);
        assertThat(testResponsavelCliente.getFuncao()).isEqualTo(UPDATED_FUNCAO);
        assertThat(testResponsavelCliente.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testResponsavelCliente.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testResponsavelCliente.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testResponsavelCliente.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingResponsavelCliente() throws Exception {
        int databaseSizeBeforeUpdate = responsavelClienteRepository.findAll().size();
        responsavelCliente.setId(count.incrementAndGet());

        // Create the ResponsavelCliente
        ResponsavelClienteDTO responsavelClienteDTO = responsavelClienteMapper.toDto(responsavelCliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsavelClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responsavelClienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelCliente in the database
        List<ResponsavelCliente> responsavelClienteList = responsavelClienteRepository.findAll();
        assertThat(responsavelClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResponsavelCliente() throws Exception {
        int databaseSizeBeforeUpdate = responsavelClienteRepository.findAll().size();
        responsavelCliente.setId(count.incrementAndGet());

        // Create the ResponsavelCliente
        ResponsavelClienteDTO responsavelClienteDTO = responsavelClienteMapper.toDto(responsavelCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelCliente in the database
        List<ResponsavelCliente> responsavelClienteList = responsavelClienteRepository.findAll();
        assertThat(responsavelClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResponsavelCliente() throws Exception {
        int databaseSizeBeforeUpdate = responsavelClienteRepository.findAll().size();
        responsavelCliente.setId(count.incrementAndGet());

        // Create the ResponsavelCliente
        ResponsavelClienteDTO responsavelClienteDTO = responsavelClienteMapper.toDto(responsavelCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelClienteMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsavelClienteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResponsavelCliente in the database
        List<ResponsavelCliente> responsavelClienteList = responsavelClienteRepository.findAll();
        assertThat(responsavelClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResponsavelClienteWithPatch() throws Exception {
        // Initialize the database
        responsavelClienteRepository.saveAndFlush(responsavelCliente);

        int databaseSizeBeforeUpdate = responsavelClienteRepository.findAll().size();

        // Update the responsavelCliente using partial update
        ResponsavelCliente partialUpdatedResponsavelCliente = new ResponsavelCliente();
        partialUpdatedResponsavelCliente.setId(responsavelCliente.getId());

        partialUpdatedResponsavelCliente
            .nome(UPDATED_NOME)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .rg(UPDATED_RG)
            .telefone2(UPDATED_TELEFONE_2)
            .nomeMae(UPDATED_NOME_MAE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restResponsavelClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponsavelCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponsavelCliente))
            )
            .andExpect(status().isOk());

        // Validate the ResponsavelCliente in the database
        List<ResponsavelCliente> responsavelClienteList = responsavelClienteRepository.findAll();
        assertThat(responsavelClienteList).hasSize(databaseSizeBeforeUpdate);
        ResponsavelCliente testResponsavelCliente = responsavelClienteList.get(responsavelClienteList.size() - 1);
        assertThat(testResponsavelCliente.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testResponsavelCliente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testResponsavelCliente.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testResponsavelCliente.getRg()).isEqualTo(UPDATED_RG);
        assertThat(testResponsavelCliente.getTelefone1()).isEqualTo(DEFAULT_TELEFONE_1);
        assertThat(testResponsavelCliente.getTelefone2()).isEqualTo(UPDATED_TELEFONE_2);
        assertThat(testResponsavelCliente.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testResponsavelCliente.getNomeMae()).isEqualTo(UPDATED_NOME_MAE);
        assertThat(testResponsavelCliente.getNomePai()).isEqualTo(DEFAULT_NOME_PAI);
        assertThat(testResponsavelCliente.getFuncao()).isEqualTo(DEFAULT_FUNCAO);
        assertThat(testResponsavelCliente.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testResponsavelCliente.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testResponsavelCliente.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testResponsavelCliente.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateResponsavelClienteWithPatch() throws Exception {
        // Initialize the database
        responsavelClienteRepository.saveAndFlush(responsavelCliente);

        int databaseSizeBeforeUpdate = responsavelClienteRepository.findAll().size();

        // Update the responsavelCliente using partial update
        ResponsavelCliente partialUpdatedResponsavelCliente = new ResponsavelCliente();
        partialUpdatedResponsavelCliente.setId(responsavelCliente.getId());

        partialUpdatedResponsavelCliente
            .cpf(UPDATED_CPF)
            .nome(UPDATED_NOME)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .rg(UPDATED_RG)
            .telefone1(UPDATED_TELEFONE_1)
            .telefone2(UPDATED_TELEFONE_2)
            .email(UPDATED_EMAIL)
            .nomeMae(UPDATED_NOME_MAE)
            .nomePai(UPDATED_NOME_PAI)
            .funcao(UPDATED_FUNCAO)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restResponsavelClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponsavelCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponsavelCliente))
            )
            .andExpect(status().isOk());

        // Validate the ResponsavelCliente in the database
        List<ResponsavelCliente> responsavelClienteList = responsavelClienteRepository.findAll();
        assertThat(responsavelClienteList).hasSize(databaseSizeBeforeUpdate);
        ResponsavelCliente testResponsavelCliente = responsavelClienteList.get(responsavelClienteList.size() - 1);
        assertThat(testResponsavelCliente.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testResponsavelCliente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testResponsavelCliente.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testResponsavelCliente.getRg()).isEqualTo(UPDATED_RG);
        assertThat(testResponsavelCliente.getTelefone1()).isEqualTo(UPDATED_TELEFONE_1);
        assertThat(testResponsavelCliente.getTelefone2()).isEqualTo(UPDATED_TELEFONE_2);
        assertThat(testResponsavelCliente.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testResponsavelCliente.getNomeMae()).isEqualTo(UPDATED_NOME_MAE);
        assertThat(testResponsavelCliente.getNomePai()).isEqualTo(UPDATED_NOME_PAI);
        assertThat(testResponsavelCliente.getFuncao()).isEqualTo(UPDATED_FUNCAO);
        assertThat(testResponsavelCliente.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testResponsavelCliente.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testResponsavelCliente.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testResponsavelCliente.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingResponsavelCliente() throws Exception {
        int databaseSizeBeforeUpdate = responsavelClienteRepository.findAll().size();
        responsavelCliente.setId(count.incrementAndGet());

        // Create the ResponsavelCliente
        ResponsavelClienteDTO responsavelClienteDTO = responsavelClienteMapper.toDto(responsavelCliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsavelClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, responsavelClienteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsavelClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelCliente in the database
        List<ResponsavelCliente> responsavelClienteList = responsavelClienteRepository.findAll();
        assertThat(responsavelClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResponsavelCliente() throws Exception {
        int databaseSizeBeforeUpdate = responsavelClienteRepository.findAll().size();
        responsavelCliente.setId(count.incrementAndGet());

        // Create the ResponsavelCliente
        ResponsavelClienteDTO responsavelClienteDTO = responsavelClienteMapper.toDto(responsavelCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsavelClienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponsavelCliente in the database
        List<ResponsavelCliente> responsavelClienteList = responsavelClienteRepository.findAll();
        assertThat(responsavelClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResponsavelCliente() throws Exception {
        int databaseSizeBeforeUpdate = responsavelClienteRepository.findAll().size();
        responsavelCliente.setId(count.incrementAndGet());

        // Create the ResponsavelCliente
        ResponsavelClienteDTO responsavelClienteDTO = responsavelClienteMapper.toDto(responsavelCliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsavelClienteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsavelClienteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResponsavelCliente in the database
        List<ResponsavelCliente> responsavelClienteList = responsavelClienteRepository.findAll();
        assertThat(responsavelClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResponsavelCliente() throws Exception {
        // Initialize the database
        responsavelClienteRepository.saveAndFlush(responsavelCliente);

        int databaseSizeBeforeDelete = responsavelClienteRepository.findAll().size();

        // Delete the responsavelCliente
        restResponsavelClienteMockMvc
            .perform(delete(ENTITY_API_URL_ID, responsavelCliente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResponsavelCliente> responsavelClienteList = responsavelClienteRepository.findAll();
        assertThat(responsavelClienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

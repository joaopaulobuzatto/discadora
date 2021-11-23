package com.telvendas.discadora.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.telvendas.discadora.IntegrationTest;
import com.telvendas.discadora.domain.Cliente;
import com.telvendas.discadora.repository.ClienteRepository;
import com.telvendas.discadora.service.dto.ClienteDTO;
import com.telvendas.discadora.service.mapper.ClienteMapper;
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
 * Integration tests for the {@link ClienteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClienteResourceIT {

    private static final String DEFAULT_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBB";

    private static final String DEFAULT_RAZAO_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZAO_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_FANTASIA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_FANTASIA = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_1 = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_2 = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_3 = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_4 = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_4 = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_5 = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_5 = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_6 = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_6 = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_7 = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_7 = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_8 = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_8 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_1 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_2 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_2 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_3 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_3 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_4 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_4 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_5 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_5 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_6 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_6 = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTIDADE_FUNCIONARIOS = 1;
    private static final Integer UPDATED_QUANTIDADE_FUNCIONARIOS = 2;

    private static final Integer DEFAULT_QUANTIDADE_LINHAS_MOVEL = 1;
    private static final Integer UPDATED_QUANTIDADE_LINHAS_MOVEL = 2;

    private static final Integer DEFAULT_QUANTIDADE_LINHAS_FIXA = 1;
    private static final Integer UPDATED_QUANTIDADE_LINHAS_FIXA = 2;

    private static final String DEFAULT_NOME_CONTATO_PRINCIPAL = "AAAAAAAAAA";
    private static final String UPDATED_NOME_CONTATO_PRINCIPAL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_1_CONTATO_PRINCIPAL = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_1_CONTATO_PRINCIPAL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_2_CONTATO_PRINCIPAL = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_2_CONTATO_PRINCIPAL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_CONTATO_PRINCIPAL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_CONTATO_PRINCIPAL = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final String DEFAULT_CEP = "AAAAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBBBB";

    private static final String DEFAULT_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_LOGRADOURO = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_CIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_UF = "AAAAAAAAAA";
    private static final String UPDATED_UF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/clientes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClienteMockMvc;

    private Cliente cliente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cliente createEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .cnpj(DEFAULT_CNPJ)
            .razaoSocial(DEFAULT_RAZAO_SOCIAL)
            .nomeFantasia(DEFAULT_NOME_FANTASIA)
            .telefone1(DEFAULT_TELEFONE_1)
            .telefone2(DEFAULT_TELEFONE_2)
            .telefone3(DEFAULT_TELEFONE_3)
            .telefone4(DEFAULT_TELEFONE_4)
            .telefone5(DEFAULT_TELEFONE_5)
            .telefone6(DEFAULT_TELEFONE_6)
            .telefone7(DEFAULT_TELEFONE_7)
            .telefone8(DEFAULT_TELEFONE_8)
            .email1(DEFAULT_EMAIL_1)
            .email2(DEFAULT_EMAIL_2)
            .email3(DEFAULT_EMAIL_3)
            .email4(DEFAULT_EMAIL_4)
            .email5(DEFAULT_EMAIL_5)
            .email6(DEFAULT_EMAIL_6)
            .quantidadeFuncionarios(DEFAULT_QUANTIDADE_FUNCIONARIOS)
            .quantidadeLinhasMovel(DEFAULT_QUANTIDADE_LINHAS_MOVEL)
            .quantidadeLinhasFixa(DEFAULT_QUANTIDADE_LINHAS_FIXA)
            .nomeContatoPrincipal(DEFAULT_NOME_CONTATO_PRINCIPAL)
            .telefone1ContatoPrincipal(DEFAULT_TELEFONE_1_CONTATO_PRINCIPAL)
            .telefone2ContatoPrincipal(DEFAULT_TELEFONE_2_CONTATO_PRINCIPAL)
            .emailContatoPrincipal(DEFAULT_EMAIL_CONTATO_PRINCIPAL)
            .observacao(DEFAULT_OBSERVACAO)
            .cep(DEFAULT_CEP)
            .logradouro(DEFAULT_LOGRADOURO)
            .bairro(DEFAULT_BAIRRO)
            .numero(DEFAULT_NUMERO)
            .cidade(DEFAULT_CIDADE)
            .uf(DEFAULT_UF);
        return cliente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cliente createUpdatedEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .cnpj(UPDATED_CNPJ)
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .nomeFantasia(UPDATED_NOME_FANTASIA)
            .telefone1(UPDATED_TELEFONE_1)
            .telefone2(UPDATED_TELEFONE_2)
            .telefone3(UPDATED_TELEFONE_3)
            .telefone4(UPDATED_TELEFONE_4)
            .telefone5(UPDATED_TELEFONE_5)
            .telefone6(UPDATED_TELEFONE_6)
            .telefone7(UPDATED_TELEFONE_7)
            .telefone8(UPDATED_TELEFONE_8)
            .email1(UPDATED_EMAIL_1)
            .email2(UPDATED_EMAIL_2)
            .email3(UPDATED_EMAIL_3)
            .email4(UPDATED_EMAIL_4)
            .email5(UPDATED_EMAIL_5)
            .email6(UPDATED_EMAIL_6)
            .quantidadeFuncionarios(UPDATED_QUANTIDADE_FUNCIONARIOS)
            .quantidadeLinhasMovel(UPDATED_QUANTIDADE_LINHAS_MOVEL)
            .quantidadeLinhasFixa(UPDATED_QUANTIDADE_LINHAS_FIXA)
            .nomeContatoPrincipal(UPDATED_NOME_CONTATO_PRINCIPAL)
            .telefone1ContatoPrincipal(UPDATED_TELEFONE_1_CONTATO_PRINCIPAL)
            .telefone2ContatoPrincipal(UPDATED_TELEFONE_2_CONTATO_PRINCIPAL)
            .emailContatoPrincipal(UPDATED_EMAIL_CONTATO_PRINCIPAL)
            .observacao(UPDATED_OBSERVACAO)
            .cep(UPDATED_CEP)
            .logradouro(UPDATED_LOGRADOURO)
            .bairro(UPDATED_BAIRRO)
            .numero(UPDATED_NUMERO)
            .cidade(UPDATED_CIDADE)
            .uf(UPDATED_UF);
        return cliente;
    }

    @BeforeEach
    public void initTest() {
        cliente = createEntity(em);
    }

    @Test
    @Transactional
    void createCliente() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();
        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);
        restClienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate + 1);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testCliente.getRazaoSocial()).isEqualTo(DEFAULT_RAZAO_SOCIAL);
        assertThat(testCliente.getNomeFantasia()).isEqualTo(DEFAULT_NOME_FANTASIA);
        assertThat(testCliente.getTelefone1()).isEqualTo(DEFAULT_TELEFONE_1);
        assertThat(testCliente.getTelefone2()).isEqualTo(DEFAULT_TELEFONE_2);
        assertThat(testCliente.getTelefone3()).isEqualTo(DEFAULT_TELEFONE_3);
        assertThat(testCliente.getTelefone4()).isEqualTo(DEFAULT_TELEFONE_4);
        assertThat(testCliente.getTelefone5()).isEqualTo(DEFAULT_TELEFONE_5);
        assertThat(testCliente.getTelefone6()).isEqualTo(DEFAULT_TELEFONE_6);
        assertThat(testCliente.getTelefone7()).isEqualTo(DEFAULT_TELEFONE_7);
        assertThat(testCliente.getTelefone8()).isEqualTo(DEFAULT_TELEFONE_8);
        assertThat(testCliente.getEmail1()).isEqualTo(DEFAULT_EMAIL_1);
        assertThat(testCliente.getEmail2()).isEqualTo(DEFAULT_EMAIL_2);
        assertThat(testCliente.getEmail3()).isEqualTo(DEFAULT_EMAIL_3);
        assertThat(testCliente.getEmail4()).isEqualTo(DEFAULT_EMAIL_4);
        assertThat(testCliente.getEmail5()).isEqualTo(DEFAULT_EMAIL_5);
        assertThat(testCliente.getEmail6()).isEqualTo(DEFAULT_EMAIL_6);
        assertThat(testCliente.getQuantidadeFuncionarios()).isEqualTo(DEFAULT_QUANTIDADE_FUNCIONARIOS);
        assertThat(testCliente.getQuantidadeLinhasMovel()).isEqualTo(DEFAULT_QUANTIDADE_LINHAS_MOVEL);
        assertThat(testCliente.getQuantidadeLinhasFixa()).isEqualTo(DEFAULT_QUANTIDADE_LINHAS_FIXA);
        assertThat(testCliente.getNomeContatoPrincipal()).isEqualTo(DEFAULT_NOME_CONTATO_PRINCIPAL);
        assertThat(testCliente.getTelefone1ContatoPrincipal()).isEqualTo(DEFAULT_TELEFONE_1_CONTATO_PRINCIPAL);
        assertThat(testCliente.getTelefone2ContatoPrincipal()).isEqualTo(DEFAULT_TELEFONE_2_CONTATO_PRINCIPAL);
        assertThat(testCliente.getEmailContatoPrincipal()).isEqualTo(DEFAULT_EMAIL_CONTATO_PRINCIPAL);
        assertThat(testCliente.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
        assertThat(testCliente.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testCliente.getLogradouro()).isEqualTo(DEFAULT_LOGRADOURO);
        assertThat(testCliente.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testCliente.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCliente.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testCliente.getUf()).isEqualTo(DEFAULT_UF);
    }

    @Test
    @Transactional
    void createClienteWithExistingId() throws Exception {
        // Create the Cliente with an existing ID
        cliente.setId(1L);
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClientes() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList
        restClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].razaoSocial").value(hasItem(DEFAULT_RAZAO_SOCIAL)))
            .andExpect(jsonPath("$.[*].nomeFantasia").value(hasItem(DEFAULT_NOME_FANTASIA)))
            .andExpect(jsonPath("$.[*].telefone1").value(hasItem(DEFAULT_TELEFONE_1)))
            .andExpect(jsonPath("$.[*].telefone2").value(hasItem(DEFAULT_TELEFONE_2)))
            .andExpect(jsonPath("$.[*].telefone3").value(hasItem(DEFAULT_TELEFONE_3)))
            .andExpect(jsonPath("$.[*].telefone4").value(hasItem(DEFAULT_TELEFONE_4)))
            .andExpect(jsonPath("$.[*].telefone5").value(hasItem(DEFAULT_TELEFONE_5)))
            .andExpect(jsonPath("$.[*].telefone6").value(hasItem(DEFAULT_TELEFONE_6)))
            .andExpect(jsonPath("$.[*].telefone7").value(hasItem(DEFAULT_TELEFONE_7)))
            .andExpect(jsonPath("$.[*].telefone8").value(hasItem(DEFAULT_TELEFONE_8)))
            .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1)))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2)))
            .andExpect(jsonPath("$.[*].email3").value(hasItem(DEFAULT_EMAIL_3)))
            .andExpect(jsonPath("$.[*].email4").value(hasItem(DEFAULT_EMAIL_4)))
            .andExpect(jsonPath("$.[*].email5").value(hasItem(DEFAULT_EMAIL_5)))
            .andExpect(jsonPath("$.[*].email6").value(hasItem(DEFAULT_EMAIL_6)))
            .andExpect(jsonPath("$.[*].quantidadeFuncionarios").value(hasItem(DEFAULT_QUANTIDADE_FUNCIONARIOS)))
            .andExpect(jsonPath("$.[*].quantidadeLinhasMovel").value(hasItem(DEFAULT_QUANTIDADE_LINHAS_MOVEL)))
            .andExpect(jsonPath("$.[*].quantidadeLinhasFixa").value(hasItem(DEFAULT_QUANTIDADE_LINHAS_FIXA)))
            .andExpect(jsonPath("$.[*].nomeContatoPrincipal").value(hasItem(DEFAULT_NOME_CONTATO_PRINCIPAL)))
            .andExpect(jsonPath("$.[*].telefone1ContatoPrincipal").value(hasItem(DEFAULT_TELEFONE_1_CONTATO_PRINCIPAL)))
            .andExpect(jsonPath("$.[*].telefone2ContatoPrincipal").value(hasItem(DEFAULT_TELEFONE_2_CONTATO_PRINCIPAL)))
            .andExpect(jsonPath("$.[*].emailContatoPrincipal").value(hasItem(DEFAULT_EMAIL_CONTATO_PRINCIPAL)))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE)))
            .andExpect(jsonPath("$.[*].uf").value(hasItem(DEFAULT_UF)));
    }

    @Test
    @Transactional
    void getCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get the cliente
        restClienteMockMvc
            .perform(get(ENTITY_API_URL_ID, cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cliente.getId().intValue()))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ))
            .andExpect(jsonPath("$.razaoSocial").value(DEFAULT_RAZAO_SOCIAL))
            .andExpect(jsonPath("$.nomeFantasia").value(DEFAULT_NOME_FANTASIA))
            .andExpect(jsonPath("$.telefone1").value(DEFAULT_TELEFONE_1))
            .andExpect(jsonPath("$.telefone2").value(DEFAULT_TELEFONE_2))
            .andExpect(jsonPath("$.telefone3").value(DEFAULT_TELEFONE_3))
            .andExpect(jsonPath("$.telefone4").value(DEFAULT_TELEFONE_4))
            .andExpect(jsonPath("$.telefone5").value(DEFAULT_TELEFONE_5))
            .andExpect(jsonPath("$.telefone6").value(DEFAULT_TELEFONE_6))
            .andExpect(jsonPath("$.telefone7").value(DEFAULT_TELEFONE_7))
            .andExpect(jsonPath("$.telefone8").value(DEFAULT_TELEFONE_8))
            .andExpect(jsonPath("$.email1").value(DEFAULT_EMAIL_1))
            .andExpect(jsonPath("$.email2").value(DEFAULT_EMAIL_2))
            .andExpect(jsonPath("$.email3").value(DEFAULT_EMAIL_3))
            .andExpect(jsonPath("$.email4").value(DEFAULT_EMAIL_4))
            .andExpect(jsonPath("$.email5").value(DEFAULT_EMAIL_5))
            .andExpect(jsonPath("$.email6").value(DEFAULT_EMAIL_6))
            .andExpect(jsonPath("$.quantidadeFuncionarios").value(DEFAULT_QUANTIDADE_FUNCIONARIOS))
            .andExpect(jsonPath("$.quantidadeLinhasMovel").value(DEFAULT_QUANTIDADE_LINHAS_MOVEL))
            .andExpect(jsonPath("$.quantidadeLinhasFixa").value(DEFAULT_QUANTIDADE_LINHAS_FIXA))
            .andExpect(jsonPath("$.nomeContatoPrincipal").value(DEFAULT_NOME_CONTATO_PRINCIPAL))
            .andExpect(jsonPath("$.telefone1ContatoPrincipal").value(DEFAULT_TELEFONE_1_CONTATO_PRINCIPAL))
            .andExpect(jsonPath("$.telefone2ContatoPrincipal").value(DEFAULT_TELEFONE_2_CONTATO_PRINCIPAL))
            .andExpect(jsonPath("$.emailContatoPrincipal").value(DEFAULT_EMAIL_CONTATO_PRINCIPAL))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP))
            .andExpect(jsonPath("$.logradouro").value(DEFAULT_LOGRADOURO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.cidade").value(DEFAULT_CIDADE))
            .andExpect(jsonPath("$.uf").value(DEFAULT_UF));
    }

    @Test
    @Transactional
    void getNonExistingCliente() throws Exception {
        // Get the cliente
        restClienteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente
        Cliente updatedCliente = clienteRepository.findById(cliente.getId()).get();
        // Disconnect from session so that the updates on updatedCliente are not directly saved in db
        em.detach(updatedCliente);
        updatedCliente
            .cnpj(UPDATED_CNPJ)
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .nomeFantasia(UPDATED_NOME_FANTASIA)
            .telefone1(UPDATED_TELEFONE_1)
            .telefone2(UPDATED_TELEFONE_2)
            .telefone3(UPDATED_TELEFONE_3)
            .telefone4(UPDATED_TELEFONE_4)
            .telefone5(UPDATED_TELEFONE_5)
            .telefone6(UPDATED_TELEFONE_6)
            .telefone7(UPDATED_TELEFONE_7)
            .telefone8(UPDATED_TELEFONE_8)
            .email1(UPDATED_EMAIL_1)
            .email2(UPDATED_EMAIL_2)
            .email3(UPDATED_EMAIL_3)
            .email4(UPDATED_EMAIL_4)
            .email5(UPDATED_EMAIL_5)
            .email6(UPDATED_EMAIL_6)
            .quantidadeFuncionarios(UPDATED_QUANTIDADE_FUNCIONARIOS)
            .quantidadeLinhasMovel(UPDATED_QUANTIDADE_LINHAS_MOVEL)
            .quantidadeLinhasFixa(UPDATED_QUANTIDADE_LINHAS_FIXA)
            .nomeContatoPrincipal(UPDATED_NOME_CONTATO_PRINCIPAL)
            .telefone1ContatoPrincipal(UPDATED_TELEFONE_1_CONTATO_PRINCIPAL)
            .telefone2ContatoPrincipal(UPDATED_TELEFONE_2_CONTATO_PRINCIPAL)
            .emailContatoPrincipal(UPDATED_EMAIL_CONTATO_PRINCIPAL)
            .observacao(UPDATED_OBSERVACAO)
            .cep(UPDATED_CEP)
            .logradouro(UPDATED_LOGRADOURO)
            .bairro(UPDATED_BAIRRO)
            .numero(UPDATED_NUMERO)
            .cidade(UPDATED_CIDADE)
            .uf(UPDATED_UF);
        ClienteDTO clienteDTO = clienteMapper.toDto(updatedCliente);

        restClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testCliente.getRazaoSocial()).isEqualTo(UPDATED_RAZAO_SOCIAL);
        assertThat(testCliente.getNomeFantasia()).isEqualTo(UPDATED_NOME_FANTASIA);
        assertThat(testCliente.getTelefone1()).isEqualTo(UPDATED_TELEFONE_1);
        assertThat(testCliente.getTelefone2()).isEqualTo(UPDATED_TELEFONE_2);
        assertThat(testCliente.getTelefone3()).isEqualTo(UPDATED_TELEFONE_3);
        assertThat(testCliente.getTelefone4()).isEqualTo(UPDATED_TELEFONE_4);
        assertThat(testCliente.getTelefone5()).isEqualTo(UPDATED_TELEFONE_5);
        assertThat(testCliente.getTelefone6()).isEqualTo(UPDATED_TELEFONE_6);
        assertThat(testCliente.getTelefone7()).isEqualTo(UPDATED_TELEFONE_7);
        assertThat(testCliente.getTelefone8()).isEqualTo(UPDATED_TELEFONE_8);
        assertThat(testCliente.getEmail1()).isEqualTo(UPDATED_EMAIL_1);
        assertThat(testCliente.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testCliente.getEmail3()).isEqualTo(UPDATED_EMAIL_3);
        assertThat(testCliente.getEmail4()).isEqualTo(UPDATED_EMAIL_4);
        assertThat(testCliente.getEmail5()).isEqualTo(UPDATED_EMAIL_5);
        assertThat(testCliente.getEmail6()).isEqualTo(UPDATED_EMAIL_6);
        assertThat(testCliente.getQuantidadeFuncionarios()).isEqualTo(UPDATED_QUANTIDADE_FUNCIONARIOS);
        assertThat(testCliente.getQuantidadeLinhasMovel()).isEqualTo(UPDATED_QUANTIDADE_LINHAS_MOVEL);
        assertThat(testCliente.getQuantidadeLinhasFixa()).isEqualTo(UPDATED_QUANTIDADE_LINHAS_FIXA);
        assertThat(testCliente.getNomeContatoPrincipal()).isEqualTo(UPDATED_NOME_CONTATO_PRINCIPAL);
        assertThat(testCliente.getTelefone1ContatoPrincipal()).isEqualTo(UPDATED_TELEFONE_1_CONTATO_PRINCIPAL);
        assertThat(testCliente.getTelefone2ContatoPrincipal()).isEqualTo(UPDATED_TELEFONE_2_CONTATO_PRINCIPAL);
        assertThat(testCliente.getEmailContatoPrincipal()).isEqualTo(UPDATED_EMAIL_CONTATO_PRINCIPAL);
        assertThat(testCliente.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testCliente.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testCliente.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testCliente.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testCliente.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCliente.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testCliente.getUf()).isEqualTo(UPDATED_UF);
    }

    @Test
    @Transactional
    void putNonExistingCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClienteWithPatch() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente using partial update
        Cliente partialUpdatedCliente = new Cliente();
        partialUpdatedCliente.setId(cliente.getId());

        partialUpdatedCliente
            .nomeFantasia(UPDATED_NOME_FANTASIA)
            .telefone7(UPDATED_TELEFONE_7)
            .telefone8(UPDATED_TELEFONE_8)
            .email1(UPDATED_EMAIL_1)
            .email2(UPDATED_EMAIL_2)
            .email3(UPDATED_EMAIL_3)
            .email4(UPDATED_EMAIL_4)
            .email5(UPDATED_EMAIL_5)
            .quantidadeLinhasMovel(UPDATED_QUANTIDADE_LINHAS_MOVEL)
            .quantidadeLinhasFixa(UPDATED_QUANTIDADE_LINHAS_FIXA)
            .emailContatoPrincipal(UPDATED_EMAIL_CONTATO_PRINCIPAL)
            .observacao(UPDATED_OBSERVACAO)
            .cep(UPDATED_CEP)
            .logradouro(UPDATED_LOGRADOURO)
            .uf(UPDATED_UF);

        restClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCliente))
            )
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testCliente.getRazaoSocial()).isEqualTo(DEFAULT_RAZAO_SOCIAL);
        assertThat(testCliente.getNomeFantasia()).isEqualTo(UPDATED_NOME_FANTASIA);
        assertThat(testCliente.getTelefone1()).isEqualTo(DEFAULT_TELEFONE_1);
        assertThat(testCliente.getTelefone2()).isEqualTo(DEFAULT_TELEFONE_2);
        assertThat(testCliente.getTelefone3()).isEqualTo(DEFAULT_TELEFONE_3);
        assertThat(testCliente.getTelefone4()).isEqualTo(DEFAULT_TELEFONE_4);
        assertThat(testCliente.getTelefone5()).isEqualTo(DEFAULT_TELEFONE_5);
        assertThat(testCliente.getTelefone6()).isEqualTo(DEFAULT_TELEFONE_6);
        assertThat(testCliente.getTelefone7()).isEqualTo(UPDATED_TELEFONE_7);
        assertThat(testCliente.getTelefone8()).isEqualTo(UPDATED_TELEFONE_8);
        assertThat(testCliente.getEmail1()).isEqualTo(UPDATED_EMAIL_1);
        assertThat(testCliente.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testCliente.getEmail3()).isEqualTo(UPDATED_EMAIL_3);
        assertThat(testCliente.getEmail4()).isEqualTo(UPDATED_EMAIL_4);
        assertThat(testCliente.getEmail5()).isEqualTo(UPDATED_EMAIL_5);
        assertThat(testCliente.getEmail6()).isEqualTo(DEFAULT_EMAIL_6);
        assertThat(testCliente.getQuantidadeFuncionarios()).isEqualTo(DEFAULT_QUANTIDADE_FUNCIONARIOS);
        assertThat(testCliente.getQuantidadeLinhasMovel()).isEqualTo(UPDATED_QUANTIDADE_LINHAS_MOVEL);
        assertThat(testCliente.getQuantidadeLinhasFixa()).isEqualTo(UPDATED_QUANTIDADE_LINHAS_FIXA);
        assertThat(testCliente.getNomeContatoPrincipal()).isEqualTo(DEFAULT_NOME_CONTATO_PRINCIPAL);
        assertThat(testCliente.getTelefone1ContatoPrincipal()).isEqualTo(DEFAULT_TELEFONE_1_CONTATO_PRINCIPAL);
        assertThat(testCliente.getTelefone2ContatoPrincipal()).isEqualTo(DEFAULT_TELEFONE_2_CONTATO_PRINCIPAL);
        assertThat(testCliente.getEmailContatoPrincipal()).isEqualTo(UPDATED_EMAIL_CONTATO_PRINCIPAL);
        assertThat(testCliente.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testCliente.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testCliente.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testCliente.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testCliente.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCliente.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testCliente.getUf()).isEqualTo(UPDATED_UF);
    }

    @Test
    @Transactional
    void fullUpdateClienteWithPatch() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente using partial update
        Cliente partialUpdatedCliente = new Cliente();
        partialUpdatedCliente.setId(cliente.getId());

        partialUpdatedCliente
            .cnpj(UPDATED_CNPJ)
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .nomeFantasia(UPDATED_NOME_FANTASIA)
            .telefone1(UPDATED_TELEFONE_1)
            .telefone2(UPDATED_TELEFONE_2)
            .telefone3(UPDATED_TELEFONE_3)
            .telefone4(UPDATED_TELEFONE_4)
            .telefone5(UPDATED_TELEFONE_5)
            .telefone6(UPDATED_TELEFONE_6)
            .telefone7(UPDATED_TELEFONE_7)
            .telefone8(UPDATED_TELEFONE_8)
            .email1(UPDATED_EMAIL_1)
            .email2(UPDATED_EMAIL_2)
            .email3(UPDATED_EMAIL_3)
            .email4(UPDATED_EMAIL_4)
            .email5(UPDATED_EMAIL_5)
            .email6(UPDATED_EMAIL_6)
            .quantidadeFuncionarios(UPDATED_QUANTIDADE_FUNCIONARIOS)
            .quantidadeLinhasMovel(UPDATED_QUANTIDADE_LINHAS_MOVEL)
            .quantidadeLinhasFixa(UPDATED_QUANTIDADE_LINHAS_FIXA)
            .nomeContatoPrincipal(UPDATED_NOME_CONTATO_PRINCIPAL)
            .telefone1ContatoPrincipal(UPDATED_TELEFONE_1_CONTATO_PRINCIPAL)
            .telefone2ContatoPrincipal(UPDATED_TELEFONE_2_CONTATO_PRINCIPAL)
            .emailContatoPrincipal(UPDATED_EMAIL_CONTATO_PRINCIPAL)
            .observacao(UPDATED_OBSERVACAO)
            .cep(UPDATED_CEP)
            .logradouro(UPDATED_LOGRADOURO)
            .bairro(UPDATED_BAIRRO)
            .numero(UPDATED_NUMERO)
            .cidade(UPDATED_CIDADE)
            .uf(UPDATED_UF);

        restClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCliente))
            )
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testCliente.getRazaoSocial()).isEqualTo(UPDATED_RAZAO_SOCIAL);
        assertThat(testCliente.getNomeFantasia()).isEqualTo(UPDATED_NOME_FANTASIA);
        assertThat(testCliente.getTelefone1()).isEqualTo(UPDATED_TELEFONE_1);
        assertThat(testCliente.getTelefone2()).isEqualTo(UPDATED_TELEFONE_2);
        assertThat(testCliente.getTelefone3()).isEqualTo(UPDATED_TELEFONE_3);
        assertThat(testCliente.getTelefone4()).isEqualTo(UPDATED_TELEFONE_4);
        assertThat(testCliente.getTelefone5()).isEqualTo(UPDATED_TELEFONE_5);
        assertThat(testCliente.getTelefone6()).isEqualTo(UPDATED_TELEFONE_6);
        assertThat(testCliente.getTelefone7()).isEqualTo(UPDATED_TELEFONE_7);
        assertThat(testCliente.getTelefone8()).isEqualTo(UPDATED_TELEFONE_8);
        assertThat(testCliente.getEmail1()).isEqualTo(UPDATED_EMAIL_1);
        assertThat(testCliente.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testCliente.getEmail3()).isEqualTo(UPDATED_EMAIL_3);
        assertThat(testCliente.getEmail4()).isEqualTo(UPDATED_EMAIL_4);
        assertThat(testCliente.getEmail5()).isEqualTo(UPDATED_EMAIL_5);
        assertThat(testCliente.getEmail6()).isEqualTo(UPDATED_EMAIL_6);
        assertThat(testCliente.getQuantidadeFuncionarios()).isEqualTo(UPDATED_QUANTIDADE_FUNCIONARIOS);
        assertThat(testCliente.getQuantidadeLinhasMovel()).isEqualTo(UPDATED_QUANTIDADE_LINHAS_MOVEL);
        assertThat(testCliente.getQuantidadeLinhasFixa()).isEqualTo(UPDATED_QUANTIDADE_LINHAS_FIXA);
        assertThat(testCliente.getNomeContatoPrincipal()).isEqualTo(UPDATED_NOME_CONTATO_PRINCIPAL);
        assertThat(testCliente.getTelefone1ContatoPrincipal()).isEqualTo(UPDATED_TELEFONE_1_CONTATO_PRINCIPAL);
        assertThat(testCliente.getTelefone2ContatoPrincipal()).isEqualTo(UPDATED_TELEFONE_2_CONTATO_PRINCIPAL);
        assertThat(testCliente.getEmailContatoPrincipal()).isEqualTo(UPDATED_EMAIL_CONTATO_PRINCIPAL);
        assertThat(testCliente.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
        assertThat(testCliente.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testCliente.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testCliente.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testCliente.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCliente.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testCliente.getUf()).isEqualTo(UPDATED_UF);
    }

    @Test
    @Transactional
    void patchNonExistingCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clienteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();
        cliente.setId(count.incrementAndGet());

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clienteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeDelete = clienteRepository.findAll().size();

        // Delete the cliente
        restClienteMockMvc
            .perform(delete(ENTITY_API_URL_ID, cliente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

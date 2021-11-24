package com.telvendas.discadora.web.rest;

import com.telvendas.discadora.repository.ResponsavelClienteRepository;
import com.telvendas.discadora.service.ResponsavelClienteService;
import com.telvendas.discadora.service.dto.ResponsavelClienteDTO;
import com.telvendas.discadora.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.telvendas.discadora.domain.ResponsavelCliente}.
 */
@RestController
@RequestMapping("/api")
public class ResponsavelClienteResource {

    private final Logger log = LoggerFactory.getLogger(ResponsavelClienteResource.class);

    private static final String ENTITY_NAME = "responsavelCliente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResponsavelClienteService responsavelClienteService;

    private final ResponsavelClienteRepository responsavelClienteRepository;

    public ResponsavelClienteResource(
        ResponsavelClienteService responsavelClienteService,
        ResponsavelClienteRepository responsavelClienteRepository
    ) {
        this.responsavelClienteService = responsavelClienteService;
        this.responsavelClienteRepository = responsavelClienteRepository;
    }

    /**
     * {@code POST  /responsavel-clientes} : Create a new responsavelCliente.
     *
     * @param responsavelClienteDTO the responsavelClienteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new responsavelClienteDTO, or with status {@code 400 (Bad Request)} if the responsavelCliente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/responsavel-clientes")
    public ResponseEntity<ResponsavelClienteDTO> createResponsavelCliente(@RequestBody ResponsavelClienteDTO responsavelClienteDTO)
        throws URISyntaxException {
        log.debug("REST request to save ResponsavelCliente : {}", responsavelClienteDTO);
        if (responsavelClienteDTO.getId() != null) {
            throw new BadRequestAlertException("A new responsavelCliente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResponsavelClienteDTO result = responsavelClienteService.save(responsavelClienteDTO);
        return ResponseEntity
            .created(new URI("/api/responsavel-clientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /responsavel-clientes/:id} : Updates an existing responsavelCliente.
     *
     * @param id the id of the responsavelClienteDTO to save.
     * @param responsavelClienteDTO the responsavelClienteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsavelClienteDTO,
     * or with status {@code 400 (Bad Request)} if the responsavelClienteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the responsavelClienteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/responsavel-clientes/{id}")
    public ResponseEntity<ResponsavelClienteDTO> updateResponsavelCliente(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResponsavelClienteDTO responsavelClienteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ResponsavelCliente : {}, {}", id, responsavelClienteDTO);
        if (responsavelClienteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responsavelClienteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responsavelClienteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResponsavelClienteDTO result = responsavelClienteService.save(responsavelClienteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, responsavelClienteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /responsavel-clientes/:id} : Partial updates given fields of an existing responsavelCliente, field will ignore if it is null
     *
     * @param id the id of the responsavelClienteDTO to save.
     * @param responsavelClienteDTO the responsavelClienteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsavelClienteDTO,
     * or with status {@code 400 (Bad Request)} if the responsavelClienteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the responsavelClienteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the responsavelClienteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/responsavel-clientes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResponsavelClienteDTO> partialUpdateResponsavelCliente(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResponsavelClienteDTO responsavelClienteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResponsavelCliente partially : {}, {}", id, responsavelClienteDTO);
        if (responsavelClienteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responsavelClienteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responsavelClienteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResponsavelClienteDTO> result = responsavelClienteService.partialUpdate(responsavelClienteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, responsavelClienteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /responsavel-clientes} : get all the responsavelClientes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of responsavelClientes in body.
     */
    @GetMapping("/responsavel-clientes")
    public ResponseEntity<List<ResponsavelClienteDTO>> getAllResponsavelClientes(Pageable pageable) {
        log.debug("REST request to get a page of ResponsavelClientes");
        Page<ResponsavelClienteDTO> page = responsavelClienteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /responsavel-clientes/:id} : get the "id" responsavelCliente.
     *
     * @param id the id of the responsavelClienteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the responsavelClienteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/responsavel-clientes/{id}")
    public ResponseEntity<ResponsavelClienteDTO> getResponsavelCliente(@PathVariable Long id) {
        log.debug("REST request to get ResponsavelCliente : {}", id);
        Optional<ResponsavelClienteDTO> responsavelClienteDTO = responsavelClienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(responsavelClienteDTO);
    }

    /**
     * {@code DELETE  /responsavel-clientes/:id} : delete the "id" responsavelCliente.
     *
     * @param id the id of the responsavelClienteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/responsavel-clientes/{id}")
    public ResponseEntity<Void> deleteResponsavelCliente(@PathVariable Long id) {
        log.debug("REST request to delete ResponsavelCliente : {}", id);
        responsavelClienteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}

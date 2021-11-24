package com.telvendas.discadora.web.rest;

import com.telvendas.discadora.repository.FaturaRepository;
import com.telvendas.discadora.service.FaturaService;
import com.telvendas.discadora.service.dto.FaturaDTO;
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
 * REST controller for managing {@link com.telvendas.discadora.domain.Fatura}.
 */
@RestController
@RequestMapping("/api")
public class FaturaResource {

    private final Logger log = LoggerFactory.getLogger(FaturaResource.class);

    private static final String ENTITY_NAME = "fatura";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FaturaService faturaService;

    private final FaturaRepository faturaRepository;

    public FaturaResource(FaturaService faturaService, FaturaRepository faturaRepository) {
        this.faturaService = faturaService;
        this.faturaRepository = faturaRepository;
    }

    /**
     * {@code POST  /faturas} : Create a new fatura.
     *
     * @param faturaDTO the faturaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new faturaDTO, or with status {@code 400 (Bad Request)} if the fatura has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/faturas")
    public ResponseEntity<FaturaDTO> createFatura(@RequestBody FaturaDTO faturaDTO) throws URISyntaxException {
        log.debug("REST request to save Fatura : {}", faturaDTO);
        if (faturaDTO.getId() != null) {
            throw new BadRequestAlertException("A new fatura cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FaturaDTO result = faturaService.save(faturaDTO);
        return ResponseEntity
            .created(new URI("/api/faturas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /faturas/:id} : Updates an existing fatura.
     *
     * @param id the id of the faturaDTO to save.
     * @param faturaDTO the faturaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated faturaDTO,
     * or with status {@code 400 (Bad Request)} if the faturaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the faturaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/faturas/{id}")
    public ResponseEntity<FaturaDTO> updateFatura(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FaturaDTO faturaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Fatura : {}, {}", id, faturaDTO);
        if (faturaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, faturaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!faturaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FaturaDTO result = faturaService.save(faturaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, faturaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /faturas/:id} : Partial updates given fields of an existing fatura, field will ignore if it is null
     *
     * @param id the id of the faturaDTO to save.
     * @param faturaDTO the faturaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated faturaDTO,
     * or with status {@code 400 (Bad Request)} if the faturaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the faturaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the faturaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/faturas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FaturaDTO> partialUpdateFatura(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FaturaDTO faturaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Fatura partially : {}, {}", id, faturaDTO);
        if (faturaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, faturaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!faturaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FaturaDTO> result = faturaService.partialUpdate(faturaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, faturaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /faturas} : get all the faturas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of faturas in body.
     */
    @GetMapping("/faturas")
    public ResponseEntity<List<FaturaDTO>> getAllFaturas(Pageable pageable) {
        log.debug("REST request to get a page of Faturas");
        Page<FaturaDTO> page = faturaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /faturas/:id} : get the "id" fatura.
     *
     * @param id the id of the faturaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the faturaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/faturas/{id}")
    public ResponseEntity<FaturaDTO> getFatura(@PathVariable Long id) {
        log.debug("REST request to get Fatura : {}", id);
        Optional<FaturaDTO> faturaDTO = faturaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(faturaDTO);
    }

    /**
     * {@code DELETE  /faturas/:id} : delete the "id" fatura.
     *
     * @param id the id of the faturaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/faturas/{id}")
    public ResponseEntity<Void> deleteFatura(@PathVariable Long id) {
        log.debug("REST request to delete Fatura : {}", id);
        faturaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}

package com.telvendas.discadora.web.rest;

import com.telvendas.discadora.repository.LinhaMovelRepository;
import com.telvendas.discadora.service.LinhaMovelService;
import com.telvendas.discadora.service.dto.LinhaMovelDTO;
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
 * REST controller for managing {@link com.telvendas.discadora.domain.LinhaMovel}.
 */
@RestController
@RequestMapping("/api")
public class LinhaMovelResource {

    private final Logger log = LoggerFactory.getLogger(LinhaMovelResource.class);

    private static final String ENTITY_NAME = "linhaMovel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LinhaMovelService linhaMovelService;

    private final LinhaMovelRepository linhaMovelRepository;

    public LinhaMovelResource(LinhaMovelService linhaMovelService, LinhaMovelRepository linhaMovelRepository) {
        this.linhaMovelService = linhaMovelService;
        this.linhaMovelRepository = linhaMovelRepository;
    }

    /**
     * {@code POST  /linha-movels} : Create a new linhaMovel.
     *
     * @param linhaMovelDTO the linhaMovelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new linhaMovelDTO, or with status {@code 400 (Bad Request)} if the linhaMovel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/linha-movels")
    public ResponseEntity<LinhaMovelDTO> createLinhaMovel(@RequestBody LinhaMovelDTO linhaMovelDTO) throws URISyntaxException {
        log.debug("REST request to save LinhaMovel : {}", linhaMovelDTO);
        if (linhaMovelDTO.getId() != null) {
            throw new BadRequestAlertException("A new linhaMovel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LinhaMovelDTO result = linhaMovelService.save(linhaMovelDTO);
        return ResponseEntity
            .created(new URI("/api/linha-movels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /linha-movels/:id} : Updates an existing linhaMovel.
     *
     * @param id the id of the linhaMovelDTO to save.
     * @param linhaMovelDTO the linhaMovelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated linhaMovelDTO,
     * or with status {@code 400 (Bad Request)} if the linhaMovelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the linhaMovelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/linha-movels/{id}")
    public ResponseEntity<LinhaMovelDTO> updateLinhaMovel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LinhaMovelDTO linhaMovelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LinhaMovel : {}, {}", id, linhaMovelDTO);
        if (linhaMovelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, linhaMovelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!linhaMovelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LinhaMovelDTO result = linhaMovelService.save(linhaMovelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, linhaMovelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /linha-movels/:id} : Partial updates given fields of an existing linhaMovel, field will ignore if it is null
     *
     * @param id the id of the linhaMovelDTO to save.
     * @param linhaMovelDTO the linhaMovelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated linhaMovelDTO,
     * or with status {@code 400 (Bad Request)} if the linhaMovelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the linhaMovelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the linhaMovelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/linha-movels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LinhaMovelDTO> partialUpdateLinhaMovel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LinhaMovelDTO linhaMovelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LinhaMovel partially : {}, {}", id, linhaMovelDTO);
        if (linhaMovelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, linhaMovelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!linhaMovelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LinhaMovelDTO> result = linhaMovelService.partialUpdate(linhaMovelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, linhaMovelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /linha-movels} : get all the linhaMovels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of linhaMovels in body.
     */
    @GetMapping("/linha-movels")
    public ResponseEntity<List<LinhaMovelDTO>> getAllLinhaMovels(Pageable pageable) {
        log.debug("REST request to get a page of LinhaMovels");
        Page<LinhaMovelDTO> page = linhaMovelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /linha-movels/:id} : get the "id" linhaMovel.
     *
     * @param id the id of the linhaMovelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the linhaMovelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/linha-movels/{id}")
    public ResponseEntity<LinhaMovelDTO> getLinhaMovel(@PathVariable Long id) {
        log.debug("REST request to get LinhaMovel : {}", id);
        Optional<LinhaMovelDTO> linhaMovelDTO = linhaMovelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(linhaMovelDTO);
    }

    /**
     * {@code DELETE  /linha-movels/:id} : delete the "id" linhaMovel.
     *
     * @param id the id of the linhaMovelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/linha-movels/{id}")
    public ResponseEntity<Void> deleteLinhaMovel(@PathVariable Long id) {
        log.debug("REST request to delete LinhaMovel : {}", id);
        linhaMovelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}

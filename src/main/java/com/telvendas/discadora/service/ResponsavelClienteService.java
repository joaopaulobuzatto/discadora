package com.telvendas.discadora.service;

import com.telvendas.discadora.domain.ResponsavelCliente;
import com.telvendas.discadora.repository.ResponsavelClienteRepository;
import com.telvendas.discadora.service.dto.ResponsavelClienteDTO;
import com.telvendas.discadora.service.mapper.ResponsavelClienteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ResponsavelCliente}.
 */
@Service
@Transactional
public class ResponsavelClienteService {

    private final Logger log = LoggerFactory.getLogger(ResponsavelClienteService.class);

    private final ResponsavelClienteRepository responsavelClienteRepository;

    private final ResponsavelClienteMapper responsavelClienteMapper;

    public ResponsavelClienteService(
        ResponsavelClienteRepository responsavelClienteRepository,
        ResponsavelClienteMapper responsavelClienteMapper
    ) {
        this.responsavelClienteRepository = responsavelClienteRepository;
        this.responsavelClienteMapper = responsavelClienteMapper;
    }

    /**
     * Save a responsavelCliente.
     *
     * @param responsavelClienteDTO the entity to save.
     * @return the persisted entity.
     */
    public ResponsavelClienteDTO save(ResponsavelClienteDTO responsavelClienteDTO) {
        log.debug("Request to save ResponsavelCliente : {}", responsavelClienteDTO);
        ResponsavelCliente responsavelCliente = responsavelClienteMapper.toEntity(responsavelClienteDTO);
        responsavelCliente = responsavelClienteRepository.save(responsavelCliente);
        return responsavelClienteMapper.toDto(responsavelCliente);
    }

    /**
     * Partially update a responsavelCliente.
     *
     * @param responsavelClienteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ResponsavelClienteDTO> partialUpdate(ResponsavelClienteDTO responsavelClienteDTO) {
        log.debug("Request to partially update ResponsavelCliente : {}", responsavelClienteDTO);

        return responsavelClienteRepository
            .findById(responsavelClienteDTO.getId())
            .map(existingResponsavelCliente -> {
                responsavelClienteMapper.partialUpdate(existingResponsavelCliente, responsavelClienteDTO);

                return existingResponsavelCliente;
            })
            .map(responsavelClienteRepository::save)
            .map(responsavelClienteMapper::toDto);
    }

    /**
     * Get all the responsavelClientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ResponsavelClienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResponsavelClientes");
        return responsavelClienteRepository.findAll(pageable).map(responsavelClienteMapper::toDto);
    }

    /**
     * Get one responsavelCliente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ResponsavelClienteDTO> findOne(Long id) {
        log.debug("Request to get ResponsavelCliente : {}", id);
        return responsavelClienteRepository.findById(id).map(responsavelClienteMapper::toDto);
    }

    /**
     * Delete the responsavelCliente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ResponsavelCliente : {}", id);
        responsavelClienteRepository.deleteById(id);
    }
}

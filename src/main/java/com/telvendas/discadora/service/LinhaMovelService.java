package com.telvendas.discadora.service;

import com.telvendas.discadora.domain.LinhaMovel;
import com.telvendas.discadora.repository.LinhaMovelRepository;
import com.telvendas.discadora.service.dto.LinhaMovelDTO;
import com.telvendas.discadora.service.mapper.LinhaMovelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LinhaMovel}.
 */
@Service
@Transactional
public class LinhaMovelService {

    private final Logger log = LoggerFactory.getLogger(LinhaMovelService.class);

    private final LinhaMovelRepository linhaMovelRepository;

    private final LinhaMovelMapper linhaMovelMapper;

    public LinhaMovelService(LinhaMovelRepository linhaMovelRepository, LinhaMovelMapper linhaMovelMapper) {
        this.linhaMovelRepository = linhaMovelRepository;
        this.linhaMovelMapper = linhaMovelMapper;
    }

    /**
     * Save a linhaMovel.
     *
     * @param linhaMovelDTO the entity to save.
     * @return the persisted entity.
     */
    public LinhaMovelDTO save(LinhaMovelDTO linhaMovelDTO) {
        log.debug("Request to save LinhaMovel : {}", linhaMovelDTO);
        LinhaMovel linhaMovel = linhaMovelMapper.toEntity(linhaMovelDTO);
        linhaMovel = linhaMovelRepository.save(linhaMovel);
        return linhaMovelMapper.toDto(linhaMovel);
    }

    /**
     * Partially update a linhaMovel.
     *
     * @param linhaMovelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LinhaMovelDTO> partialUpdate(LinhaMovelDTO linhaMovelDTO) {
        log.debug("Request to partially update LinhaMovel : {}", linhaMovelDTO);

        return linhaMovelRepository
            .findById(linhaMovelDTO.getId())
            .map(existingLinhaMovel -> {
                linhaMovelMapper.partialUpdate(existingLinhaMovel, linhaMovelDTO);

                return existingLinhaMovel;
            })
            .map(linhaMovelRepository::save)
            .map(linhaMovelMapper::toDto);
    }

    /**
     * Get all the linhaMovels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LinhaMovelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LinhaMovels");
        return linhaMovelRepository.findAll(pageable).map(linhaMovelMapper::toDto);
    }

    /**
     * Get one linhaMovel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LinhaMovelDTO> findOne(Long id) {
        log.debug("Request to get LinhaMovel : {}", id);
        return linhaMovelRepository.findById(id).map(linhaMovelMapper::toDto);
    }

    /**
     * Delete the linhaMovel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LinhaMovel : {}", id);
        linhaMovelRepository.deleteById(id);
    }
}

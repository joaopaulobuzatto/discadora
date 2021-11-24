package com.telvendas.discadora.service;

import com.telvendas.discadora.domain.Filial;
import com.telvendas.discadora.repository.FilialRepository;
import com.telvendas.discadora.service.dto.FilialDTO;
import com.telvendas.discadora.service.mapper.FilialMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Filial}.
 */
@Service
@Transactional
public class FilialService {

    private final Logger log = LoggerFactory.getLogger(FilialService.class);

    private final FilialRepository filialRepository;

    private final FilialMapper filialMapper;

    public FilialService(FilialRepository filialRepository, FilialMapper filialMapper) {
        this.filialRepository = filialRepository;
        this.filialMapper = filialMapper;
    }

    /**
     * Save a filial.
     *
     * @param filialDTO the entity to save.
     * @return the persisted entity.
     */
    public FilialDTO save(FilialDTO filialDTO) {
        log.debug("Request to save Filial : {}", filialDTO);
        Filial filial = filialMapper.toEntity(filialDTO);
        filial = filialRepository.save(filial);
        return filialMapper.toDto(filial);
    }

    /**
     * Partially update a filial.
     *
     * @param filialDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FilialDTO> partialUpdate(FilialDTO filialDTO) {
        log.debug("Request to partially update Filial : {}", filialDTO);

        return filialRepository
            .findById(filialDTO.getId())
            .map(existingFilial -> {
                filialMapper.partialUpdate(existingFilial, filialDTO);

                return existingFilial;
            })
            .map(filialRepository::save)
            .map(filialMapper::toDto);
    }

    /**
     * Get all the filials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FilialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Filials");
        return filialRepository.findAll(pageable).map(filialMapper::toDto);
    }

    /**
     * Get one filial by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FilialDTO> findOne(Long id) {
        log.debug("Request to get Filial : {}", id);
        return filialRepository.findById(id).map(filialMapper::toDto);
    }

    /**
     * Delete the filial by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Filial : {}", id);
        filialRepository.deleteById(id);
    }
}

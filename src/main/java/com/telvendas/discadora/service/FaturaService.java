package com.telvendas.discadora.service;

import com.telvendas.discadora.domain.Fatura;
import com.telvendas.discadora.repository.FaturaRepository;
import com.telvendas.discadora.service.dto.FaturaDTO;
import com.telvendas.discadora.service.mapper.FaturaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Fatura}.
 */
@Service
@Transactional
public class FaturaService {

    private final Logger log = LoggerFactory.getLogger(FaturaService.class);

    private final FaturaRepository faturaRepository;

    private final FaturaMapper faturaMapper;

    public FaturaService(FaturaRepository faturaRepository, FaturaMapper faturaMapper) {
        this.faturaRepository = faturaRepository;
        this.faturaMapper = faturaMapper;
    }

    /**
     * Save a fatura.
     *
     * @param faturaDTO the entity to save.
     * @return the persisted entity.
     */
    public FaturaDTO save(FaturaDTO faturaDTO) {
        log.debug("Request to save Fatura : {}", faturaDTO);
        Fatura fatura = faturaMapper.toEntity(faturaDTO);
        fatura = faturaRepository.save(fatura);
        return faturaMapper.toDto(fatura);
    }

    /**
     * Partially update a fatura.
     *
     * @param faturaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FaturaDTO> partialUpdate(FaturaDTO faturaDTO) {
        log.debug("Request to partially update Fatura : {}", faturaDTO);

        return faturaRepository
            .findById(faturaDTO.getId())
            .map(existingFatura -> {
                faturaMapper.partialUpdate(existingFatura, faturaDTO);

                return existingFatura;
            })
            .map(faturaRepository::save)
            .map(faturaMapper::toDto);
    }

    /**
     * Get all the faturas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FaturaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Faturas");
        return faturaRepository.findAll(pageable).map(faturaMapper::toDto);
    }

    /**
     * Get one fatura by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FaturaDTO> findOne(Long id) {
        log.debug("Request to get Fatura : {}", id);
        return faturaRepository.findById(id).map(faturaMapper::toDto);
    }

    /**
     * Delete the fatura by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Fatura : {}", id);
        faturaRepository.deleteById(id);
    }
}

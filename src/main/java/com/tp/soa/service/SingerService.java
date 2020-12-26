package com.tp.soa.service;

import com.tp.soa.service.dto.SingerDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tp.soa.domain.Singer}.
 */
public interface SingerService {

    /**
     * Save a singer.
     *
     * @param singerDTO the entity to save.
     * @return the persisted entity.
     */
    SingerDTO save(SingerDTO singerDTO);

    /**
     * Get all the singers.
     *
     * @return the list of entities.
     */
    List<SingerDTO> findAll();


    /**
     * Get the "id" singer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SingerDTO> findOne(Long id);

    /**
     * Delete the "id" singer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

package com.tp.soa.service;

import com.tp.soa.service.dto.AlbumsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tp.soa.domain.Albums}.
 */
public interface AlbumsService {

    /**
     * Save a albums.
     *
     * @param albumsDTO the entity to save.
     * @return the persisted entity.
     */
    AlbumsDTO save(AlbumsDTO albumsDTO);

    /**
     * Get all the albums.
     *
     * @return the list of entities.
     */
    List<AlbumsDTO> findAll();


    /**
     * Get the "id" albums.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AlbumsDTO> findOne(Long id);

    /**
     * Delete the "id" albums.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

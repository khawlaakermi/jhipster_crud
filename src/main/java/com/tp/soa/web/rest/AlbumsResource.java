package com.tp.soa.web.rest;

import com.tp.soa.service.AlbumsService;
import com.tp.soa.web.rest.errors.BadRequestAlertException;
import com.tp.soa.service.dto.AlbumsDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.tp.soa.domain.Albums}.
 */
@RestController
@RequestMapping("/api")
public class AlbumsResource {

    private final Logger log = LoggerFactory.getLogger(AlbumsResource.class);

    private static final String ENTITY_NAME = "albums";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlbumsService albumsService;

    public AlbumsResource(AlbumsService albumsService) {
        this.albumsService = albumsService;
    }

    /**
     * {@code POST  /albums} : Create a new albums.
     *
     * @param albumsDTO the albumsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new albumsDTO, or with status {@code 400 (Bad Request)} if the albums has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/albums")
    public ResponseEntity<AlbumsDTO> createAlbums(@RequestBody AlbumsDTO albumsDTO) throws URISyntaxException {
        log.debug("REST request to save Albums : {}", albumsDTO);
        if (albumsDTO.getId() != null) {
            throw new BadRequestAlertException("A new albums cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlbumsDTO result = albumsService.save(albumsDTO);
        return ResponseEntity.created(new URI("/api/albums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /albums} : Updates an existing albums.
     *
     * @param albumsDTO the albumsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated albumsDTO,
     * or with status {@code 400 (Bad Request)} if the albumsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the albumsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/albums")
    public ResponseEntity<AlbumsDTO> updateAlbums(@RequestBody AlbumsDTO albumsDTO) throws URISyntaxException {
        log.debug("REST request to update Albums : {}", albumsDTO);
        if (albumsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlbumsDTO result = albumsService.save(albumsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, albumsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /albums} : get all the albums.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of albums in body.
     */
    @GetMapping("/albums")
    public List<AlbumsDTO> getAllAlbums() {
        log.debug("REST request to get all Albums");
        return albumsService.findAll();
    }

    /**
     * {@code GET  /albums/:id} : get the "id" albums.
     *
     * @param id the id of the albumsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the albumsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/albums/{id}")
    public ResponseEntity<AlbumsDTO> getAlbums(@PathVariable Long id) {
        log.debug("REST request to get Albums : {}", id);
        Optional<AlbumsDTO> albumsDTO = albumsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(albumsDTO);
    }

    /**
     * {@code DELETE  /albums/:id} : delete the "id" albums.
     *
     * @param id the id of the albumsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/albums/{id}")
    public ResponseEntity<Void> deleteAlbums(@PathVariable Long id) {
        log.debug("REST request to delete Albums : {}", id);
        albumsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

package com.tp.soa.web.rest;

import com.tp.soa.service.SingerService;
import com.tp.soa.web.rest.errors.BadRequestAlertException;
import com.tp.soa.service.dto.SingerDTO;

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
 * REST controller for managing {@link com.tp.soa.domain.Singer}.
 */
@RestController
@RequestMapping("/api")
public class SingerResource {

    private final Logger log = LoggerFactory.getLogger(SingerResource.class);

    private static final String ENTITY_NAME = "singer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SingerService singerService;

    public SingerResource(SingerService singerService) {
        this.singerService = singerService;
    }

    /**
     * {@code POST  /singers} : Create a new singer.
     *
     * @param singerDTO the singerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new singerDTO, or with status {@code 400 (Bad Request)} if the singer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/singers")
    public ResponseEntity<SingerDTO> createSinger(@RequestBody SingerDTO singerDTO) throws URISyntaxException {
        log.debug("REST request to save Singer : {}", singerDTO);
        if (singerDTO.getId() != null) {
            throw new BadRequestAlertException("A new singer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SingerDTO result = singerService.save(singerDTO);
        return ResponseEntity.created(new URI("/api/singers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /singers} : Updates an existing singer.
     *
     * @param singerDTO the singerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated singerDTO,
     * or with status {@code 400 (Bad Request)} if the singerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the singerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/singers")
    public ResponseEntity<SingerDTO> updateSinger(@RequestBody SingerDTO singerDTO) throws URISyntaxException {
        log.debug("REST request to update Singer : {}", singerDTO);
        if (singerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SingerDTO result = singerService.save(singerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, singerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /singers} : get all the singers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of singers in body.
     */
    @GetMapping("/singers")
    public List<SingerDTO> getAllSingers() {
        log.debug("REST request to get all Singers");
        return singerService.findAll();
    }

    /**
     * {@code GET  /singers/:id} : get the "id" singer.
     *
     * @param id the id of the singerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the singerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/singers/{id}")
    public ResponseEntity<SingerDTO> getSinger(@PathVariable Long id) {
        log.debug("REST request to get Singer : {}", id);
        Optional<SingerDTO> singerDTO = singerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(singerDTO);
    }

    /**
     * {@code DELETE  /singers/:id} : delete the "id" singer.
     *
     * @param id the id of the singerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/singers/{id}")
    public ResponseEntity<Void> deleteSinger(@PathVariable Long id) {
        log.debug("REST request to delete Singer : {}", id);
        singerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

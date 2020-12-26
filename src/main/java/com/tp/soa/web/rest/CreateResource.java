package com.tp.soa.web.rest;

import com.tp.soa.domain.Create;
import com.tp.soa.repository.CreateRepository;
import com.tp.soa.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.tp.soa.domain.Create}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CreateResource {

    private final Logger log = LoggerFactory.getLogger(CreateResource.class);

    private static final String ENTITY_NAME = "create";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CreateRepository createRepository;

    public CreateResource(CreateRepository createRepository) {
        this.createRepository = createRepository;
    }

    /**
     * {@code POST  /creates} : Create a new create.
     *
     * @param create the create to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new create, or with status {@code 400 (Bad Request)} if the create has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/creates")
    public ResponseEntity<Create> createCreate(@RequestBody Create create) throws URISyntaxException {
        log.debug("REST request to save Create : {}", create);
        if (create.getId() != null) {
            throw new BadRequestAlertException("A new create cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Create result = createRepository.save(create);
        return ResponseEntity.created(new URI("/api/creates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /creates} : Updates an existing create.
     *
     * @param create the create to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated create,
     * or with status {@code 400 (Bad Request)} if the create is not valid,
     * or with status {@code 500 (Internal Server Error)} if the create couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/creates")
    public ResponseEntity<Create> updateCreate(@RequestBody Create create) throws URISyntaxException {
        log.debug("REST request to update Create : {}", create);
        if (create.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Create result = createRepository.save(create);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, create.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /creates} : get all the creates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of creates in body.
     */
    @GetMapping("/creates")
    public List<Create> getAllCreates() {
        log.debug("REST request to get all Creates");
        return createRepository.findAll();
    }

    /**
     * {@code GET  /creates/:id} : get the "id" create.
     *
     * @param id the id of the create to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the create, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/creates/{id}")
    public ResponseEntity<Create> getCreate(@PathVariable Long id) {
        log.debug("REST request to get Create : {}", id);
        Optional<Create> create = createRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(create);
    }

    /**
     * {@code DELETE  /creates/:id} : delete the "id" create.
     *
     * @param id the id of the create to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/creates/{id}")
    public ResponseEntity<Void> deleteCreate(@PathVariable Long id) {
        log.debug("REST request to delete Create : {}", id);
        createRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}

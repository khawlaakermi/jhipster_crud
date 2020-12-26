package com.tp.soa.web.rest;

import com.tp.soa.TpKhApp;
import com.tp.soa.domain.Create;
import com.tp.soa.repository.CreateRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CreateResource} REST controller.
 */
@SpringBootTest(classes = TpKhApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CreateResourceIT {

    @Autowired
    private CreateRepository createRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCreateMockMvc;

    private Create create;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Create createEntity(EntityManager em) {
        Create create = new Create();
        return create;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Create createUpdatedEntity(EntityManager em) {
        Create create = new Create();
        return create;
    }

    @BeforeEach
    public void initTest() {
        create = createEntity(em);
    }

    @Test
    @Transactional
    public void createCreate() throws Exception {
        int databaseSizeBeforeCreate = createRepository.findAll().size();
        // Create the Create
        restCreateMockMvc.perform(post("/api/creates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(create)))
            .andExpect(status().isCreated());

        // Validate the Create in the database
        List<Create> createList = createRepository.findAll();
        assertThat(createList).hasSize(databaseSizeBeforeCreate + 1);
        Create testCreate = createList.get(createList.size() - 1);
    }

    @Test
    @Transactional
    public void createCreateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = createRepository.findAll().size();

        // Create the Create with an existing ID
        create.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreateMockMvc.perform(post("/api/creates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(create)))
            .andExpect(status().isBadRequest());

        // Validate the Create in the database
        List<Create> createList = createRepository.findAll();
        assertThat(createList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCreates() throws Exception {
        // Initialize the database
        createRepository.saveAndFlush(create);

        // Get all the createList
        restCreateMockMvc.perform(get("/api/creates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(create.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCreate() throws Exception {
        // Initialize the database
        createRepository.saveAndFlush(create);

        // Get the create
        restCreateMockMvc.perform(get("/api/creates/{id}", create.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(create.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCreate() throws Exception {
        // Get the create
        restCreateMockMvc.perform(get("/api/creates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCreate() throws Exception {
        // Initialize the database
        createRepository.saveAndFlush(create);

        int databaseSizeBeforeUpdate = createRepository.findAll().size();

        // Update the create
        Create updatedCreate = createRepository.findById(create.getId()).get();
        // Disconnect from session so that the updates on updatedCreate are not directly saved in db
        em.detach(updatedCreate);

        restCreateMockMvc.perform(put("/api/creates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCreate)))
            .andExpect(status().isOk());

        // Validate the Create in the database
        List<Create> createList = createRepository.findAll();
        assertThat(createList).hasSize(databaseSizeBeforeUpdate);
        Create testCreate = createList.get(createList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCreate() throws Exception {
        int databaseSizeBeforeUpdate = createRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreateMockMvc.perform(put("/api/creates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(create)))
            .andExpect(status().isBadRequest());

        // Validate the Create in the database
        List<Create> createList = createRepository.findAll();
        assertThat(createList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCreate() throws Exception {
        // Initialize the database
        createRepository.saveAndFlush(create);

        int databaseSizeBeforeDelete = createRepository.findAll().size();

        // Delete the create
        restCreateMockMvc.perform(delete("/api/creates/{id}", create.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Create> createList = createRepository.findAll();
        assertThat(createList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.tp.soa.web.rest;

import com.tp.soa.TpKhApp;
import com.tp.soa.domain.Singer;
import com.tp.soa.repository.SingerRepository;
import com.tp.soa.service.SingerService;
import com.tp.soa.service.dto.SingerDTO;
import com.tp.soa.service.mapper.SingerMapper;

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
 * Integration tests for the {@link SingerResource} REST controller.
 */
@SpringBootTest(classes = TpKhApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SingerResourceIT {

    private static final String DEFAULT_FNAME = "AAAAAAAAAA";
    private static final String UPDATED_FNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LNAME = "AAAAAAAAAA";
    private static final String UPDATED_LNAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADRESS = "BBBBBBBBBB";

    @Autowired
    private SingerRepository singerRepository;

    @Autowired
    private SingerMapper singerMapper;

    @Autowired
    private SingerService singerService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSingerMockMvc;

    private Singer singer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Singer createEntity(EntityManager em) {
        Singer singer = new Singer()
            .fname(DEFAULT_FNAME)
            .lname(DEFAULT_LNAME)
            .adress(DEFAULT_ADRESS);
        return singer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Singer createUpdatedEntity(EntityManager em) {
        Singer singer = new Singer()
            .fname(UPDATED_FNAME)
            .lname(UPDATED_LNAME)
            .adress(UPDATED_ADRESS);
        return singer;
    }

    @BeforeEach
    public void initTest() {
        singer = createEntity(em);
    }

    @Test
    @Transactional
    public void createSinger() throws Exception {
        int databaseSizeBeforeCreate = singerRepository.findAll().size();
        // Create the Singer
        SingerDTO singerDTO = singerMapper.toDto(singer);
        restSingerMockMvc.perform(post("/api/singers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(singerDTO)))
            .andExpect(status().isCreated());

        // Validate the Singer in the database
        List<Singer> singerList = singerRepository.findAll();
        assertThat(singerList).hasSize(databaseSizeBeforeCreate + 1);
        Singer testSinger = singerList.get(singerList.size() - 1);
        assertThat(testSinger.getFname()).isEqualTo(DEFAULT_FNAME);
        assertThat(testSinger.getLname()).isEqualTo(DEFAULT_LNAME);
        assertThat(testSinger.getAdress()).isEqualTo(DEFAULT_ADRESS);
    }

    @Test
    @Transactional
    public void createSingerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = singerRepository.findAll().size();

        // Create the Singer with an existing ID
        singer.setId(1L);
        SingerDTO singerDTO = singerMapper.toDto(singer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSingerMockMvc.perform(post("/api/singers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(singerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Singer in the database
        List<Singer> singerList = singerRepository.findAll();
        assertThat(singerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSingers() throws Exception {
        // Initialize the database
        singerRepository.saveAndFlush(singer);

        // Get all the singerList
        restSingerMockMvc.perform(get("/api/singers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(singer.getId().intValue())))
            .andExpect(jsonPath("$.[*].fname").value(hasItem(DEFAULT_FNAME)))
            .andExpect(jsonPath("$.[*].lname").value(hasItem(DEFAULT_LNAME)))
            .andExpect(jsonPath("$.[*].adress").value(hasItem(DEFAULT_ADRESS)));
    }
    
    @Test
    @Transactional
    public void getSinger() throws Exception {
        // Initialize the database
        singerRepository.saveAndFlush(singer);

        // Get the singer
        restSingerMockMvc.perform(get("/api/singers/{id}", singer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(singer.getId().intValue()))
            .andExpect(jsonPath("$.fname").value(DEFAULT_FNAME))
            .andExpect(jsonPath("$.lname").value(DEFAULT_LNAME))
            .andExpect(jsonPath("$.adress").value(DEFAULT_ADRESS));
    }
    @Test
    @Transactional
    public void getNonExistingSinger() throws Exception {
        // Get the singer
        restSingerMockMvc.perform(get("/api/singers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSinger() throws Exception {
        // Initialize the database
        singerRepository.saveAndFlush(singer);

        int databaseSizeBeforeUpdate = singerRepository.findAll().size();

        // Update the singer
        Singer updatedSinger = singerRepository.findById(singer.getId()).get();
        // Disconnect from session so that the updates on updatedSinger are not directly saved in db
        em.detach(updatedSinger);
        updatedSinger
            .fname(UPDATED_FNAME)
            .lname(UPDATED_LNAME)
            .adress(UPDATED_ADRESS);
        SingerDTO singerDTO = singerMapper.toDto(updatedSinger);

        restSingerMockMvc.perform(put("/api/singers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(singerDTO)))
            .andExpect(status().isOk());

        // Validate the Singer in the database
        List<Singer> singerList = singerRepository.findAll();
        assertThat(singerList).hasSize(databaseSizeBeforeUpdate);
        Singer testSinger = singerList.get(singerList.size() - 1);
        assertThat(testSinger.getFname()).isEqualTo(UPDATED_FNAME);
        assertThat(testSinger.getLname()).isEqualTo(UPDATED_LNAME);
        assertThat(testSinger.getAdress()).isEqualTo(UPDATED_ADRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingSinger() throws Exception {
        int databaseSizeBeforeUpdate = singerRepository.findAll().size();

        // Create the Singer
        SingerDTO singerDTO = singerMapper.toDto(singer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSingerMockMvc.perform(put("/api/singers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(singerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Singer in the database
        List<Singer> singerList = singerRepository.findAll();
        assertThat(singerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSinger() throws Exception {
        // Initialize the database
        singerRepository.saveAndFlush(singer);

        int databaseSizeBeforeDelete = singerRepository.findAll().size();

        // Delete the singer
        restSingerMockMvc.perform(delete("/api/singers/{id}", singer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Singer> singerList = singerRepository.findAll();
        assertThat(singerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

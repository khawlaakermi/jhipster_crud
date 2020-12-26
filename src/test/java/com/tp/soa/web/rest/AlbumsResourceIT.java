package com.tp.soa.web.rest;

import com.tp.soa.TpKhApp;
import com.tp.soa.domain.Albums;
import com.tp.soa.repository.AlbumsRepository;
import com.tp.soa.service.AlbumsService;
import com.tp.soa.service.dto.AlbumsDTO;
import com.tp.soa.service.mapper.AlbumsMapper;

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
 * Integration tests for the {@link AlbumsResource} REST controller.
 */
@SpringBootTest(classes = TpKhApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlbumsResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private AlbumsRepository albumsRepository;

    @Autowired
    private AlbumsMapper albumsMapper;

    @Autowired
    private AlbumsService albumsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlbumsMockMvc;

    private Albums albums;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Albums createEntity(EntityManager em) {
        Albums albums = new Albums()
            .title(DEFAULT_TITLE)
            .type(DEFAULT_TYPE);
        return albums;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Albums createUpdatedEntity(EntityManager em) {
        Albums albums = new Albums()
            .title(UPDATED_TITLE)
            .type(UPDATED_TYPE);
        return albums;
    }

    @BeforeEach
    public void initTest() {
        albums = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlbums() throws Exception {
        int databaseSizeBeforeCreate = albumsRepository.findAll().size();
        // Create the Albums
        AlbumsDTO albumsDTO = albumsMapper.toDto(albums);
        restAlbumsMockMvc.perform(post("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumsDTO)))
            .andExpect(status().isCreated());

        // Validate the Albums in the database
        List<Albums> albumsList = albumsRepository.findAll();
        assertThat(albumsList).hasSize(databaseSizeBeforeCreate + 1);
        Albums testAlbums = albumsList.get(albumsList.size() - 1);
        assertThat(testAlbums.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAlbums.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createAlbumsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = albumsRepository.findAll().size();

        // Create the Albums with an existing ID
        albums.setId(1L);
        AlbumsDTO albumsDTO = albumsMapper.toDto(albums);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlbumsMockMvc.perform(post("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Albums in the database
        List<Albums> albumsList = albumsRepository.findAll();
        assertThat(albumsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAlbums() throws Exception {
        // Initialize the database
        albumsRepository.saveAndFlush(albums);

        // Get all the albumsList
        restAlbumsMockMvc.perform(get("/api/albums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(albums.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
    
    @Test
    @Transactional
    public void getAlbums() throws Exception {
        // Initialize the database
        albumsRepository.saveAndFlush(albums);

        // Get the albums
        restAlbumsMockMvc.perform(get("/api/albums/{id}", albums.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(albums.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }
    @Test
    @Transactional
    public void getNonExistingAlbums() throws Exception {
        // Get the albums
        restAlbumsMockMvc.perform(get("/api/albums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlbums() throws Exception {
        // Initialize the database
        albumsRepository.saveAndFlush(albums);

        int databaseSizeBeforeUpdate = albumsRepository.findAll().size();

        // Update the albums
        Albums updatedAlbums = albumsRepository.findById(albums.getId()).get();
        // Disconnect from session so that the updates on updatedAlbums are not directly saved in db
        em.detach(updatedAlbums);
        updatedAlbums
            .title(UPDATED_TITLE)
            .type(UPDATED_TYPE);
        AlbumsDTO albumsDTO = albumsMapper.toDto(updatedAlbums);

        restAlbumsMockMvc.perform(put("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumsDTO)))
            .andExpect(status().isOk());

        // Validate the Albums in the database
        List<Albums> albumsList = albumsRepository.findAll();
        assertThat(albumsList).hasSize(databaseSizeBeforeUpdate);
        Albums testAlbums = albumsList.get(albumsList.size() - 1);
        assertThat(testAlbums.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAlbums.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAlbums() throws Exception {
        int databaseSizeBeforeUpdate = albumsRepository.findAll().size();

        // Create the Albums
        AlbumsDTO albumsDTO = albumsMapper.toDto(albums);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlbumsMockMvc.perform(put("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Albums in the database
        List<Albums> albumsList = albumsRepository.findAll();
        assertThat(albumsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlbums() throws Exception {
        // Initialize the database
        albumsRepository.saveAndFlush(albums);

        int databaseSizeBeforeDelete = albumsRepository.findAll().size();

        // Delete the albums
        restAlbumsMockMvc.perform(delete("/api/albums/{id}", albums.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Albums> albumsList = albumsRepository.findAll();
        assertThat(albumsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

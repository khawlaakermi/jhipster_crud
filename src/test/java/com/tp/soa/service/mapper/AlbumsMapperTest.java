package com.tp.soa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AlbumsMapperTest {

    private AlbumsMapper albumsMapper;

    @BeforeEach
    public void setUp() {
        albumsMapper = new AlbumsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(albumsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(albumsMapper.fromId(null)).isNull();
    }
}

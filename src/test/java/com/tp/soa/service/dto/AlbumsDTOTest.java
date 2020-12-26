package com.tp.soa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tp.soa.web.rest.TestUtil;

public class AlbumsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlbumsDTO.class);
        AlbumsDTO albumsDTO1 = new AlbumsDTO();
        albumsDTO1.setId(1L);
        AlbumsDTO albumsDTO2 = new AlbumsDTO();
        assertThat(albumsDTO1).isNotEqualTo(albumsDTO2);
        albumsDTO2.setId(albumsDTO1.getId());
        assertThat(albumsDTO1).isEqualTo(albumsDTO2);
        albumsDTO2.setId(2L);
        assertThat(albumsDTO1).isNotEqualTo(albumsDTO2);
        albumsDTO1.setId(null);
        assertThat(albumsDTO1).isNotEqualTo(albumsDTO2);
    }
}

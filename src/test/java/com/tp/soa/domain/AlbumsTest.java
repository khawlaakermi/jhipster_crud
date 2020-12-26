package com.tp.soa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tp.soa.web.rest.TestUtil;

public class AlbumsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Albums.class);
        Albums albums1 = new Albums();
        albums1.setId(1L);
        Albums albums2 = new Albums();
        albums2.setId(albums1.getId());
        assertThat(albums1).isEqualTo(albums2);
        albums2.setId(2L);
        assertThat(albums1).isNotEqualTo(albums2);
        albums1.setId(null);
        assertThat(albums1).isNotEqualTo(albums2);
    }
}

package com.tp.soa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tp.soa.web.rest.TestUtil;

public class SingerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Singer.class);
        Singer singer1 = new Singer();
        singer1.setId(1L);
        Singer singer2 = new Singer();
        singer2.setId(singer1.getId());
        assertThat(singer1).isEqualTo(singer2);
        singer2.setId(2L);
        assertThat(singer1).isNotEqualTo(singer2);
        singer1.setId(null);
        assertThat(singer1).isNotEqualTo(singer2);
    }
}

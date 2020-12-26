package com.tp.soa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tp.soa.web.rest.TestUtil;

public class SingerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SingerDTO.class);
        SingerDTO singerDTO1 = new SingerDTO();
        singerDTO1.setId(1L);
        SingerDTO singerDTO2 = new SingerDTO();
        assertThat(singerDTO1).isNotEqualTo(singerDTO2);
        singerDTO2.setId(singerDTO1.getId());
        assertThat(singerDTO1).isEqualTo(singerDTO2);
        singerDTO2.setId(2L);
        assertThat(singerDTO1).isNotEqualTo(singerDTO2);
        singerDTO1.setId(null);
        assertThat(singerDTO1).isNotEqualTo(singerDTO2);
    }
}

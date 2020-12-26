package com.tp.soa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tp.soa.web.rest.TestUtil;

public class CreateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Create.class);
        Create create1 = new Create();
        create1.setId(1L);
        Create create2 = new Create();
        create2.setId(create1.getId());
        assertThat(create1).isEqualTo(create2);
        create2.setId(2L);
        assertThat(create1).isNotEqualTo(create2);
        create1.setId(null);
        assertThat(create1).isNotEqualTo(create2);
    }
}

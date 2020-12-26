package com.tp.soa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SingerMapperTest {

    private SingerMapper singerMapper;

    @BeforeEach
    public void setUp() {
        singerMapper = new SingerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(singerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(singerMapper.fromId(null)).isNull();
    }
}

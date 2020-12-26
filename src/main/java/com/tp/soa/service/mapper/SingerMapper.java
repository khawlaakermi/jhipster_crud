package com.tp.soa.service.mapper;


import com.tp.soa.domain.*;
import com.tp.soa.service.dto.SingerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Singer} and its DTO {@link SingerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SingerMapper extends EntityMapper<SingerDTO, Singer> {


    @Mapping(target = "avoir_albums", ignore = true)
    @Mapping(target = "removeAvoir_album", ignore = true)
    Singer toEntity(SingerDTO singerDTO);

    default Singer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Singer singer = new Singer();
        singer.setId(id);
        return singer;
    }
}

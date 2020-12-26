package com.tp.soa.service.mapper;


import com.tp.soa.domain.*;
import com.tp.soa.service.dto.AlbumsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Albums} and its DTO {@link AlbumsDTO}.
 */
@Mapper(componentModel = "spring", uses = {SingerMapper.class})
public interface AlbumsMapper extends EntityMapper<AlbumsDTO, Albums> {

    @Mapping(source = "singerr.id", target = "singerrId")
    @Mapping(source = "singerr.fname", target = "singerrFname")
    AlbumsDTO toDto(Albums albums);

    @Mapping(source = "singerrId", target = "singerr")
    Albums toEntity(AlbumsDTO albumsDTO);

    default Albums fromId(Long id) {
        if (id == null) {
            return null;
        }
        Albums albums = new Albums();
        albums.setId(id);
        return albums;
    }
}

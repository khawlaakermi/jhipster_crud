package com.tp.soa.service.impl;

import com.tp.soa.service.AlbumsService;
import com.tp.soa.domain.Albums;
import com.tp.soa.repository.AlbumsRepository;
import com.tp.soa.service.dto.AlbumsDTO;
import com.tp.soa.service.mapper.AlbumsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Albums}.
 */
@Service
@Transactional
public class AlbumsServiceImpl implements AlbumsService {

    private final Logger log = LoggerFactory.getLogger(AlbumsServiceImpl.class);

    private final AlbumsRepository albumsRepository;

    private final AlbumsMapper albumsMapper;

    public AlbumsServiceImpl(AlbumsRepository albumsRepository, AlbumsMapper albumsMapper) {
        this.albumsRepository = albumsRepository;
        this.albumsMapper = albumsMapper;
    }

    @Override
    public AlbumsDTO save(AlbumsDTO albumsDTO) {
        log.debug("Request to save Albums : {}", albumsDTO);
        Albums albums = albumsMapper.toEntity(albumsDTO);
        albums = albumsRepository.save(albums);
        return albumsMapper.toDto(albums);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlbumsDTO> findAll() {
        log.debug("Request to get all Albums");
        return albumsRepository.findAll().stream()
            .map(albumsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AlbumsDTO> findOne(Long id) {
        log.debug("Request to get Albums : {}", id);
        return albumsRepository.findById(id)
            .map(albumsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Albums : {}", id);
        albumsRepository.deleteById(id);
    }
}

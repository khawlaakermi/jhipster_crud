package com.tp.soa.service.impl;

import com.tp.soa.service.SingerService;
import com.tp.soa.domain.Singer;
import com.tp.soa.repository.SingerRepository;
import com.tp.soa.service.dto.SingerDTO;
import com.tp.soa.service.mapper.SingerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Singer}.
 */
@Service
@Transactional
public class SingerServiceImpl implements SingerService {

    private final Logger log = LoggerFactory.getLogger(SingerServiceImpl.class);

    private final SingerRepository singerRepository;

    private final SingerMapper singerMapper;

    public SingerServiceImpl(SingerRepository singerRepository, SingerMapper singerMapper) {
        this.singerRepository = singerRepository;
        this.singerMapper = singerMapper;
    }

    @Override
    public SingerDTO save(SingerDTO singerDTO) {
        log.debug("Request to save Singer : {}", singerDTO);
        Singer singer = singerMapper.toEntity(singerDTO);
        singer = singerRepository.save(singer);
        return singerMapper.toDto(singer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SingerDTO> findAll() {
        log.debug("Request to get all Singers");
        return singerRepository.findAll().stream()
            .map(singerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SingerDTO> findOne(Long id) {
        log.debug("Request to get Singer : {}", id);
        return singerRepository.findById(id)
            .map(singerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Singer : {}", id);
        singerRepository.deleteById(id);
    }
}

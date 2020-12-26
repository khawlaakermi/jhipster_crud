package com.tp.soa.repository;

import com.tp.soa.domain.Singer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Singer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SingerRepository extends JpaRepository<Singer, Long> {
}

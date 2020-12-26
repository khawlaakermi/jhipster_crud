package com.tp.soa.repository;

import com.tp.soa.domain.Albums;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Albums entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlbumsRepository extends JpaRepository<Albums, Long> {
}

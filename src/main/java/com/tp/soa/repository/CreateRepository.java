package com.tp.soa.repository;

import com.tp.soa.domain.Create;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Create entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreateRepository extends JpaRepository<Create, Long> {
}

package com.fxt.repository;

import com.fxt.domain.Testing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Testing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TestingRepository extends JpaRepository<Testing, Long> {

}

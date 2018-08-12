package com.fxt.repository;

import com.fxt.domain.FixedAssets;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FixedAssets entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FixedAssetsRepository extends JpaRepository<FixedAssets, Long> {

}

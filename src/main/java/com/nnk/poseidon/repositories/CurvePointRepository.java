package com.nnk.poseidon.repositories;

import com.nnk.poseidon.domain.CurvePointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurvePointRepository
        extends JpaRepository<CurvePointEntity, Integer> {
}

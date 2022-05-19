package com.nnk.poseidon.repositories;

import com.nnk.poseidon.domain.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Integer> {
}

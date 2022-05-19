package com.nnk.poseidon.repositories;

import com.nnk.poseidon.domain.BidListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidListRepository extends JpaRepository<BidListEntity, Integer> {
}

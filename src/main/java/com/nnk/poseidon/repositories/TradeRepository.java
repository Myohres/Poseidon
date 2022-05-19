package com.nnk.poseidon.repositories;

import com.nnk.poseidon.domain.TradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<TradeEntity, Integer> {
}

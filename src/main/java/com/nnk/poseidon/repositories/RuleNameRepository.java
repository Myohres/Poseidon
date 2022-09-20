package com.nnk.poseidon.repositories;

import com.nnk.poseidon.domain.RuleNameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleNameRepository
        extends JpaRepository<RuleNameEntity, Integer> {
}

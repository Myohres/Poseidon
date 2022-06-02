package com.nnk.poseidon.service;

import com.nnk.poseidon.domain.RuleNameEntity;
import com.nnk.poseidon.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RuleNameService {

    /**
     * RuleName repository layer.
     */
    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**
     * Find all ruleName.
     * @return List of all ruleName
     */
    public List<RuleNameEntity> findAll() {
        return ruleNameRepository.findAll();
    }

    /**
     * Find curePoint by id.
     * @param id Curve id
     * @return ruleName
     */
    public RuleNameEntity findById(final Integer id) {
        return ruleNameRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(
                "No ruleName found with id : " + id));
    }

    /**
     * Save ruleName.
     * @param ruleName ruleNameEntity
     * @return ruleName saved
     */
    public RuleNameEntity add(final RuleNameEntity ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    /**
     * Update ruleName Info.
     * @param newRuleName new ruleName infos
     * @return ruleName updated
     */
    public RuleNameEntity update(final RuleNameEntity newRuleName) {
        RuleNameEntity oldRuleName = findById(newRuleName.getId());
        oldRuleName.setName(newRuleName.getName());
        oldRuleName.setDescription(newRuleName.getDescription());
        oldRuleName.setJson(newRuleName.getJson());
        oldRuleName.setTemplate(newRuleName.getTemplate());
        oldRuleName.setSqlStr(newRuleName.getSqlStr());
        oldRuleName.setSqlPart(newRuleName.getSqlPart());
        return ruleNameRepository.save(oldRuleName);
    }

    /**
     * Delete ruleName by id.
     * @param id curveId
     */
    public void delete(final Integer id) {
        RuleNameEntity ruleNameEntity = findById(id);
        ruleNameRepository.delete(ruleNameEntity);
    }
}


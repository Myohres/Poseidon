package com.nnk.poseidon.service;

import com.nnk.poseidon.domain.CurvePointEntity;
import com.nnk.poseidon.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * Find all CurvePoint.
     * @return List of all CurvePoint
     */
    public List<CurvePointEntity> findAll() {
        return curvePointRepository.findAll();
    }

    /**
     * Find curePoint by id.
     * @param id Curve id
     * @return CurvePoint
     */
    public CurvePointEntity findById(final Integer id) {
        return curvePointRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException(
                        "No CurvePoint found with id : " + id));
    }

    /**
     * Save curvePoint.
     * @param curvePoint CurvePointEntity
     * @return curvePoint saved
     */
    public CurvePointEntity add(final CurvePointEntity curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    /**
     * Update curvePointInfo.
     * @param newCurvePoint new curvePoint infos
     * @return curvePoint updated
     */
    public CurvePointEntity update(final CurvePointEntity newCurvePoint) {
        CurvePointEntity oldCurvePoint = findById(newCurvePoint.getId());
        oldCurvePoint.setCurveId(newCurvePoint.getCurveId());
        oldCurvePoint.setTerm(newCurvePoint.getTerm());
        oldCurvePoint.setValue(newCurvePoint.getValue());
        return curvePointRepository.save(oldCurvePoint);
    }

    /**
     * Delete curvePoint by id.
     * @param id curveId
     */
    public void delete(final Integer id) {
        CurvePointEntity curvePointEntity = findById(id);
        curvePointRepository.delete(curvePointEntity);
    }
}

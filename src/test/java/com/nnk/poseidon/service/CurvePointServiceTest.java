package com.nnk.poseidon.service;

import com.nnk.poseidon.domain.CurvePointEntity;
import com.nnk.poseidon.repositories.CurvePointRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CurvePointServiceTest {

    CurvePointEntity curvePoint;

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private  CurvePointService curvePointService;

    @BeforeEach
    void setUp() {
        curvePoint = new CurvePointEntity();
        curvePoint.setId(1);
        curvePoint.setCurveId(1);
        curvePoint.setAsOfDate(new Timestamp(1));
        curvePoint.setTerm(1.0);
        curvePoint.setValue(1.0);
        curvePoint.setCreationDate(new Timestamp(1));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        when(curvePointRepository.findAll()).thenReturn(new ArrayList<CurvePointEntity>());
        curvePointService.findAll();
        verify(curvePointRepository,times(1)).findAll();
    }

    @Test
    void findById() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
        CurvePointEntity curvePointResult = curvePointService.findById(1);
        assertEquals(1,curvePointResult.getCurveId());
    }

    @Test
    void findByIdNotFound() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> curvePointService.findById(1));
    }

    @Test
    void add() {
        when(curvePointRepository.save(any())).thenReturn(curvePoint);
        curvePointService.add(curvePoint);
        verify(curvePointRepository,times(1)).save(curvePoint);
    }

    @Test
    void update() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
        CurvePointEntity curvePoint2 = new CurvePointEntity();
        curvePoint2.setCurveId(1);
        curvePoint2.setTerm(2.0);
        curvePoint2.setValue(2.0);
        curvePointService.update(curvePoint2);
        assertEquals(1,curvePoint.getId());
        assertEquals(1,curvePoint.getCurveId());
        assertEquals(2.0,curvePoint.getTerm());
        assertEquals(2.0,curvePoint.getValue());
        assertEquals(1,curvePoint.getAsOfDate().getTime());
        assertEquals(1,curvePoint.getCreationDate().getTime());
    }

    @Test
    void delete() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
        curvePointService.delete(1);
        verify(curvePointRepository,times(1)).delete(curvePoint);
    }
}
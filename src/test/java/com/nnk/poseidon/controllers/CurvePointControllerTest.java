package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.BidListEntity;
import com.nnk.poseidon.domain.CurvePointEntity;
import com.nnk.poseidon.service.BidListService;
import com.nnk.poseidon.service.CurvePointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CurvePointController.class)
@AutoConfigureMockMvc(addFilters = false)
class CurvePointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointService curvePointService;

    CurvePointEntity curvePointEntity;

    @BeforeEach
    void setUp() {
        curvePointEntity = new CurvePointEntity();
        curvePointEntity.setCurveId(1);
        curvePointEntity.setTerm(1.0);
        curvePointEntity.setTerm(1.0);
    }

    @Test
    void home() throws Exception {
        when(curvePointService.findAll()).thenReturn(new ArrayList<CurvePointEntity>());
        mockMvc.perform(get("/curvePoint/list")).andExpect(status().isOk());
    }

    @Test
    void addCurvePointForm() throws Exception {
        when(curvePointService.add(new CurvePointEntity())).thenReturn(new CurvePointEntity());
        mockMvc.perform(get("/curvePoint/add")).andExpect(status().isOk());
    }

    @Test
    void validate() {
    }

    @Test
    void validateWithError() {
    }

    @Test
    void showUpdateForm() throws Exception {
        when(curvePointService.findById(1)).thenReturn(curvePointEntity);
        mockMvc.perform(get("/curvePoint/update/1")).andExpect(status().isOk());
    }

    @Test
    void updateCurvePoint() {
    }

    @Test
    void updateBidWithNoError() throws Exception {
    }

    @Test
    void deleteCurvePoint() {
    }
}
package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.CurvePointEntity;
import com.nnk.poseidon.domain.TradeEntity;
import com.nnk.poseidon.service.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TradeController.class)
@AutoConfigureMockMvc(addFilters = false)
class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    TradeEntity tradeEntity;

    @BeforeEach
    void setUp() {
        tradeEntity = new TradeEntity();
        tradeEntity.setTradeId(1);
        tradeEntity.setAccount("account");
        tradeEntity.setType("type");
        tradeEntity.setBuyQuantity(1.0);
        tradeEntity.setSellQuantity(1.0);
        tradeEntity.setBuyPrice(1.0);
        tradeEntity.setSellPrice(1.0);
        tradeEntity.setBenchmark("benchmark");
        tradeEntity.setSecurity("security");
        tradeEntity.setStatus("status");
        tradeEntity.setTrader("trader");
        tradeEntity.setBook("book");
        tradeEntity.setCreationName("CreationName");
        tradeEntity.setCreationDate(new Timestamp(0));
        tradeEntity.setRevisionName("revisionName");
        tradeEntity.setRevisionDate(new Timestamp(0));
        tradeEntity.setDealName("dealName");
        tradeEntity.setDealType("dealType");
        tradeEntity.setSourceListId("sourceListId");
        tradeEntity.setSide("Side");
    }

    @Test
    void home() throws Exception {
        when(tradeService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/trade/list")).andExpect(status().isOk());
    }

    @Test
    void addTradeForm() throws Exception {
        when(tradeService.add(new TradeEntity())).thenReturn(new TradeEntity());
        mockMvc.perform(get("/trade/add")).andExpect(status().isOk());
    }

    @Test
    void validate() {
    }

    @Test
    void validateWithError() {
    }

    @Test
    void showUpdateForm() throws Exception {
        when(tradeService.findById(1)).thenReturn(tradeEntity);
        mockMvc.perform(get("/trade/update/1")).andExpect(status().isOk());
    }

    @Test
    void updateCurvePoint() {
    }

    @Test
    void updateBidWithNoError() throws Exception {
    }

    @Test
    void deleteTrade() {
    }
}

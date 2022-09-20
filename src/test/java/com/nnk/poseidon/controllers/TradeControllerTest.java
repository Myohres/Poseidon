package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.TradeEntity;
import com.nnk.poseidon.service.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class TradeControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    TradeEntity trade;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        trade = new TradeEntity();
        trade.setTradeId(1);
        trade.setAccount("account");
        trade.setType("type");
        trade.setBuyQuantity(1.0);
        trade.setSellQuantity(1.0);
        trade.setBuyPrice(1.0);
        trade.setSellPrice(1.0);
        trade.setBenchmark("benchmark");
        trade.setSecurity("security");
        trade.setStatus("status");
        trade.setTrader("trader");
        trade.setBook("book");
        trade.setCreationName("CreationName");
        trade.setCreationDate(new Timestamp(0));
        trade.setRevisionName("revisionName");
        trade.setRevisionDate(new Timestamp(0));
        trade.setDealName("dealName");
        trade.setDealType("dealType");
        trade.setSourceListId("sourceListId");
        trade.setSide("Side");
    }

    @Test
    void home() throws Exception {
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("list"));
    }

    @Test
    void addTradeForm() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    void validate() throws Exception {
        mockMvc.perform(post("/trade/validate")
                .param("account","aa")
                .param("type", "tt")
                .param("buyQuantity", "1d"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/trade/list"));
    }

    @Test
    void validateWithError() throws Exception {
        mockMvc.perform(post("/trade/validate")
                .param("account","")
                .param("type", "")
                .param("buyQuantity", " 1"))
                .andExpect(view().name("trade/add"));
    }

    @Test
    void showUpdateForm() throws Exception {
        when(tradeService.findById(1)).thenReturn(trade);
        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tradeEntity"))
                .andExpect(view().name("trade/update"));
    }

    @Test
    void showUpdateFormWithNoTradeFound() throws Exception {
        doThrow(new NoSuchElementException()).when(tradeService).findById(any());
        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/trade/list"));
    }

    @Test
    void updateCurvePoint() throws Exception {
        when((tradeService.add(trade))).thenReturn(trade);
        mockMvc.perform(post("/trade/update/1")
                .param("account","aa")
                .param("type", "tt")
                .param("buyQuantity", "1d"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/trade/list"));
    }

    @Test
    void updateBidWithError() throws Exception {
        mockMvc.perform(post("/trade/update/1")
                .param("account"," ")
                .param("type", "tt")
                .param("buyQuantity", ""))
                .andExpect(view().name("trade/update"));
    }

    @Test
    void deleteTrade() throws Exception {
        when(tradeService.findById(any())).thenReturn(trade);
        when(tradeService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/trade/delete/13"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/trade/list"));
    }

    @Test
    void getAllTrade() throws Exception {
        when(tradeService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/trade"))
                .andExpect(status().isOk());
    }


    @Test
    void getTradeById() throws Exception {
        when(tradeService.findById(any())).thenReturn(trade);
        mockMvc.perform(get("/trade/tradeId/1"))
                .andExpect(status().isOk());

    }

    @Test
    void getTradeByIdNotFound() throws Exception {
        when(tradeService.findById(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/trade/tradeId/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addTrade() throws Exception {
        mockMvc.perform(post("/trade/add")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void addTradeNotFound() throws Exception {
        when(tradeService.add(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/trade/add")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateTrade() throws Exception {
        when(tradeService.update(any())).thenReturn(trade);
        mockMvc.perform(put("/trade")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateTradeNotFound() throws Exception {
        when(tradeService.update(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(put("/trade")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteTrade() throws Exception {
        when(tradeService.findById(any())).thenReturn(trade);
       mockMvc.perform(delete("/trade/tradeId/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteTradeNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(tradeService).delete(1);
        mockMvc.perform(delete("/trade/tradeId/1"))
                .andExpect(status().isNotFound());
    }

}

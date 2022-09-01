package com.nnk.poseidon.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidon.domain.BidListEntity;
import com.nnk.poseidon.service.BidListService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class BidListControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;

    BidListEntity bidListEntity;

    @BeforeEach
    void setUp() {
        bidListEntity = new BidListEntity();
        bidListEntity.setBidListId(1);
        bidListEntity.setAccount("account");
        bidListEntity.setType("Type");
        bidListEntity.setBidQuantity(1.0);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void home() throws Exception {
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("List"));
    }

    @Test
    void addBidForm() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    void validateWithNoError() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                .param("account","aa")
                .param("type", "tt")
                .param("bidQuantity", "1d"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/bidList/list"));
    }

    @Test
    void validateWithError() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                .param("account","")
                .param("type", "")
                .param("bidQuantity", ""))
                .andExpect(view().name("bidList/add"));
    }

    @Test
    void showUpdateForm() throws Exception {
        when(bidListService.findById(1)).thenReturn(bidListEntity);
        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bidList"))
                .andExpect(view().name("bidList/update"));
    }

    @Test
    void showUpdateFormWithNoBidFound() throws Exception {
        doThrow(new NoSuchElementException()).when(bidListService).findById(any());
        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/bidList/list"));
    }

    @Test
    void updateBidWithNoError() throws Exception {
        when((bidListService.add(bidListEntity))).thenReturn(bidListEntity);
        mockMvc.perform(post("/bidList/update/1")
                .param("account","aa")
                .param("type", "tt")
                .param("bidQuantity", "1d"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/bidList/list"));
    }

    @Test
    void updateBidWithError() throws Exception {
        mockMvc.perform(post("/bidList/update/1")
                .param("account"," ")
                .param("type", "tt")
                .param("bidQuantity", "1d"))
                .andExpect(view().name("bidList/update"));
    }

    @Test
    void deleteBid() throws Exception {
        when(bidListService.findById(any())).thenReturn(bidListEntity);
        when(bidListService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/bidList/delete/13"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/bidList/list"));
    }


    @Test
    void getAllBidList() throws Exception {
        when(bidListService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/bidList"))
                .andExpect(status().isOk());
    }


    @Test
    void getBidListById() throws Exception {
        when(bidListService.findById(any())).thenReturn(bidListEntity);
        mockMvc.perform(get("/bidList/bidListId/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getBidListByIdNotFound() throws Exception {
        when(bidListService.findById(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/bidList/bidListId/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addBidList() throws Exception {
        mockMvc.perform(post("/bidList/add")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void addBidListError() throws Exception {
        when(bidListService.add(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/bidList/add")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateBidList() throws Exception {
        when(bidListService.update(any())).thenReturn(new BidListEntity());
        mockMvc.perform(put("/bidList")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateBidListNotFound() throws Exception {
        when(bidListService.update(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(put("/bidList")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteBidList() throws Exception {
        when(bidListService.findById(any())).thenReturn(bidListEntity);
        mockMvc.perform(delete("/bidList/bidListId/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBidListByIdTestBidListNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(bidListService).delete(1);
        mockMvc.perform(delete("/bidList/bidListId/1"))
                .andExpect(status().isNotFound());
    }
}
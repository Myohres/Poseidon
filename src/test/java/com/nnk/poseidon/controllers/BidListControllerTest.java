package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.BidListEntity;
import com.nnk.poseidon.service.BidListService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BidListController.class)
@AutoConfigureMockMvc(addFilters = false)
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
       when(bidListService.findAll()).thenReturn(new ArrayList<BidListEntity>());
        mockMvc.perform(get("/bidList/list")).andExpect(status().isOk());
    }

    @Test
    void addBidForm() throws Exception {
       when((bidListService.add(new BidListEntity()))).thenReturn(new BidListEntity());
       mockMvc.perform(get("/bidList/add")).andExpect(status().isOk());
    }

    @Test
    void validate() {
    }

    @Test
    void showUpdateForm() throws Exception {
        when(bidListService.findById(1)).thenReturn(bidListEntity);
        mockMvc.perform(get("/bidList/update/1")).andExpect(status().isOk());
    }/*

   @Test
    void updateBidWithError() throws Exception {
        when(validate(bidListEntity)).thenReturn(new BindingResult());

        mockMvc.perform(post("/bidList/update/1")).andExpect(status().isOk());
   }

   @Test
    void updateBidWithNoError() throws Exception {
        when((bidListService.add(bidListEntity))).thenReturn(bidListEntity);

        mockMvc.perform(post("/bidList/update/1")).andExpect(status().isOk());
   }

    @Test
    void deleteBid() throws Exception {
        when(bidListService.findById(1)).thenReturn(bidListEntity);
        when(bidListService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/bidList/delete/1")).andExpect(status().isOk());
    }*/
}
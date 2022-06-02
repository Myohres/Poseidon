package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.RatingEntity;
import com.nnk.poseidon.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RatingController.class)
@AutoConfigureMockMvc(addFilters = false)
class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    RatingEntity ratingEntity;

    @BeforeEach
    void setUp() {
        ratingEntity = new RatingEntity();
        ratingEntity.setId(1);
        ratingEntity.setMoodysRating("moody");
        ratingEntity.setFitchRating("fitch");
        ratingEntity.setSandPRating("sandP");
        ratingEntity.setOrderNumber(1);
    }

    @Test
    void home() throws Exception {
        when(ratingService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/rating/list")).andExpect(status().isOk());
    }

    @Test
    void addRatingForm() throws Exception {
        when(ratingService.add(new RatingEntity())).thenReturn(new RatingEntity());
        mockMvc.perform(get("/rating/add")).andExpect(status().isOk());
    }

    @Test
    void validate() throws Exception {
    }

    @Test
    void validateWithError() throws Exception {
    }

    @Test
    void showUpdateForm() throws Exception {
        when(ratingService.findById(1)).thenReturn(ratingEntity);
        mockMvc.perform(get("/rating/update/1")).andExpect(status().isOk());
    }

    @Test
    void updateRating() throws Exception {
    }

    @Test
    void deleteRating() throws Exception {
    }
}
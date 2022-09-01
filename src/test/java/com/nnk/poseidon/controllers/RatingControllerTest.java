package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.RatingEntity;
import com.nnk.poseidon.service.RatingService;
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
class RatingControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    RatingEntity rating;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        rating = new RatingEntity();
        rating.setId(1);
        rating.setMoodysRating("moody");
        rating.setFitchRating("fitch");
        rating.setSandPRating("sandP");
        rating.setOrderNumber(1);
    }

    @Test
    void home() throws Exception {
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("list"));
    }

    @Test
    void addRatingForm() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    void validate() throws Exception {
        mockMvc.perform(post("/rating/validate")
                .param("moodysRating","mm")
                .param("sandPRating", "ss")
                .param("fitchRating", "ff")
                .param("orderNumber","1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/rating/list"));
    }

    @Test
    void validateWithError() throws Exception {
       /* mockMvc.perform(post("/rating/validate")
                .param("moodysRating","mm")
                .param("sandPRating", "ss")
                .param("fitchRating", "ff")
                .param("orderNumber","1"))
                .andExpect(view().name("rating/add"));*/
    }

    @Test
    void showUpdateForm() throws Exception {
        when(ratingService.findById(1)).thenReturn(rating);
        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("rating"))
                .andExpect(view().name("rating/update"));
    }

    @Test
    void showUpdateFormWithNoRatingFound() throws Exception {
        doThrow(new NoSuchElementException()).when(ratingService).findById(any());
        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/rating/list"));
    }

    @Test
    void updateRating() throws Exception {
        when((ratingService.add(rating))).thenReturn(rating);
        mockMvc.perform(post("/rating/update/1")
                .param("moodysRating","mm")
                .param("sandPRating", "ss")
                .param("fitchRating", "ff")
                .param("orderNumber","1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/rating/list"));
    }

    @Test
    void updateRatingWithError() throws Exception {
       /* mockMvc.perform(post("/rating/update/1")
                .param("moodysRating"," ")
                .param("sandPRating", "ss")
                .param("fitchRating", "ff")
                .param("orderNumber","1"))
                .andExpect(view().name("rating/update"));*/
    }

    @Test
    void deleteRating() throws Exception {
        when(ratingService.findById(any())).thenReturn(rating);
        when(ratingService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/rating/delete/13"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/rating/list"));
    }


    @Test
    void getAllRating() throws Exception {
        when(ratingService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/rating"))
                .andExpect(status().isOk());
    }


    @Test
    void getRatingById() throws Exception {
        when(ratingService.findById(any())).thenReturn(rating);
        mockMvc.perform(get("/rating/ratingId/1"))
                .andExpect(status().isOk());

    }

    @Test
    void getRatingByIdNotFound() throws Exception {
        when(ratingService.findById(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/rating/ratingId/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addRating() throws Exception {
        mockMvc.perform(post("/rating/add")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void addRatingError() throws Exception {
        when(ratingService.add(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/rating/add")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateRating2() throws Exception {
        when(ratingService.update(any())).thenReturn(rating);
        mockMvc.perform(put("/rating")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateRatingNotFound() throws Exception {
        when(ratingService.update(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(put("/rating")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteRating() throws Exception {
        mockMvc.perform(delete("/rating/ratingId/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteRatingNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(ratingService).delete(1);
        mockMvc.perform(delete("/rating/ratingId/1"))
                .andExpect(status().isNotFound());
    }
}
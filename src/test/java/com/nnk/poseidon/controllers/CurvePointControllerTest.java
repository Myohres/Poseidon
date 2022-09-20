package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.CurvePointEntity;
import com.nnk.poseidon.service.CurvePointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="admin",roles={"ADMIN"})
@ExtendWith(MockitoExtension.class)
class CurvePointControllerTest {

   /* @Autowired
    private WebApplicationContext context;*/

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointService curvePointService;

    CurvePointEntity curvePoint;

    @BeforeEach
    void setUp() {
      /*  mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();*/

        curvePoint = new CurvePointEntity();
        curvePoint.setCurveId(1);
        curvePoint.setTerm(1.0);
        curvePoint.setTerm(1.0);
    }

    @Test
    void home() throws Exception {
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("list"));
    }

    @Test
    void addCurvePointForm() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    void validateWithNoError() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                .param("curveId","1")
                .param("term", "1d")
                .param("value", "1d"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/curvePoint/list"));
    }

    @Test
    void validateWithError() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                .param("curveId","")
                .param("term", "s")
                .param("value", "1d"))
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    void showUpdateForm() throws Exception {
        when(curvePointService.findById(1)).thenReturn(curvePoint);
        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("curvePointEntity"))
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    void showUpdateFormWithNoCurveFound() throws Exception {
        doThrow(new NoSuchElementException()).when(curvePointService).findById(any());
        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/curvePoint/list"));
    }

    @Test
    void updateCurvePointWithNoError() throws Exception {
        when((curvePointService.add(curvePoint))).thenReturn(curvePoint);
        mockMvc.perform(post("/curvePoint/update/1")
                .param("curveId","1")
                .param("term", "2")
                .param("value", "1d"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/curvePoint/list"));
    }

    @Test
    void updateBidWithError() throws Exception {
        mockMvc.perform(post("/curvePoint/update/1")
                .param("curveId"," ")
                .param("term", "2")
                .param("value", "1d"))
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    void deleteCurvePoint() throws Exception {
        when(curvePointService.findById(any())).thenReturn(curvePoint);
        when(curvePointService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/curvePoint/delete/13"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/curvePoint/list"));
    }




    @Test
    void getAllCurvePoint() throws Exception {
        when(curvePointService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/curvePoint"))
                .andExpect(status().isOk());
    }


    @Test
    void getCurvePointById() throws Exception {
        when(curvePointService.findById(any())).thenReturn(curvePoint);
        mockMvc.perform(get("/curvePoint/curvePointId/1"))
                .andExpect(status().isOk());

    }

    @Test
    void getCurvePointByIdNotFound() throws Exception {
        when(curvePointService.findById(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/curvePoint/curvePointId/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addCurvePoint() throws Exception {
        mockMvc.perform(post("/curvePoint/add")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void addCurvePointError() throws Exception {
        when(curvePointService.add(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/curvePoint/add")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateCurvePoint2() throws Exception {
        when(curvePointService.update(any())).thenReturn(curvePoint);
        mockMvc.perform(put("/curvePoint")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateCurvePointNotFound() throws Exception {
        when(curvePointService.update(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(put("/curvePoint")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCurvePoint() throws Exception {
        mockMvc.perform(delete("/curvePoint/curvePointId/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCurvePointByIdTestcurvePointNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(curvePointService).delete(1);
        mockMvc.perform(delete("/curvePoint/curvePointId/1"))
                .andExpect(status().isNotFound());
    }


}
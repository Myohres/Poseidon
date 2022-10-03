package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.RuleNameEntity;
import com.nnk.poseidon.service.RuleNameService;
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

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

    RuleNameEntity ruleName;

    @BeforeEach
    void setUp() {
        ruleName = new RuleNameEntity();
        ruleName.setId(1);
        ruleName.setName("name");
        ruleName.setDescription("description");
        ruleName.setJson("json");
        ruleName.setTemplate("template");
        ruleName.setSqlStr("sqlStr");
        ruleName.setSqlPart("sqlPart");
    }

    @Test
    void home() throws Exception {
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("list"));
    }

    @Test
    void addRuleNameForm() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    void validate() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                .param("name","nn")
                .param("description", "dd")
                .param("json", "jj")
                .param("template", "tt")
                .param("sqlStr", "ss")
                .param("sqlPart", "sp"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/ruleName/list"));
    }

    @Test
    void validateWithError() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                .param("name","")
                .param("description", "dd")
                .param("json", "jj")
                .param("template", "tt")
                .param("sqlStr", "ss")
                .param("sqlPart", "sp"))
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    void showUpdateForm() throws Exception {
        when(ruleNameService.findById(1)).thenReturn(ruleName);
        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ruleNameEntity"))
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    void showUpdateFormWithNoRuleNameFound() throws Exception {
        doThrow(new NoSuchElementException()).when(ruleNameService).findById(any());
        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/ruleName/list"));
    }

    @Test
    void updateBidWithError() throws Exception {
        mockMvc.perform(post("/ruleName/update/1")
                .param("name"," ")
                .param("description", " ")
                .param("json", "")
                .param("template", "tt")
                .param("sqlStr", "ss")
                .param("sqlPart", "sp"))
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    void updateRuleNameWithNoError() throws Exception {
        when((ruleNameService.update(ruleName))).thenReturn(ruleName);
        mockMvc.perform(post("/ruleName/update/1")
                .param("name","name")
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "ss")
                .param("sqlPart", "sp"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/ruleName/list"));
    }

    @Test
    void deleteRuleName() throws Exception {
        when(ruleNameService.findById(any())).thenReturn(ruleName);
        when(ruleNameService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/ruleName/delete/13"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/ruleName/list"));
    }

    @Test
    void getAllruleName() throws Exception {
        when(ruleNameService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/ruleName"))
                .andExpect(status().isOk());
    }

    @Test
    void getRuleNameById() throws Exception {
        when(ruleNameService.findById(any())).thenReturn(ruleName);
        mockMvc.perform(get("/ruleName/ruleNameId/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getRuleNameByIdNotFound() throws Exception {
        when(ruleNameService.findById(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/ruleName/ruleNameId/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addRuleName() throws Exception {
        mockMvc.perform(post("/ruleName/add")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void addRuleNameError() throws Exception {
        when(ruleNameService.add(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/ruleName/add")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateRuleName() throws Exception {
        when(ruleNameService.update(any())).thenReturn(ruleName);
        mockMvc.perform(put("/ruleName")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateRuleNameNotFound() throws Exception {
        when(ruleNameService.update(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(put("/ruleName")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteRuleName() throws Exception {
        when(ruleNameService.findById(any())).thenReturn(ruleName);
        mockMvc.perform(delete("/ruleName/ruleNameId/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteRuleNameNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(ruleNameService).delete(1);
        mockMvc.perform(delete("/ruleName/ruleNameId/1"))
                .andExpect(status().isNotFound());
    }

}
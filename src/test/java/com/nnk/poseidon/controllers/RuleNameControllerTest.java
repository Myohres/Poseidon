package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.RuleNameEntity;
import com.nnk.poseidon.service.RuleNameService;
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

@WebMvcTest(controllers = RuleNameController.class)
@AutoConfigureMockMvc(addFilters = false)
class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

    RuleNameEntity ruleNameEntity;

    @BeforeEach
    void setUp() {
        ruleNameEntity = new RuleNameEntity();
        ruleNameEntity.setId(1);
        ruleNameEntity.setName("name");
        ruleNameEntity.setDescription("description");
        ruleNameEntity.setJson("json");
        ruleNameEntity.setTemplate("template");
        ruleNameEntity.setSqlStr("sqlStr");
        ruleNameEntity.setSqlPart("sqlPart");
    }

    @Test
    void home() throws Exception {
        when(ruleNameService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/ruleName/list")).andExpect(status().isOk());
    }

    @Test
    void addRuleNameForm() throws Exception {
        when(ruleNameService.add(new RuleNameEntity())).thenReturn(new RuleNameEntity());
        mockMvc.perform(get("/ruleName/add")).andExpect(status().isOk());
    }

    @Test
    void validate() throws Exception {
    }

    @Test
    void validateWithError() throws Exception {
    }

    @Test
    void showUpdateForm() throws Exception {
        when(ruleNameService.findById(1)).thenReturn(ruleNameEntity);
        mockMvc.perform(get("/ruleName/update/1")).andExpect(status().isOk());
    }

    @Test
    void updateRuleName() throws Exception {
    }

    @Test
    void deleteRuleName() throws Exception {
    }
}
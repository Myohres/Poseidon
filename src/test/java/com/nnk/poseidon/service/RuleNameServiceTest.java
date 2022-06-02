package com.nnk.poseidon.service;

import com.nnk.poseidon.domain.RatingEntity;
import com.nnk.poseidon.domain.RuleNameEntity;
import com.nnk.poseidon.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RuleNameServiceTest {

    RuleNameEntity ruleNameEntity;

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameService ruleNameService;

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
    void findAll() {
        when(ruleNameRepository.findAll()).thenReturn(new ArrayList<>());
        ruleNameService.findAll();
        verify(ruleNameRepository,times(1)).findAll();
    }

    @Test
    void findById() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleNameEntity));
        RuleNameEntity ruleNameEntity1 = ruleNameService.findById(1);
        assertEquals(1,ruleNameEntity1.getId());
    }

    @Test
    void findByIdNotFound() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> ruleNameService.findById(1));
    }

    @Test
    void add() {
        when(ruleNameRepository.save(any())).thenReturn(ruleNameEntity);
        ruleNameService.add(ruleNameEntity);
        verify(ruleNameRepository,times(1)).save(ruleNameEntity);
    }

    @Test
    void update() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleNameEntity));
        RuleNameEntity ruleNameEntity1 = new RuleNameEntity();
        ruleNameEntity1.setId(1);
        ruleNameEntity1.setName("name2");
        ruleNameEntity1.setDescription("description2");
        ruleNameEntity1.setJson("json2");
        ruleNameEntity1.setTemplate("template2");
        ruleNameEntity1.setSqlStr("sqlStr2");
        ruleNameEntity1.setSqlPart("sqlPart2");
        ruleNameService.update(ruleNameEntity1);
        assertEquals(1, ruleNameEntity.getId());
        assertEquals("name2", ruleNameEntity.getName());
        assertEquals("description2", ruleNameEntity.getDescription());
        assertEquals("json2", ruleNameEntity.getJson());
        assertEquals("template2", ruleNameEntity.getTemplate());
        assertEquals("sqlStr2", ruleNameEntity.getSqlStr());
        assertEquals("sqlPart2", ruleNameEntity.getSqlPart());
    }

    @Test
    void delete() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleNameEntity));
        ruleNameService.delete(1);
        verify(ruleNameRepository,times(1)).delete(ruleNameEntity);
    }
}
package com.nnk.poseidon.service;

import com.nnk.poseidon.domain.TradeEntity;
import com.nnk.poseidon.repositories.TradeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    TradeEntity tradeEntity;

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

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

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        when(tradeRepository.findAll()).thenReturn(new ArrayList<TradeEntity>());
        tradeService.findAll();
        verify(tradeRepository,times(1)).findAll();
    }

    @Test
    void findById() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(tradeEntity));
        TradeEntity tradeEntity = tradeService.findById(1);
        assertEquals(1,tradeEntity.getTradeId());
    }

    @Test
    void findByIdNotFound() {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> tradeService.findById(1));
    }

    @Test
    void add() {
        when(tradeRepository.save(any())).thenReturn(tradeEntity);
        tradeService.add(tradeEntity);
        verify(tradeRepository,times(1)).save(tradeEntity);
    }

    @Test
    void update() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(tradeEntity));

        TradeEntity tradeEntity1 = new TradeEntity();
        tradeEntity1.setTradeId(1);
        tradeEntity1.setAccount("account2");
        tradeEntity1.setType("type2");
        tradeEntity1.setBuyQuantity(102d);

        tradeService.update(tradeEntity1);

        assertEquals(1, tradeEntity.getTradeId());
        assertEquals("account2", tradeEntity.getAccount());
        assertEquals("type2", tradeEntity.getType());
        assertEquals(102, tradeEntity.getBuyQuantity());
        assertEquals(1.0,tradeEntity.getSellQuantity());
        assertEquals(1.0,tradeEntity.getBuyPrice());
        assertEquals(1.0,tradeEntity.getSellPrice());
        assertEquals("benchmark", tradeEntity.getBenchmark());
        assertEquals("security", tradeEntity.getSecurity());
        assertEquals("status", tradeEntity.getStatus());
        assertEquals("trader", tradeEntity.getTrader());
        assertEquals("book", tradeEntity.getBook());
        assertEquals("CreationName", tradeEntity.getCreationName());
        assertEquals(0, tradeEntity.getCreationDate().getTime());
        assertEquals("revisionName", tradeEntity.getRevisionName());
        assertEquals(0, tradeEntity.getRevisionDate().getTime());
        assertEquals("dealName", tradeEntity.getDealName());
        assertEquals("dealType", tradeEntity.getDealType());
        assertEquals("sourceListId", tradeEntity.getSourceListId());
        assertEquals("Side", tradeEntity.getSide());
    }

    @Test
    void delete() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(tradeEntity));
        tradeService.delete(1);
        verify(tradeRepository,times(1)).delete(tradeEntity);
    }
}


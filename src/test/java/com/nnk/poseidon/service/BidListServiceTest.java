package com.nnk.poseidon.service;

import com.nnk.poseidon.domain.BidListEntity;
import com.nnk.poseidon.repositories.BidListRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BidListServiceTest {

    BidListEntity bidList;

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListService bidListService;

    @BeforeEach
    void setUp() {
        bidList = new BidListEntity();
        bidList.setBidListId(1);
        bidList.setAccount("account");
        bidList.setType("type");
        bidList.setBidQuantity(10d);
        bidList.setAskQuantity(20d);
        bidList.setBid(1d);
        bidList.setAsk(2d);
        bidList.setBenchmark("benchmark");
        bidList.setBidListDate(new Timestamp(0));
        bidList.setCommentary("commentary");
        bidList.setSecurity("security");
        bidList.setStatus("status");
        bidList.setTrader("trader");
        bidList.setBook("book");
        bidList.setCreationName("CreationName");
        bidList.setCreationDate(new Timestamp(0));
        bidList.setRevisionName("revisionName");
        bidList.setRevisionDate(new Timestamp(0));
        bidList.setDealName("dealName");
        bidList.setDealType("dealType");
        bidList.setSourceListId("sourceListId");
        bidList.setSide("Side");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        when(bidListRepository.findAll()).thenReturn(new ArrayList<BidListEntity>());
        List<BidListEntity> bidListEntityList = bidListService.findAll();
        verify(bidListRepository,times(1)).findAll();
    }

    @Test
    void findById() {
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));
        BidListEntity bidListResult = bidListService.findById(1);
        assertEquals(1,bidListResult.getBidListId());
    }

    @Test
    void findByIdNotFound() {
        when(bidListRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> bidListService.findById(1));
    }

    @Test
    void add() {
        when(bidListRepository.save(any())).thenReturn(bidList);
        bidListService.add(bidList);
        verify(bidListRepository,times(1)).save(bidList);
    }

    @Test
    void update() {
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));

        BidListEntity bidList2 = new BidListEntity();
        bidList2.setBidListId(1);
        bidList2.setAccount("account2");
        bidList2.setType("type2");
        bidList2.setBidQuantity(102d);

        bidListService.update(bidList2);

        assertEquals(1,bidList.getBidListId());
        assertEquals("account2",bidList.getAccount());
        assertEquals("type2",bidList.getType());
        assertEquals(102,bidList.getBidQuantity());

        assertEquals(20,bidList.getAskQuantity());
        assertEquals(1,bidList.getBid());
        assertEquals(2,bidList.getAsk());
        assertEquals("benchmark",bidList.getBenchmark());
        assertEquals(0,bidList.getBidListDate().getTime());
        assertEquals("commentary",bidList.getCommentary());
        assertEquals("security",bidList.getSecurity());
        assertEquals("status",bidList.getStatus());
        assertEquals("trader",bidList.getTrader());
        assertEquals("book",bidList.getBook());
        assertEquals("CreationName",bidList.getCreationName());
        assertEquals(0,bidList.getCreationDate().getTime());
        assertEquals("revisionName",bidList.getRevisionName());
        assertEquals(0,bidList.getRevisionDate().getTime());
        assertEquals("dealName",bidList.getDealName());
        assertEquals("dealType",bidList.getDealType());
        assertEquals("sourceListId",bidList.getSourceListId());
        assertEquals("Side",bidList.getSide());
    }

    @Test
    void delete() {
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));
        bidListService.delete(1);
        verify(bidListRepository,times(1)).delete(bidList);
    }
}
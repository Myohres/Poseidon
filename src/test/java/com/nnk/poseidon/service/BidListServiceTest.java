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
        bidList.setBidQuantity(10);
        bidList.setAskQuantity(20);
        bidList.setBid(1);
        bidList.setAsk(2);
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
        bidList2.setBidQuantity(102);
        bidList2.setAskQuantity(202);
        bidList2.setBid(12);
        bidList2.setAsk(22);
        bidList2.setBenchmark("benchmark2");
        bidList2.setBidListDate(new Timestamp(2));
        bidList2.setCommentary("commentary2");
        bidList2.setSecurity("security2");
        bidList2.setStatus("status2");
        bidList2.setTrader("trader2");
        bidList2.setBook("book2");
        bidList2.setCreationName("CreationName2");
        bidList2.setCreationDate(new Timestamp(2));
        bidList2.setRevisionName("revisionName");
        bidList2.setRevisionDate(new Timestamp(2));
        bidList2.setDealName("dealName2");
        bidList2.setDealType("dealType2");
        bidList2.setSourceListId("sourceListId2");
        bidList2.setSide("Side2");

        bidListService.update(bidList2);

        assertEquals(1,bidList.getBidListId());
        assertEquals("account2",bidList.getAccount());
        assertEquals("type2",bidList.getType());
        assertEquals(102,bidList.getBidQuantity());
        assertEquals(202,bidList.getAskQuantity());
        assertEquals(12,bidList.getBid());
        assertEquals(22,bidList.getAsk());
        assertEquals("benchmark2",bidList.getBenchmark());
        assertEquals(2,bidList.getBidListDate().getTime());
        assertEquals("commentary2",bidList.getCommentary());
        assertEquals("security2",bidList.getSecurity());
        assertEquals("status2",bidList.getStatus());
        assertEquals("trader2",bidList.getTrader());
        assertEquals("book2",bidList.getBook());
        assertEquals("CreationName2",bidList2.getCreationName());
        assertEquals(2,bidList.getCreationDate().getTime());
        assertEquals("revisionName",bidList.getRevisionName());
        assertEquals(2,bidList.getRevisionDate().getTime());
        assertEquals("dealName2",bidList.getDealName());
        assertEquals("dealType2",bidList.getDealType());
        assertEquals("sourceListId2",bidList.getSourceListId());
        assertEquals("Side2",bidList.getSide());
    }

    @Test
    void delete() {
        bidListService.delete(1);
        verify(bidListRepository,times(1)).delete(bidList);
    }
}
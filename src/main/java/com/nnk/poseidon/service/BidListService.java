package com.nnk.poseidon.service;

import com.nnk.poseidon.domain.BidListEntity;
import com.nnk.poseidon.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BidListService {

    @Autowired
    private BidListRepository bidListRepository;

    public List<BidListEntity> findAll() {
        return bidListRepository.findAll();
    }

    public BidListEntity findById(Integer id) {
      return  bidListRepository.findById(id).orElseThrow(()
              -> new NoSuchElementException("No BidList found with id : " + id));
    }

    public BidListEntity add(BidListEntity bidListEntity) {
        return bidListRepository.save(bidListEntity);
    }

    public BidListEntity update(BidListEntity newBidListEntity) {
        BidListEntity oldBidListEntity = findById(newBidListEntity.getBidListId());
        oldBidListEntity.setAccount(newBidListEntity.getAccount());
        oldBidListEntity.setType(newBidListEntity.getType());
        oldBidListEntity.setBidQuantity(newBidListEntity.getBidQuantity());
        oldBidListEntity.setAskQuantity(newBidListEntity.getAskQuantity());
        oldBidListEntity.setBid(newBidListEntity.getBid());
        oldBidListEntity.setAsk(newBidListEntity.getAsk());
        oldBidListEntity.setBenchmark(newBidListEntity.getBenchmark());
        oldBidListEntity.setBidListDate(newBidListEntity.getBidListDate());
        oldBidListEntity.setCommentary(newBidListEntity.getCommentary());
        oldBidListEntity.setSecurity(newBidListEntity.getSecurity());
        oldBidListEntity.setStatus(newBidListEntity.getStatus());
        oldBidListEntity.setTrader(newBidListEntity.getTrader());
        oldBidListEntity.setBook(newBidListEntity.getBook());
        oldBidListEntity.setCreationName(newBidListEntity.getCreationName());
        oldBidListEntity.setCreationDate(newBidListEntity.getCreationDate());
        oldBidListEntity.setRevisionName(newBidListEntity.getRevisionName());
        oldBidListEntity.setRevisionDate(newBidListEntity.getRevisionDate());
        oldBidListEntity.setDealName(newBidListEntity.getDealName());
        oldBidListEntity.setDealType(newBidListEntity.getDealType());
        oldBidListEntity.setSourceListId(newBidListEntity.getSourceListId());
        oldBidListEntity.setSide(newBidListEntity.getSide());
        return bidListRepository.save(oldBidListEntity);
    }

    public void delete(Integer id) {
        BidListEntity bidListEntity = findById(id);
        bidListRepository.delete(bidListEntity);
    }
}

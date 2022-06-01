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
        return bidListRepository.save(oldBidListEntity);
    }

    public void delete(Integer id) {
        BidListEntity bidListEntity = findById(id);
        bidListRepository.delete(bidListEntity);
    }
}

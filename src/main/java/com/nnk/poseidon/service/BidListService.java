package com.nnk.poseidon.service;

import com.nnk.poseidon.domain.BidListEntity;
import com.nnk.poseidon.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BidListService {
    /**
     * BidList repository layer.
     */
    @Autowired
    private BidListRepository bidListRepository;

    /**
     * Get all BidList.
     * @return List of all BidList
     */
    public Iterable<BidListEntity> findAll() {
        return bidListRepository.findAll();
    }

    /**
     * Get bidList by id.
     * @param id bidList id
     * @return bidList entity
     */
    public BidListEntity findById(final Integer id) {

      return  bidListRepository.findById(id).orElseThrow(()
              -> new NoSuchElementException(
                      "No BidList found with id : " + id));
    }

    /**
     * add bidList.
     * @param bidListEntity bidList to add
     * @return bidList added
     */
    public BidListEntity add(final BidListEntity bidListEntity) {
        return bidListRepository.save(bidListEntity);
    }

    /**
     * Update bidList account, type and quantity.
     * @param newBidListEntity bidList to update
     * @return bidList updated
     */
    public BidListEntity update(final BidListEntity newBidListEntity) {
        BidListEntity oldBidListEntity =
                findById(newBidListEntity.getBidListId());
        oldBidListEntity.setAccount(newBidListEntity.getAccount());
        oldBidListEntity.setType(newBidListEntity.getType());
        oldBidListEntity.setBidQuantity(newBidListEntity.getBidQuantity());
        return bidListRepository.save(oldBidListEntity);
    }

    /**
     * Delete a bidList.
     * @param id bideList id
     */
    public void delete(final Integer id) {
        BidListEntity bidListEntity = findById(id);
        bidListRepository.delete(bidListEntity);
    }
}

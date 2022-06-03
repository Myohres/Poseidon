package com.nnk.poseidon.service;

import com.nnk.poseidon.domain.TradeEntity;
import com.nnk.poseidon.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TradeService {

    /**
     * trade repository layer.
     */
    @Autowired
    private TradeRepository tradeRepository;

    /**
     * Find all trade.
     * @return List of all trade
     */
    public List<TradeEntity> findAll() {
        return tradeRepository.findAll();
    }

    /**
     * Find trade by id.
     * @param id trade id
     * @return trade
     */
    public TradeEntity findById(final Integer id) {
        return  tradeRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("No Trade found with id : " + id));
    }

    /**
     * Save trade.
     * @param tradeEntity trade
     * @return trade saved
     */
    public TradeEntity add(final TradeEntity tradeEntity) {
        return tradeRepository.save(tradeEntity);
    }

    /**
     * Update trade Info.
     * @param newTradeEntity new trade infos
     * @return trade updated
     */
    public TradeEntity update(final TradeEntity newTradeEntity) {
        TradeEntity oldTradeEntity = findById(newTradeEntity.getTradeId());
        oldTradeEntity.setAccount(newTradeEntity.getAccount());
        oldTradeEntity.setType(newTradeEntity.getType());
        oldTradeEntity.setBuyQuantity(newTradeEntity.getBuyQuantity());
        return tradeRepository.save(oldTradeEntity);
    }

    /**
     * Delete trade by id.
     * @param id curveId
     */
    public void delete(final Integer id) {
        TradeEntity tradeEntity = findById(id);
        tradeRepository.delete(tradeEntity);
    }
}

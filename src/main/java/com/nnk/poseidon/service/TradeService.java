package com.nnk.poseidon.service;

import com.nnk.poseidon.domain.TradeEntity;
import com.nnk.poseidon.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    public List<TradeEntity> findAll() {
        return tradeRepository.findAll();
    }

    public TradeEntity findById(final Integer id) {
        return  tradeRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("No Trade found with id : " + id));
    }

    public TradeEntity add(final TradeEntity tradeEntity) {
        return tradeRepository.save(tradeEntity);
    }

    public TradeEntity update(final TradeEntity newTradeEntity) {
        TradeEntity oldTradeEntity = findById(newTradeEntity.getTradeId());
        oldTradeEntity.setAccount(newTradeEntity.getAccount());
        oldTradeEntity.setType(newTradeEntity.getType());
        oldTradeEntity.setBuyQuantity(newTradeEntity.getBuyQuantity());
        return tradeRepository.save(oldTradeEntity);
    }

    public void delete(final Integer id) {
        TradeEntity tradeEntity = findById(id);
        tradeRepository.delete(tradeEntity);
    }
}

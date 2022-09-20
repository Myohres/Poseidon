package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.TradeEntity;
import com.nnk.poseidon.service.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Controller
@CrossOrigin("http://localhost:4200")
public class TradeController {

    /**
     * Trade service layer.
     */
    @Autowired
    private TradeService tradeService;

    /**
     * Show all Trade.
     * @param model list Trade
     * @return Trade list page
     */
    @RequestMapping("/trade/list")
    public String home(final Model model) {
        model.addAttribute("list", tradeService.findAll());
        return "trade/list";
    }

    /**
     * Go to adding Trade page.
     * @param tradeEntity Trade to add
     * @return Trade adding page
     */
    @GetMapping("/trade/add")
    public String addUser(final TradeEntity tradeEntity) {
        return "trade/add";
    }

    /**
     * Validate fields Trade before adding.
     * @param tradeEntity Trade to add
     * @param result binding
     * @param model Trade to pass
     * @return Trade adding page
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid final TradeEntity tradeEntity,
                           final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            return "trade/add";
        }
        tradeService.add(tradeEntity);
        model.addAttribute("list", tradeService.findAll());
        return "redirect:/trade/list";
    }

    /**
     * Go to updating Trade page.
     * @param id Trade id
     * @param model Trade to pass
     * @return Trade updating page
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id,
                                 final Model model) {
        try {
            TradeEntity tradeEntity = tradeService.findById(id);
            model.addAttribute("tradeEntity", tradeEntity);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            model.addAttribute("list", tradeService.findAll());
            return "redirect:/trade/list";
        }
        return "trade/update";
    }

    /**
     * updating Trade page.
     * @param id Trade id
     * @param tradeEntity Trade to update
     * @param result binding
     * @param model list Trade
     * @return Trade list page
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") final Integer id,
                              @Valid final TradeEntity tradeEntity,
                             final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        }
        tradeEntity.setTradeId(id);
        tradeService.update(tradeEntity);
        model.addAttribute("list", tradeService.findAll());
        return "redirect:/trade/list";
    }

    /**
     * Deleting Trade.
     * @param id Trade id
     * @param model list Trade
     * @return Trade list page
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") final Integer id,
                              final Model model) {
        tradeService.delete(id);
        model.addAttribute("list", tradeService.findAll());
        return "redirect:/trade/list";
    }

    /**
     * Get all trade.
     * @return trade list
     */
    @GetMapping("/trade")
    public ResponseEntity<List<TradeEntity>> getAllTrade() {
        log.info("GET/trade");
        return ResponseEntity.ok(tradeService.findAll());
    }

    /**
     * Get trade by id.
     * @param tradeId id trade
     * @return trade
     */
    @GetMapping("/trade/tradeId/{id}")
    public ResponseEntity<TradeEntity> getTradeById(
            @PathVariable("id") final Integer tradeId) {
        log.info("GET/trade/tradeId/" + tradeId);
        try {
            return ResponseEntity.ok(tradeService.findById(tradeId));
        } catch (NoSuchElementException e) {
            log.error("GetTradeById error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add a trade.
     * @param tradeEntity trade
     * @return trade added
     */
    @PostMapping("/trade/add")
    public ResponseEntity<TradeEntity> addTrade(
            @RequestBody final TradeEntity tradeEntity) {
        log.info("POST/trade/add");
        try {
            return ResponseEntity.ok(tradeService.add(tradeEntity));
        } catch (NoSuchElementException e) {
            log.error("addTrade error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update trade.
     * @param tradeEntity trade new information
     * @return trade updated
     */
    @PutMapping("/trade")
    public ResponseEntity<TradeEntity> updatetrade(
            @RequestBody final TradeEntity tradeEntity) {
        log.info("PUT/trade/tradeId/ " + tradeEntity.getTradeId());
        try {
            return ResponseEntity.ok(tradeService.update(tradeEntity));
        } catch (NoSuchElementException e) {
            log.error("UpdateTrade error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a trade by his id.
     * @param tradeId id trade
     * @return trade deleted
     */
    @DeleteMapping("/trade/tradeId/{id}")
    public ResponseEntity<?> deleteTrade(
            @PathVariable("id") final Integer tradeId) {
        log.info("DEL/trade/tradeId/" + tradeId);
        try {
            tradeService.delete(tradeId);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            log.error("DeleteTrade error " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}

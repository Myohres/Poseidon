package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.TradeEntity;
import com.nnk.poseidon.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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
     * @param trade Trade to add
     * @param model Trade to pass
     * @return Trade adding page
     */
    @GetMapping("/trade/add")
    public String addUser(final TradeEntity trade, final Model model) {

        model.addAttribute("trade", trade);
        return "trade/add";
    }

    /**
     * Validate fields Trade before adding.
     * @param trade Trade to add
     * @param result binding
     * @param model Trade to pass
     * @return Trade adding page
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid final TradeEntity trade,
                           final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            model.addAttribute("trade", trade);
            //TODO display errors
            return "trade/add";
        }
        tradeService.add(trade);
        model.addAttribute("list", tradeService.findAll());
        return "trade/add";
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
        TradeEntity tradeEntity = tradeService.findById(id);
        model.addAttribute("trade", tradeEntity);
        return "trade/update";
    }

    /**
     * updating Trade page.
     * @param id Trade id
     * @param trade Trade to update
     * @param result binding
     * @param model list Trade
     * @return Trade list page
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") final Integer id,
                              @Valid final TradeEntity trade,
                             final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            return "redirect:/trade/list";
        }
        trade.setTradeId(id);
        tradeService.update(trade);
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
}

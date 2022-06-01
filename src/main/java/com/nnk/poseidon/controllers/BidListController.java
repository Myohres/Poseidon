package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.BidListEntity;
import com.nnk.poseidon.repositories.BidListRepository;
import com.nnk.poseidon.service.BidListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@CrossOrigin("http://localhost:4200")
public class BidListController {

    @Autowired
    BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
       List<BidListEntity> list = bidListService.findAll();
        model.addAttribute("liste", list);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidListEntity bid, Model model) {
        model.addAttribute("bidList", bid);
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidListEntity bid, BindingResult result, Model model) {
        if (result.hasErrors()) {
           model.addAttribute("bidList", bid);
            //TODO display errors
            return "bidList/add";

        }
        bidListService.add(bid);
        model.addAttribute("liste", bidListService.findAll());
        return "redirect:/bidList/list";

    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidListEntity bidListEntity = bidListService.findById(id);
        model.addAttribute("bidList", bidListEntity);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListEntity bidList,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/update";
        }
        bidList.setBidListId(id);
        bidListService.add(bidList);
        model.addAttribute("liste", bidListService.findAll());
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.delete(id);
        model.addAttribute("liste", bidListService.findAll());
        return "redirect:/bidList/list";
    }

}

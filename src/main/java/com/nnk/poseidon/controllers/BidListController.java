package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.BidListEntity;
import com.nnk.poseidon.service.BidListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.NoSuchElementException;


@Slf4j
@Controller
@CrossOrigin("http://localhost:4200")
public class BidListController {
    /**
     * BidList service layer.
     */
    @Autowired
    private BidListService bidListService;

    /**
     * bidList list home page.
     * @param model all bidList in DB
     * @return bidList list home page url
     */
    @RequestMapping("/bidList/list")
    public String home(final Model model) {
        model.addAttribute("List", bidListService.findAll());
        return "bidList/list";
    }

    /**
     * Adding bidList page.
     * @param bidListEntity BidListEntity to add
     * @return BidList add form page url
     */
    @GetMapping("/bidList/add")
    public String addBidForm(final BidListEntity bidListEntity) {
        return "bidList/add";
    }

    /**
     * Validating champs bidList adding page.
     * @param bidListEntity BidListEntity to add
     * @param result validate detection error
     * @param model bidList list home page
     * @return if error return add page with error message
     * else add bidList and redirect to home page BidList
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid final BidListEntity bidListEntity,
                           final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            return "bidList/add";
        }
        bidListService.add(bidListEntity);
        model.addAttribute("List", bidListService.findAll());
        return "redirect:/bidList/list";
    }

    /**
     * Update bidList form page.
     * @param id bidList to update
     * @param model bidList list home page
     * @return If bidList not found redirect to home page BidList
     * else to validating page updating bidList
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id,
                                 final Model model) {
        try {
            BidListEntity bidListEntity = bidListService.findById(id);
            model.addAttribute("bidListEntity", bidListEntity);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            model.addAttribute("List", bidListService.findAll());
            return "redirect:/bidList/list";
        }
        return "bidList/update";
    }

    /**
     * Updating champs bidList adding page.
     * @param id bidList to update id
     * @param bidList BidListEntity to update
     * @param result validate detection error
     * @param model bidList list home page
     * @return if error return update page with error message
     *      * else update bidList and redirect to home page BidList
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") final Integer id,
                            @Valid final BidListEntity bidList,
                            final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            return "bidList/update";
        }
        bidList.setBidListId(id);
        bidListService.update(bidList);
        model.addAttribute("list", bidListService.findAll());
        return "redirect:/bidList/list";
    }

    /**
     * Delete bidList.
     * @param id bidList to delete id
     * @param model bidList list home page
     * @return home page bidList
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") final Integer id,
                            final Model model) {
        bidListService.delete(id);
        model.addAttribute("list", bidListService.findAll());
        return "redirect:/bidList/list";
    }

    /**
     * Get all bidList.
     * @return bidList list
     */

    @GetMapping("/bidList")
    public ResponseEntity<Iterable<BidListEntity>> getAllBidList() {
        log.info("GET/bidList");
        return ResponseEntity.ok(bidListService.findAll());
    }

    /**
     * Get bidList by id.
     * @param bidListId id bidlist
     * @return bidlist
     */
    @GetMapping("/bidList/bidListId/{id}")
    public ResponseEntity<BidListEntity> getBidById(
            @PathVariable("id") final Integer bidListId) {
        log.info("GET/bidList/bidListId/" + bidListId);
        try {
            return ResponseEntity.ok(bidListService.findById(bidListId));
        } catch (NoSuchElementException e) {
            log.error("GetBidListById error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add a bidList.
     * @param bidListEntity bidList
     * @return bidList added
     */
    @PostMapping("/bidList/add")
    public ResponseEntity<BidListEntity> addBidList(
            @RequestBody final BidListEntity bidListEntity) {
        log.info("POST/bidList/add");
        try {
            return ResponseEntity.ok(bidListService.add(bidListEntity));
        } catch (NoSuchElementException e) {
            log.error("addBidList error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update bidList.
     * @param bidListEntity bidList new information
     * @return bidList updated
     */
    @PutMapping("/bidList")
    public ResponseEntity<BidListEntity> updateBidList(
            @RequestBody final BidListEntity bidListEntity) {
        log.info("PUT/bidList/bidListId/ " + bidListEntity.getBidListId());
        try {
            return ResponseEntity.ok(bidListService.update(bidListEntity));
        } catch (NoSuchElementException e) {
            log.error("UpdateBidList error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a bidList by his id.
     * @param bidListId id bidlist
     * @return bidList deleted
     */
    @DeleteMapping("/bidList/bidListId/{id}")
    public ResponseEntity<?> deleteBidList(
            @PathVariable("id") final Integer bidListId) {
        log.info("DEL/bidList/bidListId/" + bidListId);
        try {
            bidListService.delete(bidListId);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            log.error("DeleteBidList error " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}


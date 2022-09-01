package com.nnk.poseidon.controllers;

import com.nnk.poseidon.domain.RatingEntity;
import com.nnk.poseidon.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Controller
@CrossOrigin("http://localhost:4200")
public class RatingController {

    /**
     * Ratting Service layer.
     */
    @Autowired
    private RatingService ratingService;

    /**
     * Get rating list page.
     * @param model rating list to pass
     * @return rating list page
     */
    @RequestMapping("/rating/list")
    public String home(final Model model) {
        model.addAttribute("list", ratingService.findAll());
        return "rating/list";
    }

    /**
     * Get adding ratting page.
     * @param ratingEntity ratting to add
     * @return adding page
     */
    @GetMapping("/rating/add")
    public String addRatingForm(final RatingEntity ratingEntity) {
        return "rating/add";
    }

    /**
     * Validate ratting for adding.
     * @param rating ratting to add
     * @param result binding
     * @param model ratting list
     * @return rating list page
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid final RatingEntity rating,
                           final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            return "rating/add";
        }
        ratingService.add(rating);
        model.addAttribute("list", ratingService.findAll());
        return "redirect:/rating/list";
    }

    /**
     * Get updating rating page.
     * @param id rating id
     * @param model rating to pass
     * @return rating update page
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id,
                                 final Model model) {
        try {
            RatingEntity ratingEntity = ratingService.findById(id);
            model.addAttribute("rating", ratingEntity);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            model.addAttribute("list", ratingService.findAll());
            return "redirect:/rating/list";
        }
        return "rating/update";
    }

    /**
     * Update ratting.
     * @param id ratting id
     * @param rating ratting to update
     * @param result binding
     * @param model binding to pass
     * @return ratting list page if ok else ratting update page
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") final Integer id,
                               @Valid final RatingEntity rating,
                              final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            return "rating/update";
        }
        rating.setId(id);
        ratingService.update(rating);
        model.addAttribute("list", ratingService.findAll());
        return "redirect:/rating/list";
    }

    /**
     * Delete ratting.
     * @param id ratting id
     * @param model ratting list
     * @return ratting list page
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") final Integer id,
                               final Model model) {
        ratingService.delete(id);
        model.addAttribute("list", ratingService.findAll());
        return "redirect:/rating/list";
    }

    /**
     * Get all rating.
     * @return rating list
     */
    @GetMapping("/rating")
    public ResponseEntity<List<RatingEntity>> getAllRating() {
        log.info("GET/rating");
        return ResponseEntity.ok(ratingService.findAll());
    }

    /**
     * Get rating by id.
     * @param ratingId id rating
     * @return rating
     */
    @GetMapping("/rating/ratingId/{id}")
    public ResponseEntity<RatingEntity> getRatingById(@PathVariable("id") final Integer ratingId) {
        log.info("GET/rating/ratingId/" + ratingId);
        try {
            return ResponseEntity.ok(ratingService.findById(ratingId));
        } catch (NoSuchElementException e) {
            log.error("GetRatingById error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add a rating.
     * @param ratingEntity rating
     * @return rating added
     */
    @PostMapping("/rating/add")
    public ResponseEntity<RatingEntity> addRating(@RequestBody final RatingEntity ratingEntity) {
        log.info("POST/rating/add");
        try {
            return ResponseEntity.ok(ratingService.add(ratingEntity));
        } catch (NoSuchElementException e) {
            log.error("addRating error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update rating.
     * @param ratingEntity rating new information
     * @return rating updated
     */
    @PutMapping("/rating")
    public ResponseEntity<RatingEntity> updateRating(
            @RequestBody RatingEntity ratingEntity) {
        log.info("PUT/rating/ratingId/ " + ratingEntity.getId());
        try {
            return ResponseEntity.ok(ratingService.update(ratingEntity));
        } catch (NoSuchElementException e) {
            log.error("UpdateRating error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a rating by his id.
     * @param ratingId id rating
     * @return rating deleted
     */
    @DeleteMapping("/rating/ratingId/{id}")
    public ResponseEntity<?> deleteRating(@PathVariable("id") final Integer ratingId) {
        log.info("DEL/rating/ratingId/" + ratingId);
        try {
            ratingService.delete(ratingId);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            log.error("DeleteRating error " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
